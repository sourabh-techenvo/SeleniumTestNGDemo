package testSFEC;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ManageData {

    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;
    private JavascriptExecutor js;


    public ManageData(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("WebDriver instance is null!");
        }
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.actions = new Actions(driver);
        this.js = (JavascriptExecutor) driver;

        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button[@title='Save']")
    private WebElement save;



    @FindBy(xpath = "//button[@title='Yes']")
    private WebElement Yes;


    @FindBy(xpath = "//button[@title='Submit']")
    private WebElement Submit;


    @FindBy(xpath = "//button[@name=\"Submit\"]")
    private WebElement submit;

    @FindBy(xpath = "//button[@title='Yes']")
    private WebElement yes;

    public void selectEffectiveStartDate(int daysToAdd) {
        try {

            Thread.sleep(5000);

            WebElement proceedBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@title='Proceed']")));

            String xpath = "(//ui5-date-picker-xweb-calendar-widget[ @placeholder='MM/DD/YYYY'])[2]";
            WebElement dateInput = driver.findElement(By.xpath(xpath));

            actions.click(dateInput)
                   .keyDown(Keys.CONTROL)
                   .sendKeys("a")
                   .keyUp(Keys.CONTROL)
                   .sendKeys(Keys.BACK_SPACE)
                   .perform();

            Thread.sleep(5000);

            LocalDate today = LocalDate.now();
            LocalDate futureDate = today.plusDays(daysToAdd);
            String formattedDate = futureDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

            for (char c : formattedDate.toCharArray()) {
                actions.sendKeys(String.valueOf(c)).perform();
                Thread.sleep(100);
            }

            proceedBtn.click();

        } catch (Exception e) {
            System.out.println("Failed to select effective start date: " + e.getMessage());
        }
    }



    public void selectDropdownOption(String dropdownXpath, String listContainerXpath, String optionToSelect) {
        try {
        	Thread.sleep(500);
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dropdownXpath)));
            dropdown.click();
            WebElement dropdownListContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(listContainerXpath)));

            boolean optionFound = false;
            int previousCount = -1;

            while (!optionFound) {
                List<WebElement> dropdownItems = dropdownListContainer.findElements(By.tagName("li"));
                int currentCount = dropdownItems.size();

                // Search for the option
                for (WebElement item : dropdownItems) {
                    String itemText = item.getText().trim();
                    System.out.println("Dropdown item: " + itemText);
                    if (itemText.equalsIgnoreCase(optionToSelect)) {
                        wait.until(ExpectedConditions.elementToBeClickable(item)).click();
                        optionFound = true;
                        break;
                    }
                }

                // End condition: if no more items are being loaded
                if (optionFound || (currentCount == previousCount)) {
                    break;
                }

                previousCount = currentCount;

                // Scroll the last item into view to trigger loading more
                WebElement lastItem = dropdownItems.get(dropdownItems.size() - 1);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", lastItem);

                Thread.sleep(5000); // Let new items load
            }

            if (!optionFound) {
                throw new NoSuchElementException("Option '" + optionToSelect + "' not found in dropdown.");
            }
        } catch (Exception e) {
            System.err.println("Error selecting dropdown option: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void selectsearchdropdown(int index, String value) {

        try {
            Actions actions = new Actions(driver);

            String xpathExpression = "(//label[@class='hiddenAriaContent']/following::input[@role='combobox'])[" + index + "]";
            WebElement parentCostCenter = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(By.xpath(xpathExpression)));

            actions.click(parentCostCenter)
                   .keyDown(Keys.CONTROL)
                   .sendKeys("a")
                   .keyUp(Keys.CONTROL)
                   .sendKeys(Keys.BACK_SPACE)
                   .perform();

            parentCostCenter.sendKeys(value);

            Thread.sleep(5000);
            actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();

        } catch (Exception e) {
            System.out.println("Failed to interact with Parent Cost Center field: " + e.getMessage());
        }
    }

    public void selectsearchdropdownPosition(int index, String value) {

        try {
            Actions actions = new Actions(driver);

            String xpathExpression = "(//label[@class='hiddenAriaContent']/following::input[@role='combobox'])[" + index + "]";
            WebElement parentCostCenter = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(By.xpath(xpathExpression)));

            actions.click(parentCostCenter)
                   .keyDown(Keys.CONTROL)
                   .sendKeys("a")
                   .keyUp(Keys.CONTROL)
                   .sendKeys(Keys.BACK_SPACE)
                   .perform();

            parentCostCenter.sendKeys(value);

            Thread.sleep(5000);
            actions.sendKeys(Keys.ENTER).perform();

        } catch (Exception e) {
            System.out.println("Failed to interact with Parent Cost Center field: " + e.getMessage());
        }
    }


    public WebElement getTextArea(int index) {
    	return driver.findElement(By.xpath(
    			String.format("(//label[@class=\"hiddenAriaContent\"]/following::textarea[@class=\"formElement ectTextArea hideScrollbar fd-textarea fd-textarea--compact\"])[%d]"
    					, index)));
    }



    public void Yes() {
        try {
            WebElement annualizationFactorElement = wait.until(ExpectedConditions.elementToBeClickable(yes));
            annualizationFactorElement.click();
            Thread.sleep(5000); // Equivalent to WebUI.delay(5)
        } catch (Exception e) {
             System.out.println("Normal click failed");
        }

    }
    public void Clicksubmit() {
        try {
            WebElement annualizationFactorElement = wait.until(ExpectedConditions.elementToBeClickable(yes));
            annualizationFactorElement.click();
            Thread.sleep(5000); // Equivalent to WebUI.delay(5)
        } catch (Exception e) {
             System.out.println("Normal click failed");
        }

    }


    public void manageDataModifyDeactivate(String optionToSelect) {
        try {

        	 String dropdownXpath = "(//label[@class=\"hiddenAriaContent\"]//following::i[@class=\"sap-icon--slim-arrow-down\"])";
             String listContainerXpath = "(//div[@class=\"globalMenu sf-combo-listselect\"])[1]";

            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dropdownXpath+"[1]")));
            dropdown.click();
            WebElement dropdownListContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(listContainerXpath)));

            boolean optionFound = false;
            int previousCount = -1;

            while (!optionFound) {
                List<WebElement> dropdownItems = dropdownListContainer.findElements(By.tagName("li"));
                int currentCount = dropdownItems.size();

                for (WebElement item : dropdownItems) {
                    String itemText = item.getText().trim();
                    System.out.println("Dropdown item: " + itemText);
                    if (itemText.equalsIgnoreCase(optionToSelect)) {
                        wait.until(ExpectedConditions.elementToBeClickable(item)).click();
                        optionFound = true;
                        break;
                    }
                }

                if (optionFound || (currentCount == previousCount)) {
                    break;
                }

                previousCount = currentCount;

                // Scroll the last item into view to trigger loading more
                WebElement lastItem = dropdownItems.get(dropdownItems.size() - 1);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", lastItem);

                Thread.sleep(500); // Let new items load
            }

            if (!optionFound) {
                throw new NoSuchElementException("Option '" + optionToSelect + "' not found in dropdown.");
            }


            WebElement dropdown2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dropdownXpath+"[2]")));
            dropdown2.click();
            WebElement selectFirstOptionHeadOfDivisionDropdowndropdownList = driver.findElement(By.xpath("(//div[@class='globalMenu sf-combo-listselect']//div/a)[1]"));

            while (!selectFirstOptionHeadOfDivisionDropdowndropdownList.isDisplayed()) {
            	((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 100);");
            	Thread.sleep(500);
            }
            selectFirstOptionHeadOfDivisionDropdowndropdownList.click();

            Thread.sleep(5000);


        } catch (Exception e) {
            System.err.println("Error selecting dropdown option: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void handleYesButton() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            // Locate the Yes button
            WebElement yesButton = driver.findElement(By.xpath("//button[@title='Yes']"));

            // Verify visibility before clicking
            if (wait.until(ExpectedConditions.visibilityOf(yesButton)) != null) {
                yesButton.click();
                System.out.println("OK button clicked, changes are applied.");
            } else {
                System.out.println("OK button is not visible. Proceeding to the next step.");
            }

        } catch (Exception e) {
            System.out.println("An error occurred while verifying the OK button. Proceeding to the next step. Error: " + e.getMessage());
        }
    }





    public void insertNewRecord() throws InterruptedException {
        js = (JavascriptExecutor) driver;

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        Thread.sleep(5000);
        WebElement insertNewRecordLabel = driver.findElement(By.xpath("//a[@title=\"Insert New Record\"]")); // Adjust locator
        insertNewRecordLabel.click();


        // Wait for Proceed button to be visible

        WebElement proceedBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@title='Proceed']"))); // Adjust locator

        Thread.sleep(5000); // Delay after visibility

        // Click and clear date input field
        WebElement dateInput = driver.findElement(By.xpath("//div[@class='datePicker ectControl ']//following::ui5-date-picker-xweb-calendar-widget[@placeholder='MM/DD/YYYY']")); // Adjust locator

        Thread.sleep(5000);

        actions.click(dateInput)
               .keyDown(Keys.CONTROL)
               .sendKeys("a")
               .keyUp(Keys.CONTROL)
               .sendKeys(Keys.BACK_SPACE)
               .perform();

        Thread.sleep(5000); // Equivalent to WebUI.delay(5)
        LocalDate today = LocalDate.now();
        LocalDate futureDate = today.plusDays(1);
        String formattedDate = futureDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        for (char c : formattedDate.toCharArray()) {
            actions.sendKeys(String.valueOf(c)).perform();
            Thread.sleep(1000);
        }
        proceedBtn.click();

