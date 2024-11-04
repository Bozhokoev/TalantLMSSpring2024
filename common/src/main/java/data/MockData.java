package data;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Slf4j
public class MockData {

    private static final Faker faker = new Faker();

    private static final List<String> SPECIAL_SYMBOLS = List.of(
            "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "_", "=", "+",
            "[", "]", "{", "}", "|", "\\", ":", ";", "'", "\"", ",", "/",
            "<", ">", "?", "~", "`"
    );

    private static final String CHARACTERS_EN = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHARACTERS_RU = "абвгдеёжзийклмнопрстуфхцчшщыэюя";

    public static String generateRandomSpecialSymbol() {
        var random = faker.random();
        var index = random.nextInt(SPECIAL_SYMBOLS.size());
        return SPECIAL_SYMBOLS.get(index);
    }

    public static String generateRandomSpecialSymbol(int length) {
        var result = new StringBuilder(length);
        var random = new Random();

        for (var i = 0; i < length; i++) {
            var randomSymbol = SPECIAL_SYMBOLS.get(random.nextInt(SPECIAL_SYMBOLS.size()));
            result.append(randomSymbol);
        }

        return result.toString();
    }

    public static String generateRandomSymbols(int length) {
        String symbols = "/-?:().,'+";

        Random random = new Random();

        StringBuilder randomString = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(symbols.length());
            randomString.append(symbols.charAt(index));
        }

        return randomString.toString();
    }

    public static String getDummyMessage() {
        return faker.chuckNorris().fact();
    }

    public static double getRandomDouble(int numberOfDecimals) {
        return faker.number().randomDouble(numberOfDecimals, 100, 200);
    }

    public static String generatePassword() {
        String s = faker.internet().password(2, 5, true, true, true);
        s = s + "1qazXSW@!23";
        log.info("Generated password: {}", s);
        return s;
    }

    public static String generatePhoneNumber() {
        // Define possible ranges for the first 3 digits
        int[][] numberRanges = {
                {700, 709},
                {500, 509},
                {550, 559},
                {770, 779}
        };

        Random random = new Random();

        // Select a range randomly
        int[] selectedRange = numberRanges[random.nextInt(numberRanges.length)];

        // Generate the first 3 digits from the selected range
        int firstThreeDigits = random.nextInt(selectedRange[1] - selectedRange[0] + 1) + selectedRange[0];

        // Generate the next 6 random digits (but only take 6 digits to ensure a 9-digit total)
        StringBuilder remainingDigits = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            remainingDigits.append(random.nextInt(10));  // Random number from 0 to 9
        }

        // Construct the 9-digit phone number
        String phoneNumber = firstThreeDigits + remainingDigits.toString();

        // Log the generated phone number
        log.info("Generated phone number: {}", phoneNumber);

        return phoneNumber;
    }

    public static String generatePhoneNumber(int number) {
        String s = faker.phoneNumber().subscriberNumber(number);
        log.info("Generated phone number: {}", s);
        return s;
    }

    public static String generateEmail() {
        String s = faker.internet().uuid() + "@hotmail.com";
        log.info("Generated email: {}", s);
        return s;
    }

    public static String generateName() {
        return faker.name().firstName();
    }

    public static String generateLastName() {
        return faker.name().lastName();
    }



    public static String generateMock() {
        return faker.business().creditCardType();
    }

    public static String generateNoAttach() {
        return "not_needed";
    }

    public static int generateNum(int min, int max) {
        return faker.number().numberBetween(min, max);
    }

    public static String generateUuid() {
        return faker.internet().uuid();
    }

    public static double generateDouble(int min, int max) {
        return faker.number().randomDouble(2, min, max);
    }

    public static String generatePostalCode() {
        return faker.address().buildingNumber();
    }

    public static String generateAddress() {
        return faker.address().fullAddress();
    }

    public static String generateState() {
        return faker.address().state();
    }

    public static String generateCompanyName() {
        return faker.commerce().productName();
    }

    public static int generateRegistrationNumber() {
        return faker.number().randomDigit();
    }

    public static int generateCompanyOperates() {
        return faker.number().randomDigit();
    }

    public static String generateCity() {
        return faker.address().city();
    }

    public static String generateLogin() {
        return "+996" + generatePhoneNumber();
    }

    public static String generateMiddleName() {
        return faker.name().firstName();
    }


    public static String autotestNameWithDateAndTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return "AutoTest " + now.format(formatter);
    }

    public static String generateDateToday() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return now.format(formatter);
    }

    public static String generateRandomWord(int textLength) {
        var random = new Random();
        var word = new StringBuilder();
        for (var i = 0; i < textLength; i++) {
            var index = random.nextInt(CHARACTERS_EN.length());
            word.append(CHARACTERS_EN.charAt(index));
        }
        return word.toString();
    }

    public static String generateRandomWordRu(int textLength) {
        var random = new Random();
        var word = new StringBuilder();
        for (var i = 0; i < textLength; i++) {
            var index = random.nextInt(CHARACTERS_RU.length());
            word.append(CHARACTERS_RU.charAt(index));
        }
        return word.toString();
    }

    public static String generateRetrievalRefNumber() {
        return faker.number().digits(11);
    }

    public static String generateMerchantName() {
        return "AUTOTEST " + faker.name().username().toUpperCase();
    }

    public static String generateMerchantId() {
        return faker.number().digits(15);
    }

    public static String generateTransLink() {
        return faker.number().digits(12) + generateRandomWord(9).toUpperCase() + faker.number().digits(19);
    }

    public static String generateInt(int length) {
        return faker.number().digits(length);
    }

    public static String generateTraceId() {
        return faker.business().creditCardType().toUpperCase();
    }

    public static String generateProgramName() {
        return faker.backToTheFuture().quote().toUpperCase();
    }

    public static String generateCardNumber() {
        return generateInt(6) + "******" + generateInt(4);
    }

    public static void main(String[] args) {
        System.out.println(generateRandomWord(5));
    }


}