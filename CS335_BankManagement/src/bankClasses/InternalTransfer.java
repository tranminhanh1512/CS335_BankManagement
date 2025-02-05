package bankClasses;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class InternalTransfer {

    public static void transferBetweenAccounts(Customer customer, String fromAccountType, String toAccountType, int amount) {
        Account fromAccount = getAccountByType(customer, fromAccountType);
        Account toAccount = getAccountByType(customer, toAccountType);

        if (fromAccount != null && toAccount != null) {
            if (fromAccount.getAccBal() >= amount) {
                fromAccount.subtractBal(amount);
                toAccount.addBal(amount);
                rewriteFile(customer, fromAccount, toAccount);
                System.out.println("Transfer successful.");
            } else {
                System.out.println("Insufficient funds in the " + fromAccountType + " account.");
            }
        } else {
            System.out.println("One or both accounts not found.");
        }
    }

    public static void transferBetweenCustomers(Customer fromCustomer, String fromAccountType,
                                                Customer toCustomer, String toAccountType, int amount) {
        Account fromAccount = getAccountByType(fromCustomer, fromAccountType);
        Account toAccount = getAccountByType(toCustomer, toAccountType);

        if (fromAccount != null && toAccount != null) {
            if (fromAccount.getAccBal() >= amount) {
                fromAccount.subtractBal(amount);
                toAccount.addBal(amount);
                System.out.println("Transfer successful.");
            } else {
                System.out.println("Insufficient funds in the " + fromAccountType + " account.");
            }
        } else {
            System.out.println("One or both accounts not found.");
        }
    }

    private static Account getAccountByType(Customer customer, String accountType) {
        for (Account account : customer.getAccountList()) {
            if (account.getAccType().equalsIgnoreCase(accountType)) {
                return account;
            }
        }
        return null;
    }
    
    private static void rewriteFile(Customer customer, Account a1, Account a2){
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
                    if (stored_customerID.equals(customer.getCustomerID())) {
                    	if(a1.getAccType().toLowerCase()=="checking") {
	                        parts[7] = Integer.toString(a1.getAccBal());
                			parts[9] = Integer.toString(a2.getAccBal());
                    	}else {
                    		parts[7] = Integer.toString(a2.getAccBal());
                			parts[9] = Integer.toString(a1.getAccBal());
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
}
