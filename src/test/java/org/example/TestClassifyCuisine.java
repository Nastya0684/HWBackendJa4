package org.example;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TestClassifyCuisine extends SpoonaccularTest{

    @Test
    void postResponseLanguage() throws IOException {
        given()
                .spec(requestSpecification)
                .param("title", "рис карри")
                .param("ingredientList", "карри рис")
                .param("language", "ru")
                .expect()
                .body("confidence", is(0F))
                .when()
                .post("recipes/cuisine")
                .then()
                .spec(responseSpecification);
    }

    @Test
    void postDataTypeResponse() throws IOException {
        given()
                .spec(requestSpecification)
                .param("title", "curry rice")
                .param("ingredientList", "rice curry")
                .param("language", "en")
                .expect()
                .body("cuisines[0]",is("Indian"))
                .body("confidence", greaterThanOrEqualTo(0.9F))
                .when()
                .post("recipes/cuisine")
                .then()
                .spec(responseSpecification);
    }
}
