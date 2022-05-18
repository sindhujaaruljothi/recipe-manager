package com.abnamro.assignment.recipemanager;


import com.abnamro.assignment.model.IngredientDetail;
import com.abnamro.assignment.model.Ingredients;
import com.abnamro.assignment.model.RecipeDetail;
import com.abnamro.assignment.model.UnitDetail;
import com.abnamro.assignment.model.UpdateRecipeDetail;
import com.abnamro.assignment.recipemanager.model.Ingredient;
import com.abnamro.assignment.recipemanager.model.Recipe;
import com.abnamro.assignment.recipemanager.model.RecipeIngredientQuantity;
import com.abnamro.assignment.recipemanager.model.ScaleUnit;
import com.abnamro.assignment.recipemanager.model.UserProfile;
import com.abnamro.assignment.recipemanager.security.CustomUserDetail;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestDataLoader {
    public static final String RECIPE_NAME = "NonVegrecipeName";
    public static final String RECIPE_ID = "recipeId";
    public static final String RECIPE_ID_VLUE = "001";
    public static final String INGREDIENT_NAME = "ingredient1";
    public static final String UNIT_VALUE = "kg";
    public static final String COOKING_INSTRUCTIONS = "CookingInstructions";
    public static final String USER_ID = "UserId1";
    public static final String VALID_USER_ID = "emailid@domain.com";
    public static final String VALID_PASSWORD = "UserId@123";
    public static final String JWT_TOKEN = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlbWFpbGlkQGRvbWFpbi5jb20iLCJleHAiOjcyMDAwMTY1Mjg4OTI1NywiaWF0IjoxNjUyODg5MjU3fQ.d8_9JcTNEEUvh7Np3I3GN2lZfX4c-OeahqC0PKICcl2CHLm3L45LbkPgKOu9WJvzQUcNItQEfElvQ093Juortg";


    public static RecipeDetail getRecipeDetails() {
        return new RecipeDetail().recipeName(RECIPE_NAME)
                .noOfPeopleSuitable(10l)
                .cookingInstructions(COOKING_INSTRUCTIONS)
                .ingredients(getIngredients());
    }

    public static UpdateRecipeDetail getUpdateRecipeDetails() {
        return new UpdateRecipeDetail().recipeId(RECIPE_NAME).recipeName(RECIPE_NAME)
                .noOfPeopleSuitable(10l)
                .cookingInstructions(COOKING_INSTRUCTIONS)
                .ingredients(getIngredients());
    }

    public static List<Ingredients> getIngredients() {
        return List.of(new Ingredients().ingredientDetail(new IngredientDetail()
                .ingredientName(INGREDIENT_NAME)).quantity(10.0f).unit(new UnitDetail()
                .unitValue(UNIT_VALUE))
        );
    }

    public static List<Ingredient> getIngredientModel() {
        Ingredient ingredient = new Ingredient();
        ingredient.setIngredientName(INGREDIENT_NAME);
        return List.of(ingredient);

    }

    public static void setPrincipal() {
        SecurityContextHolder.setContext(new SecurityContextImpl(new TestingAuthenticationToken(new
                CustomUserDetail(new UserProfile(USER_ID, null)), null)));

    }

    public static List<ScaleUnit> getScaleUnitModel() {
        ScaleUnit scaleUnit = new ScaleUnit();
        scaleUnit.setUnitValue(UNIT_VALUE);
        return List.of(scaleUnit);

    }

    public static List<Recipe> getRecipeModel() {
        Recipe recipe = new Recipe();
        recipe.setRecipeId(RECIPE_NAME);
        recipe.setRecipeName(RECIPE_NAME);
        recipe.setCookingInstructions(COOKING_INSTRUCTIONS);
        recipe.setIngredientQuantitySet(getIngredientQuantitySet(recipe));
        recipe.setCreateDatetime(OffsetDateTime.now());
        return List.of(recipe);
    }

    private static Set<RecipeIngredientQuantity> getIngredientQuantitySet(Recipe recipe) {
        Set<RecipeIngredientQuantity> recipeIngredientQuantities = new HashSet<>();
        recipeIngredientQuantities.add(new RecipeIngredientQuantity(recipe, getIngredientModel().get(0), 10.0f, getScaleUnitModel().get(0)));
        return recipeIngredientQuantities;
    }

    public static RecipeDetail getRecipeDetail() {
        return new RecipeDetail().recipeName(RECIPE_NAME)
                .ingredients(getIngredients())
                .cookingInstructions(COOKING_INSTRUCTIONS)
                .noOfPeopleSuitable(10l)
                .isVegetarian(true);
    }

    public static UpdateRecipeDetail getUpdateRecipeDetail() {
        return new UpdateRecipeDetail().recipeName(RECIPE_NAME)
                .ingredients(getIngredients())
                .cookingInstructions(COOKING_INSTRUCTIONS)
                .noOfPeopleSuitable(10l)
                .isVegetarian(true)
                .recipeId("001");
    }


}
