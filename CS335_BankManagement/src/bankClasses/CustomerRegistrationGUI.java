package bankClasses;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
//import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomerRegistrationGUI {
	
	CustomerRegistrationGUI(){
	

        //Creating the Frame
        JFrame frame = new JFrame("User Registration Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        JPanel panel = new JPanel();
        frame.add(panel);

        panel.setLayout(null);

        //Box for first name
        JLabel firstNameLabel = new JLabel("First name");
        firstNameLabel.setBounds(10, 20, 80, 25);
        panel.add(firstNameLabel);
        JTextField firstText = new JTextField(12);
        firstText.setBounds(100,20,165,25);
        panel.add(firstText);

        //Box for last name
        JLabel lastNameLabel = new JLabel("Last name");
        lastNameLabel.setBounds(10, 50, 80, 25);
        panel.add(lastNameLabel);
        JTextField lastText = new JTextField(12);
        lastText.setBounds(100,50,165,25);
        panel.add(lastText);

        //Box for email
        JLabel emailLabel = new JLabel("Email address");
        emailLabel.setBounds(10, 80, 100, 25);
        panel.add(emailLabel);
        JTextField emailText = new JTextField(12);
        emailText.setBounds(100,80,165,25);
        panel.add(emailText);

        //Box for DOB
        JLabel dobLabel = new JLabel("Date of birth");
        dobLabel.setBounds(10, 110, 80, 25);
        panel.add(dobLabel);
        JTextField dobText = new JTextField(12);
        dobText.setBounds(100,110,165,25);
        panel.add(dobText);
        
      //Box for username
        JLabel userNameLabel = new JLabel("Username");
        userNameLabel.setBounds(10, 140, 80, 25);
        panel.add(userNameLabel);
        JTextField userNameText = new JTextField(12);
        userNameText.setBounds(100,140,165,25);
        panel.add(userNameText);
        

        //Box for password
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10,170,80,25);
        panel.add(passwordLabel);
        JPasswordField passwordText = new JPasswordField(12);
        passwordText.setBounds(100,170,165,25);
        panel.add(passwordText);

        //Registration button
        JButton registrationButton = new JButton("Register");
        registrationButton.setBounds(10, 200, 120, 25);
        panel.add(registrationButton);

        //Display message
        JLabel messageLabel = new JLabel();
        messageLabel.setBounds(100,250,250,35);
        messageLabel.setFont(new Font(null,Font.ITALIC,20));
        panel.add(messageLabel);

        frame.setVisible(true);

        registrationButton.addActionListener((ActionEvent e) -> {
            String firstName = firstText.getText();
            String lastName = lastText.getText();
            String email = emailText.getText();
            String dob = dobText.getText();
            Date birthday = null;
            try {
                birthday = parseDate(dob);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            String username = userNameText.getText();
            String password = new String(passwordText.getPassword());
            Customer newCustomer = new Customer(firstName, lastName, email, birthday, username, password);

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || dob.isEmpty() || password.isEmpty()) {
                // If any of the fields are empty, display an error message
                messageLabel.setText("Please fill in all fields");
            } else {
                try {
                    boolean customerExists = false;
                    String firstColumn;
                    String secondColumn;
                    FileReader reader = new FileReader("data/CustomerList.csv");
                    BufferedReader buffReader = new BufferedReader(reader);

                    String fline;
                    while ((fline = buffReader.readLine()) != null) {
                        String[] columns = fline.split(",");
                        if (columns.length >= 2) {
                            firstColumn = columns[0].trim();
                            secondColumn = columns[1].trim();
                            if (firstName.equals(firstColumn) && lastName.equals(secondColumn)) {
                                customerExists = true;
                                break; // No need to continue checking
                            }
                        }
                    }
                    buffReader.close();

                    if (customerExists) {
                        System.out.println("Customer Account already exists. Please login with your credentials");
                    } else {
                        writeNewCustomer(newCustomer);
                        messageLabel.setForeground(Color.blue);
                        messageLabel.setText("Registration successful!");
                    }
                } catch (IOException er) {
                    er.printStackTrace();
                }
            }
            frame.dispose();
            new LoginGUI();
        });

        
	}
	
	private Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.parse(dateString);
	}
	
	private void writeNewCustomer(Customer customer) {
    	try (FileWriter pw = new FileWriter("data/CustomerList.csv", true)) {
        	
        	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        	String formattedDate = dateFormat.format(customer.getDOB());
        	pw.append("\n");
            pw.append(customer.getFirstName() + ",");
            pw.append(customer.getLastName() + ",");
            pw.append(customer.getEmail() + ",");
            pw.append(formattedDate + ",");
            pw.append(customer.getCustomerID() + ",");
            pw.append(customer.getPassword() + ",");           
            pw.append("0" + ",");
            pw.append("0" + ",");
            pw.append("0" + ",");
            pw.append("0");
            
        } catch (IOException e2) {
            System.out.println("Error writing to file ");
            e2.printStackTrace();
        }
	}
    

    public static void main(String[] args) {
    		
    	new CustomerRegistrationGUI();
    		
    		
    	}


}
