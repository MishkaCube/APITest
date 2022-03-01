import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class MainTest extends BaseTest {
    @Test
    public void getFirstEmail() {
        requestSpecification
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("data[0].first_name", equalTo("George"))
                .body("data[0].last_name", equalTo("Bluth"))
                .body("data[0].email", equalTo("george.bluth@reqres.in"));
    }

    @Test
    public void getSecondEmail() {
        Assert.assertEquals(true, findName("Michael"));
    }

    private boolean findName(String name) {
        int max_pages = findMaxPages();

        for (int page = 1; page < max_pages + 1; page++) {
            List data =  given()
                    .baseUri("https://reqres.in/api")
                    .basePath("/users")
                    .contentType(ContentType.JSON)
                    .param("page", page)
                    .when()
                    .get()
                    .then()
                    .statusCode(200).extract().jsonPath().get("data.first_name");
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).equals(name)) {
                    System.out.println("Совпадение");
                    return true;
                }
            }
        }
        return true;
    }

    private int findMaxPages() {
        Response response =
                requestSpecification
                        .when()
                        .get();
        JsonPath jp = new JsonPath(response.asString());
        String value = jp.getString("total_pages");
        return Integer.parseInt(value);
    }

}
