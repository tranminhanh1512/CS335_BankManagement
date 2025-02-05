package bankClasses;

import java.util.*;
import java.text.ParseException;

class logIn{
    private int maxAttempts = 3;
    private ArrayList<Customer> customerList;
    
    public logIn() {
		this.customerList = implementCustomerList();
    }
    
    public void authenticateUser() {
    
        Scanner scanner = new Scanner(System.in); 
        
        int attempts = 0;
        while (attempts < maxAttempts) {
            System.out.printf("Enter username: ");
            String username = scanner.nextLine();
            System.out.printf("Enter password: ");
            String password = scanner.nextLine();

            if (login(username, password)) {
                System.out.println("Login successful!");
                return;
            } else {
                System.out.println("Login failed. Please try again.");
            }
            attempts++;
        }

        scanner.close();
        System.out.println("Max attempts exceeded"); 
    }
    public ArrayList<Customer> implementCustomerList(){
		ArrayList<Customer> cusList = new ArrayList<Customer>();
		CustomerFactory cF = new CustomerFactory("data/CustomerList.csv");
		try {
            while (cF.hasMoreData()) {
            	cusList.add(cF.getNextCustomer());
            }
            return cusList;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null; 
    }
    
    public Customer getLoggedInCustomer(String username) {
    	for (Customer customer : customerList) {
            if (customer.getCustomerID().equals(username)) {
                return customer;
            }
        }
        return null;
    }
    
    public boolean login(String username, String password) {
        Customer loggedInCustomer = this.getLoggedInCustomer(username);
        if (loggedInCustomer != null) {
            if (loggedInCustomer.getPassword().equals(password)) {
                System.out.println("Login successful!");
                return true;
            } else {
                System.out.println("Incorrect password");
            }
        } else {
            System.out.println("Username does not exist");
        }
        return false;
    }
    
}