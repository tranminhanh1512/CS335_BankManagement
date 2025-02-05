package bankClasses;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.FileWriter;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

    class CustomerFactory{
    	private static int customerIDCounter = 1;
        Scanner sc; 
        File customerFile;
        CustomerFactory(){
            sc = new Scanner(System.in);
        }

        CustomerFactory(String source){
            
            try{
              customerFile = new File("data/CustomerList.csv");
              sc = new Scanner(customerFile);
              
              if (sc.hasNextLine()) {
                  sc.nextLine();
              }
              }
            catch (FileNotFoundException e) {
              System.out.println("An error occurred.Enter info from keyboard");
              e.printStackTrace();
              sc = new Scanner(System.in);
            }
        }

        public Customer makeCustomer()throws Exception{
        	try {
            System.out.printf("Client, what is your first name? : ");
            String firstName = sc.next();
            
            System.out.printf("Client, what is your last name? : ");
            String lastName = sc.next();
            
            System.out.printf(firstName + " "+ lastName+ ", what is your email? : ");
            String email = sc.next();
            
            System.out.printf(firstName + " "+ lastName+ ", what is your date of birth? mm/dd/yyyy: ");
            String dobString = sc.next();
            Date birthday = parseDate(dobString);
            
            String username = generateCustomerID(firstName, lastName);
            System.out.printf(firstName + " "+ lastName+ ", your username/customer ID is : "+username);
            System.out.println();
            System.out.printf(firstName + " "+ lastName+ ", what is your password?");
            String password = sc.next();
            
            Customer cus = new Customer(firstName, lastName, email, birthday, username, password);
            
            String answer = "y";
            
            do {
                System.out.println("Do you want to open an account? [y/n] ");
                answer = sc.next();
                if (answer.equals("y")) {
                    System.out.printf("What type of account do you want? [C]hecking/[S]aving? ");
                    String accTypeAns = sc.next();
                    String accType;
                    if (accTypeAns.equals("C")) {
                        accType = "Checking";
                    } else {
                        accType = "Saving";
                    }

                    Random random = new Random();
                    int accNum = random.nextInt(999999 - 100000 + 1) + 100000;
                    System.out.println("Your account number is: " + accNum);

                    System.out.printf("How much money do you want to insert? ");
                    int accBal = sc.nextInt();

                    Account a = new Account(accNum, accType, accBal, username);

                    cus.addAccount(a);
                }
            } while (answer.equals("y"));

            try (FileWriter pw = new FileWriter("data/CustomerList.csv", true)) {
                pw.append(firstName + ",");
                pw.append(lastName + ",");
                pw.append(email + ",");
                pw.append(dobString + ",");
                pw.append(username + ",");
                pw.append(password + ",");

                if (cus.getCheckingAccount()!=null&& cus.getSavingAccount()!=null) {
                    Account cusCheckAcc = cus.getCheckingAccount();
                    Account cusSavAcc = cus.getSavingAccount();
                    pw.append(cusCheckAcc.getAccNum() + ",");
                    pw.append(cusCheckAcc.getAccBal() + ",");
                    pw.append(cusSavAcc.getAccNum() + ",");
                    pw.append(cusSavAcc.getAccBal() +"\n");
                } else {
                    if (cus.getCheckingAccount()!=null) {
                        Account cusCheckAcc = cus.getCheckingAccount();
                        pw.append(cusCheckAcc.getAccNum() + ",");
                        pw.append(cusCheckAcc.getAccBal() + ",");
                        pw.append("0" + ",");
                        pw.append("0" + "\n");
                    } else if (cus.getSavingAccount()!=null) {
                        Account cusSavAcc = cus.getSavingAccount();
                        pw.append("0" + ",");
                        pw.append("0" + ",");
                        pw.append(cusSavAcc.getAccNum() + ",");
                        pw.append(cusSavAcc.getAccBal() + "\n");
                    } else {
                        pw.append("0" + ",");
                        pw.append("0" + ",");
                        pw.append("0" + ",");
                        pw.append("0" + "\n");
                    }
                }

                System.out.println("Finished writing to file");

            } catch (FileNotFoundException e) {
                System.out.println("Error writing to file ");
                e.printStackTrace();
            }
            return cus;
            } catch(ParseException e){
        		 System.out.println("Error parsing date. Please enter a valid date format (mm/dd/yyyy).");
        	     sc.nextLine(); 
        	     return makeCustomer();
        	}
       
    }
        
    private Date parseDate(String dateString) throws ParseException {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            return dateFormat.parse(dateString);
    }
    
    private String generateCustomerID(String firstName, String lastName) {
        return String.format("%s%s%03d", firstName.substring(0, 1).toUpperCase(), lastName.toUpperCase(), customerIDCounter++);
    }


    boolean hasMoreData(){return(sc.hasNextLine());}

    Customer getNextCustomer() throws ParseException{
    	
    	Customer c; 
        Account a1;
        Account a2;
        if (sc.hasNextLine()) {
            String data = sc.nextLine();
            String[] arrOfStr = data.split(",", 10);
            c = new Customer(arrOfStr[0], arrOfStr[1], arrOfStr[2], parseDate(arrOfStr[3]), arrOfStr[4], arrOfStr[5]);
            // Create checking account only if account number is not 0
            if (!arrOfStr[6].equals("0")) {
                a1 = new Account(Integer.parseInt(arrOfStr[6]), "Checking", Integer.parseInt(arrOfStr[7]), arrOfStr[4]);
                c.addAccount(a1);
            }
            // Create saving account only if account number is not 0
            if (!arrOfStr[8].equals("0")) {
                a2 = new Account(Integer.parseInt(arrOfStr[8]), "Saving", Integer.parseInt(arrOfStr[9]), arrOfStr[4]);
                c.addAccount(a2);
            }
            return c;
        } else {
            c = new Customer("a", "b", "c", parseDate("01/01/1900"), "d", "e");
        }
        return c;
    	}
    }

