package com.abnamro.assignment.recipemanager.service;

import com.abnamro.assignment.model.IngredientDetail;
import com.abnamro.assignment.recipemanager.model.Ingredient;
import com.abnamro.assignment.recipemanager.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.abnamro.assignment.recipemanager.mapper.IngredientMapper.INGREDIENT_MAPPER;

@Service
@RequiredArgsConstructor
public class IngredientManager {
    private final IngredientRepository ingredientRepository;

    public IngredientDetail addIngredients(IngredientDetail ingredientDetails) {
        Ingredient ingredient = ingredientRepository.findByIngredientName(ingredientDetails.getIngredientName());
        if (Objects.isNull(ingredient)) {
            ingredient = ingredientRepository.save(INGREDIENT_MAPPER.mapToIngredient(ingredientDetails));
        }
        return INGREDIENT_MAPPER.mapToIngredientDetail(ingredient);
    }

    public List<IngredientDetail> getAllIngredients() {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        return INGREDIENT_MAPPER.mapToIngredientDetails(ingredients);
    }
}
