import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

public class CreateUser {
    /**
     * @return JsonPath
     * @desc THis function is used to create new user
     */
    public JsonPath CreateUser(){
        RequestSpecification request = RestAssured.given();
        // Setting Base URI
        request.baseUri("https://randomuser.me");
        // Setting Base Path
        request.basePath("/api");

        Response response = request.get();
        Assert.assertEquals(response.getStatusCode(),200);
        String rbody = response.asString();
        JsonPath jp = new JsonPath( rbody );

        System.out.println(response.asString());
        return jp;
    }
}
