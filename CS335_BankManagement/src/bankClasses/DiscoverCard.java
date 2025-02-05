package bankClasses;

public class DiscoverCard extends CreditCard {

    public DiscoverCard(String card_number, int balance, int limit, int cvv, String username) {
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
            if (value.substring(0, 4).equals("6011")) {
                return "Discover";
            }
        }catch(Exception e) {
            return "Invalid";
        }
        return "Invalid";
    }
}