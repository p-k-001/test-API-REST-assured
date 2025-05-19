package api;

import api.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class AddingUserTest {

    User user1 = new User("Lucy Liu", "l.l@icanbreakit.eu", 18, "user");

    //    private final ObjectMapper objectMapper = new ObjectMapper();
    @BeforeClass
    public void setupClass() {
        RestAssured.baseURI = "https://test-api-one-gamma.vercel.app";
    }

    @BeforeMethod
    @AfterMethod
    public void clearDatabase() {
        given()
                .when()
                .delete("/users")
                .then()
                .statusCode(204);
    }

    @Test
    public void addUserTest() throws JsonProcessingException {

        // 1. check that the db is empty
        given()
                .when()
                .get("/users")
                .then()
                .body("", hasSize(0));

        // 2. add a user
        ObjectMapper mapper = new ObjectMapper();
        String user1Json = mapper.writeValueAsString(user1);

        given()
                .body(user1Json)
                .header("Content-Type", "application/json")
                .when()
                .post("/users")
                .then()
                .statusCode(201);

        // 3. check that there is just one user in db

        given()
                .when()
                .get("/users")
                .then()
                .body("", hasSize(1));

        // 4. check the previously added user

        given()
                .param("id", 1)
                .when()
                .get("/users")
                .then()
                .body("[0].id", equalTo(1))
                .body("[0].name", equalTo(user1.getName()))
                .body("[0].email", equalTo(user1.getEmail()))
                .body("[0].age", equalTo(user1.getAge()))
                .body("[0].role", equalTo(user1.getRole()));
    }
}