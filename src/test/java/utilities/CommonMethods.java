package utilities;

import java.io.IOException;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import basedrivers.BaseDriver;

public class CommonMethods extends BaseDriver{
	
	public void fillField(Locator field, String value) throws IOException {
		field.fill(value);
	}
	
	public void scrollDown() throws InterruptedException {
		String script = "window.scrollTo(0, document.body.scrollHeight)";
		page.evaluate(script);
		Thread.sleep(3000);
	}
	
	public void scrollUp() throws InterruptedException {
		String script = "window.scrollTo(0, 0)";
		page.evaluate(script);
		Thread.sleep(3000);
	}
	
	public void scrollToElement(String locator) throws InterruptedException {
		ElementHandle element = page.querySelector(locator);
		element.scrollIntoViewIfNeeded();
		Thread.sleep(3000);
	}


}
