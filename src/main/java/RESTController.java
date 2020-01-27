import static spark.Spark.*;

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