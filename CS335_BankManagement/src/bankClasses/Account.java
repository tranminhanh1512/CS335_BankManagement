package bankClasses;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class Account{
	
	protected int accNum;
	protected String accType;
	protected int accBal;
	protected String customerID;
	
	Account(int num, String type, int bal, String cusID){
		this.accNum = num;
		this.accType = type;
		this.accBal = bal;
		this.customerID = cusID;
	}
	
	public int getAccNum(){
		return this.accNum;
	}
	
	public String getAccType(){
		return this.accType;
	}
	
	public int getAccBal(){
		return this.accBal;
	}
	
	public String getCustomerID(){
		return this.customerID;
	}
	
	
	public void setAccBal(int newBal){
		this.accBal = newBal;
	}
	
	public void addBal(int baltransfer) {
		this.accBal += baltransfer;
	}
	
	public void subtractBal(int baltransfer) {
		this.accBal -= baltransfer;
	}
	
	public String toString(){
		return("Account Number: " + this.accNum + ", type: " + this.accType + ", balance: " + this.accBal + ", customer ID: "+ this.customerID);
	}
	
	public void withdraw(Integer amount, Customer cus, String acc) { // Updated to accept double
	    if (amount > 0 && amount <= accBal) {
	        accBal -= amount;
	        System.out.println("Withdrawal of $" + amount + " successful. Current balance: $" + accBal);
	        try (BufferedReader reader = new BufferedReader(new FileReader("data/CustomerList.csv"))) {
	            String line;
	            StringBuilder updatedFileContent = new StringBuilder(); // Declare StringBuilder to store updated file content
	            
	            // Read and append the header line
	            String header = reader.readLine();
	            updatedFileContent.append(header).append("\n");
	            
	            while ((line = reader.readLine()) != null) {
	                String[] parts = line.split(",");
	                if (parts.length >= 10) { // Ensure the line has enough elements
	                    String stored_customerID = parts[4].trim();
	                    if (stored_customerID.equals(cus.getCustomerID())) {
	                    	switch (acc.toLowerCase()) {
	                    		case "checking":
	    	                        parts[7] = Integer.toString(accBal);
	    	                        break;
	                    		case "saving":
	                    			parts[9] = Integer.toString(accBal);
	                    			break;
	                    	}
	                    }
	                }
	                // Reconstruct the line with modified parts
	                updatedFileContent.append(String.join(",", parts));
	                
	                // Append newline character if it's not the last line
	                if (reader.ready()) {
	                    updatedFileContent.append("\n");
	                }	            }

	            // Write the updated content back to the file
	            try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/CustomerList.csv"))) {
	                writer.write(updatedFileContent.toString());
	            }
	        } catch (IOException e) {
	            System.out.println("Customer not found in database.");
	            e.printStackTrace();
	        }
	    } else {
	        System.out.println("Invalid withdrawal amount or insufficient balance.");
	    }
	}


	public void insert(Integer amount, Customer cus, String acc) { // Added insert method, accepts double
	    if (amount > 0) {
	        accBal += amount;
	        System.out.println("Insertion of $" + amount + " successful. Current balance: $" + accBal);
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
	                    if (stored_customerID.equals(cus.getCustomerID())) {
	                    	
	                    	switch (acc.toLowerCase()) {
                    		case "checking":
    	                        parts[7] = Integer.toString(accBal);
    	                        break;
                    		case "saving":
                    			parts[9] = Integer.toString(accBal);
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
	    } else {
	        System.out.println("Invalid insertion amount.");
	    }
	}
	
}