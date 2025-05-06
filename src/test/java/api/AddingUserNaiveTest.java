package api;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class AddingUserNaiveTest {

    @Test
    public void addUserTest() {
        RestAssured.baseURI = "https://test-api-one-gamma.vercel.app";

        String name = "Lucy Liu";
        String email = "l.l@icanbreakit.eu";
        String role = "user";

        // 1. set up - empty the db (delete all users)
        given().when()
               .delete("/users")
               .then()
               .statusCode(204);

        // 2. check that the db is empty
        given().when()
               .get("/users")
               .then()
               .body("", hasSize(0));


        // 3. add a user
        given().header("Content-Type", "application/json")
               .body("{\"name\":\"" + name + "\",\"email\":\"" + email + "\",\"role\":\"" + role + "\"}")
               .when()
               .post("/users")
               .then()
               .statusCode(201);

        // 4. check that there is just one user in db
        given().when()
               .get("/users")
               .then()
               .body("", hasSize(1));

        // 5. check the previously added user
        given().param("id", 1)
               .when()
               .get("/users")
               .then()
               .body("[0].id", equalTo(1))
               .body("[0].name", equalTo(name))
               .body("[0].email", equalTo(email))
               .body("[0].role", equalTo(role));


        // 6. tear down - empty the db (delete all users)
        given().when()
               .delete("/users")
               .then()
               .statusCode(204);

    }

}
