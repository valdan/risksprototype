import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

import static org.fluentlenium.core.filter.FilterConstructor.*;

public class IntegrationTest {
    
    @Test
    public void test() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo("http://localhost:3333");
                
                assertThat(browser.$("header h1").first().getText()).isEqualTo("Play 2.0 sample application — Risk database");
                assertThat(browser.$("section h1").first().getText()).isEqualTo("574 risks found");

                assertThat(browser.$("#pagination li.current").first().getText()).isEqualTo("Displaying 1 to 10 of 574");

                browser.$("#pagination li.next a").click();

                assertThat(browser.$("#pagination li.current").first().getText()).isEqualTo("Displaying 11 to 20 of 574");
                browser.$("#searchbox").text("Apple");
                browser.$("#searchsubmit").click();

                assertThat(browser.$("section h1").first().getText()).isEqualTo("13 risks found");
                browser.$("a", withText("Apple II")).click();

                assertThat(browser.$("section h1").first().getText()).isEqualTo("Edit risk");

                browser.$("#discontinued").text("10-10-2001");
                browser.$("input.primary").click();

                assertThat(browser.$("div.error").size()).isEqualTo(1);
                assertThat(browser.$("div.error label").first().getText()).isEqualTo("Discontinued date");

                browser.$("#discontinued").text("xxx");
                browser.$("input.primary").click();

                assertThat(browser.$("div.error").size()).isEqualTo(1);
                assertThat(browser.$("div.error label").first().getText()).isEqualTo("Discontinued date");

                browser.$("#discontinued").text("");
                browser.$("input.primary").click();

                assertThat(browser.$("section h1").first().getText()).isEqualTo("574 risks found");
                assertThat(browser.$(".alert-message").first().getText()).isEqualTo("Done! Risk Apple II has been updated");

                browser.$("#searchbox").text("Apple");
                browser.$("#searchsubmit").click();

                browser.$("a", withText("Apple II")).click();
                browser.$("input.danger").click();

                assertThat(browser.$("section h1").first().getText()).isEqualTo("573 risks found");
                assertThat(browser.$(".alert-message").first().getText()).isEqualTo("Done! Risk has been deleted");

                browser.$("#searchbox").text("Apple");
                browser.$("#searchsubmit").click();

                assertThat(browser.$("section h1").first().getText()).isEqualTo("12 risks found");
                
            }
        });
    }
  
}
