package tests.tokens;

import io.restassured.response.Response;
import models.payloads.TokensQueryVariables;
import models.payloads.VariableData;
import models.response.TokensResponse;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import commons.BaseTest;
import utils.GenericUtils;
import utils.RestUtils;
import static commons.Constants.*;

public class PositiveTokensTests extends BaseTest {
    VariableData data;
    @BeforeMethod
    void setMethodName(ITestResult result){
        String methodName = result.getMethod().getMethodName();
        log.info("Fetching Test Data for Test method: " + methodName);
        data = dataMap.get(methodName);
    }

    @Test(description = "Test to verify if result data contains all tokens with valid Ethereum Address")
    public void verifyEthereumAddressFromTokensAPIResult(){
        TokensQueryVariables tokensQueryVariables = new TokensQueryVariables(new TokensQueryVariables
                .Input(new TokensQueryVariables.Input.Filter(data.getChainID()), data.getLimit(), data.getCursor()));
        log.info("Build Query Variables: " + tokensQueryVariables);
        Response response = RestUtils.Query(queryString.get(QUERY_ID_ADDRESS_WITH_PAGE), tokensQueryVariables);
        log.info("Verifying Status Code of Query Execution");
        Assert.assertEquals(response.getStatusCode(),  200);
        TokensResponse tokensResponse = GenericUtils.deserializeJsonStringToPojo(response.body().asString(), TokensResponse.class);
        log.info("Converting Json Response into POJO of TokenResponse Class for further verifications");
        Assert.assertEquals(tokensResponse.getData().getTokens().getTokens().size(), data.getLimit(),
                "Data Size is not according to limit input");
        log.info("Iterating through all the token and verifying if their address follows Ethereum address format or not");
        for (TokensResponse.Data.Tokens.Token token : tokensResponse.getData().getTokens().getTokens()) {
            String address = token.getAddress();
            Assert.assertTrue(GenericUtils.isEthereumAddressValid(address),
                    "Ethereum Address doesn't follow the format: "+ address);
        }
    }

    @Test(description = "Test to verify maximum allowed value(100) for limit variable.")
    public void verifyMaxLimitInputValue() {
        TokensQueryVariables tokensQueryVariables = new TokensQueryVariables(new TokensQueryVariables
                .Input(new TokensQueryVariables.Input.Filter(data.getChainID()), data.getLimit(), data.getCursor()));
        log.info("Build Query Variables: " + tokensQueryVariables);
        Response response = RestUtils.Query(queryString.get(QUERY_ID_ADDRESS_WITH_PAGE), tokensQueryVariables);
        log.info("Verifying Status Code of Query Execution");
        Assert.assertEquals(response.getStatusCode(),  200);
        log.info("Converting Json Response into POJO of TokenResponse Class for further verifications");
        TokensResponse tokensResponse = GenericUtils.deserializeJsonStringToPojo(response.body().asString(), TokensResponse.class);
        log.info("Checking if no. of tokens returned in result are same as limit");
        Assert.assertEquals(tokensResponse.getData().getTokens().getTokens().size(), data.getLimit(),
                "Data Size is not according to limit input");
    }

    @Test(description = "Test to verify if passing a valid Ethereum address return tokens for the same address or not.")
    public void getTokensBasedOnEthereumAddress(){
        TokensQueryVariables tokensQueryVariables = new TokensQueryVariables(new TokensQueryVariables
                .Input(new TokensQueryVariables.Input.Filter(data.getChainID(), data.getAddress()), data.getLimit(),
                data.getCursor()));
        log.info("Build Query Variables: " + tokensQueryVariables);
        Response response = RestUtils.Query(queryString.get(QUERY_ID_ADDRESS_WITH_PAGE), tokensQueryVariables);
        log.info("Verifying Status Code of Query Execution");
        Assert.assertEquals(response.getStatusCode(),  200);
        log.info("Converting Json Response into POJO of TokenResponse Class for further verifications");
        TokensResponse tokensResponse = GenericUtils.deserializeJsonStringToPojo(response.body().asString(), TokensResponse.class);
        log.info("Checking if address of token returned in result is same as input address or not");
        Assert.assertEquals(tokensResponse.getData().getTokens().getTokens().get(0).getAddress(),
                data.getAddress());
        log.info("Verifying that only one token is returned");
        Assert.assertEquals(tokensResponse.getData().getTokens().getTokens().size(), 1);
    }

