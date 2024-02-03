import io.restassured.RestAssured;
import org.junit.Test;

public class Tests {

    @Test
    public void getTextTest() {
        System.out.println(RestAssured.get("https://playground.learnqa.ru/api/get_text").andReturn().asString());
    }
}
