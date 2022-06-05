package org.example;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.*;

public class TestComplexSearch extends SpoonaccularTest{

    @Test
    void getRecipeWithQueryParameters() throws IOException {
        given()
                .spec(requestSpecification)
                .param("query", "pasta")
                .param("maxFat", "25")
                .param("number", 2)
                .expect()
                .body("offset", is(0))
                .body("totalResults", notNullValue())
                .when()
                .get("recipes/complexSearch")
                .then()
                .spec(responseSpecification);
    }

    @Test
    void getRecipesByNutrients() throws IOException {
        given()
                .spec(requestSpecification)
                .param("minCalories", "100")
                .param("maxCalories", "150")
                .param("number", 2)
                .expect()
                .body("size()", is(2))
                .body("[0].calories", greaterThanOrEqualTo(100))
                .body("[0].calories", lessThanOrEqualTo(150))
                .when()
                .get("recipes/findByNutrients")
                .then()
                .spec(responseSpecification);
    }

    @Test
    void getRandomRecipes()throws IOException {
        given()
                .spec(requestSpecification)
                .param("tags", "vegetarian,dessert")
                .param("number", 5)
                .expect()
                .body("recipes.size()", is(5))
                .body("recipes[0]", hasKey("id"))
                .when()
                .get("recipes/random?number=5&tags=vegetarian,dessert")
                .then()
                .spec(responseSpecification);
    }

    @Test
    void getGuessNutritionByDishName()throws IOException {
        given()
                .spec(requestSpecification)
                .param("title", "Spaghetti Aglio et Olio")
                .expect()
                .body("calories.value", lessThan(450F) )
                .when()
                .get("recipes/guessNutrition")
                .then()
                .spec(responseSpecification);
    }

    @Test
    void getIngredientSubstitutesByID()throws IOException {
        given()
                .spec(requestSpecification)
                .param("ingredientName", "butter")
                .expect()
                .body("ingredient", is("butter") )
                .body("substitutes.size()",greaterThan(0))
                .when()
                .get("/food/ingredients/1001/substitutes")
                .then()
                .spec(responseSpecification);
    }
}
