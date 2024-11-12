package data;

import com.github.javafaker.Faker;

public class MockDataGenerator {
    private static Faker faker = new Faker();

    public static String randomFirstName(){
        return faker.name().firstName();
    }

    public static String randomLastName(){
        return faker.name().lastName();
    }

    public static String randomEmail(){
        return faker.internet().emailAddress();
    }

    public static String randomGrade(){
        return faker.options().option("Junior", "Middle", "Senior", "Lead");
    }
}
