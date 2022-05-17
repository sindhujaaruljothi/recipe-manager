package com.abnamro.assignment.recipemanager.mapper;

import com.abnamro.assignment.model.GetRecipeDetail;
import com.abnamro.assignment.model.IngredientDetail;
import com.abnamro.assignment.model.Ingredients;
import com.abnamro.assignment.model.RecipeDetail;
import com.abnamro.assignment.model.UnitDetail;
import com.abnamro.assignment.model.UpdateRecipeDetail;
import com.abnamro.assignment.recipemanager.model.Recipe;
import com.abnamro.assignment.recipemanager.model.RecipeIngredientQuantity;
import com.abnamro.assignment.recipemanager.model.ScaleUnit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RecipeMapper {
    RecipeMapper RECIPE_MAPPER = Mappers.getMapper(RecipeMapper.class);

    Recipe mapToPostRecipe(RecipeDetail RecipeDetail, String recipeId);

    Recipe mapToPatchRecipe(UpdateRecipeDetail getRecipeDetail);

    List<GetRecipeDetail> mapToRecipeDetails(List<Recipe> recipes);

    default GetRecipeDetail recipeToRecipeDetail(Recipe recipe) {
        if (recipe == null) {
            return null;
        }

        GetRecipeDetail recipeDetail = new GetRecipeDetail();
        recipeDetail.setRecipeId(recipe.getRecipeId());
        recipeDetail.setRecipeName(recipe.getRecipeName());
        recipeDetail.setDishType(recipe.getDishType());
        recipeDetail.setNoOfPeopleSuitable(recipe.getNoOfPeopleSuitable());
        recipeDetail.setCookingInstructions(recipe.getCookingInstructions());
        recipeDetail.setCreateDateTime(OffsetToStringDateTime(recipe.getCreateDatetime()));
        recipeDetail.setIngredients(getIngredients(recipe.getIngredientQuantitySet()));

        return recipeDetail;
    }

    default List<Ingredients> getIngredients(Set<RecipeIngredientQuantity> recipeIngredientQuantity){
        if ( recipeIngredientQuantity == null ) {
            return null;
        }

        List<Ingredients> list = new ArrayList<Ingredients>( recipeIngredientQuantity.size() );
        for ( RecipeIngredientQuantity recipeIngredientQuantity1 : recipeIngredientQuantity ) {
            list.add( recipeIngredientQuantityToIngredients( recipeIngredientQuantity1 ) );
        }

        return list;
    }

    default Ingredients recipeIngredientQuantityToIngredients(RecipeIngredientQuantity recipeIngredientQuantity){
        if ( recipeIngredientQuantity == null ) {
            return null;
        }

        Ingredients ingredients = new Ingredients();
        ingredients.setIngredientDetail(new IngredientDetail().ingredientName(recipeIngredientQuantity.getIngredient().getIngredientName()));
        ingredients.setQuantity( recipeIngredientQuantity.getQuantity() );
        ingredients.setUnit( scaleUnitToUnitDetail( recipeIngredientQuantity.getUnit() ) );

        return ingredients;

}

    default UnitDetail scaleUnitToUnitDetail(ScaleUnit scaleUnit) {
        if ( scaleUnit == null ) {
            return null;
        }

        UnitDetail unitDetail = new UnitDetail();

        unitDetail.setUnitValue( scaleUnit.getUnitValue() );

        return unitDetail;
    }

    default String OffsetToStringDateTime(OffsetDateTime createDatetime) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd‐MM‐yyyy HH:mm");
        return fmt.format(createDatetime);
    }
}
