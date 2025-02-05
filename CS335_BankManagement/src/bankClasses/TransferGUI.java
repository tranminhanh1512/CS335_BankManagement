package bankClasses;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;

public class TransferGUI {

    private Customer loggedInCustomer;
    private AccountGUI accountGUI;

    public TransferGUI(Customer loggedInCustomer, AccountGUI accountGUI) {
        this.loggedInCustomer = loggedInCustomer;
        this.accountGUI = accountGUI;

        JFrame frame = new JFrame("Internal Transfer");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel fromAccountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel fromAccountLabel = new JLabel("From Account:");
        JComboBox<String> fromAccountDropdown = new JComboBox<>(new String[]{"Checking", "Saving"});
        fromAccountPanel.add(fromAccountLabel);
        fromAccountPanel.add(fromAccountDropdown);
        panel.add(fromAccountPanel);

        JPanel toAccountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel toAccountLabel = new JLabel("To Account:");
        JComboBox<String> toAccountDropdown = new JComboBox<>(new String[]{"Checking", "Saving"});
        toAccountPanel.add(toAccountLabel);
        toAccountPanel.add(toAccountDropdown);
        panel.add(toAccountPanel);

        JPanel amountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel amountLabel = new JLabel("Amount:");
        JTextField amountField = new JTextField(10);
        amountPanel.add(amountLabel);
        amountPanel.add(amountField);
        panel.add(amountPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton transferButton = new JButton("Transfer");
        JButton backButton = new JButton("Back");
        buttonPanel.add(transferButton);
        buttonPanel.add(backButton);
        panel.add(buttonPanel);

        JLabel messageLabel = new JLabel();
        panel.add(messageLabel);

        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fromAccountType = (String) fromAccountDropdown.getSelectedItem();
                String toAccountType = (String) toAccountDropdown.getSelectedItem();
                int amount;
                try {
                    amount = Integer.parseInt(amountField.getText());
                } catch (NumberFormatException ex) {
                    messageLabel.setText("Invalid amount.");
                    return;
                }

                try {
                    InternalTransfer.transferBetweenAccounts(loggedInCustomer, fromAccountType, toAccountType, amount);
                    messageLabel.setText("Transfer successful.");
                } catch (Exception ex) {
                    messageLabel.setText("Error: " + ex.getMessage());
                }

                accountGUI.updateAccountInfo();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the current frame
                try {
                    new AccountGUI(loggedInCustomer); // Open the AccountGUI
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.setVisible(true);
    }
}