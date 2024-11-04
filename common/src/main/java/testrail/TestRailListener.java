package testrail;

import com.geniusto.common.config.ConfigurationManager;
import lombok.extern.slf4j.Slf4j;
import org.testng.*;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class TestRailListener implements ITestListener, ISuiteListener {

    @Override
    public void onStart(ISuite iSuite) {
        TestRailAppender.start();
        TestRailProject project = null;
        XmlSuite xmlSuite = iSuite.getXmlSuite();
        List<XmlTest> testClasses = xmlSuite.getTests();
        for (XmlTest xmlTest : testClasses) {
            List<XmlClass> xmlClasses = xmlTest.getXmlClasses();
            for (XmlClass xmlClass : xmlClasses) {
                String className = xmlClass.getName();
                Class<?> testClass;
                try {
                    testClass = Class.forName(className);
                } catch (ClassNotFoundException e) {
                    continue;
                }
                project = testClass.getSuperclass().getAnnotation(TestRailProject.class);
            }
        }
        assert project != null;
        String projectId = String.valueOf(project.id());
        String testRunName = "Test Case ID - "+iSuite.getAllMethods().stream().map(iTestNGMethod ->
                Integer.valueOf(Arrays.stream(iTestNGMethod.getGroups())
                        .filter(this::canParseInt).findFirst().get())
        ).collect(Collectors.toList())+ " Local date and time ";
        TestRailCreds creds = new TestRailCreds.Builder()
                .withProjectUrl(ConfigurationManager.getConfiguration().testRailUrl())
                .withUsername(ConfigurationManager.getConfiguration().testRailUser())
                .withPassword(ConfigurationManager.getConfiguration().testRailPassword())
                .build();
        TestRailHelper.createNewTestRailRun(creds
                , projectId
                , testRunName
                , getAllIncludeTestCaseIds(iSuite));
    }

    private List<Integer> getAllIncludeTestCaseIds(ISuite iSuite) {
        List<Integer> collect = iSuite.getAllMethods().stream().map(
                iTestNGMethod ->
                        Integer.valueOf(Arrays.stream(iTestNGMethod.getGroups())
                                .filter(this::canParseInt).findFirst().get())
        ).collect(Collectors.toList());
        System.err.println(collect);
        return collect;
    }

    private boolean canParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        TestRailHelper.setTestRailStatus(iTestResult
                , TestResult.PASSED
                , TestRailAppender.stop());
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        TestRailHelper.setTestRailStatus(iTestResult
                , TestResult.FAILED
                , TestRailAppender.stop());
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        TestRailHelper.setTestRailStatus(iTestResult
                , TestResult.BLOCKED
                , TestRailAppender.stop());
    }


    @Override
    public void onTestStart(ITestResult iTestResult) {
        TestRailAppender.start();
        log.info("Test started: {} \nDescription is: {}", iTestResult.getMethod().getMethodName(), iTestResult.getMethod().getDescription());
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        String className = iTestContext.getAllTestMethods().length > 0 ? iTestContext.getAllTestMethods()[0].getInstance().getClass().toString() : "UNDEFINED";
        log.warn("START CLASS: {}", className);
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        String className = iTestContext.getAllTestMethods().length > 0
                ? iTestContext.getAllTestMethods()[0].getInstance().getClass().toString() : "UNDEFINED";
        log.warn("CLASS FINISHED: {}", className);
        log.warn("Passed tests: {}", iTestContext.getPassedTests().size());
        log.warn("Failed tests: {}", iTestContext.getFailedTests().size());
        log.warn("Skipped tests: {}", iTestContext.getSkippedTests().size());
    }


}