    @Test(description = "Test to verify if the query will work or not if we don't pass cursor input in variables")
    public void verifyThatQueryWorksWithoutCursor(){
        TokensQueryVariables tokensQueryVariables = new TokensQueryVariables(new TokensQueryVariables
                .Input(new TokensQueryVariables.Input.Filter(data.getChainID(), data.getAddress()),  data.getLimit()));
        log.info("Build Query Variables: " + tokensQueryVariables);
        Response response = RestUtils.Query(queryString.get(QUERY_ID_ADDRESS_WITH_PAGE), tokensQueryVariables);
        log.info("Verifying Status Code of Query Execution");
        Assert.assertEquals(response.getStatusCode(),  200);
        log.info("Converting Json Response into POJO of TokenResponse Class for further verifications");
        TokensResponse tokensResponse = GenericUtils.deserializeJsonStringToPojo(response.body().asString(), TokensResponse.class);
        log.info("Checking if Data is not returned or not to see if query worked");
        Assert.assertNotNull(tokensResponse.getData().getTokens(), "No data is returned");
        log.info("Checking if address of token returned in result is same as input address or not");
        Assert.assertEquals(tokensResponse.getData().getTokens().getTokens().get(0).getAddress(),
                data.getAddress(), "Query didn't work without cursor");
    }

    @Test(description = "Test to verify if the query will work or not if we don't pass limit input in variables")
    public void verifyThatQueryWorksWithoutLimit(){
        TokensQueryVariables tokensQueryVariables = new TokensQueryVariables(new TokensQueryVariables
                .Input(new TokensQueryVariables.Input.Filter(data.getChainID(), data.getAddress()), data.getCursor()));
        log.info("Build Query Variables: " + tokensQueryVariables);
        Response response = RestUtils.Query(queryString.get(QUERY_ID_ADDRESS_WITH_PAGE), tokensQueryVariables);
        log.info("Verifying Status Code of Query Execution");
        Assert.assertEquals(response.getStatusCode(),  200);
        log.info("Converting Json Response into POJO of TokenResponse Class for further verifications");
        TokensResponse tokensResponse = GenericUtils.deserializeJsonStringToPojo(response.body().asString(), TokensResponse.class);
        log.info("Checking if Data is not returned or not to see if query worked");
        Assert.assertNotNull(tokensResponse.getData().getTokens(), "No data is returned");
        log.info("Checking if address of token returned in result is same as input address or not");
        Assert.assertEquals(tokensResponse.getData().getTokens().getTokens().get(0).getAddress(),
                data.getAddress(), "Query didn't work without cursor");
    }

    @Test(description = "Test to verify if the query will work or not if we don't pass address filter input in variables")
    public void verifyThatQueryWorksWithoutAddress() {
        TokensQueryVariables tokensQueryVariables = new TokensQueryVariables(new TokensQueryVariables
                .Input(new TokensQueryVariables.Input.Filter(data.getChainID()), data.getLimit(), data.getCursor()));
        log.info("Build Query Variables: " + tokensQueryVariables);
        Response response = RestUtils.Query(queryString.get(QUERY_ID_ADDRESS_WITH_PAGE), tokensQueryVariables);
        log.info("Verifying Status Code of Query Execution");
        Assert.assertEquals(response.getStatusCode(),  200);
        log.info("Converting Json Response into POJO of TokenResponse Class for further verifications");
        TokensResponse tokensResponse = GenericUtils.deserializeJsonStringToPojo(response.body().asString(), TokensResponse.class);
        log.info("Checking if Data is not returned or not to see if query worked");
        Assert.assertNotNull(tokensResponse.getData().getTokens(), "No data is returned");
        log.info("Checking if returned no. of token are same as the limit input");
        Assert.assertEquals(tokensResponse.getData().getTokens().getTokens().size(), data.getLimit(),
                "Data Size is not as per given limit input");
    }

