package bankClasses;

public class MasterCard extends CreditCard {

    public MasterCard(String card_number, int balance, int limit, int cvv, String username) {
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
            if (value.charAt(1) == '1' || value.charAt(1) == '2' || value.charAt(1) == '3' || value.charAt(1) == '4' || value.charAt(1) == '5') {
                return "MasterCard";
            }
        }catch(Exception e){
            return "Invalid";
        }
        return "Invalid";
    }
}