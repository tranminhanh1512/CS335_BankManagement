package bankClasses;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ATM{
	
	public static String customerID;
	
	    public boolean validateCard(String card_number, int cvv, String card_type, String filename) {
	        // Validate the card using the CreditCard subclass validate function
	        CreditCard card = null;
	        switch (card_type.toLowerCase()) {
	            case "visa":
	                card = new VisaCard(card_number, 0, 0, cvv, null);
	                break;
	            case "mastercard":
	                card = new MasterCard(card_number, 0, 0, cvv, null);
	                break;
	            case "discover":
	                card = new DiscoverCard(card_number, 0, 0, cvv, null);
	                break;
	            default:
	                return false; // Invalid card type
	        }

	        if (card.validateCardType(card_number) != null) {
	            // Card is valid, now check if it's in the CSV file
	            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
	            	reader.readLine();
	                String line;
	                int attempts = 0;
	                boolean foundCard = false;
	                while ((line = reader.readLine()) != null) {
	                    // Split the line by commas
	                    String[] parts = line.split(",");

	                    // Ensure the line has enough elements (card number and CVV)
	                    if (parts.length >= 2) {
	                        String stored_card_number = parts[2].trim();
	                        int stored_cvv = Integer.parseInt(parts[5].trim());
	                        customerID = parts[0].trim();

	                        // Check if card number matches
	                        if (card_number.equals(stored_card_number)) {
	                            foundCard = true;
	                            if (cvv == stored_cvv) {
	                                return true; // Card number and CVV are valid
	                            } else {
	                                attempts++;
	                                if (attempts >= 3) {
	                                    // Change card status to Inactive after 3 attempts
	                                    card.changeStatus("Inactive");
	                                    System.out.println("Max attempts exceeded. Changing card status to Inactive...");
	                                    return false;
	                                }
	                            }
	                        }
	                    }
	                }

	                if (!foundCard) {
	                    System.out.println("Card not found in database.");
	                } else {
	                    System.out.println("Incorrect CVV.");
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        } else {
	            // Card validation failed
	            System.out.println("Card validation failed.");
	        }

	        return false; // Card number and/or CVV not found
	    }
	    
	    public Customer findCustomer(String customerID) {
	        try (BufferedReader reader = new BufferedReader(new FileReader("data/CustomerList.csv"))) {
	            String line;
	            reader.readLine(); // Skip the header line
	            while ((line = reader.readLine()) != null) {
	                String[] parts = line.split(",");
	                if (parts.length >= 6) { // Ensure the line has enough elements
	                    String stored_customerID = parts[4].trim();
	                    if (stored_customerID.equals(customerID)) {
	                        // Found the customer, parse the details and return
	                        String firstName = parts[0].trim();
	                        String lastName = parts[1].trim();
	                        String email = parts[2].trim();
	                        Date birthday = parseDate(parts[3]);
	                        String password = parts[5].trim();
	                        
	                        // Create and return the Customer object
	                        Customer c = new Customer(firstName, lastName, email, birthday, customerID, password);
	                        Account a1 = new Account(Integer.parseInt(parts[6]), "Checking", Integer.parseInt(parts[7]), parts[4]);
	                		Account a2 = new Account(Integer.parseInt(parts[8]), "Saving", Integer.parseInt(parts[9]), parts[4]);
	                		c.addAccount(a1);
	                		c.addAccount(a2);
	                		return c;
	                    }
	                }
	            }
	            // If no customer found with the given ID
	            System.out.println("Customer not found in database.");
	        } catch (IOException | ParseException e) {
	            e.printStackTrace();
	        }
	        return null; // Return null if customer not found
	    }
	    
	    private static Date parseDate(String dateString) throws ParseException {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            return dateFormat.parse(dateString);
    }

}