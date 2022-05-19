package com.abnamro.assignment.recipemanager.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Table(name = "recipe")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@EqualsAndHashCode
public class Recipe {
    @Id
    @Column(name = "recipe_id")
    private String recipeId;
    @Column(name = "recipe_name")
    private String recipeName;
    @Column(name = "is_vegetarian", columnDefinition = "boolean default false")
    private Boolean isVegetarian;
    @Column(name = "no_of_people_suitable")
    private Long noOfPeopleSuitable;
    @OneToMany(
            mappedBy = "recipe",
            cascade = CascadeType.ALL
    )
    private Set<RecipeIngredientQuantity> ingredientQuantitySet = new HashSet<>();
    @Column(name = "cooking_instructions")
    private String cookingInstructions;
    @Column(name = "create_date_time")
    private OffsetDateTime createDatetime;
    @Column(name = "update_date_time")
    private OffsetDateTime updateDateTime;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserProfile userProfile;

    @PrePersist
    public void prePersist() {
        createDatetime = OffsetDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updateDateTime = OffsetDateTime.now();
    }

    public void addIngredient(Ingredient ingredient, Float quantity, ScaleUnit unit) {
        RecipeIngredientQuantity recipeIngredientQuantity = new RecipeIngredientQuantity(this, ingredient, quantity, unit);
        ingredientQuantitySet.add(recipeIngredientQuantity);
        ingredient.getQuantitySet().add(recipeIngredientQuantity);
    }

}
