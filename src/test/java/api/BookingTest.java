package api;

import api.apis.BookingApi;
import api.utils.JsonFileReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BookingTest {
    private static final Logger logger = LogManager.getLogger(BookingTest.class);
    private BookingApi bookingAPI;
    @BeforeClass
    public void setup() {
        // Configure base URI (update as needed)
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        logger.info("Base URI set to: " + RestAssured.baseURI);
        bookingAPI  = new BookingApi();

    }

    @Test
    public void testCreateBookingUsingJsonFile(){
        String payload = JsonFileReader.readJson("src/test/resources/testdata/createBooking.json");
        Response response = bookingAPI.createBooking(payload);
        Assert.assertEquals(response.getStatusCode(), 200, "Booking creation using external JSON failed");
        int bookingId = response.jsonPath().getInt("bookingid");
        Assert.assertTrue(bookingId > 0, "Invalid booking id");
    }
    @Test
    public void testCreateBookingUsingJson_EmptyPayload() {
        logger.info("Running test: testCreateBookingUsingJson_EmptyPayload");
        Response response = bookingAPI.createBooking(null);
        Assert.assertEquals(response.getStatusCode(), 400, "Booking creation should fail with empty payload with status code 400");
    }
    @Test
    public void testCreateBookingUsingJson_MalformedJson() {
        logger.info("Running test: testCreateBookingUsingJson_MalformedJson");
        String malformedJson = "{ \"firstname\": \"Jane\", \"lastname\": \"Doe\", "; // Truncated JSON
        Response response = bookingAPI.createBooking(malformedJson);
        Assert.assertEquals(response.getStatusCode(), 400, "Booking creation should fail with malformed JSON witg status code 400");
    }
}
