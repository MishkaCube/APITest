import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class MainTest extends BaseTest
{
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
        requestSpecification
                .when()
                    .get()
                    .then()
                        .statusCode(200)
                .body("data.find{it.email=='michael.lawson@reqres'}.first_name",
                        equalTo("Michael"));
    }

}
