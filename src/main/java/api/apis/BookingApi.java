package api.apis;

import api.base.RequestBuilder;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class BookingApi {
    public static final Logger logger = LogManager.getLogger(BookingApi.class);
    @Step("Creating booking using external JSON payload")
    public Response createBooking(String jsonPayload) {
        logger.info("Creating booking using external JSON payload...");
        try {
            Response response = new RequestBuilder()
                    .setEndpoint("/booking")
                    .setHttpMethod("POST")
                    .setHeaders(Map.of("Content-Type", "application/json"))
                    .setBody(jsonPayload)
                    .execute();
            logger.info("Booking request submitted successfully. Status code: {} ,{}", response.getStatusCode(),response.body().prettyPrint());
            return response;
        } catch (Exception e) {
            logger.error("Error creating booking using JSON: {}", e.getMessage());
            throw e;
        }
    }
    @Step("Retrieving booking with ID: {0}")
    public Response getBooking(int bookingId) {
        logger.info("Getting booking details for booking ID: {}", bookingId);
        try {
            Response response = new RequestBuilder()
                    .setEndpoint("/booking/" + bookingId)
                    .setHttpMethod("GET")
                    .execute();
            logger.info("Booking details retrieved successfully. Status code: {} ,{}", response.getStatusCode(),response.body().prettyPrint());
            return response;
        } catch (Exception e) {
            logger.error("Error getting booking details: {}", e.getMessage());
            throw e;
        }
    }
}
