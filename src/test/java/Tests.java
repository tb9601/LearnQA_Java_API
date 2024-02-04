import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.Assert;
import org.junit.Test;

import javax.swing.plaf.synth.SynthOptionPaneUI;

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
                .statusCode(301);
    }

    @Test
    public void longRedirectV2() {
        String url = "https://playground.learnqa.ru/api/long_redirect";
        while (given().redirects().follow(false).get(url).statusCode() != 200) {
            String tempUrl = given().redirects().follow(false).get(url).getHeader("Location");
            System.out.println("redirect to: " + tempUrl);
            url = tempUrl;
        }
    }

    @Test
    public void longTimeJobTest() {
        String url = "https://playground.learnqa.ru/ajax/api/longtime_job";
        String token = RestAssured.get(url).jsonPath().getString("token");
        long seconds = Long.parseLong(RestAssured.get(url).jsonPath().getString("seconds"));

        JsonPath jsonResult = given().queryParam("token", token).get(url).jsonPath();

        Assert.assertEquals("Job is NOT ready", jsonResult.getString("status"));

        try {
            System.out.println("Wait " + seconds + " seconds");
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        jsonResult = given().queryParam("token", token).get(url).jsonPath();

        Assert.assertEquals("Job is ready", jsonResult.getString("status"));
    }

    @Test
    public void stringLengthTest() {
        String var = "sgfdhshdfgsdfgd";
        Assert.assertTrue(var.length() > 15);
    }
}
