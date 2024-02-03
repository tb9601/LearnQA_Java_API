import io.restassured.RestAssured;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class Tests {

    @Test
    public void getTextTest() {
        System.out.println(RestAssured.get("https://playground.learnqa.ru/api/get_text").andReturn().asString());
    }

    @Test
    public void jsonTest() {
        System.out.println(RestAssured.get("https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath()
                .getString("messages[1]"));
    }

    @Test
    public void longRedirectTest() {
        given().
                redirects().follow(false)
                .when().get("https://playground.learnqa.ru/api/long_redirect")
                .then()
                .statusCode(302);
    }
}
