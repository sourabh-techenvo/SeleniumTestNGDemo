package testSFEC;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

// import ExtentReports.ExtentTestManager;

public class Utilities {

    private WebDriverWait wait;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final DateTimeFormatter formatterTwo = DateTimeFormatter.ofPattern("MMM dd, yyyy");


    public Utilities(WebDriver driver) {
    	  this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public static String generateRandomValue(int length) {
        String lowercaseCharacters = "abcdefghijklmnopqrstuvwxyz";
        String uppercaseCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder randomText = new StringBuilder();
        if (length > 0) {
            int firstLetterIndex = random.nextInt(uppercaseCharacters.length());
            randomText.append(uppercaseCharacters.charAt(firstLetterIndex));
        }
        for (int i = 1; i < length; i++) {
            int index = random.nextInt(lowercaseCharacters.length());
            randomText.append(lowercaseCharacters.charAt(index));
        }
        return randomText.toString();
    }

    public static String generateIntegers(int length) {
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomInt = random.nextInt(10);
            result.append(randomInt);
        }
        return result.toString();
    }




    // 1. Generate a unique-looking Gmail (e.g., user_a7x2b3q8@gmail.com)
    public static String generateUniqueGmail() {
        String randomString = generateRandomString(8);
        return "user_" + randomString.toLowerCase() + "@gmail.com";
    }

    // 2. Generate random alphanumeric string of given length
    public static String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        return sb.toString();
    }

    // 3. Get past date (7 days ago)
    public static String getPastDate() {
        LocalDate pastDate = LocalDate.now().minusDays(7);
        return pastDate.format(formatter);
    }

    // 4. Get today's date
    public static String getPresentDate() {
        LocalDate today = LocalDate.now();
        return today.format(formatter);
    }

    // 5. Get future date (7 days ahead)
    public static String getFutureDate() {
        LocalDate futureDate = LocalDate.now().plusDays(7);
        return futureDate.format(formatter);
    }
	public static String UpdategeneratePastDate(int daysInPast) {
		Random random = new Random();
		// Generate a random number of days between now and the specified past range
		int randomDays = random.nextInt(daysInPast + 1);
		LocalDate pastDate = LocalDate.now().minusDays(randomDays);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd,yyyy");
		return pastDate.format(formatter);

	}

	public static String generatePhoneNumber() {
        Random random = new Random();
        StringBuilder phoneNumber = new StringBuilder("9");

        for (int i = 0; i < 9; i++) {
            int digit = random.nextInt(10);
            phoneNumber.append(digit);
        }
        return phoneNumber.toString();
    }

    /**
     * Generates the country code for India (+91).
     * @return A string containing "+91".
     */

    public static String generateIndiaCountryCode() {
        return "+91";
    }

private static final String ALPHABETS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String generateRandomUppercaseLetters(int length) {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(ALPHABETS.charAt(rand.nextInt(ALPHABETS.length())));
        }

        return sb.toString();
    }

    public static String getFutureDateFormatTwo() {
        LocalDate futureDate = LocalDate.now().plusDays(14);
        return futureDate.format(formatterTwo);
    }
public static String getPastDateFormatTwo() {
        LocalDate pastDate = LocalDate.now().minusDays(25);
        return pastDate.format(formatterTwo);
    }


}