//        WebElement proceedBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@title='Proceed']"))); // Adjust locator
//
//        Thread.sleep(5000); // Delay after visibility
//
//        // Click and clear date input field
//        WebElement dateInput = driver.findElement(By.xpath("//div[@class='datePicker ectControl ']//following::ui5-date-picker-xweb-calendar-widget[@placeholder='MM/DD/YYYY']")); // Adjust locator
//
//        Thread.sleep(5000);
//
//        actions.click(dateInput)
//               .keyDown(Keys.CONTROL)
//               .sendKeys("a")
//               .keyUp(Keys.CONTROL)
//               .sendKeys(Keys.BACK_SPACE)
//               .perform();
//
//        Thread.sleep(5000); // Equivalent to WebUI.delay(5)
//        LocalDate today = LocalDate.now();
//        LocalDate futureDate = today.plusDays(7);
//        String formattedDate = futureDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
//        for (char c : formattedDate.toCharArray()) {
//            actions.sendKeys(String.valueOf(c)).perform();
//            Thread.sleep(1000);
//        }
//        proceedBtn.click();

    }



    public WebElement getInputField(int index) {
        return driver.findElement(By.xpath(String.format(
            "(//label[@class=\"hiddenAriaContent\"]/following::input[@placeholder=\"Click or focus to edit\"])[%d]",
            index)));

    }
    public void EnterPastDate(int elementIndex, int daysToSubtract) throws InterruptedException {
        String xpath = "(//ui5-date-picker-xweb-calendar-widget[@format-pattern='MM/dd/yyyy'])[" + elementIndex + "]";
        WebElement dateInput = driver.findElement(By.xpath(xpath));

        actions.click(dateInput)
               .keyDown(Keys.CONTROL)
               .sendKeys("a")
               .keyUp(Keys.CONTROL)
               .sendKeys(Keys.BACK_SPACE)
               .perform();

        Thread.sleep(500);

        LocalDate pastDate = LocalDate.now().minusDays(daysToSubtract);
        String formattedDate = pastDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        for (char c : formattedDate.toCharArray()) {
            actions.sendKeys(String.valueOf(c)).perform();
            Thread.sleep(100);
        }

        Thread.sleep(300);
        actions.sendKeys(Keys.TAB).perform();
        Thread.sleep(300);
    }
    public void EnterPresentDate(int elementIndex) throws InterruptedException {
        String xpath = "(//ui5-date-picker-xweb-calendar-widget[@format-pattern='MM/dd/yyyy'])[" + elementIndex + "]";
        WebElement dateInput = driver.findElement(By.xpath(xpath));

        // Clear existing value
        actions.click(dateInput)
               .keyDown(Keys.CONTROL)
               .sendKeys("a")
               .keyUp(Keys.CONTROL)
               .sendKeys(Keys.BACK_SPACE)
               .perform();

        Thread.sleep(500);

        // Get today's date
        LocalDate presentDate = LocalDate.now();
        String formattedDate = presentDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        // Type it character by character
        for (char c : formattedDate.toCharArray()) {
            actions.sendKeys(String.valueOf(c)).perform();
            Thread.sleep(100);
        }

        Thread.sleep(300);
        actions.sendKeys(Keys.TAB).perform();  // Confirm the value
        Thread.sleep(300);
    }

    public void selectfirstdropdown(int dropdownIndex) {
        try {
            Actions actions = new Actions(driver);
            Thread.sleep(2000); // Use WebDriverWait in production code

            // Clear input field - update the locator as needed

            // Dynamically locate the dropdown icon based on the index
            String dropdownXpath = "(//label[@class='hiddenAriaContent']//following::i[@class='sap-icon--slim-arrow-down'])[" + dropdownIndex + "]";
            WebElement dropdown = driver.findElement(By.xpath(dropdownXpath));
            dropdown.click();

            Thread.sleep(3000); // Wait for dropdown to expand

            // Select the first item from the dropdown list
            WebElement firstListItem = driver.findElement(By.xpath("(//div[@class='globalMenu sf-combo-listselect']//div/a)[1]"));
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Scroll until visible (if needed)
            while (!firstListItem.isDisplayed()) {
                js.executeScript("arguments[0].scrollIntoView(true);", firstListItem);
                Thread.sleep(500);
            }

            firstListItem.click();

        } catch (Exception e) {
            System.out.println("Dropdown selection failed: " + e.getMessage());
        }
    }

    public void setData(WebElement element,String string) throws InterruptedException {
    	wait.until(ExpectedConditions.visibilityOf(element));
    	element.clear();
    	element.sendKeys(string);
        System.out.println("Value filled Sucessfully " + string);
        Thread.sleep(5000); // Equivalent to WebUI.delay(5)

    }
    public void setData(WebElement element,int number) throws InterruptedException {
    	wait.until(ExpectedConditions.visibilityOf(element));
    	element.clear();
    	element.sendKeys(number+"");
        System.out.println("Value filled Sucessfully " + number);
        Thread.sleep(5000); // Equivalent to WebUI.delay(5)

    }

    public void Save() {
        try {
            WebElement annualizationFactorElement = wait.until(ExpectedConditions.elementToBeClickable(save));
            annualizationFactorElement.click();
            Thread.sleep(5000); // Equivalent to WebUI.delay(5)

        } catch (Exception e) {
        	 System.out.println("Normal click failed");
        }
    }



    public void Submit() {
        try {
            WebElement annualizationFactorElement = wait.until(ExpectedConditions.elementToBeClickable(Submit));
            annualizationFactorElement.click();
            Thread.sleep(5000); // Equivalent to WebUI.delay(5)

        } catch (Exception e) {
        	 System.out.println("Normal click failed");
        }
    }

    public void enterFutureDate(int elementIndex, int daysToAdd) throws InterruptedException {
        // Adjusted XPath to use elementIndex dynamically
        String xpath = "(//ui5-date-picker-xweb-calendar-widget[@format-pattern='MM/dd/yyyy'])[" + elementIndex + "]";
        WebElement dateInput = driver.findElement(By.xpath(xpath));

        // Clear existing date
        actions.click(dateInput)
               .keyDown(Keys.CONTROL)
               .sendKeys("a")
               .keyUp(Keys.CONTROL)
               .sendKeys(Keys.BACK_SPACE)
               .perform();

        Thread.sleep(500);

        // Calculate future date
        LocalDate futureDate = LocalDate.now().plusDays(daysToAdd);
        String formattedDate = futureDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        // Type date character by character
        for (char c : formattedDate.toCharArray()) {
            actions.sendKeys(String.valueOf(c)).perform();
            Thread.sleep(100);
        }

        Thread.sleep(300);

        // Click Proceed button
        actions.sendKeys(Keys.TAB).perform(); // Triggers blur event
        Thread.sleep(300);

    }

	public void takeActionMakeCorrection(String dynamicLabel, int dynamicIndex) throws InterruptedException {

		String xpath = String.format("(//div[contains(@aria-label, 'start date') and contains(@aria-label, '%s')])[%d]", dynamicLabel, dynamicIndex);
		WebElement dynamicElement = driver.findElement(By.xpath(xpath));
		dynamicElement.click();
		Thread.sleep(5000);

		int index = 5;
		String dropdownXPath = String.format("(//label[@class='hiddenAriaContent']//following::i[@class='sap-icon--slim-arrow-down'])[%d]", index);
		WebElement dropdown = driver.findElement(By.xpath(dropdownXPath));
		dropdown.click();

		// Step 2: Click the "Make Correction" button
		WebElement makeCorrectionBtn = driver.findElement(By.xpath("//span[text()='Make Correction']"));
		makeCorrectionBtn.click();
	}

	public void manageDataModify(String optionToSelect, String Value) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            Actions actions = new Actions(driver);

            // First Dropdown Handling
            String dropdownXpath = "//label[@class='hiddenAriaContent']//following::span[contains(@class,'toggle') and contains(@class,'fd-input-group__addon')]";
            String listContainerXpath = "(//div[@class='globalMenu sf-combo-listselect'])[1]";

            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dropdownXpath + "[1]")));
            dropdown.click();

            WebElement dropdownListContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(listContainerXpath)));
            boolean optionFound = false;
            int previousCount = -1;

            while (!optionFound) {
                List<WebElement> dropdownItems = dropdownListContainer.findElements(By.tagName("li"));
                int currentCount = dropdownItems.size();

                for (WebElement item : dropdownItems) {
                    String itemText = item.getText().trim();
                    if (itemText.equalsIgnoreCase(optionToSelect)) {
                        wait.until(ExpectedConditions.elementToBeClickable(item)).click();
                        optionFound = true;
                        break;
                    }
                }

                if (optionFound || currentCount == previousCount) {
					break;
				}

                previousCount = currentCount;
                WebElement lastItem = dropdownItems.get(dropdownItems.size() - 1);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", lastItem);
                Thread.sleep(500);
            }

            if (!optionFound) {
                throw new NoSuchElementException("Option '" + optionToSelect + "' not found in dropdown.");
            }


            String comboboxXpath = "(//input[@role='combobox'])[2]";
            WebElement frequencyInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(comboboxXpath)));

            actions.click(frequencyInput)
                   .keyDown(Keys.CONTROL)
                   .sendKeys("a")
                   .keyUp(Keys.CONTROL)
                   .sendKeys(Keys.BACK_SPACE)
                   .perform();

            frequencyInput.sendKeys(Value);

            Thread.sleep(3000);

            actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();

            System.out.println("Successfully selected frequency: " + Value);
            Thread.sleep(3000);

        } catch (Exception e) {
            System.err.println("Error selecting dropdown option: " + e.getMessage());
            e.printStackTrace();
        }
    }


	public void manageDataModifyDeactivate1(String optionToSelect) {
        try {

             String dropdownXpath = "//label[@class='hiddenAriaContent']//following::span[contains(@class,'toggle') and contains(@class,'fd-input-group__addon')]";
             String listContainerXpath = "(//div[@class=\"globalMenu sf-combo-listselect\"])[1]";
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dropdownXpath+"[1]")));
            dropdown.click();
            WebElement dropdownListContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(listContainerXpath)));
            boolean optionFound = false;
            int previousCount = -1;
            while (!optionFound) {
                List<WebElement> dropdownItems = dropdownListContainer.findElements(By.tagName("li"));
                int currentCount = dropdownItems.size();
                for (WebElement item : dropdownItems) {
                    String itemText = item.getText().trim();
                    System.out.println("Dropdown item: " + itemText);
                    if (itemText.equalsIgnoreCase(optionToSelect)) {
                        wait.until(ExpectedConditions.elementToBeClickable(item)).click();
                        optionFound = true;
                        break;
                    }
                }

                if (optionFound || (currentCount == previousCount)) {
                    break;
                }

                previousCount = currentCount;
                WebElement lastItem = dropdownItems.get(dropdownItems.size() - 1);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", lastItem);

                Thread.sleep(500); // Let new items load
            }

            if (!optionFound) {
                throw new NoSuchElementException("Option '" + optionToSelect + "' not found in dropdown.");
            }


            WebElement dropdown2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dropdownXpath+"[2]")));
            dropdown2.click();
            WebElement selectFirstOptionHeadOfDivisionDropdowndropdownList = driver.findElement(By.xpath("(//div[@class='globalMenu sf-combo-listselect']//div/a)[1]"));

            while (!selectFirstOptionHeadOfDivisionDropdowndropdownList.isDisplayed()) {
                ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 100);");
                Thread.sleep(500);
            }
            selectFirstOptionHeadOfDivisionDropdowndropdownList.click();

            Thread.sleep(5000); // Equivalent to WebUI.delay(5)


        } catch (Exception e) {
            System.err.println("Error selecting dropdown option: " + e.getMessage());
            e.printStackTrace();
        }
    }


	 public void selectDropdownOption(int dropdownIndex, String optionToSelect) {
	        try {
	        	String selectDropdown = "(//label[@class='hiddenAriaContent']//following::i[@class='sap-icon--slim-arrow-down'])[" + dropdownIndex + "]";
	            String selectDropdownList = "(//div[@class='globalMenu sf-combo-listselect'])[1]";
	        	Thread.sleep(500);
	        	System.out.println("Select Dropdown"+selectDropdown);
	            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(selectDropdown)));
	            dropdown.click();

	        	System.out.println(selectDropdownList);
	            WebElement dropdownListContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(selectDropdownList)));
	            boolean optionFound = false;
	            int previousCount = -1;
	            while (!optionFound) {
	                List<WebElement> dropdownItems = dropdownListContainer.findElements(By.tagName("li"));
	                int currentCount = dropdownItems.size();
	                for (WebElement item : dropdownItems) {
	                    String itemText = item.getText().trim();
	                    System.out.println("Dropdown item: " + itemText);
	                    if (itemText.equalsIgnoreCase(optionToSelect)) {
	                        wait.until(ExpectedConditions.elementToBeClickable(item)).click();
	                        optionFound = true;
	                        break;
	                    }
	                }
	                if (optionFound || (currentCount == previousCount)) {
	                    break;
	                }
	                previousCount = currentCount;
	                WebElement lastItem = dropdownItems.get(dropdownItems.size() - 1);
	                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", lastItem);
	                Thread.sleep(5000);
	            }

	            if (!optionFound) {
	                throw new NoSuchElementException("Option '" + optionToSelect + "' not found in dropdown.");
	            }
	        } catch (Exception e) {
	            System.err.println("Error selecting dropdown option: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }
	 public void selectRecruiterdropdown(String value) {
	        try {
	            Actions actions = new Actions(driver);

	            String xpathExpression = "//input[@placeholder=\"Recruiter\"]";
	            WebElement parentCostCenter = new WebDriverWait(driver, Duration.ofSeconds(10))
	                    .until(ExpectedConditions.elementToBeClickable(By.xpath(xpathExpression)));

	            actions.click(parentCostCenter)
	                   .keyDown(Keys.CONTROL)
	                   .sendKeys("a")
	                   .keyUp(Keys.CONTROL)
	                   .sendKeys(Keys.BACK_SPACE)
	                   .perform();

	            parentCostCenter.sendKeys(value);

	            Thread.sleep(6000);
	            actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();

	        } catch (Exception e) {
	            System.out.println("Failed to interact with Parent Cost Center field: " + e.getMessage());
	        }
	    }
	 public void selectPrimaryAndSecondaryValue(int dropdownIndex) {
         try {
             Actions actions = new Actions(driver);
             Thread.sleep(2000); // Use WebDriverWait in production code

             // Clear input field - update the locator as needed

             // Dynamically locate the dropdown icon based on the index
             String dropdownXpath = "(//label[@class='hiddenAriaContent']//following::i[@class='sap-icon--slim-arrow-down'])[" + dropdownIndex + "]";
             WebElement dropdown = driver.findElement(By.xpath(dropdownXpath));
             dropdown.click();

             Thread.sleep(3000); // Wait for dropdown to expand

             // Select the first item from the dropdown list
             WebElement firstListItem = driver.findElement(By.xpath("(//div[@class='globalMenu sf-combo-listselect']//div/a)[1]"));
             JavascriptExecutor js = (JavascriptExecutor) driver;

             // Scroll until visible (if needed)
             while (!firstListItem.isDisplayed()) {
                 js.executeScript("arguments[0].scrollIntoView(true);", firstListItem);
                 Thread.sleep(500);
             }

             firstListItem.click();

         } catch (Exception e) {
             System.out.println("Dropdown selection failed: " + e.getMessage());
         }
     }


	 public void setstandardhours(String value) {
	        try {
	            Actions actions = new Actions(driver);

	            String xpathExpression = "(//label[@class='hiddenAriaContent']/following::input[@placeholder='Click or focus to edit'])[4]";
	            WebElement parentCostCenter = new WebDriverWait(driver, Duration.ofSeconds(10))
	                    .until(ExpectedConditions.elementToBeClickable(By.xpath(xpathExpression)));

	            actions.click(parentCostCenter)
	                   .keyDown(Keys.CONTROL)
	                   .sendKeys("a")
	                   .keyUp(Keys.CONTROL)
	                   .sendKeys(Keys.BACK_SPACE)
	                   .perform();

	            parentCostCenter.sendKeys(value);

	            Thread.sleep(6000);
	            actions.sendKeys(Keys.TAB).perform();

	        } catch (Exception e) {
	            System.out.println("Failed to interact with Parent Cost Center field: " + e.getMessage());
	        }
	    }

	 public void selectsearchentity(int index, String value) {

	        try {
	            Actions actions = new Actions(driver);

	            String xpathExpression = "(//input[@role='combobox'])[" + index + "]";
	            WebElement parentCostCenter = new WebDriverWait(driver, Duration.ofSeconds(10))
	                    .until(ExpectedConditions.elementToBeClickable(By.xpath(xpathExpression)));
	            actions.click(parentCostCenter)
	                   .keyDown(Keys.CONTROL)
	                   .sendKeys("a")
	                   .keyUp(Keys.CONTROL)
	                   .sendKeys(Keys.BACK_SPACE)
	                   .perform();
	            parentCostCenter.sendKeys(value);
	            Thread.sleep(5000);
	            actions.sendKeys(Keys.ENTER).perform();
	        } catch (Exception e) {
	            System.out.println("Failed to interact with Parent Cost Center field: " + e.getMessage());
	        }
	    }
	 public void manageDataModifyDeactivate(int index,String value) {
	        try {

	        	 String dropdownXpath = "(//label[@class=\"hiddenAriaContent\"]//following::i[@class=\"sap-icon--slim-arrow-down\"])";
//
	        	String xpathExpression = "(//input[@role='combobox'])[" + index + "]";
	            WebElement parentCostCenter = new WebDriverWait(driver, Duration.ofSeconds(10))
	                    .until(ExpectedConditions.elementToBeClickable(By.xpath(xpathExpression)));
	            actions.click(parentCostCenter)
	                   .keyDown(Keys.CONTROL)
	                   .sendKeys("a")
	                   .keyUp(Keys.CONTROL)
	                   .sendKeys(Keys.BACK_SPACE)
	                   .perform();
	            parentCostCenter.sendKeys(value);
	            Thread.sleep(5000);
	            actions.sendKeys(Keys.ENTER).perform();
	            WebElement dropdown2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dropdownXpath+"[2]")));
	            dropdown2.click();
	            WebElement selectFirstOptionHeadOfDivisionDropdowndropdownList = driver.findElement(By.xpath("(//div[@class='globalMenu sf-combo-listselect']//div/a)[" + index + "]"));
	            while (!selectFirstOptionHeadOfDivisionDropdowndropdownList.isDisplayed()) {
	            	((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 100);");
	            	Thread.sleep(500);
	            }
	            selectFirstOptionHeadOfDivisionDropdowndropdownList.click();
	            Thread.sleep(5000);
	        } catch (Exception e) {
	            System.err.println("Error selecting dropdown option: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }
	 public void enterDateWithOffset(int elementIndex, int daysOffset) throws InterruptedException {
	        String xpath = "(//ui5-date-picker-xweb-calendar-widget[@format-pattern='MM/dd/yyyy'])[" + elementIndex + "]";
	        WebElement dateInput = driver.findElement(By.xpath(xpath));

	        // Clear existing input
	        actions.click(dateInput)
	               .keyDown(Keys.CONTROL)
	               .sendKeys("a")
	               .keyUp(Keys.CONTROL)
	               .sendKeys(Keys.BACK_SPACE)
	               .perform();

	        Thread.sleep(500);

	        // Calculate future or past date
	        LocalDate targetDate = LocalDate.now().plusDays(daysOffset);
	        String formattedDate = targetDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

	        // Type the date character by character
	        for (char c : formattedDate.toCharArray()) {
	            actions.sendKeys(String.valueOf(c)).perform();
	            Thread.sleep(100);
	        }

	        // Finalize input
	        Thread.sleep(300);
	        actions.sendKeys(Keys.TAB).perform();
	        Thread.sleep(300);
	    }

	 public void selectPositionSearchDropdown(int index, String value) {

	        try {
	            Actions actions = new Actions(driver);

	            String xpathExpression = "(//label[@class='hiddenAriaContent']/following::input[@role='combobox'])[" + index + "]";
	            WebElement parentCostCenter = new WebDriverWait(driver, Duration.ofSeconds(10))
	                    .until(ExpectedConditions.elementToBeClickable(By.xpath(xpathExpression)));

	            actions.click(parentCostCenter)
	                   .keyDown(Keys.CONTROL)
	                   .sendKeys("a")
	                   .keyUp(Keys.CONTROL)
	                   .sendKeys(Keys.BACK_SPACE)
	                   .perform();

	            parentCostCenter.sendKeys(value);

	            Thread.sleep(5000);
	            actions.sendKeys(Keys.ENTER).perform();

	        } catch (Exception e) {
	            System.out.println("Failed to interact with Parent Cost Center field: " + e.getMessage());
	        }
	    }

	 public void SelectCurrency(int index, String positionName) throws InterruptedException {
	        String xpath = "(//input[@role='combobox'])[" + index + "]";
	        Actions actions = new Actions(driver);
	        WebElement inputField = new WebDriverWait(driver, Duration.ofSeconds(10))
	                .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

	        actions.click(inputField)
	                .keyDown(Keys.CONTROL)
	                .sendKeys("a")
	                .keyUp(Keys.CONTROL)
	                .sendKeys(Keys.BACK_SPACE)
	                .perform();

	        inputField.sendKeys(positionName);
	        Thread.sleep(5000);
	        actions.sendKeys(Keys.ENTER).perform();
	    }
	public void SelectValueBySearch(int index, String positionName) throws InterruptedException {
	        String xpath = "(//input[@role='combobox'])[" + index + "]";
	        Actions actions = new Actions(driver);
	        WebElement inputField = new WebDriverWait(driver, Duration.ofSeconds(10))
	                .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

	        actions.click(inputField)
	                .keyDown(Keys.CONTROL)
	                .sendKeys("a")
	                .keyUp(Keys.CONTROL)
	                .sendKeys(Keys.BACK_SPACE)
	                .perform();

	        inputField.sendKeys(positionName);
	        Thread.sleep(5000);
	        actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();
	    }

	public WebElement getTextArea() {
        return driver.findElement(By.xpath(
        String.format("//textarea[@class='formElement ectTextArea hideScrollbar fd-textarea fd-textarea--compact']")));
        }



	public String handleWorkflowParticipant() throws InterruptedException {


        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement viewWorkflowBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[contains(text(),'View Workflow Participants')]")));
        viewWorkflowBtn.click();
        Thread.sleep(5000);
        WebElement managerNameSpan = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[contains(@id, '_userText')]")));

        String getManagerName = managerNameSpan.getText();
        System.out.println("Manager Name: " + getManagerName);

        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(),'Submit')]")));
        submitButton.click();

        actions.sendKeys(Keys.ENTER).perform();
        Thread.sleep(5000);
        driver.navigate().refresh();

        return getManagerName;
    }



    public void proxyAsManager1(String managerName,int count) {
        try {


            Actions actions = new Actions(driver);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            Thread.sleep(2000);

            for (int i = 0; i < count; i++) {
                actions.sendKeys(Keys.TAB).perform();
                Thread.sleep(500);
            }

            actions.sendKeys(Keys.ENTER).perform();
            Thread.sleep(500);

//            for (int i = 0; i < 1; i++) {
//                actions.sendKeys(Keys.ARROW_DOWN).perform();
//
//                Thread.sleep(1000);
//            }
            actions.sendKeys(Keys.TAB).perform();         // Move focus from "Public Profile"
            Thread.sleep(500);
            actions.sendKeys(Keys.ARROW_DOWN).perform();  // Move to "Proxy Now"
            Thread.sleep(1000);
            actions.sendKeys(Keys.ENTER).perform();
            Thread.sleep(2000);


            for (char c : managerName.toCharArray()) {
                actions.sendKeys(String.valueOf(c)).perform();
                Thread.sleep(100);
            }

            Thread.sleep(10000);
            actions.sendKeys(Keys.ENTER).perform();
            Thread.sleep(500);
            actions.sendKeys(Keys.ENTER).perform();
            Thread.sleep(500);

            actions.sendKeys(Keys.TAB).perform();
            Thread.sleep(2000);
            actions.sendKeys(Keys.ENTER).perform();
            Thread.sleep(2000);

            WebElement quickActionsLabel = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h2[text()='Quick Actions']")));
            if (quickActionsLabel.getText().equals("Quick Actions")) {
                System.out.println("Quick Actions label verified.");
            }
            Thread.sleep(5000);

            for (int i = 1; i < 5; i++) {
                actions.sendKeys(Keys.TAB).perform();
                Thread.sleep(500);
            }

            actions.sendKeys(Keys.ENTER).perform();
            Thread.sleep(2000);
            actions.sendKeys(Keys.ARROW_DOWN).perform();
            Thread.sleep(2000);
            actions.sendKeys(Keys.ARROW_DOWN).perform();
            Thread.sleep(2000);
            actions.sendKeys(Keys.ENTER).perform();
            Thread.sleep(2000);

        } catch (Exception e) {
            System.out.println("Error in proxyAsManager: " + e.getMessage());
        }
    }
      public void proxyAsManager(String managerName,int count) {
        try {

            Actions actions = new Actions(driver);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            Thread.sleep(2000);

            for (int i = 1; i < count; i++) {
                actions.sendKeys(Keys.TAB).perform();
                Thread.sleep(500);
            }

            actions.sendKeys(Keys.ENTER).perform();
            Thread.sleep(5000);

            for (int i = 0; i < 2; i++) {
                actions.sendKeys(Keys.ARROW_DOWN).perform();
                Thread.sleep(500);
            }
            actions.sendKeys(Keys.ENTER).perform();
            Thread.sleep(2000);


            for (char c : managerName.toCharArray()) {
                actions.sendKeys(String.valueOf(c)).perform();
                Thread.sleep(100);
            }

            Thread.sleep(10000);
            actions.sendKeys(Keys.ENTER).perform();
            Thread.sleep(500);
            actions.sendKeys(Keys.ENTER).perform();
            Thread.sleep(500);

            actions.sendKeys(Keys.TAB).perform();
            Thread.sleep(2000);
            actions.sendKeys(Keys.ENTER).perform();
            Thread.sleep(2000);

            WebElement quickActionsLabel = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h2[text()='Quick Actions']")));
            if (quickActionsLabel.getText().equals("Quick Actions")) {
                System.out.println("Quick Actions label verified.");
            }
            Thread.sleep(5000);

            for (int i = 1; i < 5; i++) {
                actions.sendKeys(Keys.TAB).perform();
                Thread.sleep(500);
            }

            actions.sendKeys(Keys.ENTER).perform();
            Thread.sleep(2000);
            actions.sendKeys(Keys.ARROW_DOWN).perform();
            Thread.sleep(2000);
            actions.sendKeys(Keys.ARROW_DOWN).perform();
            Thread.sleep(2000);
            actions.sendKeys(Keys.ENTER).perform();
            Thread.sleep(2000);

        } catch (Exception e) {
            System.out.println("Error in proxyAsManager: " + e.getMessage());
        }
    }
    public void clickApproveButtonWithIndex() throws InterruptedException {
        Thread.sleep(1000);

        String xpath = String.format("(//bdi[text()='Approve'])[1]");
        WebElement approveButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath(xpath))
        );
        approveButton.click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        driver.navigate().refresh();
    }





}
