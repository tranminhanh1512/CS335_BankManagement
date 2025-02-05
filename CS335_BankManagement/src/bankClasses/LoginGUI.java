package bankClasses;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginGUI {
    private logIn loginHandler; // Instance of the logIn class

    LoginGUI() {
        loginHandler = new logIn(); // Initialize the login handler

        // Creating the Frame
        JFrame frame = new JFrame("User Login Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        JPanel panel = new JPanel();
        frame.add(panel);

        panel.setLayout(null);

        // Box for username
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(10, 20, 80, 25);
        panel.add(usernameLabel);
        JTextField userText = new JTextField(12);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        // Box for password
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);
        JTextField passwordText = new JPasswordField(12);
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 120, 25);
        panel.add(loginButton);

        // New Customer
        JButton newCustomerBttn = new JButton("New Customer");
        newCustomerBttn.setBounds(10, 120, 130, 25);
        panel.add(newCustomerBttn);

        // Display message
        JLabel messageLabel = new JLabel();
        messageLabel.setBounds(50, 200, 400, 100);
        messageLabel.setFont(new Font(null, Font.ITALIC, 20));
        panel.add(messageLabel);

        frame.setVisible(true);

        newCustomerBttn.addActionListener((ActionEvent ae) -> {
            frame.dispose();
            new CustomerRegistrationGUI();
        });
        

        loginButton.addActionListener((ActionEvent e) -> {
            String username = userText.getText();
            String password = passwordText.getText();
            if (username.isEmpty() || password.isEmpty()) {
                // If any of the fields are empty, display an error message
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("Please fill in all fields");
            } else {
                // Attempt to log in using the provided username and password
                boolean loginSuccessful = loginHandler.login(username, password);
                Customer loginCus = loginHandler.getLoggedInCustomer(username);
                if (loginSuccessful) {
                    messageLabel.setForeground(Color.BLUE);
                    messageLabel.setText("Login successful!");
                    frame.dispose();
                    new MenuFrame(loginCus);
                } else {
                    messageLabel.setForeground(Color.RED);
                    messageLabel.setText("Incorrect username or password");
                }
            }
        });
    }

    public static void main(String[] args) {
        new LoginGUI();
    }
}