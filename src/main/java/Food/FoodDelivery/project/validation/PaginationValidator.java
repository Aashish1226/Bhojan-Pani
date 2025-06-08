package Food.FoodDelivery.project.validation;
public class PaginationValidator {
    public static int validatePage(int page) {
        return Math.max(page, 0);
    }
    public static int validateSize(int size) {
        return size <= 0 ? 5 : size;
    }
    public static String validateSortDirection(String sortDirection) {
        if (!"asc".equalsIgnoreCase(sortDirection) && !"desc".equalsIgnoreCase(sortDirection)) {
            throw new IllegalArgumentException("Invalid sort direction: '" + sortDirection + "'. Allowed values: 'asc' or 'desc'.");
        }
        return sortDirection;
    }
}