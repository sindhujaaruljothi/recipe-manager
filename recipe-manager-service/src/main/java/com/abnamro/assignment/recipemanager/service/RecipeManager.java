package com.abnamro.assignment.recipemanager.service;

import com.abnamro.assignment.model.GetRecipeDetail;
import com.abnamro.assignment.model.Ingredients;
import com.abnamro.assignment.model.RecipeDetail;
import com.abnamro.assignment.model.UpdateRecipeDetail;
import com.abnamro.assignment.recipemanager.exception.UnAuthorizedException;
import com.abnamro.assignment.recipemanager.model.Ingredient;
import com.abnamro.assignment.recipemanager.model.Recipe;
import com.abnamro.assignment.recipemanager.model.ScaleUnit;
import com.abnamro.assignment.recipemanager.repository.IngredientRepository;
import com.abnamro.assignment.recipemanager.repository.RecipeRepository;
import com.abnamro.assignment.recipemanager.repository.UnitRepository;
import com.abnamro.assignment.recipemanager.security.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.abnamro.assignment.recipemanager.constant.RecipeManagerConstants.USER_NOT_AUTHORIZED;
import static com.abnamro.assignment.recipemanager.mapper.IngredientMapper.INGREDIENT_MAPPER;
import static com.abnamro.assignment.recipemanager.mapper.RecipeMapper.RECIPE_MAPPER;
import static com.abnamro.assignment.recipemanager.mapper.UnitMapper.UNIT_MAPPER;

@Service
@RequiredArgsConstructor
public class RecipeManager {
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final UnitRepository unitRepository;

    @Transactional
    public void addRecipe(RecipeDetail recipeDetail) {
        CustomUserDetail customUserDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String recipeId = getRecipeId();
        Recipe recipe = RECIPE_MAPPER.mapToPostRecipe(recipeDetail, recipeId);
        addIngredientAndScaleUnit(recipe, recipeDetail.getIngredients());
        recipe.setUserProfile(customUserDetail.getUserProfile());
        recipeRepository.save(recipe);
    }

    private Recipe addIngredientAndScaleUnit(Recipe recipe, List<Ingredients> ingredients) {
        List<String> ingredientNameList = getIngredientNameList();
        List<String> scaleUnits = getScaleUnitsList();
        for (Ingredients ingredientDetail : ingredients) {
            Ingredient ingredient = INGREDIENT_MAPPER.mapToIngredient(ingredientDetail.getIngredientDetail());
            ScaleUnit scaleUnit = UNIT_MAPPER.mapToScaleUnit(ingredientDetail.getUnit());
            if (!ingredientNameList.contains(ingredientDetail.getIngredientDetail().getIngredientName())) {
                ingredientRepository.save(ingredient);
            }
            if (!scaleUnits.contains(ingredientDetail.getUnit().getUnitValue())) {
                unitRepository.save(scaleUnit);
            }
            recipe.addIngredient(ingredient, ingredientDetail.getQuantity(), UNIT_MAPPER.mapToScaleUnit(ingredientDetail.getUnit()));
        }
        return recipe;
    }

    private List<String> getScaleUnitsList() {
        return unitRepository.findAll().parallelStream().map(ScaleUnit::getUnitValue).collect(Collectors.toList());
    }

    private List<String> getIngredientNameList() {
        return ingredientRepository.findAll()
                .parallelStream().map(Ingredient::getIngredientName).collect(Collectors.toList());
    }

    private String getRecipeId() {
        return UUID.randomUUID().toString();
    }


    public List<GetRecipeDetail> getRecipes(String recipeName) {
        List<Recipe> recipes;
        if (Objects.isNull(recipeName)) {
            recipes = recipeRepository.findAll();
        } else {
            recipes = recipeRepository.findByRecipeNameContaining(recipeName);
        }
        return RECIPE_MAPPER.mapToRecipeDetails(recipes);
    }

    @Transactional
    public void deleteRecipe(String recipeName) {
        CustomUserDetail userProfile = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Integer status = recipeRepository.deleteByRecipeNameAndUserProfile(recipeName, userProfile.getUserProfile());
        if(status == 0){
            throw new UnAuthorizedException(USER_NOT_AUTHORIZED);
        }
    }

    public void updateRecipe(UpdateRecipeDetail recipeDetails) {
        CustomUserDetail userProfile = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Recipe> optionalRecipe = recipeRepository.findByRecipeIdAndUserProfile(recipeDetails.getRecipeId(),userProfile.getUserProfile());
        if(!optionalRecipe.isPresent()){
            throw new UnAuthorizedException(USER_NOT_AUTHORIZED);
        }
        Recipe recipe = RECIPE_MAPPER.mapToPatchRecipe(recipeDetails);
        addIngredientAndScaleUnit(recipe,recipeDetails.getIngredients());
        recipe.setUserProfile(userProfile.getUserProfile());
        recipe.setCreateDatetime(optionalRecipe.get().getCreateDatetime());
        recipeRepository.save(recipe);

    }
}
