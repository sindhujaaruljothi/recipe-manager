package com.abnamro.assignment.recipemanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "recipe_ingrediant_quantity")
public class RecipeIngredientQuantity {
    @EmbeddedId
    private RecipeIngredientKey id = new RecipeIngredientKey();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("recipeId")
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ingredientName")
    @JoinColumn(name = "ingredient_Name")
    private Ingredient ingredient;

    private Float quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_value", referencedColumnName = "unit_value")
    private ScaleUnit unit;

    public RecipeIngredientQuantity(Recipe recipe, Ingredient ingredient, Float quantity, ScaleUnit unit) {
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.unit = unit;
        this.id = new RecipeIngredientKey(recipe.getRecipeId(), ingredient.getIngredientName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        RecipeIngredientQuantity that = (RecipeIngredientQuantity) o;
        return Objects.equals(recipe, that.recipe) &&
                Objects.equals(ingredient, that.ingredient);
    }


}
