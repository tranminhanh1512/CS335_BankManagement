package bankClasses;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GetCreditCardInfo{
	
	public CreditCard findCreditCard(Customer loginCustomer) {
	    CreditCard card = null;

	    try (BufferedReader reader = new BufferedReader(new FileReader("data/CustomerCreditCardList.csv"))) {
	        String line;
	        reader.readLine(); // Skip the header line
	        while ((line = reader.readLine()) != null) {
	            String[] parts = line.split(",");
	            if (parts.length >= 6) { // Ensure the line has enough elements
	                String stored_customerID = parts[0].trim();
	                if (stored_customerID.equals(loginCustomer.getCustomerID())) {
	                    // Found the customer, parse the details and return
	                    String username = parts[0].trim();
	                    String type = parts[1].trim();
	                    String cardNumber = parts[2].trim();
	                    Integer limit = Integer.parseInt(parts[3]);
	                    Integer balance = Integer.parseInt(parts[4]);
	                    Integer cvv = Integer.parseInt(parts[5]);
	                    String status = parts[6].trim(); // corrected index

	                    // Create and return the Customer object
	                    switch (type.toLowerCase()) {
	                        case "visa":
	                            card = new VisaCard(cardNumber, balance, limit, cvv, username);
	                            break;
	                        case "mastercard":
	                            card = new MasterCard(cardNumber, balance, limit, cvv, username);
	                            break;
	                        case "discover":
	                            card = new DiscoverCard(cardNumber, balance, limit, cvv, username);
	                            break;
	                        default:
	                            return null; // Invalid card type
	                    }
	                    card.status=status; // corrected method name
	                    break; // exit loop after finding the card
	                }
	            }
	        }
	    } catch (IOException e) {
	        System.out.println("Customer not found in database.");
	        e.printStackTrace();
	    }
	    return card; // Return null if customer not found
	}
	
	public void updateCreditCardInfo(Customer loginCustomer, String action) {
		try (BufferedReader reader = new BufferedReader(new FileReader("data/CustomerCreditCardList.csv"))) {
	        String line;
	        StringBuilder updatedFileContent = new StringBuilder(); // Declare StringBuilder to store updated file content
            
            // Read and append the header line
            String header = reader.readLine();
            updatedFileContent.append(header).append("\n");
            
	        while ((line = reader.readLine()) != null) {
	            String[] parts = line.split(",");
	            if (parts.length >= 6) { // Ensure the line has enough elements
	                String stored_customerID = parts[0].trim();
	                if (stored_customerID.equals(loginCustomer.getCustomerID())) {
	                	switch (action.toLowerCase()) {
                        case "paying":
                        	parts[4] = Integer.toString(0);
                        	break;
                        case "deactivate":
                        	parts[6] = "Inactive";
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
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/CustomerCreditCardList.csv"))) {
	            writer.write(updatedFileContent.toString());}
	    } catch (IOException e) {
	        System.out.println("Customer not found in database.");
	        e.printStackTrace();
	    }
	}

}