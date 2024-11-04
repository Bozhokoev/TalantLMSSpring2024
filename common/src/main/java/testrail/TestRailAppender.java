package testrail;

import org.apache.log4j.Appender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;
import org.testng.ITestResult;

public class TestRailAppender implements Appender {
    private static StringBuilder sb = new StringBuilder();
    private static String loggerName = "TestRail";

    @Override
    public void addFilter(Filter filter) {

    }

    @Override
    public Filter getFilter() {
        return null;
    }

    @Override
    public void clearFilters() {

    }

    @Override
    public void close() {

    }

    @Override
    public void doAppend(LoggingEvent loggingEvent) {
        // FIXME: not thread safe, probably could use loggingEvent.getThreadName(); to separate threads
        String className = loggingEvent.getLoggerName();
        String substring = className.substring(className.lastIndexOf(".") + 1);
        if (!substring.equals("ResponseBody")) {
            if (!substring.equals("ApiRequest") || !loggingEvent.getLevel().equals(Level.WARN)) {
                    sb.append("[")
                            .append(DateUtils.fromTimeStamp(loggingEvent.getTimeStamp()))
                            .append("] - ")
                            .append(substring)
                            .append(": [")
                            .append(loggingEvent.getLevel().toString())
                            .append("] - ").append(loggingEvent.getMessage()).append("\n");
            }
        }
    }

    @Override
    public String getName() {
        return loggerName;
    }

    @Override
    public void setErrorHandler(ErrorHandler errorHandler) {

    }

    @Override
    public ErrorHandler getErrorHandler() {
        return null;
    }

    @Override
    public void setLayout(Layout layout) {

    }

    @Override
    public Layout getLayout() {
        return null;
    }

    @Override
    public void setName(String s) {
        loggerName = s;
    }

    @Override
    public boolean requiresLayout() {
        return false;
    }

    public static void start() {
        sb = new StringBuilder();
    }

    public static String stop() {
        return sb.toString();
    }
}