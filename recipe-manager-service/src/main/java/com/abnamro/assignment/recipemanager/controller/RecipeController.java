package com.abnamro.assignment.recipemanager.controller;

import com.abnamro.assignment.api.RecipeManagementApi;
import com.abnamro.assignment.model.GetRecipeDetail;
import com.abnamro.assignment.model.RecipeDetail;
import com.abnamro.assignment.model.UpdateRecipeDetail;
import com.abnamro.assignment.recipemanager.service.RecipeManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecipeController implements RecipeManagementApi {
    private final RecipeManager recipeManager;

    @Override
    public ResponseEntity<Void> addRecipes(RecipeDetail recipeDetail) {
        recipeManager.addRecipe(recipeDetail);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> deleteRecipe(String recipeId) {
        recipeManager.deleteRecipe(recipeId);
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @Override
    public ResponseEntity<List<GetRecipeDetail>> fetchRecipes(String recipeName) {
        return new ResponseEntity<>(recipeManager.getRecipes(recipeName), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateRecipe(UpdateRecipeDetail recipeDetails) {
        recipeManager.updateRecipe(recipeDetails);
        return null;
    }
}
