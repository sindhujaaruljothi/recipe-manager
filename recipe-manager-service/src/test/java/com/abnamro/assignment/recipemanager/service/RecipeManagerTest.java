package com.abnamro.assignment.recipemanager.service;

import com.abnamro.assignment.model.GetRecipeDetail;
import com.abnamro.assignment.recipemanager.exception.UnAuthorizedException;
import com.abnamro.assignment.recipemanager.model.Ingredient;
import com.abnamro.assignment.recipemanager.model.Recipe;
import com.abnamro.assignment.recipemanager.model.ScaleUnit;
import com.abnamro.assignment.recipemanager.repository.IngredientRepository;
import com.abnamro.assignment.recipemanager.repository.RecipeRepository;
import com.abnamro.assignment.recipemanager.repository.UnitRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.abnamro.assignment.recipemanager.TestDataLoader.RECIPE_NAME;
import static com.abnamro.assignment.recipemanager.TestDataLoader.getIngredientModel;
import static com.abnamro.assignment.recipemanager.TestDataLoader.getRecipeDetails;
import static com.abnamro.assignment.recipemanager.TestDataLoader.getRecipeModel;
import static com.abnamro.assignment.recipemanager.TestDataLoader.getScaleUnitModel;
import static com.abnamro.assignment.recipemanager.TestDataLoader.getUpdateRecipeDetails;
import static com.abnamro.assignment.recipemanager.TestDataLoader.setPrincipal;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeManagerTest {
    @InjectMocks
    private RecipeManager recipeManager;
    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private IngredientRepository ingredientRepository;
    @Mock
    private UnitRepository unitRepository;

    @Test
    void test_postRecipe_whenCorrectNewRecipeIngredientUnitIsAdded_thenNewRecipeIsAddedSuccessfully() {
        setPrincipal();
        recipeManager.addRecipe(getRecipeDetails());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
        verify(ingredientRepository, times(1)).save(any(Ingredient.class));
        verify(ingredientRepository, times(1)).findAll();
        verify(unitRepository, times(1)).findAll();
        verify(unitRepository, times(1)).save(any(ScaleUnit.class));
    }

    @Test
    void test_postRecipe_addWhenCorrectNewRecipeIngredientIsAlreadyAdded_thenIngredientIsNotSavedAgain() {
        setPrincipal();
        when(ingredientRepository.findAll()).thenReturn(getIngredientModel());
        recipeManager.addRecipe(getRecipeDetails());

        verify(recipeRepository, times(1)).save(any(Recipe.class));
        verify(ingredientRepository, times(0)).save(any(Ingredient.class));
        verify(ingredientRepository, times(1)).findAll();
        verify(unitRepository, times(1)).findAll();
        verify(unitRepository, times(1)).save(any(ScaleUnit.class));
    }

    @Test
    void test_postRecipe_whenCorrectNewRecipeUnitIsAlreadyAdded_thenUnitIsNotSavedAgain() {
        setPrincipal();
        when(unitRepository.findAll()).thenReturn(getScaleUnitModel());
        recipeManager.addRecipe(getRecipeDetails());

        verify(recipeRepository, times(1)).save(any(Recipe.class));
        verify(ingredientRepository, times(1)).save(any(Ingredient.class));
        verify(ingredientRepository, times(1)).findAll();
        verify(unitRepository, times(1)).findAll();
        verify(unitRepository, times(0)).save(any(ScaleUnit.class));
    }

    @Test
    void test_getRecipe_whenRecipeNameIsNotGiven_RetrievesAllRecipe() {
        when(recipeRepository.findAll()).thenReturn(getRecipeModel());
        List<GetRecipeDetail> recipeDetails = recipeManager.getRecipes(null);
        Assertions.assertEquals(1, recipeDetails.size());
        verify(recipeRepository, times(1)).findAll();
        verify(recipeRepository, times(0)).findByRecipeNameContaining(any());

    }

    @Test
    void test_getRecipe_whenRecipeNameIsGiven_RetrievesSpecificRecipe() {
        when(recipeRepository.findByRecipeNameContaining(RECIPE_NAME)).thenReturn(getRecipeModel());
        recipeManager.getRecipes(RECIPE_NAME);
        verify(recipeRepository, times(0)).findAll();
        verify(recipeRepository, times(1)).findByRecipeNameContaining(any());

    }

    @Test
    void test_deleteRecipe_whenUserTriesToDeleteAnotherUsersRecipe_thenUnAuthorizedErrorShouldBeThrown() {
        setPrincipal();
        when(recipeRepository.deleteByRecipeIdAndUserProfile(any(), any())).thenReturn(0);
        Assertions.assertThrows(UnAuthorizedException.class, () -> recipeManager.deleteRecipe(RECIPE_NAME));
        verify(recipeRepository, times(1)).deleteByRecipeIdAndUserProfile(any(), any());
    }

    @Test
    void test_deleteRecipe_whenUserTriesToDeleteOwnRecipe_thenRecipeShouldBeDeleted() {
        setPrincipal();
        when(recipeRepository.deleteByRecipeIdAndUserProfile(any(), any())).thenReturn(-1);
        recipeManager.deleteRecipe(RECIPE_NAME);
        verify(recipeRepository, times(1)).deleteByRecipeIdAndUserProfile(any(), any());
    }

    @Test
    void test_updateRecipe_whenUserTriesToUpdateOtherRecipe_thenUnAuthorizedErrorShouldBeThrown() {
        setPrincipal();
        when(recipeRepository.findByRecipeIdAndUserProfile(any(), any())).thenReturn(Optional.empty());
        Assertions.assertThrows(UnAuthorizedException.class, () -> recipeManager.updateRecipe(getUpdateRecipeDetails()));
        verify(recipeRepository, times(1)).findByRecipeIdAndUserProfile(any(), any());
        verify(recipeRepository, times(0)).save(any());
    }

    @Test
    void test_updateRecipe_whenUserTriesToUpdateOwnRecipe_thenUpdateShouldbeSuccessful() {
        setPrincipal();
        when(recipeRepository.findByRecipeIdAndUserProfile(any(), any())).thenReturn(Optional.of(getRecipeModel().get(0)));
        recipeManager.updateRecipe(getUpdateRecipeDetails());
        verify(recipeRepository, times(1)).findByRecipeIdAndUserProfile(any(), any());
        verify(recipeRepository, times(1)).save(any());
    }


}