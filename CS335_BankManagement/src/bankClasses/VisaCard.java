package bankClasses;

public class VisaCard extends CreditCard {

    public VisaCard(String card_number, int balance, int limit, int cvv, String username) {
        super(card_number, balance, limit, cvv, username);
    }

    @Override
	public String validateCardType(String card_number) {
    	if (card_number.equals("")){
            return "Invalid";
        }
        try {
            long number = Double.valueOf(card_number).longValue();
            String value = Long.toString(number);
            if ((value.length()==13 || value.length()==16) && value.charAt(0)=='4'){
                return "Visa";
            }
        }catch(Exception e) {
            return "Invalid";
        }
        return "Invalid";
    }
}