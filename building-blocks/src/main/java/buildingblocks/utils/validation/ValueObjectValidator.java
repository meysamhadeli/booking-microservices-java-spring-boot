package buildingblocks.utils.validation;

import java.math.BigDecimal;
import java.util.UUID;

public final class ValueObjectValidator {

    private ValueObjectValidator() {
        throw new AssertionError("Cannot instantiate utility class.");
    }

    public static void assertNonNullOrEmpty(String value) {
        if(value.isBlank())
            throw new IllegalArgumentException("Value cannot be empty.");
    }

    public static void assertNonNullOrEmpty(Object obj) {
        if(obj == null)
            throw new IllegalArgumentException("Value cannot be empty.");
    }

    public static void assertNonNullOrEmpty(UUID value) {
        if (value == null || value.equals(new UUID(0L, 0L)))
            throw new IllegalArgumentException("Value cannot be empty.");
    }

    public static void assertNonNegativeOrNull(Number number) {
        assertNonNullOrEmpty(number);

        // Convert the number to BigDecimal for accurate comparison
        BigDecimal bigDecimalValue = new BigDecimal(number.toString());
        if (bigDecimalValue.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Number cannot be negative.");
        }
    }
}

