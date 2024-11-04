package testrail;

import org.testng.ITestResult;

public class TestRailHelper {
    private static APIClient apiClient;
    private static Long suiteId;


    public static void createNewTestRailRun(TestRailCreds projectCreds, String projectId, String testRunName, List<Integer> allIncludeTestCaseIds) {
        if (suiteId == null) createRun(projectCreds, projectId, testRunName, allIncludeTestCaseIds);
    }

    public static void setTestRailStatus(ITestResult iTestResult, TestResult result, String logs) {
        String testId = getTestRailId(iTestResult);
        String commentValue = "Automatically generated test result \nLOGS:\n" + logs;
        int testResult = getTestResult(result);
        Map<String, Object> resultData = new HashMap<>();
        resultData.put("status_id", testResult);

        if (testResult == 5) {
            commentValue = "TEST HAS BEEN FAILED. \n STACK TRACE: \n" + logs
                    + ExceptionUtils.getStackTrace(iTestResult.getThrowable());
        }

        resultData.put("comment", commentValue);

        try {
            apiClient.sendPost("add_result_for_case/" + suiteId + "/" + testId, resultData);
        } catch (IOException | APIException e) {
            e.printStackTrace();
        }
    }

    private static void createRun(TestRailCreds projectCreds
            , String projectId
            , String testRunName
            , List<Integer> allIncludeTestCaseIds) {
        String currentDateTime = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss"));

        apiClient = new APIClient(projectCreds.getProjectUrl());
        apiClient.setUser(projectCreds.getUsername());
        apiClient.setPassword(projectCreds.getPassword());

        Map<String, Object> requestData = new HashMap<>();
        requestData.put("include_all", 0);
        requestData.put("case_ids", allIncludeTestCaseIds);
        if (testRunName.length() <= 43) {
            requestData.put("name", testRunName + currentDateTime);
        } else {
            requestData.put("name", "Night Suite Run "+ currentDateTime);
        }
        requestData.put("description", "Automatically generated test suite");

        JSONObject obj = null;
        try {
            obj = (JSONObject) apiClient.sendPost("/add_run/" + projectId, requestData);
            System.out.println(obj.toJSONString());
        } catch (IOException | APIException e) {
            System.out.println("There is a problem with creating new Test run. StackTrace: \n");
            e.printStackTrace();
        }

        suiteId = (Long) obj.get("id");
    }

    private static int getTestResult(TestResult result) {
        int statusId = 0;

        switch (result) {
            case PASSED:
                statusId = 1;
                break;
            case BLOCKED:
                statusId = 2;
                break;
            case FAILED:
                statusId = 5;
                break;
        }

        return statusId;
    }

    private static String getTestRailId(ITestResult iTestResult) {
        String testId = "";
        Method testMethod = null;
        try {
            testMethod = iTestResult.getMethod().getRealClass().getMethod(iTestResult.getMethod().getMethodName());
        } catch (NoSuchMethodException e) {
            System.out.println("There is a problem with getting TestRailCase id value. StackTrace: \n");
            e.printStackTrace();
        }
        if (testMethod.isAnnotationPresent(TestRailCase.class))
            testId = testMethod.getAnnotation(TestRailCase.class).id();

        return testId;
    }

}