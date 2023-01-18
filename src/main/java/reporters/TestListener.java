package reporters;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import static commons.BaseTest.log;

public class TestListener implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        log.info("****---- Started Test " + result.getMethod().getMethodName() + "----****");
        ExtentTestManager.startTest(result.getTestClass().getRealClass().getSimpleName()
                + " : " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("****---- Completed Test " + result.getMethod().getMethodName() + " Successfully----****");
        ExtentTestManager.getTest().log(Status.PASS, MarkupHelper.createCodeBlock(getTestDescription(result)));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.info("****---- Test " + result.getMethod().getMethodName() + " Failed----****");
        if (null != result.getThrowable()) {
            ExtentTestManager.getTest().log(Status.FAIL, MarkupHelper.createCodeBlock(getTestDescription(result), result.getThrowable().getMessage()));
        }else {
            ExtentTestManager.getTest().log(Status.FAIL, MarkupHelper.createCodeBlock(getTestDescription(result)));
        }

    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.info("****---- Test " + result.getMethod().getMethodName() + " Skipped----****");
        ExtentTestManager.getTest().log(Status.SKIP, MarkupHelper.createCodeBlock(getTestDescription(result)));
    }

    @Override
    public void onStart(ITestContext context) {
        log.info("****---- Test Suite " + context.getName()+ " Started----****");
    }

    @Override
    public void onFinish(ITestContext context) {
        log.info("****---- Test Suite " + context.getName()+ " Finished----****");
        ExtentTestManager.endTest();
    }

    private String getTestDescription(ITestResult result){
        String description = result.getMethod().getDescription();
        return !description.isBlank() ? description : "No Test Description Provided";
    }
}
