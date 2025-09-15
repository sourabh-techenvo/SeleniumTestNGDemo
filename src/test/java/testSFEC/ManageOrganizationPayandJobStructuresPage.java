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

public class ManageOrganizationPayandJobStructuresPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;
    private JavascriptExecutor js;

    public ManageOrganizationPayandJobStructuresPage(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("WebDriver instance is null!");
        }     this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.actions = new Actions(driver);
        this.js = (JavascriptExecutor) driver;

        PageFactory.initElements(driver, this);
    }


    @FindBy(xpath = "//button[@title='Save']")
    private WebElement save;

    @FindBy(xpath = "//a[@title='Click or press enter to change state to deleted']")
    private WebElement delete_icon;

    @FindBy(xpath = "//button[@title='Yes']")
    private WebElement yes;

    @FindBy(xpath = "(//button[@class='globalRoundedCornersXSmall globalPrimaryButton fd-button fd-button--compact fd-button--emphasized'])[2]")
    private WebElement okBtn;

    @FindBy(xpath = "//button[@title='Add']")
    private WebElement add;

    @FindBy(xpath = "//a[@class='ectMultipleRelationAddIcon']")
    private WebElement payComponent;

    @FindBy(xpath = "//a[@title='Click or press enter to change state to deleted']")
    private WebElement delete;

    public WebElement getInputField(int index) {
        return driver.findElement(By.xpath(
                String.format(
                        "(//label[@class='hiddenAriaContent']/following::input[@class='formElement  fd-input fd-input--compact'])[%d]", index)));
    }

    public WebElement getTextArea(int index) {
        return driver.findElement(By.xpath(
                String.format("(//label[@class=\"hiddenAriaContent\"]/following::textarea[@class=\"formElement ectTextArea hideScrollbar fd-textarea fd-textarea--compact\"])[%d]"
                        , index)));
    }
    public void clickOnOk() {
        try {
            WebElement annualizationFactorElement = wait.until(ExpectedConditions.elementToBeClickable(okBtn));
            annualizationFactorElement.click();
            Thread.sleep(5000); // Equivalent to WebUI.delay(5)

        } catch (Exception e) {
             System.out.println("Normal click failed");
        }
    }
    public void clickOnDelete() {
        try {
            // Use findElements which returns an empty list if nothing is found
            List<WebElement> deleteIcons = driver.findElements(By.xpath("delete"));

            if (!deleteIcons.isEmpty()) {
                WebElement deleteIcon = deleteIcons.get(0);
                if (deleteIcon.isDisplayed() && deleteIcon.isEnabled()) {
                    wait.until(ExpectedConditions.elementToBeClickable(deleteIcon)).click();
                    Thread.sleep(5000); // Optional delay
                    System.out.println("Delete icon clicked.");
                } else {
                    System.out.println("Delete icon is present but not clickable.");
                }
            } else {
                System.out.println("Delete icon not found. Moving ahead.");
            }

        } catch (Exception e) {
            System.out.println("An error occurred while trying to click on the delete icon: " + e.getMessage());
        }
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
    public void Save() {
        try {
            WebElement annualizationFactorElement = wait.until(ExpectedConditions.elementToBeClickable(save));
            annualizationFactorElement.click();
            Thread.sleep(5000); // Equivalent to WebUI.delay(5)

        } catch (Exception e) {
             System.out.println("Normal click failed");
        }
    }

    public void selectDropdownOption(String dropdownXpath, String listContainerXpath, String optionToSelect) {
        try {
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dropdownXpath)));
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
    public void selectsearchdropdown(int index, String value) {

        try {
            Actions actions = new Actions(driver);

            String xpathExpression = "(//input[@class='formElement fd-input fd-input--compact fd-input-group__input fd-input-group__input--compact'])[" + index + "]";
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
    public void clickAddNewAssociationButton() {
        try {
            String xpath = "(//a[@class='ectMultipleRelationAddIcon'])[1]";
            WebElement addNewAssociationButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addNewAssociationButton);
            Thread.sleep(500);
            wait.until(ExpectedConditions.elementToBeClickable(addNewAssociationButton)).click();
            System.out.println("Clicked 'Add New Association' button successfully.");

        } catch (Exception e) {
            System.err.println("Failed to click Add New Association button: " + e.getMessage());
            e.printStackTrace();
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

    public void manageOrganizationModifyDeactivate(String optionToSelect) {
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
    public void manageOrganizationModify(String optionToSelect, String frequencyValue) {
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

            frequencyInput.sendKeys(frequencyValue);

            Thread.sleep(3000);

            actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();

            System.out.println("Successfully selected frequency: " + frequencyValue);
            Thread.sleep(3000);

        } catch (Exception e) {
            System.err.println("Error selecting dropdown option: " + e.getMessage());
            e.printStackTrace();
        }
    }



    public void insertNewRecord() throws InterruptedException {
        js = (JavascriptExecutor) driver;

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        Thread.sleep(5000);
        WebElement insertNewRecordLabel = driver.findElement(By.xpath("//span[@class='text fd-button__text fd-button__text--compact']"));
        insertNewRecordLabel.click();
        WebElement proceedBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@title='Proceed']")));
        Thread.sleep(5000);
        WebElement dateInput = driver.findElement(By.xpath("//div[@class='datePicker ectControl sapUiSizeCompact ']//following::ui5-date-picker-xweb-calendar-widget[@placeholder='MM/DD/YYYY']")); // Adjust locator
        actions.click(dateInput)
               .keyDown(Keys.CONTROL)
               .sendKeys("a")
               .keyUp(Keys.CONTROL)
               .sendKeys(Keys.BACK_SPACE)
               .perform();

        Thread.sleep(5000); // Equivalent to WebUI.delay(5)
        LocalDate today = LocalDate.now();
        LocalDate futureDate = today.plusDays(6);
        String formattedDate = futureDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        for (char c : formattedDate.toCharArray()) {
            actions.sendKeys(String.valueOf(c)).perform();
            Thread.sleep(1000);
        }
        proceedBtn.click();
    }
    public void takeActionMakeCorrection(String dynamicLabel, int dynamicIndex) {
        try {
            String xpath = "(//a[@class='SFContextualMenuLabel link fd-button fd-button--compact fd-button--menu'])[" + dynamicIndex + "]";
            WebElement dropdownElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

            // Perform mouse hover
            Actions actions = new Actions(driver);
            actions.moveToElement(dropdownElement).perform();

            // Instead of TAB, simulate ARROW_DOWN then ENTER
            actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();

            System.out.println("Take action triggered for label: " + dynamicLabel);

        } catch (Exception e) {
            System.err.println("Error in takeActionMakeCorrection: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void selectEffectiveStartDate(int daysToAdd) {
        try {

            Thread.sleep(5000);

            String xpath = "(//ui5-date-picker-xweb-calendar-widget[@placeholder='MM/DD/YYYY'])[1]";
            WebElement dateInput = driver.findElement(By.xpath(xpath));

            actions.click(dateInput)
                   .keyDown(Keys.CONTROL)
                   .sendKeys("a")
                   .keyUp(Keys.CONTROL)
                   .sendKeys(Keys.BACK_SPACE)
                   .perform();

            Thread.sleep(5000);

            LocalDate today = LocalDate.now();
            LocalDate futureDate = today.minusDays(daysToAdd);
            String formattedDate = futureDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

            for (char c : formattedDate.toCharArray()) {
                actions.sendKeys(String.valueOf(c)).perform();
                Thread.sleep(100);
            }

        } catch (Exception e) {
            System.out.println("Failed to select effective start date: " + e.getMessage());
        }
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
    public void clickOnPayComponent() {
        try {
            WebElement annualizationFactorElement = wait.until(ExpectedConditions.elementToBeClickable(payComponent));
            annualizationFactorElement.click();
            Thread.sleep(5000); // Equivalent to WebUI.delay(5)

        } catch (Exception e) {
             System.out.println("Normal click failed");
        }
    }
    public WebElement selectHistory(int index) {
        return driver.findElement(By.xpath(
                String.format(
                        "(//div[@class='titleBlock'])[%d]", index)));
    }


    public void selectDropdownOption(int dropdownIndex, String optionToSelect) {
        try {
            String selectDropdown = "(//label[@class='hiddenAriaContent']//following::span[@class='toggle fd-input-group__addon fd-input-group__addon--compact fd-input-group__addon--button'])[" + dropdownIndex + "]";
            String selectDropdownList = "(//div[@class='globalMenu sf-combo-listselect'])[1]";
            Thread.sleep(500);
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(selectDropdown)));
            dropdown.click();
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
 public void selectsearchlocation(int index, String value) {

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
         actions.sendKeys(Keys.ARROW_DOWN).perform();
         Thread.sleep(1000);
         actions.sendKeys(Keys.ENTER).perform();

     } catch (Exception e) {
         System.out.println("Failed to interact with Parent Cost Center field: " + e.getMessage());
     }
 }
 public void takeActionMakeCorrectionHistory(String dynamicLabel, int dynamicIndex) throws InterruptedException {
     String xpath = "(//div[@class='titleBlock'])[" + dynamicIndex + "]";
     WebElement dynamicElement = driver.findElement(By.xpath(xpath));
     dynamicElement.click();
     Thread.sleep(5000);
     int index = 1;
     String dropdownXPath = String.format("(//a[@class='SFContextualMenuLabel link fd-button fd-button--compact fd-button--menu'])[%d]", index);
     WebElement dropdown = driver.findElement(By.xpath(dropdownXPath));
     dropdown.click();
     Thread.sleep(500);
     actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).perform();

 }
 public void clickOnDeleteIconIfExists() {
	    try {
	        List<WebElement> deleteIcons = driver.findElements(By.xpath("//a[@title='Click or press enter to change state to deleted']"));

	        if (!deleteIcons.isEmpty()) {
	            WebElement deleteIcon = deleteIcons.get(0); // Or loop if needed
	            wait.until(ExpectedConditions.elementToBeClickable(deleteIcon)).click();
	            System.out.println("Delete icon clicked.");
	        } else {
	            System.out.println("Delete icon not found. Skipping...");
	        }

	    } catch (Exception e) {
	        System.out.println("Exception while trying to click delete icon: " + e.getMessage());
	    }
	}


}
