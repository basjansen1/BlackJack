package blackjack;

import java.util.ArrayList;

public class Hand {
	private ArrayList<Card> hand;
	private int inzet;
	private String action;
	
	public Hand() {
		hand = new ArrayList<Card>();
		inzet = 0;
		action = "";
	}
	
	public void addCardToHand(Card card) {
		hand.add(card);
	}
	
	public void removeCardFromHand(int index) {
		if (index < hand.size()) {
			hand.remove(index);
		}
	}
	
	public void getHand(String typeOfPlayer) {
		if (typeOfPlayer.equals("Speler") || typeOfPlayer.equals("bereken")) {
			for (Card card : hand) {
				System.out.print(card.getCard() + " ");
			}
		}
		else {
			for (int i = 0; i < hand.size() - 1; i++) {
				System.out.println(hand.get(i).getCard());
			}
		}
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	public String getAction() {
		return action;
	}
	
	public void setInzet(int inzet) {
		this.inzet = inzet;
	}
	
	public int getInzet() {
		return inzet;
	}
	
	public int getTotalCardsInHand() {
		return hand.size();
	}
	
	public int getTotalPoints() {
		int totalvalue = 0;
		
		for (Card card : hand) {
			if (card.getCardValue() == 1 && (totalvalue + 11) < 22) {
				totalvalue += 11;
			}
			// Dit is zodat de heren, boeren, en vrouwern kaart ook voor 10 punten tellen.
			else if (card.getCardValue() == 11 || card.getCardValue() == 12 || card.getCardValue() == 13) {
				totalvalue += 10;
			}
			else {
				totalvalue += card.getCardValue();
			}
		}
		
		// Kijk achteraf of er een aas in zit (aas = 1 standaard in mijn code)
		// Als de punten boven de 21 komen laat de aas als 1 tellen.
		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i).getCardValue() == 1 && totalvalue > 21) {
				// Als er een aas in de hand zit en de waarde komt boven de 21 gaat de aas voor 1 tellen.
				totalvalue -= 10;
			}
		}
		return totalvalue;
	}
	
}
