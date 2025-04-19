import javax.swing.*;
import java.awt.*;
import java.io.*;

public class RestaurantManagementSystem {
    static final String BASE_PATH = "C:\\Users\\user\\Desktop\\JAVA Project\\";
    private static final String USER_FILE = BASE_PATH + "project.txt";
    private static MenuManager menuManager;
    private static OrderManager orderManager;

    public static void initialize() {
        new File(BASE_PATH).mkdirs();
        menuManager = new MenuManager();
        orderManager = new OrderManager();
        showMainMenu();
    }

    private static void showMainMenu() {
        JFrame frame = new JFrame("Restaurant Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        JButton adminMenuButton = new JButton("Admin Menu");

        loginButton.addActionListener(e -> login(frame));
        registerButton.addActionListener(e -> register());
        adminMenuButton.addActionListener(e -> showAdminMenu());

        panel.add(loginButton);
        panel.add(registerButton);
        panel.add(adminMenuButton);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void login(JFrame parent) {
        JTextField phoneField = new JTextField();
        JTextField passField = new JPasswordField();

        Object[] message = {
                "Phone:", phoneField,
                "Password:", passField
        };

        int option = JOptionPane.showConfirmDialog(parent, message, "Login", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            if (checkLogin(phoneField.getText(), passField.getText())) {
                JOptionPane.showMessageDialog(parent, "Login successful!");
                showUserMenu();
            } else {
                JOptionPane.showMessageDialog(parent, "Login failed!");
            }
        }
    }

    private static void register() {
        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField passField = new JPasswordField();

        Object[] message = {
                "Name:", nameField,
                "Phone:", phoneField,
                "Password:", passField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Register", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            saveToFile(nameField.getText(), phoneField.getText(), passField.getText());
            JOptionPane.showMessageDialog(null, "Registered Successfully!");
        }
    }

    private static void saveToFile(String name, String phone, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE, true))) {
            writer.write(name + "," + phone + "," + password + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean checkLogin(String phone, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[1].equals(phone) && parts[2].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void showUserMenu() {
        String[] options = {"View Menu", "Add to Cart", "View Cart", "Place Order & Pay", "Exit"};
        while (true) {
            int choice = JOptionPane.showOptionDialog(null, "Select an option:", "User Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                    options, options[0]);

            switch (choice) {
                case 0 -> menuManager.viewFoodItems();
                case 1 -> orderManager.addToCart(menuManager.getMenu());
                case 2 -> orderManager.viewCart();
                case 3 -> orderManager.placeOrderAndPay();
                default -> { return; }
            }
        }
    }

    private static void showAdminMenu() {
        String[] options = {"Add Food", "Delete Food", "View Menu", "View Sell History", "Back"};
        while (true) {
            int choice = JOptionPane.showOptionDialog(null, "Select Admin Option:", "Admin Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                    options, options[0]);

            switch (choice) {
                case 0 -> menuManager.addFoodItem();
                case 1 -> menuManager.deleteFoodItem();
                case 2 -> menuManager.viewFoodItems();
                case 3 -> orderManager.viewSellHistory();
                default -> { return; }
            }
        }
    }
}