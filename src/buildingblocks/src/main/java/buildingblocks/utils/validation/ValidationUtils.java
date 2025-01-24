package buildingblocks.utils.validation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;
import org.springframework.lang.Nullable;

public class ValidationUtils {

    private ValidationUtils() {
        throw new AssertionError("Cannot instantiate utility class.");
    }

    private static final Set<String> ALLOWED_CURRENCIES = Set.of("USD", "EUR");

    public static <T> T notBeNull(@Nullable T argument, String argumentName) {
        if (argument == null) {
            throw new IllegalArgumentException(argumentName + " cannot be null.");
        }
        return argument;
    }

    public static String notBeEmpty(@Nullable String argument, String argumentName) {
        if (argument == null || argument.isEmpty()) {
            throw new IllegalArgumentException(argumentName + " cannot be null or empty.");
        }
        return argument;
    }

    public static String notBeNullOrWhiteSpace(@Nullable String argument, String argumentName) {
        if (argument == null || argument.trim().isEmpty()) {
            throw new IllegalArgumentException(argumentName + " cannot be null, empty, or whitespace.");
        }
        return argument;
    }

    public static UUID notBeEmpty(@Nullable UUID argument, String argumentName) {
        if (argument == null || argument.equals(UUID.fromString("00000000-0000-0000-0000-000000000000"))) {
            throw new IllegalArgumentException(argumentName + " cannot be null or empty.");
        }
        return argument;
    }

    public static int notBeNegativeOrZero(int argument, String argumentName) {
        if (argument <= 0) {
            throw new IllegalArgumentException(argumentName + " must be greater than zero.");
        }
        return argument;
    }

    public static long notBeNegativeOrZero(long argument, String argumentName) {
        if (argument <= 0) {
            throw new IllegalArgumentException(argumentName + " must be greater than zero.");
        }
        return argument;
    }

    public static double notBeNegativeOrZero(double argument, String argumentName) {
        if (argument <= 0) {
            throw new IllegalArgumentException(argumentName + " must be greater than zero.");
        }
        return argument;
    }

    public static String notBeInvalidEmail(String email, String argumentName) {
        String emailRegex = "^[\\w.-]+@[\\w-]+\\.[a-z]{2,}$";
        if (!Pattern.matches(emailRegex, email)) {
            throw new IllegalArgumentException(argumentName + " is not a valid email address.");
        }
        return email;
    }

    public static String notBeInvalidPhoneNumber(String phoneNumber, String argumentName) {
        String phoneRegex = "^[+]?\\d{10,15}$";
        if (!Pattern.matches(phoneRegex, phoneNumber)) {
            throw new IllegalArgumentException(argumentName + " is not a valid phone number.");
        }
        return phoneNumber;
    }

    public static String notBeInvalidCurrency(@Nullable String currency, String argumentName) {
        if (currency == null || !ALLOWED_CURRENCIES.contains(currency.toUpperCase())) {
            throw new IllegalArgumentException(argumentName + " is not a valid currency.");
        }
        return currency;
    }

    public static <T extends Enum<T>> T notBeEmpty(@Nullable T enumValue, String argumentName) {
        if (enumValue == null) {
            throw new IllegalArgumentException(argumentName + " cannot be null.");
        }
        return enumValue;
    }

    public static <T extends Enum<T>> T notBeDefault(@Nullable T enumValue, String argumentName) {
        if (enumValue == null || enumValue.ordinal() == 0) {
            throw new IllegalArgumentException(argumentName + " cannot be the default enum value.");
        }
        return enumValue;
    }

    public static void notBeNullOrEmpty(String value) {
        if(value.isBlank())
            throw new IllegalArgumentException("Value cannot be empty.");
    }

    public static void notBeNullOrEmpty(Object obj) {
        if(obj == null)
            throw new IllegalArgumentException("Value cannot be empty.");
    }

    public static void notBeNullOrEmpty(UUID value) {
        if (value == null || value.equals(new UUID(0L, 0L)))
            throw new IllegalArgumentException("Value cannot be empty.");
    }

    public static void notBeNegativeOrNull(Number number) {
        notBeNullOrEmpty(number);

        // Convert the number to BigDecimal for accurate comparison
        BigDecimal bigDecimalValue = new BigDecimal(number.toString());
        if (bigDecimalValue.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Number cannot be negative.");
        }
    }

    public static void validLocalDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            throw new IllegalArgumentException("Date and time cannot be null.");
        }
    }
}
