package Food.FoodDelivery.project.Enum;

import java.util.Arrays;

public enum FoodType {
    VEGETARIAN, NON_VEGETARIAN, VEGAN;

    public static boolean isValid(String value) {
        return Arrays.stream(FoodType.values())
                .anyMatch(type -> type.name().equalsIgnoreCase(value));
    }
}