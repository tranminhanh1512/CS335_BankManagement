package bankClasses;

public class Transaction {
	protected String storeName;
	protected int transAmount;
	protected int accID;
	
	Transaction(String name, int num, int id){
		this.storeName = name;
		this.transAmount = num;
		this.accID = id;
	}
	
	public int getAccNum() {
		return accID;
	}
		
	public String getStName() {
		return storeName;
	}
	
	public int getTransAmount() {
		return transAmount;
	}

	
	public String toString() {
		return("Store: " + storeName + ", Amount: " + transAmount + ", Ending balance: ");
	}
}
