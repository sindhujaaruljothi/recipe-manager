package com.abnamro.assignment.recipemanager.repository;

import com.abnamro.assignment.recipemanager.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, String> {
    Ingredient findByIngredientName(String ingredientName);

    Ingredient findByIngredientNameContaining(String ingredientName);

}
