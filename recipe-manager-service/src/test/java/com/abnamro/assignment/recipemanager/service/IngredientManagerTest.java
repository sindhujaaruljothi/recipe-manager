package com.abnamro.assignment.recipemanager.service;

import com.abnamro.assignment.recipemanager.TestDataLoader;
import com.abnamro.assignment.recipemanager.repository.IngredientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientManagerTest {
    @InjectMocks
    private IngredientManager ingredientManager;
    @Mock
    private IngredientRepository ingredientRepository;

    @Test
    void test_getIngredients_whenGetAllIngredients_thenRetrieveAllIngredients() {
        when(ingredientRepository.findAll()).thenReturn(TestDataLoader.getIngredientModel());
        Assertions.assertEquals(ingredientManager.getAllIngredients().get(0).getIngredientName(), TestDataLoader.INGREDIENT_NAME);
        verify(ingredientRepository).findAll();
    }

    @Test
    void test_getIngredients_whenNoValueInIngredientRepo_thenReturnEmptyList() {
        when(ingredientRepository.findAll()).thenReturn(Collections.emptyList());
        Assertions.assertEquals(ingredientManager.getAllIngredients().size(), 0);
        verify(ingredientRepository).findAll();
    }


}