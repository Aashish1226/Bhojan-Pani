package Food.FoodDelivery.project.Enum;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum OrderSortField {
    ORDER_STATUS("orderStatus"),
    ORDER_PLACED_AT("orderPlacedAt"),
    FINAL_AMOUNT("finalAmount");

    private final String field;

    OrderSortField(String field) {
        this.field = field;
    }

    public static boolean isValid(String value) {
        return Arrays.stream(values()).anyMatch(f -> f.getField().equalsIgnoreCase(value));
    }
}
