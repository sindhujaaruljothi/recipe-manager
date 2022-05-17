package com.abnamro.assignment.recipemanager.controller;

import com.abnamro.assignment.api.IngredientManagementApi;
import com.abnamro.assignment.model.IngredientDetail;
import com.abnamro.assignment.recipemanager.service.IngredientManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class IngredientController implements IngredientManagementApi {
    private final IngredientManager ingredientManager;

    @Override
    public ResponseEntity<IngredientDetail> addIngredient(IngredientDetail ingredientDetail) {
        log.info("add Ingredient initiated");
        return new ResponseEntity<>(ingredientManager.addIngredients(ingredientDetail), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<IngredientDetail>> fetchIngredients() {
        log.info("fetch ingredients initiated");
        return new ResponseEntity<>(ingredientManager.getAllIngredients(), HttpStatus.OK);
    }

}
