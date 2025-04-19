import javax.swing.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class OrderManager {
    private static final String CART_FILE = RestaurantManagementSystem.BASE_PATH + "cart.txt";
    private static final String SELL_HISTORY_FILE = RestaurantManagementSystem.BASE_PATH + "sell_history.txt";

    public void addToCart(ArrayList<FoodItem> menu) {
        if (menu.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Menu is empty.");
            return;
        }
        String[] names = menu.stream().map(FoodItem::getName).toArray(String[]::new);
        while (true) {
            String selected = (String) JOptionPane.showInputDialog(null,
                    "Select item to add (Cancel to stop):", "Add to Cart",
                    JOptionPane.QUESTION_MESSAGE, null, names, names[0]);
            if (selected == null) break;
            for (FoodItem item : menu) {
                if (item.getName().equals(selected)) {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(CART_FILE, true))) {
                        writer.write(item.getName() + "," + item.getPrice() + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void viewCart() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CART_FILE))) {
            StringBuilder sb = new StringBuilder();
            String line;
            int total = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                sb.append(parts[0]).append(" - BDT ").append(parts[1]).append("\n");
                total += Integer.parseInt(parts[1]);
            }
            sb.append("Total: BDT ").append(total);
            JOptionPane.showMessageDialog(null, sb.toString(), "Your Cart", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void placeOrderAndPay() {
        File cart = new File(CART_FILE);
        if (!cart.exists() || cart.length() == 0) {
            JOptionPane.showMessageDialog(null, "Cart is empty!");
            return;
        }

        viewCart();

        String name = JOptionPane.showInputDialog("Enter your name:");
        String address = JOptionPane.showInputDialog("Enter your address:");

        String[] paymentMethods = {"Bkash", "Nagad"};
        String payment = (String) JOptionPane.showInputDialog(null, "Select payment method:",
                "Payment", JOptionPane.QUESTION_MESSAGE, null, paymentMethods, paymentMethods[0]);

        if (payment != null) {
            int totalAmount = calculateTotalAmount();
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yy"));
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(SELL_HISTORY_FILE, true))) {
                writer.write(date + "," + name + "," + totalAmount + "," + address + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            new File(CART_FILE).delete();
            JOptionPane.showMessageDialog(null, "Order placed successfully and cart cleared.");
        }
    }

    private int calculateTotalAmount() {
        int total = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(CART_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                total += Integer.parseInt(parts[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return total;
    }

    public void viewSellHistory() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SELL_HISTORY_FILE))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString(), "Sell History", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}