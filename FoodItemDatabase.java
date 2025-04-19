import java.util.Arrays;
import java.util.List;

public class FoodItemDatabase {
    public static List<FoodItem> getInitialFoodItems() {
        return Arrays.asList(
                new FoodItem("Biryani", 250),
                new FoodItem("Kebab", 180),
                new FoodItem("Fried Chicken", 200),
                new FoodItem("Naan", 30),
                new FoodItem("Butter Chicken", 350),
                new FoodItem("Grilled Fish", 220),
                new FoodItem("Pilaf", 150),
                new FoodItem("Lentils", 80),
                new FoodItem("Chicken Soup", 120),
                new FoodItem("Fruit Juice", 70)
        );
    }
}