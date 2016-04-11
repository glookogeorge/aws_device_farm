package Tests.GlookoLogbook;

import Tests.AbstractBaseTests.TestBase;
import Util.Helpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static Util.Helpers.*;

/**
 * Created by glookogeorge on 3/30/16.
 */
public class SignUpTests extends TestBase {

    @Override
    public String getName() {
        return "SignUp";
    }

    /**
     * Sets up the alert view page
     */
    @BeforeTest
    @Override
    public void setUpPage() {}

    @Test
    public boolean signup1() throws Exception {
        boolean passed = true;
        try {
            wait(for_radiobutton("QA"));
            element(for_radiobutton("QA")).click();
            wait(for_button("OK"));
            element(for_button("OK")).click();
            wait(for_id("com.glooko.logbook:id/sign_up"));
            element(for_id("com.glooko.logbook:id/sign_up")).click();
            wait(for_id("com.glooko.logbook:id/firstname_field"));
            element(for_id("com.glooko.logbook:id/firstname_field")).sendKeys("TestFirstName");
            element(for_id("com.glooko.logbook:id/lastname_field")).sendKeys("TestLastName");
            String email = generateEmail();
            element(for_id("com.glooko.logbook:id/username_field")).sendKeys(email);
            element(for_id("com.glooko.logbook:id/sign_up_button")).click();
            wait(for_text_exact("Are you compatible with Glooko?"));
        } catch (Exception e) {
            passed = false;
        }
        assert passed == true;
        return passed;
    }

    /** wait wraps Helpers.wait **/
    public static WebElement wait(By locator) {
        return Helpers.wait(locator);
    }
}
