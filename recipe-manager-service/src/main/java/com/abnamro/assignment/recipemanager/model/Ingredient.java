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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "ingredient")
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    @Id
    @Column(name = "ingredient_name")
    private String ingredientName;
    @OneToMany(
            mappedBy = "ingredient"
            , cascade = CascadeType.ALL
    )
    private Set<RecipeIngredientQuantity> quantitySet = new HashSet<>();

}
