package blackjack;

public class Card {
	private int cardValue;
	private String cardSymbol;
	
	public Card(String cardSymbol, int cardValue) {
		this.cardSymbol = cardSymbol;
		this.cardValue = cardValue;
	}
	
	public String getCardLetter() {
		switch(cardValue) {
			case 1:
				return "A";
			case 2:
				return "2";
			case 3:
				return "3";
			case 4:
				return "4";
			case 5:
				return "5";
			case 6:
				return "6";
			case 7:
				return "7";
			case 8:
				return "8";
			case 9:
				return "9";
			case 10:
				return "10";
			case 11:
				return "B";
			case 12:
				return "V";
			case 13:
				return "H";
			default:
				return "?";
		}
	}
		
	public int getCardValue() {
		switch(cardValue) {
			case 1:
				return 1;
			case 2:
				return 2;
			case 3:
				return 3;
			case 4:
				return 4;
			case 5:
				return 5;
			case 6:
				return 6;
			case 7:
				return 7;
			case 8:
				return 8;
			case 9:
				return 9;
			case 10:
				return 10;
			case 11:
				return 10;
			case 12:
				return 10;
			case 13:
				return 10;
			default:
				return 0;
		}
	}
	
	public String getCardSymbol() {
		return cardSymbol;
	}
	
	public String getCard() {
		return getCardSymbol() + getCardLetter();
	}
}
