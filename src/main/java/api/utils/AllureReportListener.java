package api.utils;

import org.testng.IExecutionListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

public class AllureReportListener implements IExecutionListener {
    private static final Logger logger = LogManager.getLogger(AllureReportListener.class);

    @Override
    public void onExecutionStart() {
        logger.info("Test execution started.");
    }

    @Override
    public void onExecutionFinish() {
        logger.info("Test execution finished. Generating Allure report...");
        try {
            // Create a ProcessBuilder to execute the Allure CLI command.
            // This command generates the report in the "allure-report" directory.
            ProcessBuilder pb = new ProcessBuilder(
                    "allure", "generate", "allure-results", "--clean", "-o", "allure-report"
            );
            pb.redirectErrorStream(true);
            Process process = pb.start();

            // Optionally capture the output for logging.
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    logger.info(line);
                }
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                logger.error("Allure report generation failed with exit code {}", exitCode);
                return;
            }

            // Get the project root directory dynamically.
            String projectPath = System.getProperty("user.dir");
            String reportPath = projectPath + "/allure-report/index.html";
            logger.info("Opening Allure report at: {}", reportPath);

            // Open the report using the Desktop API.
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI("file:///" + reportPath.replace("\\", "/")));
            } else {
                logger.error("Desktop is not supported. Please open the report manually.");
            }
        } catch (Exception e) {
            logger.error("Error generating or opening Allure report: {}", e.getMessage(), e);
        }
    }
}
