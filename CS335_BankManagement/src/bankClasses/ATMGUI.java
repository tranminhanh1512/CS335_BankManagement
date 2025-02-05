package bankClasses;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ATMGUI {
    private JFrame frame;
    private JPanel panel;
    private JTextField cardTypeField;
    private JTextField cardNumberField;
    private JPasswordField cvvField;
    private JButton insertButton;
    
    private ATM atmHandler;
    
    ATMGUI() {
    	atmHandler = new ATM();
    	
        frame = new JFrame("ATM");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel cardTypeLabel = new JLabel("Type of Card:");
        cardTypeField = new JTextField();
        panel.add(cardTypeLabel);
        panel.add(cardTypeField);

        JLabel cardNumberLabel = new JLabel("Card Number:");
        cardNumberField = new JTextField();
        panel.add(cardNumberLabel);
        panel.add(cardNumberField);

        JLabel cvvLabel = new JLabel("CVV:");
        cvvField = new JPasswordField();
        panel.add(cvvLabel);
        panel.add(cvvField);

        insertButton = new JButton("Insert");
        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String cardType = cardTypeField.getText();
                String cardNumber = cardNumberField.getText();
                String cvv = new String(cvvField.getPassword());

                // Validate the card and CVV using the ATM class
                boolean isValid = atmHandler.validateCard(cardNumber, Integer.parseInt(cvv), cardType, "data/CustomerCreditCardList.csv");
                Customer creditCustomer = atmHandler.findCustomer(ATM.customerID);
                // Display appropriate message based on validation result
                if (isValid) {
                    JOptionPane.showMessageDialog(frame, "Login successful!");
                    frame.dispose(); // Close the ATM GUI
                    new MenuFrame(creditCustomer); // Open the menu
                } else {
                    JOptionPane.showMessageDialog(frame, "Login failed. Please try again.");
                }
            }
        });
        panel.add(insertButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Create and show the ATM GUI
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ATMGUI();
            }
        });
    }
}