    @Test(description = "Test to verify that we should not get any data for non existing ethereum address")
    public void getTokensBasedOnNonExistingEthereumAddress() {
        TokensQueryVariables tokensQueryVariables = new TokensQueryVariables(new TokensQueryVariables
                .Input(new TokensQueryVariables.Input.Filter(data.getChainID(), data.getAddress()), data.getLimit(),
                data.getCursor()));
        log.info("Build Query Variables: " + tokensQueryVariables);
        Response response = RestUtils.Query(queryString.get(QUERY_ID_ADDRESS_WITH_PAGE), tokensQueryVariables);
        log.info("Verifying Status Code of Query Execution");
        Assert.assertEquals(response.getStatusCode(),  200);
        log.info("Converting Json Response into POJO of TokenResponse Class for further verifications");
        TokensResponse tokensResponse = GenericUtils.deserializeJsonStringToPojo(response.body().asString(), TokensResponse.class);
        log.info("Checking that no. of tokens returned in the result should be null");
        Assert.assertNull(tokensResponse.getData().getTokens().getTokens(), "Got token even when invalid address" +
                " was passed as input.");
        log.info("Checking that next page cursor is blank or not");
        Assert.assertTrue(tokensResponse.getData().getTokens().getPageInfo().getNextCursor().isEmpty(), "Result has " +
                "next cursor even when no tokens were found in result");
        log.info("Checking that previous page cursor is blank or not");
        Assert.assertTrue(tokensResponse.getData().getTokens().getPageInfo().getPrevCursor().isEmpty(), "Result has " +
                "previous cursor even when no tokens were found in result");
    }

    @Test(description = "Test to verify the functionality of cursor input. Passing cursor of next page should fetch " +
            "token data from next page. If we go back to previous page then the original token will be at the end of the page")
    public void verifyNextPageCursor() {
        TokensQueryVariables tokensQueryVariables = new TokensQueryVariables(new TokensQueryVariables
                .Input(new TokensQueryVariables.Input.Filter(data.getChainID(), data.getAddress()), data.getLimit(),
                data.getCursor()));
        log.info("Build Query Variables: " + tokensQueryVariables);
        Response response = RestUtils.Query(queryString.get(QUERY_ID_ADDRESS_WITH_PAGE), tokensQueryVariables);
        log.info("Verifying Status Code of Query Execution");
        Assert.assertEquals(response.getStatusCode(),  200);
        log.info("Converting Json Response into POJO of TokenResponse Class for further verifications");
        TokensResponse tokensResponse = GenericUtils.deserializeJsonStringToPojo(response.body().asString(), TokensResponse.class);
        //Get next page cursor
        log.info("Collecting next page cursor from result");
        String nextPageCursor = tokensResponse.getData().getTokens().getPageInfo().getNextCursor();
        //use cursor to get data from next page
        log.info("Creating Query Variables again using next page cursor. Removing address from filter too.");
        tokensQueryVariables = new TokensQueryVariables(new TokensQueryVariables
                .Input(new TokensQueryVariables.Input.Filter(data.getChainID()), data.getLimit(), nextPageCursor));
        response = RestUtils.Query(queryString.get(QUERY_ID_ADDRESS_WITH_PAGE), tokensQueryVariables);
        log.info("Converting Json Response into POJO of TokenResponse Class for further verifications");
        tokensResponse = GenericUtils.deserializeJsonStringToPojo(response.body().asString(), TokensResponse.class);
        //Get previous page cursor from next page to go back to the page where original address was last entry
        log.info("Collecting previous page cursor from result");
        String previousPageCursor = tokensResponse.getData().getTokens().getPageInfo().getPrevCursor();
        //use cursor to get data from next page
        log.info("Creating Query Variables again using previous page cursor. Added address back to the filter.");
        tokensQueryVariables = new TokensQueryVariables(new TokensQueryVariables
                .Input(new TokensQueryVariables.Input.Filter(data.getChainID(), data.getAddress()), data.getLimit(), previousPageCursor));
        response = RestUtils.Query(queryString.get(QUERY_ID_ADDRESS_WITH_PAGE), tokensQueryVariables);
        log.info("Converting Json Response into POJO of TokenResponse Class for further verifications");
        tokensResponse = GenericUtils.deserializeJsonStringToPojo(response.body().asString(), TokensResponse.class);
        //verify that page have the original valid ethereum address
        log.info("Verifying if result page has the original address or not");
        Assert.assertEquals(tokensResponse.getData().getTokens().getTokens().get(0).getAddress(), data.getAddress(),
                "Didn't find address of the original address in the page result");
    }
}
