package cucumber;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "features", glue = "cucumber/steps", plugin = { "pretty", "html:reports/cucumber_report.html" })//ß, tags = "@AddPlace and @DeletePlace")
public class TestRunner {
}
