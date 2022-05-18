package com.abnamro.assignment.recipemanager.controller;

import com.abnamro.assignment.recipemanager.RecipeManagerApplication;
import com.abnamro.assignment.recipemanager.TestDataLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.LinkedHashMap;
import java.util.List;

import static com.abnamro.assignment.recipemanager.TestDataLoader.JWT_TOKEN;
import static com.abnamro.assignment.recipemanager.TestDataLoader.RECIPE_ID;
import static com.abnamro.assignment.recipemanager.TestDataLoader.RECIPE_ID_VLUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = RecipeManagerApplication.class)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ActiveProfiles("it")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.HSQLDB, replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RecipeControllerIT {
    private static final String RECIPE_ENDPOINT = "/recipe-management/client-api/v1/recipe";
    private static final ObjectMapper OBJECT_MAPPER = Jackson2ObjectMapperBuilder.json().build();
    @Autowired
    MockMvc mockMvc;


    @Test
    @Sql("/recipe.sql")
    void test_GetRecipes_WhenGetRecipeIsCalled_AllRecipesAreRetrieved() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(RECIPE_ENDPOINT)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        List<LinkedHashMap> recipeDetail = OBJECT_MAPPER.readValue(result, List.class);
        System.out.println(recipeDetail);
        assertThat(recipeDetail.size()).isEqualTo(2);
        assertThat(recipeDetail.get(0).get(RECIPE_ID)).isEqualTo(RECIPE_ID_VLUE);
    }

    @Test
    void test_AddRecipes_WhenAddRecipeIsCalledWithoutToken_thenUnAuthorizedExceptionMustBeThrown() throws Exception {
        mockMvc.perform(post(RECIPE_ENDPOINT)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(CONTENT_TYPE, APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(TestDataLoader.getRecipeDetail()))
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }

    @Test
    @Sql("/recipe.sql")
    void test_AddRecipes_WhenAddRecipeIsCalledWithCorrectToken_thenStatusIsCreated() throws Exception {
        mockMvc.perform(post(RECIPE_ENDPOINT)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(CONTENT_TYPE, APPLICATION_JSON)
                        .header(AUTHORIZATION, JWT_TOKEN)
                        .content(OBJECT_MAPPER.writeValueAsString(TestDataLoader.getRecipeDetail()))
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    @Sql("/recipe.sql")
    void test_UpdateRecipe_WhenUpdateRecipeIsCalledWithCorrectToken_thenStatusIsCreated() throws Exception {
        mockMvc.perform(put(RECIPE_ENDPOINT)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(CONTENT_TYPE, APPLICATION_JSON)
                        .header(AUTHORIZATION, JWT_TOKEN)
                        .content(OBJECT_MAPPER.writeValueAsString(TestDataLoader.getUpdateRecipeDetail()))
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @Sql("/recipe.sql")
    void test_UpdateRecipe_WhenUpdateRecipeIsCalledWithCorrectTokenWithWrongRecipeId_thenStatusIsUNAuthorized() throws Exception {
        mockMvc.perform(put(RECIPE_ENDPOINT)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(CONTENT_TYPE, APPLICATION_JSON)
                        .header(AUTHORIZATION, JWT_TOKEN)
                        .content(OBJECT_MAPPER.writeValueAsString(TestDataLoader.getUpdateRecipeDetail()))
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }

    @Test
    @Sql("/recipe.sql")
    void test_DeleteRecipe_WhenDeleteRecipeIsCalledWithCorrectTokenAndRecipeId_thenStatusIsOk() throws Exception {
        mockMvc.perform(delete(RECIPE_ENDPOINT)
                        .accept(MediaType.APPLICATION_JSON)
                        .param(RECIPE_ID, RECIPE_ID_VLUE)
                        .header(CONTENT_TYPE, APPLICATION_JSON)
                        .header(AUTHORIZATION, JWT_TOKEN)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @Sql("/recipe.sql")
    void test_DeleteRecipe_WhenDeleteRecipeIsCalledWithCorrectTokenAndWrongRecipeId_thenStatusIsUnAuthorized() throws Exception {
        mockMvc.perform(delete(RECIPE_ENDPOINT)
                        .accept(MediaType.APPLICATION_JSON)
                        .param(RECIPE_ID, "002")
                        .header(CONTENT_TYPE, APPLICATION_JSON)
                        .header(AUTHORIZATION, JWT_TOKEN)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }


}