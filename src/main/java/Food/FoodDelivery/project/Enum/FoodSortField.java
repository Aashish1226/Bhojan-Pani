package Food.FoodDelivery.project.Enum;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum FoodSortField {
    NAME("name"),
    PRICE("price"),
    CREATE_DATE("createDate"),
    TOTAL_ORDER_COUNT("totalOrderCount"),
    AVG_PREP_TIME("averagePrepTimeInMinutes");
    private final String field;

    FoodSortField(String field) {
        this.field = field;
    }

    public static boolean isValid(String value) {
        return Arrays.stream(FoodSortField.values())
                .anyMatch(f -> f.field.equalsIgnoreCase(value));
    }
}
