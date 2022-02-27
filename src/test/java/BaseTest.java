import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

abstract public class BaseTest {
    protected final RequestSpecification requestSpecification = given()
            .baseUri("https://reqres.in/api")
            .basePath("/users")
            .contentType(ContentType.JSON);

}
