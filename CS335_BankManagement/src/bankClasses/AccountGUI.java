package bankClasses;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class AccountGUI {

    private Customer loggedInCustomer;
    private JPanel accountInfoPanel;
    private JPanel panel;

    AccountGUI(Customer loginCus) throws ParseException {
        this.loggedInCustomer = loginCus;
        accountInfoPanel = new JPanel();

        JFrame frame = new JFrame("Account");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 400);
        panel = new JPanel();
        frame.add(panel);
        panel.setLayout(new BorderLayout());
        
        
        // Display the account information regardless of whether the customer has accounts or not
        displayAccountsPage();

        // Check if the customer has any accounts
        if (loggedInCustomer.getAccountList().isEmpty()) {
            // If no accounts, display a message and option to open an account
            JLabel messageLabel = new JLabel("You don't have any accounts.");
            messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
            messageLabel.setHorizontalAlignment(JLabel.CENTER);
            panel.add(messageLabel, BorderLayout.CENTER);

            JButton openAccountButton = new JButton("Open Bank Account");
            openAccountButton.addActionListener(e -> {
                try {
                	frame.dispose();
                    new OpenBankAccountGUI(loggedInCustomer, this);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            });
            
            // Create a back button
            JButton backButton = new JButton("Back to Menu");
            backButton.addActionListener(e -> {
                frame.dispose(); // Close the current frame
                new MenuFrame(loggedInCustomer); // Open the MenuFrame
            });
            
            // Create a panel for the message and buttons
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.add(openAccountButton);
            buttonPanel.add(backButton);

            // Add the panel with the message and buttons to the main panel
            panel.add(buttonPanel, BorderLayout.SOUTH);


        } else {
            // Create a button to go back to the menu
            JButton backButton = new JButton("Back to Menu");
            backButton.addActionListener(e -> {
                frame.dispose(); // Close the current frame
                new MenuFrame(loggedInCustomer); // Open the MenuFrame
            });

            // Create buttons for deposit and withdraw actions
            JButton depositButton = new JButton("Deposit");
            JButton withdrawButton = new JButton("Withdraw");

            // Add action listeners to the buttons
            depositButton.addActionListener(e -> {
            	frame.dispose();
            	new InsertMoneyGUI(loggedInCustomer, this);});
            withdrawButton.addActionListener(e -> {
            	frame.dispose();
            	new WithdrawMoneyGUI(loggedInCustomer, this);
            	});

            // Create a panel for the buttons and set layout
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

            // Add buttons to the button panel
            buttonPanel.add(depositButton);
            buttonPanel.add(withdrawButton);

            // Create an "Internal Transfer" button
            JButton internalTransferButton = new JButton("Transfer");
            internalTransferButton.addActionListener(e -> {
            	frame.dispose();
                new TransferGUI(loggedInCustomer, this);
            });

            // Create an "Open Bank Account" button
            JButton openAccountButton = new JButton("Open Bank Account");
            openAccountButton.addActionListener(e -> {
            	frame.dispose();
                try {
                    new OpenBankAccountGUI(loggedInCustomer, this);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            });

            // Add the buttons to the button panel
            buttonPanel.add(internalTransferButton);
            buttonPanel.add(openAccountButton);
            buttonPanel.add(backButton); // Add the back button


            // Add the button panel to the south of the main panel
            panel.add(buttonPanel, BorderLayout.SOUTH);

        }

        frame.setVisible(true);
    }

    private void displayAccountsPage() {
        accountInfoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Use FlowLayout with spacing

        addAccountInfo(); // Add account information to the panel

        JScrollPane scrollPane = new JScrollPane(accountInfoPanel);
        panel.add(scrollPane, BorderLayout.CENTER); // Add the scroll pane to the center of the frame
    }

    private void addAccountInfo() {
        for (Account acc : loggedInCustomer.getAccountList()) {
            JPanel accountPanel = new JPanel(new BorderLayout());
            accountPanel.setPreferredSize(new Dimension(300, 80)); // Set preferred size for each account panel
            accountPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border for clarity

            JButton accountTypeButton = new JButton(acc.getAccType());
            accountTypeButton.setEnabled(false);
            accountPanel.add(accountTypeButton, BorderLayout.WEST);

            JPanel detailsPanel = new JPanel(new GridLayout(2, 1));
            detailsPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            JLabel accountNumberLabel = new JLabel("Account Number: " + acc.getAccNum());
            detailsPanel.add(accountNumberLabel);

            JLabel balanceLabel = new JLabel("Balance: " + acc.getAccBal());
            detailsPanel.add(balanceLabel);

            accountPanel.add(detailsPanel, BorderLayout.CENTER);

            accountInfoPanel.add(accountPanel);
        }
    }
    
    private void clearAccountInfoPanel() {
        accountInfoPanel.removeAll();
        accountInfoPanel.revalidate();
        accountInfoPanel.repaint();
    }
    
    public void updateAccountInfo() {
        clearAccountInfoPanel();
        addAccountInfo();
    }
    
}