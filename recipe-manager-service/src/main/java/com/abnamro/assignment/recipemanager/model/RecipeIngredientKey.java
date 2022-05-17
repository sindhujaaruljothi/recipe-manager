package com.abnamro.assignment.recipemanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecipeIngredientKey implements Serializable {
    @Column(name = "recipe_id")
    private String recipeId;

    @Column(name = "ingredient_name")
    private String ingredientName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        RecipeIngredientKey that = (RecipeIngredientKey) o;
        return Objects.equals(recipeId, that.recipeId) &&
                Objects.equals(ingredientName, that.ingredientName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeId, ingredientName);
    }

}
