package org.example;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import net.javacrumbs.jsonunit.JsonAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import static net.javacrumbs.jsonunit.core.Option.IGNORING_ARRAY_ORDER;

public class SpoonaccularTest {
    private static final String API_KEY = "0970f5c615f14a2a91942df5a213e41c";
    static ResponseSpecification responseSpecification;
    static RequestSpecification requestSpecification;

    public String getResource(String name) throws Exception {
        String resource = getClass().getSimpleName() + "/" + name;
        InputStream inputStream = getClass().getResourceAsStream(resource);
        assert inputStream != null;
        byte[] bytes = inputStream.readAllBytes();
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public File getFile(String name) {
        String resource = getClass().getSimpleName() + "/" + name;
        String file = getClass().getResource(resource).getFile();
        return new File(file);
    }

    @BeforeAll
    static void beforeAll() {

        RestAssured.baseURI = "https://api.spoonacular.com/";

            responseSpecification = new ResponseSpecBuilder()
                    .log(LogDetail.BODY)
                    .log(LogDetail.ALL)
                    .expectStatusCode(200)
                    .expectStatusLine("HTTP/1.1 200 OK")
                    .expectContentType(ContentType.JSON)
                    .expectResponseTime(Matchers.lessThan(5000L))
                    .build();

            requestSpecification = new RequestSpecBuilder()
                    .addQueryParam("apiKey", API_KEY)
                    .log(LogDetail.ALL)
                    .build();

    }
}
