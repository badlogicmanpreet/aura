/*
 * Copyright (C) 2013 salesforce.com, inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.auraframework.integration.test.components.ui.inputError;

import org.auraframework.test.util.WebDriverTestCase;
import org.auraframework.test.util.WebDriverUtil.BrowserType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class InputErrorUITest extends WebDriverTestCase {
    WebDriver d;
    WebElement input;
    WebElement errorBtn;
    WebElement clearBtn;
    WebElement status;
    final String INPUT_CMP_LOCATOR = "uiInput";
    final String STATUS_CMP_LOCATOR = "uiOutputText";
    final String ERR_CSS = "has-error";
    final String STATUS_ERROR = "Got Error";
    final String STATUS_CLEAR = "Cleared error";
    final String URL = "/uitest/inputError_Test.cmp";
    final String URL_CST = "/uitest/inputError_CustomTest.cmp";

    public InputErrorUITest(String name) {
        super(name);
    }

    /**
     * Test error handling setting errors when firing onError event. Test error handling clearing errors when firing
     * onClearErrors event. Test error handling setting a custom error component.
     */
    public void testServerErrorUsingOnError() throws Exception {
        String expectedErrorMsg = "Custom Error Msg: Error Happens!";
        d = getDriver();
        open(URL_CST);
        setWebElements("ErrorFireOnErrorEvent", "ClearFireOnClearErrrorsEvent");

        errorBtn.click();
        waitForElementTextPresent(status, STATUS_ERROR);
        WebElement errorElement = d.findElement(By.id("errorDiv"));
        String errorText = errorElement.getText();
        assertTrue("Incorrect error message: " + errorText, errorText.startsWith(expectedErrorMsg));

        clearBtn.click();
        waitForElementTextPresent(status, STATUS_CLEAR);
        errorElement = d.findElement(By.id("errorDiv"));
        assertEquals("Error message not cleared", "", errorElement.getText());
    }

    /**
     * Test error handling setting a custom error component.
     */
    // uncomment after bug: W-1412621
    public void _testServerErrorUsingCustomErrorComponent() throws Exception {
        d = getDriver();
        open(URL_CST);
        setWebElements("ErrorFireOnErrorEvent", "ClearFireOnClearErrrorsEvent");
        induceError("Custom Error Msg: Error Happens!", true);
        clearError(true);
    }

    /**
     * Test error handling from server errors.
     */
    // commented for bug W-2319834
    public void _testServerError() throws Exception {
        d = getDriver();
        open(URL);
        setWebElements("ErrorServer", "ClearNoEvent");
        induceError("Error Happens!", true);
        clearError(true);
    }

    /**
     * Test error handling setting errors without firing error events just setting SimpleValue attributes. Test error
     * handling clearing errors without firing clear events just setting SimpleValue attributes.
     */
    // commented for bug W-2319834
    public void _testErrorWithoutEvents() throws Exception {
        d = getDriver();
        open(URL);
        setWebElements("ErrorNoEvent", "ClearNoEvent");
        induceError("Error Happens!", true);
        clearError(true);
    }

    /**
     * Test error handling without setting error message on SimpleValue. Test error handling clearing errors setting
     * error message to null on SimpleValue.
     */
    public void testErrorWithoutSettingErrorMessage() throws Exception {
        d = getDriver();
        open(URL);
        setWebElements("ErrorNoErrMsg", "ClearNoEvent");
        induceError(null, false);
        clearError(false);
    }

    private void setWebElements(String errorBtnLocator, String clearBtnLocator) {
        input = d.findElement(By.className(INPUT_CMP_LOCATOR));
        errorBtn = d.findElement(By.xpath("//div[@id='" + errorBtnLocator + "']/button"));
        clearBtn = d.findElement(By.xpath("//div[@id='" + clearBtnLocator + "']/button"));
        status = d.findElement(By.className(STATUS_CMP_LOCATOR));
    }

    private void induceError(String errMsg, boolean verifyErrMsg) throws Exception {
        errorBtn.click();
        waitForElementTextPresent(status, STATUS_ERROR);
        if (verifyErrMsg) {
            if (errMsg == null) {
                errMsg = "";
            }
            verifyErrorMessage(input, errMsg);
        }
        verifyCss(input, true);
    }

    private void clearError(boolean verifyErrMsg) throws Exception {
        // Sometimes running ios tests through SauceLabs can be very slow. Watching the video recording, it appears to
        // not be done processing the previous click when we try to send this click causing the click to be ignored and
        // the test to timeout on the following wait.
        if (getBrowserType() == BrowserType.IPAD || getBrowserType() == BrowserType.IPHONE) {
            waitFor(3);
        }

        clearBtn.click();
        waitForElementTextPresent(status, STATUS_CLEAR);
        if (verifyErrMsg) {
            verifyNoErrorMessage();
        }
        verifyCss(input, false);
    }

    private void verifyErrorMessage(WebElement element, String expectedErrorMsg) throws Exception {
        //
        // Note that we wait for the element to appear in our dom, as we are racing with
        // it (and we lose this race on fast machines).
        //
        waitForElementAppear(By.className("uiInputDefaultError"));
        WebElement errorElement = d.findElement(By.className("uiInputDefaultError"));
        String errorText = errorElement.getText();
        assertTrue("Incorrect error message: " + errorText, errorText.contains(expectedErrorMsg));
    }

    private void verifyNoErrorMessage() throws Exception {
        assertFalse("Did not expect an error message", isElementPresent(By.className("uiInputDefaultError")));
    }

    private void verifyCss(WebElement element, boolean isError) throws Exception {
        String inputClassValue = element.getAttribute("class");
        if (isError) {
            assertTrue("On input error css not added", inputClassValue.contains(ERR_CSS));
        } else {
            assertFalse("On input error css should not be present", inputClassValue.contains(ERR_CSS));
        }
    }
}
