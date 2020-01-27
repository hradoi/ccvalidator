//using https://www.openprogrammer.info/2015/01/06/how-to-build-a-restful-service-java-8-sparkjava-in-five-minutes/
import static spark.Spark.*;

// http://localhost:4567/validate?cardNumber=4510 8166 0257 3952&expirationDate=12/20

public class RESTController
{
    public static void main( String[] args )
    {
        //Spark uses filters to intercept any route, lets add a filter for "before" we need to register a Filter that sets the JSON Content-Type.
        before((request, response) -> response.type("application/json"));

        //Route to greeting
        get("/validate",
                (request, response)
                        -> new CreditCard(
                                request.queryParams("cardNumber"),
                                request.queryParams("expirationDate"))
                                .isValid(),
                new JsonTransformer());
    }
}