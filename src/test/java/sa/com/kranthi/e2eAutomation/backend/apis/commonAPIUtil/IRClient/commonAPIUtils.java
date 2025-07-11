package sa.com.kranthi.e2eAutomation.backend.apis.commonAPIUtil.IRClient;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import sa.com.kranthi.e2eAutomation.backend.foundation.baseConfig.BaseTest;

import java.util.Map;

public class commonAPIUtils extends BaseTest {


    public static Response sendRequest(Method method, String endPoint, Map<String, Object> headers, String body, int expectedStatusCode) {

        AssertAndLog.INFO("EndPoint is: " + endPoint);
        AssertAndLog.INFO("Method is: " + method);
        AssertAndLog.INFO("Headers are: " + headers);

        logs.info("EndPoint is: " + endPoint);
        logs.info("Method is: " + method);
        logs.info("Headers are: " + headers);

        RequestSpecification request = RestAssured.given().headers(headers);

        if (body != null) {
            request.body(body);
            AssertAndLog.INFO("Body is are: " + body);
            logs.info("Body is are: " + body);
        }

        Response res = switch (method) {
            case GET -> request.get(endPoint);
            case POST -> request.post(endPoint);
            case PUT -> request.put(endPoint);
            case DELETE -> request.delete(endPoint);
            case PATCH -> request.patch(endPoint);
            default -> null;
        };

        logs.info("Response body is: " + res.getBody().asString());

        assertEquals(res.statusCode(), expectedStatusCode, "Expected Status Code is: " + expectedStatusCode);

        return res;
    }
}
