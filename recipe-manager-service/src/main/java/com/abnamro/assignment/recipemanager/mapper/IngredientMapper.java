package com.abnamro.assignment.recipemanager.mapper;

import com.abnamro.assignment.model.IngredientDetail;
import com.abnamro.assignment.recipemanager.model.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IngredientMapper {
    IngredientMapper INGREDIENT_MAPPER = Mappers.getMapper(IngredientMapper.class);

    Ingredient mapToIngredient(IngredientDetail ingredientDetail);

    IngredientDetail mapToIngredientDetail(Ingredient ingredient);

    List<IngredientDetail> mapToIngredientDetails(List<Ingredient> ingredients);
}
