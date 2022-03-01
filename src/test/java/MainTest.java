import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;

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
        findEmail("Michael", "michael.lawson@reqres.in");
    }

    private boolean findEmail(String name, String userEmail) {
        int max_pages = findMaxPages();
        for (int page = 1; page < max_pages + 1; page++) {
            Response response =
                    given()
                            .baseUri("https://reqres.in/api")
                            .basePath("/users")
                            .contentType(ContentType.JSON)
                            .param("page", page)
                            .when()
                            .get();
            JsonPath jp = new JsonPath(response.asString());
            String first_name = jp.getString("data[0].first_name");
            String email = jp.getString("data[0].email");

            if(name.equals(first_name) || email.equals(email)) {
                return true;
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
