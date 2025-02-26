package api.base;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class RequestBuilder {
    public static final Logger logger = LogManager.getLogger(RequestBuilder.class);

    private String endpoint;

    private String httpMethod;

    private Map<String, String> headers;

    private Map<String, String> queryParams;

    private Object body;

    public RequestBuilder setEndpoint(String endpoint) {
        this.endpoint = endpoint;
        logger.debug("Endpoint set to: {}", endpoint);
        return this;
    }

    public RequestBuilder setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
        logger.debug("HTTP Method set to: {}", httpMethod);
        return this;
    }

    public RequestBuilder setHeaders(Map<String, String> headers) {
        this.headers = headers;
        logger.debug("Headers set to: {}", headers);
        return this;
    }

    public RequestBuilder setQueryParams(Map<String, String> queryParams) {
        this.queryParams = queryParams;
        logger.debug("Query Parameters set to: {}", queryParams);
        return this;
    }

    public RequestBuilder setBody(Object body) {
        this.body = body;
        logger.debug("Request body set.");
        return this;
    }

    public RequestSpecification build() {
        logger.info("Building RequestSpecification...");
        RequestSpecification request = RestAssured.given();

        try {
            if (headers == null || !headers.containsKey("Content-Type")) {
                request.contentType("application/json");
                logger.debug("Default Content-Type 'application/json' applied.");
            }
            if (headers != null && !headers.isEmpty()) {
                request.headers(headers);
            }
            if (queryParams != null && !queryParams.isEmpty()) {
                request.queryParams(queryParams);
            }
            if (body != null) {
                request.body(body);
            }
            logger.info("RequestSpecification built successfully:{}", request.log().all(true));
        } catch (Exception e) {
            logger.error("Error building RequestSpecification: {}", e.getMessage());
            throw e;
        }
        return request;
    }
    public Response execute() {
        RequestSpecification request = build();
        if (httpMethod == null || endpoint == null) {
            String errorMsg = "HTTP method and endpoint must be specified.";
            logger.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }

        logger.info("Executing {} request for endpoint: {}", httpMethod, endpoint);
        try {
            switch (httpMethod.toUpperCase()) {
                case "GET":
                    return request.get(endpoint);
                case "POST":
                    return request.post(endpoint);
                case "PUT":
                    return request.put(endpoint);
                case "DELETE":
                    return request.delete(endpoint);
                case "PATCH":
                    return request.patch(endpoint);
                default:
                    String errorMethod = "Unsupported HTTP method: " + httpMethod;
                    logger.error(errorMethod);
                    throw new IllegalArgumentException(errorMethod);
            }
        } catch (Exception e) {
            logger.error("Error executing request: {}", e.getMessage());
            throw e;
        }
    }
}
