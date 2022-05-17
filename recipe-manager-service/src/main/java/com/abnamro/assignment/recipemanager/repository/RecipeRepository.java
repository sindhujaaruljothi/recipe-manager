package com.abnamro.assignment.recipemanager.repository;

import com.abnamro.assignment.recipemanager.model.Recipe;
import com.abnamro.assignment.recipemanager.model.UserProfile;
import com.abnamro.assignment.recipemanager.security.CustomUserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe,String> {
    List<Recipe> findByRecipeNameContaining(String recipeName);

    Integer deleteByRecipeNameAndUserProfile(String recipeName, UserProfile userProfile);

    Optional<Recipe> findByRecipeIdAndUserProfile(String recipeId, UserProfile userProfile);
}
