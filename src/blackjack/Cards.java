package blackjack;

import java.util.ArrayList;
import java.util.Collections;

public class Cards {

	private ArrayList<Card> cards;
	
	public Cards() {
		cards = new ArrayList<Card>();
		
		initalizeCards();
		shuffleCards();
	}
	
	/*
	 * Methode om stokken kaarten te initialiseren. 
	 */
	private void initalizeCards() {
		// Eerste loop is zodat je 6* 52 kaarten krijgt.
		for (int h = 0; h < 6; h++) {
			for (int i = 0; i < 4; i++) {
				for (int j = 1; j < 14; j++) {
					if (i == 0) cards.add(new Card("\u2660", j));
					if (i == 1) cards.add(new Card("\u2665", j));
					if (i == 2) cards.add(new Card("\u2666", j));
					if (i == 3) cards.add(new Card("\u2663", j));
				}
			}
		}
	}
	
	/*
	 * Methode om de kaarten te shufflen zodat de deler ze uiteindelijk geshuffled kan uitdelen.
	 */
	public void shuffleCards() {
		Collections.shuffle(cards);
	}
	
	public ArrayList<Card> getDeck() {
		return cards;
	}

}
