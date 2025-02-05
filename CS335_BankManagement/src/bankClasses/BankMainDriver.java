
package bankClasses;

import java.util.ArrayList;
import java.util.Scanner;

class BankMainDriver{
	public static void main(String [] args) throws Exception{
		
		
		CustomerFactory cF = new CustomerFactory("data/CustomerList.csv");
		CustomerFactory cFCustomer = new CustomerFactory();
		ArrayList<Customer> cusList = new ArrayList<Customer>();
		
		while (cF.hasMoreData()) {
			cusList.add(cF.getNextCustomer());
		}
		
	}
}