package bankClasses;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class CreditCardApproval {

    private static final Random RANDOM = new Random();
    private ArrayList<Customer> customerList;


    // Method to decide credit card approval based on customer information
    public static CreditCard decideCreditCardApproval(String firstName, String lastName, String email, String dob,
                                                  String phoneNumber, String ssn, String address, String city,
                                                  String state, String zipCode, String residence, String employmentStatus,
                                                  int income, String creditCardType) throws ParseException {
		
        Customer creditCustomer = findCustomer(firstName, lastName, email, dob);
        if (income < 70000) {
        	if (creditCustomer.getCheckingAccount().getAccBal()>10000||creditCustomer.getSavingAccount().getAccBal()>10000) {
        		String card_number = generateValidCardNumber(creditCardType);
        		return generateCreditCard(creditCardType, creditCustomer, 1000, card_number);
        	} else if(creditCustomer.getCheckingAccount().getAccBal()>5000||creditCustomer.getSavingAccount().getAccBal()>5000) {
        		String card_number = generateValidCardNumber(creditCardType);
        		return generateCreditCard(creditCardType, creditCustomer, 500, card_number);
        	}else {
        		return null;}	
        }else {
        	String card_number = generateValidCardNumber(creditCardType);
    		return generateCreditCard(creditCardType, creditCustomer, 1500, card_number);
        }
    }
    
    private static CreditCard generateCreditCard(String cardType, Customer creditCustomer, int limit, String card_number) {
    	int cvv = getRandomCVV();
    	
    	switch(cardType.toLowerCase()) {
    	case "visa":
    		return new VisaCard(card_number, 0, limit, cvv, creditCustomer.getCustomerID());
    	case "discover":
    		return new DiscoverCard(card_number, 0, limit, cvv, creditCustomer.getCustomerID());
    	case "mastercard":
    		return new MasterCard(card_number, 0, limit, cvv, creditCustomer.getCustomerID());
    	default:
            // Return a default card or throw an exception
            return null; // or throw new IllegalArgumentException("Invalid card type: " + cardType);
    	}
    }
    
    public static Customer findCustomer(String firstName, String lastName, String email, String dobString) throws ParseException {
    	Date dob = parseDate(dobString); // Parse input string into Date object
        ArrayList<Customer> customerList = findCustomerList();
        for (Customer customer : customerList) {
            if (customer.getFirstName().equals(firstName) &&
                customer.getLastName().equals(lastName) &&
                customer.getEmail().equals(email) &&
                customer.getDOB().equals(dob)) {
                return customer; // Found the matching customer
            }
        }
        return null; // No matching customer found
    }
    
    private static ArrayList<Customer> findCustomerList() throws ParseException{
    	CustomerFactory cF = new CustomerFactory("data/CustomerList.csv");
		ArrayList<Customer> cusList = new ArrayList<Customer>();
		
		while (cF.hasMoreData()) {
			cusList.add(cF.getNextCustomer());
		}
		return cusList;
    }
    
    private static Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.parse(dateString);
    }


    private static String generateValidCardNumber(String cardType) {
        String cardNumber;
        do {
            cardNumber = generateRandomCardNumber(cardType);
        } while (!isValidCardNumber(cardType, cardNumber));
        return cardNumber;
    }

    private static String generateRandomCardNumber(String cardType) {
        switch (cardType.toLowerCase()) {
            case "visa":
                return generateRandomVisaCardNumber();
            case "discover":
                return generateRandomDiscoverCardNumber();
            case "mastercard":
                return generateRandomMasterCardNumber();
            default:
                throw new IllegalArgumentException("Invalid card type: " + cardType);
        }
    }

    private static boolean isValidCardNumber(String cardType, String cardNumber) {
        switch (cardType.toLowerCase()) {
            case "visa":
                return new VisaCard("", 0, 0, 0, "").validateCardType(cardNumber).equals("Visa");
            case "discover":
                return new DiscoverCard("", 0, 0, 0, "").validateCardType(cardNumber).equals("Discover");
            case "mastercard":
                return new MasterCard("", 0, 0, 0, "").validateCardType(cardNumber).equals("MasterCard");
            default:
                throw new IllegalArgumentException("Invalid card type: " + cardType);
        }
    }
    
    private static String generateRandomVisaCardNumber() {
        StringBuilder cardNumber = new StringBuilder();
        cardNumber.append("4"); // Visa cards always start with '4'

        // Generate remaining digits randomly
        int remainingLength = RANDOM.nextInt(4) == 0 ? 16 : 13; // Randomly choose between 13 or 16 digits
        for (int i = 1; i < remainingLength; i++) {
            cardNumber.append(RANDOM.nextInt(10)); // Append random digit
        }

        return cardNumber.toString();
    }
    
    private static String generateRandomDiscoverCardNumber() {
        StringBuilder cardNumber = new StringBuilder();
        cardNumber.append("6011"); // Discover cards always start with '6011'

        // Generate remaining 12 digits randomly
        for (int i = 0; i < 12; i++) {
            cardNumber.append(RANDOM.nextInt(10)); // Append random digit
        }

        return cardNumber.toString();
    }

    private static String generateRandomMasterCardNumber() {
        StringBuilder cardNumber = new StringBuilder();
        cardNumber.append("5"); // MasterCard cards always start with '5'

        // Randomly select the second digit between '1' and '5' (inclusive)
        int secondDigit = RANDOM.nextInt(5) + 1;
        cardNumber.append(secondDigit);

        // Generate remaining 14 digits randomly
        for (int i = 0; i < 14; i++) {
            cardNumber.append(RANDOM.nextInt(10)); // Append random digit
        }

        return cardNumber.toString();
    }
    
    private static int getRandomCVV() {
        // Generate a random number between 100 and 999
        return RANDOM.nextInt(900) + 100;
    }

    
    
}
