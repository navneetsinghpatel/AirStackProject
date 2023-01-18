package tests.tokens;

import dataproviders.TestDataProviders;
import io.restassured.response.Response;
import models.payloads.TokensQueryVariables;
import models.payloads.VariableData;
import models.requestresponse.ExpectedResponse;
import models.requestresponse.RequestResponse;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import commons.BaseTest;
import org.testng.asserts.SoftAssert;
import utils.GenericUtils;
import utils.RestUtils;

import static commons.Constants.*;

public class NegativeTokenTests extends BaseTest {

    VariableData data;

    @BeforeMethod
    void setMethodName(ITestResult result) {
        data = dataMap.get(result.getMethod().getMethodName());
    }

    @Test(dataProvider = "inputFieldNegativeTests", dataProviderClass = TestDataProviders.class, description = "Verify " +
            "Negative Scenario where request and response is given in json file")
    public void inputFieldTests(RequestResponse requestResponse) {
        Response response = RestUtils.Query(requestResponse.getInputRequest().getQuery(),
                requestResponse.getInputRequest().getVariables());
        log.info("Verifying Status Code of Query Execution");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusCode(), 422);
        ExpectedResponse actualResponse = GenericUtils.deserializeJsonStringToPojo(
                response.body().asString(), ExpectedResponse.class);
        softAssert.assertEquals(actualResponse, requestResponse.getExpectedResponse(), "Didn't get expected response");
        softAssert.assertAll();
    }

    @Test(description = "Test to verify Query with Invalid Limit Address")
    public void verifyTokenAPIWithInvalidLimitValueInput() {
        TokensQueryVariables tokensQueryVariables = new TokensQueryVariables(new TokensQueryVariables
                .Input(new TokensQueryVariables.Input.Filter(data.getChainID()), data.getLimit(), data.getCursor()));
        log.info("Build Query Variables: " + tokensQueryVariables);
        Response response = RestUtils.Query(queryString.get(QUERY_ID_ADDRESS_WITH_PAGE), tokensQueryVariables);
        log.info("Verifying Status Code of Query Execution");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusCode(), 200);
        ExpectedResponse actualResponse = GenericUtils.deserializeJsonStringToPojo(
                response.body().asString(), ExpectedResponse.class);
        if (actualResponse.getErrors() != null) {
            softAssert.assertEquals(actualResponse.getErrors().get(0).getExtensions().getINTERNAL_ERROR_CODE(), INTERNAL_ERROR_CODE_INVALID_LIMIT
                    , "Incorrect Internal Error Code is returned");
            softAssert.assertEquals(actualResponse.getErrors().get(0).getMessage(), ERROR_INVALID_LIMIT
                    , "Incorrect Error Message is returned");
        } else {
            Assert.fail("Didn't get Error while querying token using more than max Limit Input value");
        }
        softAssert.assertAll();
    }

    @Test(description = "Test to verify Query with Invalid Cursor Value")
    public void verifyTokenAPIWithInvalidCursorValueInput() {
        TokensQueryVariables tokensQueryVariables = new TokensQueryVariables(new TokensQueryVariables
                .Input(new TokensQueryVariables.Input.Filter(data.getChainID()), data.getLimit(), data.getCursor()));
        log.info("Build Query Variables: " + tokensQueryVariables);
        Response response = RestUtils.Query(queryString.get(QUERY_ID_ADDRESS_WITH_PAGE), tokensQueryVariables);
        log.info("Verifying Status Code of Query Execution");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusCode(), 200);
        ExpectedResponse actualResponse = GenericUtils.deserializeJsonStringToPojo(
                response.body().asString(), ExpectedResponse.class);
        if (actualResponse.getErrors() != null) {
            softAssert.assertEquals(actualResponse.getErrors().get(0).getExtensions().getINTERNAL_ERROR_CODE(), INTERNAL_ERROR_CODE_INVALID_CURSOR
                    , "Incorrect Internal Error Code is returned");
            softAssert.assertEquals(actualResponse.getErrors().get(0).getMessage(), ERROR_INVALID_CURSOR
                    , "Incorrect Error Message is returned");
        } else {
            Assert.fail("Didn't get Error while querying token using invalid Cursor Input value");
        }
        softAssert.assertAll();
    }

    @Test(description = "Test to verify Query with Invalid Ethereum Address")
    public void verifyTokenAPIWithInvalidAddressValueInput() {
        TokensQueryVariables tokensQueryVariables = new TokensQueryVariables(new TokensQueryVariables
                .Input(new TokensQueryVariables.Input.Filter(data.getChainID(), data.getAddress()), data.getLimit(), data.getCursor()));
        log.info("Build Query Variables: " + tokensQueryVariables);
        Response response = RestUtils.Query(queryString.get(QUERY_ID_ADDRESS_WITH_PAGE), tokensQueryVariables);
        log.info("Verifying Status Code of Query Execution");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusCode(), 200);
        ExpectedResponse actualResponse = GenericUtils.deserializeJsonStringToPojo(
                response.body().asString(), ExpectedResponse.class);
        if (actualResponse.getErrors() != null) {
            softAssert.assertEquals(actualResponse.getErrors().get(0).getExtensions().getINTERNAL_ERROR_CODE(), INTERNAL_ERROR_CODE_INVALID_ADDRESS
                    , "Incorrect Internal Error Code is returned");
            softAssert.assertEquals(actualResponse.getErrors().get(0).getMessage(), ERROR_INVALID_ADDRESS
                    , "Incorrect Error Message is returned");
        } else {
            Assert.fail("Didn't get Error while querying token using invalid Address Input value");
        }
        softAssert.assertAll();
    }

    @Test(description = "Test to check that we should get error when querying Tokens with limit input as a negative value")
    public void verifyTokensAPIWithInvalidNegativeLimitInput() {
        TokensQueryVariables tokensQueryVariables = new TokensQueryVariables(new TokensQueryVariables
                .Input(new TokensQueryVariables.Input.Filter(data.getChainID()), data.getLimit(), data.getCursor()));
        log.info("Build Query Variables: " + tokensQueryVariables);
        Response response = RestUtils.Query(queryString.get(QUERY_ID_ADDRESS_WITH_PAGE), tokensQueryVariables);
        log.info("Verifying Status Code of Query Execution");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusCode(), 200);
        log.info("Converting Json Response into POJO of ExpectedResponse Class for further verifications");
        ExpectedResponse actualResponse = GenericUtils.deserializeJsonStringToPojo(
                response.body().asString(), ExpectedResponse.class);
        log.info("Verifying that we get correct error message with incorrect limit value is passed");
        if (actualResponse.getErrors() != null) {
            softAssert.assertEquals(actualResponse.getErrors().get(0).getExtensions().getINTERNAL_ERROR_CODE(), INTERNAL_ERROR_CODE_INVALID_LIMIT
                    , "Incorrect Internal Error Code is returned");
            softAssert.assertEquals(actualResponse.getErrors().get(0).getMessage(), ERROR_INVALID_LIMIT
                    , "Incorrect Error Message is returned");
        } else {
            Assert.fail("Didn't get Error while querying token using Negative Limit Input");
        }
        softAssert.assertAll();
    }

    @Test(description = "Test to check that we should get error when querying Tokens with limit input as 0")
    public void verifyTokensAPIWithInvalidZeroLimitInput() {
        TokensQueryVariables tokensQueryVariables = new TokensQueryVariables(new TokensQueryVariables
                .Input(new TokensQueryVariables.Input.Filter(data.getChainID()), data.getLimit(), data.getCursor()));
        log.info("Build Query Variables: " + tokensQueryVariables);
        Response response = RestUtils.Query(queryString.get(QUERY_ID_ADDRESS_WITH_PAGE), tokensQueryVariables);
        log.info("Verifying Status Code of Query Execution");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusCode(), 200);
        log.info("Converting Json Response into POJO of ExpectedResponse Class for further verifications");
        ExpectedResponse actualResponse = GenericUtils.deserializeJsonStringToPojo(
                response.body().asString(), ExpectedResponse.class);
        log.info("Verifying that we get correct error message with incorrect limit value is passed");
        if (actualResponse.getErrors() != null) {
            softAssert.assertEquals(actualResponse.getErrors().get(0).getExtensions().getINTERNAL_ERROR_CODE(), INTERNAL_ERROR_CODE_INVALID_LIMIT
                    , "Incorrect Internal Error Code is returned");
            softAssert.assertEquals(actualResponse.getErrors().get(0).getMessage(), ERROR_INVALID_LIMIT
                    , "Incorrect Error Message is returned");
        } else {
            Assert.fail("Didn't get Error while querying token using 0 Limit Input");
        }
        softAssert.assertAll();
    }
}
