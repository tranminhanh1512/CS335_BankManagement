package bankClasses;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;


import javax.swing.*;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.*;

public class CreditCardPageGUI {

    private GetCreditCardInfo creditHandler; // Declare the creditHandler

    CreditCardPageGUI(Customer loginCustomer) {
        this.creditHandler = new GetCreditCardInfo(); // Initialize the creditHandler

        CreditCard customerCard = creditHandler.findCreditCard(loginCustomer);

        JFrame frame = new JFrame("User Credit Card Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        JPanel panel = new JPanel();
        frame.add(panel);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Use BoxLayout with Y_AXIS

        JLabel titleLabel = new JLabel("Credit Card Information");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        if (customerCard != null) {
        	 // Display card type on a separate line
            JLabel cardTypeLabel = new JLabel("Card Type: " + customerCard.getClass().getSimpleName());
            panel.add(cardTypeLabel);

            // Display card number on a separate line
            JLabel cardNumberLabel = new JLabel("Card Number: " + customerCard.getCardNumber());
            panel.add(cardNumberLabel);

            // Display credit limit on a separate line
            JLabel cardLimitLabel = new JLabel("Limit: " + customerCard.getCreditLimit());
            panel.add(cardLimitLabel);

            // Display balance on a separate line
            JLabel cardBalLabel = new JLabel("Balance: " + customerCard.getBalance());
            panel.add(cardBalLabel);
            
            //Display status
            JLabel cardStatusLabel = new JLabel("Status: " + customerCard.getStatus());
            panel.add(cardStatusLabel);
            
            // Pay Balance
            JButton balanceButton = new JButton("Paying balance");
            balanceButton.addActionListener(e -> {
                customerCard.payBalance(loginCustomer);
                creditHandler.updateCreditCardInfo(loginCustomer, "paying");
                // Update balance label with new balance
                cardBalLabel.setText("Balance: " + customerCard.getBalance());
            });
            panel.add(balanceButton);
            
            // Deactivate card
            JButton deactivateButton = new JButton("Deactivate your card");
            deactivateButton.addActionListener(e -> {
                customerCard.changeStatus("Inactive");
                creditHandler.updateCreditCardInfo(loginCustomer, "deactivate");
                // Update status label with new status
                cardStatusLabel.setText("Status: " + customerCard.getStatus());
            });
            panel.add(deactivateButton);
           
            // Create a button to open a new credit card
            JButton openNewCardButton = new JButton("Open New Credit Card");
            openNewCardButton.addActionListener(e -> {
            	new CreditCardRegistration(loginCustomer);
            	frame.dispose();
            	});
            panel.add(openNewCardButton);
            
            // Back button
            JButton backButton = new JButton("Back");
            backButton.addActionListener(e -> {
                // Close the current frame
                frame.dispose();
                // Open the previous menu frame (assuming there's another frame)
                // Add your code here to open the previous menu frame
            });
            panel.add(backButton);

        } else {
        	// Create an instruction panel
            JPanel instructionPanel = new JPanel();
            instructionPanel.setLayout(new BoxLayout(instructionPanel, BoxLayout.Y_AXIS));
            instructionPanel.setBorder(BorderFactory.createTitledBorder("Instructions"));
            instructionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(instructionPanel);


            // No credit card label
            JLabel noCardLabel = new JLabel("You don't have a credit card.");
            noCardLabel.setBounds(10, 300, 100, 25);            
            instructionPanel.add(noCardLabel);

            // Would you like to open one? label
            JLabel openCardLabel = new JLabel("Would you like to open one?");
            openCardLabel.setBounds(10, 330, 100, 25);
            instructionPanel.add(openCardLabel);

            // Display available card options
            JLabel availableCardsLabel = new JLabel("We have Mastercard, Visa, Discover.");
            availableCardsLabel.setBounds(10, 360, 120, 25);
            instructionPanel.add(availableCardsLabel);

            // Offer promotion
            JLabel promotionLabel = new JLabel("Pay $1000 in the first 3 months, get back $200.");
            promotionLabel.setBounds(10, 390, 150, 25);
            instructionPanel.add(promotionLabel);

            // Create a panel for the open credit card button
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(buttonPanel);

            // Open credit card button
            JButton openNewCardButton = new JButton("Open Credit Card");
            openNewCardButton.addActionListener(e -> {
            	new CreditCardRegistration(loginCustomer);
            	frame.dispose();
            });
            buttonPanel.add(openNewCardButton);
            
            // Back button
            JButton backButton = new JButton("Back");
            backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            backButton.addActionListener(e -> {
                // Close the current frame
                frame.dispose();
                // Open the previous menu frame (assuming there's another frame)
                // Add your code here to open the previous menu frame
            });
            panel.add(backButton);
        }

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}