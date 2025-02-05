package bankClasses;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class OpenBankAccountGUI {
    
    private Customer customer;
    private ArrayList<Customer> customerList;
    private AccountGUI accountGUI;

    OpenBankAccountGUI(Customer newCustomer, AccountGUI accountGUI ) throws ParseException {
        this.customer = newCustomer;
        this.accountGUI = accountGUI;
        this.customerList = findCustomerList();
        
        // Creating the Frame
        JFrame frame = new JFrame("Open Bank Account");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 300);
        JPanel panel = new JPanel();
        frame.add(panel);

        panel.setLayout(null);

        // Dropdown for checking account
        JLabel checkingLabel = new JLabel("Open Checking Account (Yes/No): ");
        checkingLabel.setBounds(10, 20, 225, 25);
        panel.add(checkingLabel);
        String[] yesNoOptions = { "Yes", "No" };
        JComboBox<String> checkingDropdown = new JComboBox<>(yesNoOptions);
        checkingDropdown.setBounds(225, 20, 150, 25);
        panel.add(checkingDropdown);

        // Box for checking account deposit amount
        JLabel checkingBalLabel = new JLabel(
                "Enter amount of money you want to deposit into your checking account: ");
        checkingBalLabel.setBounds(10, 50, 475, 25);
        panel.add(checkingBalLabel);
        JTextField checkingBalText = new JTextField(12);
        checkingBalText.setBounds(475, 50, 165, 25);
        panel.add(checkingBalText);
        checkingBalText.setEnabled(false); // Initially disabled

        // Dropdown for saving account
        JLabel savingLabel = new JLabel("Open Saving Account (Yes/No):");
        savingLabel.setBounds(10, 80, 225, 25);
        panel.add(savingLabel);
        JComboBox<String> savingDropdown = new JComboBox<>(yesNoOptions);
        savingDropdown.setBounds(225, 80, 150, 25);
        panel.add(savingDropdown);

        // Box for saving account deposit amount
        JLabel savingBalLabel = new JLabel(
                "Enter amount of money you want to deposit into your saving account:");
        savingBalLabel.setBounds(10, 110, 460, 25);
        panel.add(savingBalLabel);
        JTextField savingBalText = new JTextField(12);
        savingBalText.setBounds(460, 110, 165, 25);
        panel.add(savingBalText);
        savingBalText.setEnabled(false); // Initially disabled

        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.setBounds(10, 150, 120, 25);
        panel.add(createAccountButton);

        JLabel messageLabel = new JLabel();
        messageLabel.setBounds(250, 200, 250, 35);
        messageLabel.setFont(new Font(null, Font.ITALIC, 20));
        panel.add(messageLabel);
        
        // Back button
        JButton backButton = new JButton("Back");
        backButton.setBounds(150, 150, 80, 25);
        panel.add(backButton);

        frame.setVisible(true);

        checkingDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkingDropdown.getSelectedItem().equals("Yes")) {
                    checkingBalText.setEnabled(true);
                } else {
                    checkingBalText.setEnabled(false);
                    checkingBalText.setText(""); // Clear the text field
                }
            }
        });

        savingDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (savingDropdown.getSelectedItem().equals("Yes")) {
                    savingBalText.setEnabled(true);
                } else {
                    savingBalText.setEnabled(false);
                    savingBalText.setText(""); // Clear the text field
                }
            }
        });
        
        createAccountButton.addActionListener((ActionEvent e) -> {
            String checking = (String) checkingDropdown.getSelectedItem();
            String checkingBalStr = checkingBalText.getText();
            String saving = (String) savingDropdown.getSelectedItem();
            String savingBalStr = savingBalText.getText();

            if (checking.isEmpty() || (checking.equals("Yes") && checkingBalStr.isEmpty()) || saving.isEmpty()
                    || (saving.equals("Yes") && savingBalStr.isEmpty())) {
                messageLabel.setText("Please fill in all fields");
            } else {
                
                // Successfully created account
                if (checking.equals("Yes") && customer.getCheckingAccount()==null) {
                    int accCheckingNum = generateAccountNumber();
                    int checkingDeposit = Integer.parseInt(checkingBalStr);
                    Account checkingAccount = new Account(accCheckingNum, "Checking", checkingDeposit,
                            customer.getCustomerID());
                    customer.addAccount(checkingAccount);
                    writeCurrentCustomer(customer,"checking");
                   
                }
                if (saving.equals("Yes")&& customer.getSavingAccount()==null) {
                    int accSavingNum = generateAccountNumber();
                    int savingDeposit = Integer.parseInt(savingBalStr);
                    Account savingAccount = new Account(accSavingNum, "Saving", savingDeposit,
                            customer.getCustomerID());
                    customer.addAccount(savingAccount);
                    writeCurrentCustomer(customer,"saving");
                  
                }
                messageLabel.setText("Successfully created account(s)");
                accountGUI.updateAccountInfo();
            }
            
            
        });
        
     // Back button action
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the current frame
                try {
                    new AccountGUI(customer); // Open the AccountGUI
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private int generateAccountNumber() {
        Random random = new Random();
        return random.nextInt(999999 - 100000 + 1) + 100000;
    }
    
    private void writeCurrentCustomer(Customer existingCustomer, String acc) {
    	try (BufferedReader reader = new BufferedReader(new FileReader("data/CustomerList.csv"))) {
            String line;
            StringBuilder updatedFileContent = new StringBuilder(); // Declare StringBuilder to store updated file content
            
            // Read and append the header line
            String header = reader.readLine();
            updatedFileContent.append(header).append("\n");
            
            // Process the remaining lines
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 10) { // Ensure the line has enough elements
                    String stored_customerID = parts[4].trim();
                    if (stored_customerID.equals(existingCustomer.getCustomerID())) {
                    	
                    	switch (acc.toLowerCase()) {
                		case "checking":
                			parts[6] = Integer.toString(existingCustomer.getCheckingAccount().getAccNum());
	                        parts[7] = Integer.toString(existingCustomer.getCheckingAccount().getAccBal());
	                        break;
                		case "saving":
                			parts[8] = Integer.toString(existingCustomer.getCheckingAccount().getAccNum());
                			parts[9] = Integer.toString(existingCustomer.getSavingAccount().getAccBal());
                			break;
                    	}
                }
                }
                // Reconstruct the line with modified parts
                updatedFileContent.append(String.join(",", parts));
                
                // Append newline character if it's not the last line
                if (reader.ready()) {
                    updatedFileContent.append("\n");
                }
            }

            // Write the updated content back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/CustomerList.csv"))) {
                writer.write(updatedFileContent.toString());
            }
        } catch (IOException e) {
            System.out.println("Customer not found in database.");
            e.printStackTrace();
        }
    }
    
    private ArrayList<Customer> findCustomerList() throws ParseException{
    	CustomerFactory cF = new CustomerFactory("data/CustomerList.csv");
		ArrayList<Customer> cusList = new ArrayList<Customer>();
		
		while (cF.hasMoreData()) {
			cusList.add(cF.getNextCustomer());
		}
		return cusList;
    }
}