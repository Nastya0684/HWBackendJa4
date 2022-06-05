package org.example;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

public class TestImageClassification extends SpoonaccularTest {

    @Test
    void testCoffeeClassificationByFile() {
        given()
                .spec(requestSpecification)
                .multiPart(getFile("coffee.jpg"))
                .expect()
                .body("category", is("coffee"))
                .body("probability", greaterThan(0.7F))
                .when()
                .post("food/images/classify");
    }

    @Test
    void testCoffeeClassificationByURL(){
        given()
                .spec(requestSpecification)
                .param("imageUrl", "https://upload.wikimedia.org/wikipedia/commons/4/45/A_small_cup_of_coffee.JPG")
                .expect()
                .body("category", is("coffee"))
                .body("probability", greaterThan(0.7F))
                .when()
                .get("food/images/classify");
    }

}
