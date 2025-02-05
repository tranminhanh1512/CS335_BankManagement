package bankClasses;


abstract class CreditCard {

    protected String card_number;
    protected int balance;
    protected int limit;
    protected Customer owner;
    protected String username;
    protected int cvv;
    protected String status;

    public CreditCard(String card_number, int balance, int limit, int cvv, String username){
        this.card_number = card_number;
        this.balance = balance;
        this.limit = limit;
        this.username = username;
    }

    public abstract String validateCardType(String card_number);
    public  int getBalance(){
    	return this.balance;
    }
    
    public String getCardNumber() {
    	return this.card_number;
    }

    public  int getCreditLimit()
    {
    	return this.limit;
    }
    
    public  String getPersonals(){
    	return this.owner.toString();
    }
    
    public String getStatus() {
    	return this.status;
    }
    public void changeStatus(String s) {
    	this.status = s;
    }
    
    public void payBalance(Customer cus) {
    	Account cusAcc = cus.getCheckingAccount();
        if (this.balance > 0) {
            cusAcc.withdraw(this.balance, cus, "checking");
            this.balance = 0;
        } else {
            System.out.println("No balance due. Payment not required.");
        }
    }
    
    public int getCVV() {
    	return this.cvv;
    }

}