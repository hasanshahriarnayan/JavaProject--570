import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MenuManager {
    private ArrayList<FoodItem> menu;
    private static final int MAX_ITEMS = 15;

    public MenuManager() {
        this.menu = new ArrayList<>();
        initializeMenu();
    }

    private void initializeMenu() {
        List<FoodItem> initialItems = FoodItemDatabase.getInitialFoodItems();
        menu.addAll(initialItems);
    }

    public void addFoodItem() {
        if (menu.size() >= MAX_ITEMS) {
            JOptionPane.showMessageDialog(null, "Menu is full! Max " + MAX_ITEMS + " items allowed.");
            return;
        }

        String name = JOptionPane.showInputDialog("Enter food name:");
        if (name == null || name.trim().isEmpty()) return;

        String priceStr = JOptionPane.showInputDialog("Enter price:");
        try {
            int price = Integer.parseInt(priceStr);
            menu.add(new FoodItem(name, price));
            JOptionPane.showMessageDialog(null, "'" + name + "' added to menu!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid price! Numbers only.");
        }
    }

    public void deleteFoodItem() {
        if (menu.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No items to delete.");
            return;
        }
        String[] names = menu.stream().map(FoodItem::getName).toArray(String[]::new);
        String selected = (String) JOptionPane.showInputDialog(null, "Select item to delete:",
                "Delete", JOptionPane.QUESTION_MESSAGE, null, names, names[0]);
        if (selected != null) {
            menu.removeIf(item -> item.getName().equals(selected));
            JOptionPane.showMessageDialog(null, "Item deleted.");
        }
    }

    public void viewFoodItems() {
        if (menu.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No items available.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (FoodItem item : menu) {
            sb.append(item).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "Menu", JOptionPane.INFORMATION_MESSAGE);
    }

    public ArrayList<FoodItem> getMenu() {
        return menu;
    }
}