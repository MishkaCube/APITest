import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
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
       findPage();
    }

    private void findPage() {
        int total_pages = 12;
        for (int pages = 1; pages < total_pages; pages++) {
            Response response = given()
                    .baseUri("https://reqres.in/api")
                    .basePath("/users")
                    .contentType(ContentType.JSON)
                    .param("page", pages)
                    .when()
                    .get()
                    .then()
                    .statusCode(200)
                    .body("data[0].email", equalTo("michael.lawson@reqres.")).extract().response();

            JsonPath jp = new JsonPath(response.asString());
        }
    }
}
