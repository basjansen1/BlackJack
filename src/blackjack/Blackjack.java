package blackjack;

import java.util.ArrayList;
import java.util.Scanner;

public class Blackjack {
	private Hand[] playerHand;
	private Hand delerHand;
	private Cards card;
	private Player player;
	private ArrayList<Card> givenCards;
	private boolean[] handsFinished;
	private int allowedTurnsPerRound;

	public Blackjack(int amountOfHands, Player player) {
		this.player = player;
		
		playerHand = new Hand[amountOfHands];
		delerHand = new Hand();
		card = new Cards();		
		givenCards = card.getDeck();
		handsFinished = new boolean[amountOfHands];
	
		giveCards();
		allowedTurnsPerRound = this.getTurnsInRound();

		play();
	}
	
	// De surpresswarnings resource heb ik erbij gezet, omdat volgens internet je de resersouces(System.in) niet hoeft te sluiten.
	// Dat doet Java zelf. 
	@SuppressWarnings("resource")
	private void play() {
		showHands();

		String commando = "";
		int turns = 0;
		
		// TODO-> De inzet mag niet groter zijn dan het startkapitaal gedeeld door het aantal handen.
		// Outerloop label is zodat we ten alle tijden uit de loop kunnen breaken als we willen.
		outerloop:
		while(true) {
			for (int i = 0; i < playerHand.length; i++) {			
				// Ga net zolang door totdat de gene gepast heeft of inzet verdubbeld heeft.
				
				// Als de kaarten voor de helft op zijn, gebruik 6 nieuwe stokken kaarten.
				if (givenCards.size() <= 152) {
					givenCards = null;
					card = null;
					
					// Maak nieuw Cards object aan.
					card = new Cards();
					
					// Krijg de nieuwe 6 stokken kaarten.
					givenCards = card.getDeck();
					
					// Geef de stokken kaarten aan de speler.
					giveCards();
				}
				
				if (playerHand[i].getAction().equals("draaien") || playerHand[i].getAction().equals("")) {
					playerHand[i].setInzet(player.getInzet());
					
					if (playerHand[i].getTotalPoints() == 21 && playerHand[i].getTotalCardsInHand() == 2) {
						// Je hebt blackjack dus pas automatisch.
						commando = "p";
					}
					
					while(!commando.equals("p") || !commando.equals("d") || !commando.equals("2") || commando.equals("")) {
						// Dit moest ik erbij zetten, zodat als je blackjack hebt hij niet nog eens vraagt om een actie te doen.
						// Zonder dit if statement werkt het niet.
						if(commando.equals("p") && playerHand[i].getTotalPoints() == 21 
								&& playerHand[i].getTotalCardsInHand() == 2) {
							 break;
						}

						System.out.print(player.getName() + ", wat wil je doen met hand " + (i + 1) + "? ");
						Scanner c = new Scanner(System.in);
						commando = c.nextLine();
						
						if (commando.equals("p") || commando.equals("d") || commando.equals("2")) {
							break;
						}
						else {
							System.out.println("Dat begrijp ik niet.");
						}
					}
					
					switch(commando) {
						case "p":
							// Deze hand is nu inactief.
							playerHand[i].setAction("Gepast");
							break;
						case "d":
							if (playerHand[i].getTotalPoints() < 21) {
								playerHand[i].addCardToHand(givenCards.get(0));
								// Verwijder de kaart uit de lijst, zodat je hem niet nog een keer kan gebruiken.
								givenCards.remove(0);
							}
							
							System.out.print("Nieuwe situatie: ");
							playerHand[i].getHand("Speler");
							System.out.print("\t\t\t\t\t Inzet = " + player.getInzet() + "\n");
							break;
						case "2":
							int nieuweInzet = (player.getInzet() * 2);
							
							if (player.getMoney() - (player.getInzet() * playerHand.length) > nieuweInzet) {
								playerHand[i].setInzet(nieuweInzet);
								playerHand[i].setAction("Dubbel");

								if (playerHand[i].getTotalPoints() < 21) {
									playerHand[i].addCardToHand(givenCards.get(0));
									// Verwijder de kaart uit de lijst, zodat je hem niet nog een keer kan gebruiken.
									givenCards.remove(0);
									System.out.print("Nieuwe situatie: ");
									playerHand[i].getHand("Speler");
									System.out.print("\t\t\t\t\t Inzet = " + playerHand[i].getInzet() + "  Dubbel" + "\n");
								}
							}
							else {
								System.out.println("U heeft niet genoeg kapitaal om dubbel in te zetten!");
								i--;
								continue;
							}
				
							break;
						default:
							System.out.println("Je hebt een verkeerdere waarde ingevuld gappie");
							break;
					}
					
					if (playerHand[i].getTotalPoints() > 21) {
						playerHand[i].setAction("Dood");
					}
					// Je hebt 2 kaarten in de hand en 21 punten dus heb je BLACKJACK!
					if (playerHand[i].getTotalPoints() == 21 && playerHand[i].getTotalCardsInHand() == 2) {
						playerHand[i].setAction("BLACKJACK!");
					}
					// Je hebt meer dan 2 kaarten in de hand, en 21 punten samen. Dus heb je geen blackjack maar wordt er automatisch gepast.
					if (playerHand[i].getTotalPoints() == 21 && playerHand[i].getTotalCardsInHand() > 2) {
						playerHand[i].setAction("Gepast");
					}
					
					turns++;
					
					// zet een variabele array en vul die elke keer als je niet gedraaid hebt. dan weet je dat je op een gegeven moment klaar bent
					if (!playerHand[i].getAction().equals("")) {
						this.handsFinished[i] = true;
					}
					else {
						this.handsFinished[i] = false;
					}
					
					// De showHands wordt uitgeprint als de speler 1 ronde heeft gedaan of hij meerdere rondes gedaan heeft.
					// Ook wordt er gecheckt of het spel afgelopen is en dan word de methode calculateWinner()
					// aangeroepen om te berekene wie er heeft gewonnen

					//if (turns == playerHand.length || turns > playerHand.length) {
					if (turns == allowedTurnsPerRound) {
						// Zet de beurten op 0, omdat we een nieuwe ronde beginnen.
						turns = 0;
						
						// Hierna komt er een nieuwe ronde dus moeten we weer opvragen 
						// na hoeveel beurten we spelen voordat we het overzicht laten zien
						allowedTurnsPerRound = getTurnsInRound();
												
						System.out.println();
						showHands();
						System.out.println();
						
						if (gameFinished()) {
							System.out.print("Deler: ");
							delerHand.getHand("bereken");
							
							Scanner c = new Scanner(System.in);
							
							while(delerHand.getTotalPoints() < 17) {
								System.out.print("\nDruk op return om de deler te laten spelen...");
								String enterKey;
								enterKey = c.nextLine();
								if (enterKey.equals("")) {
									calculateWinner();
								}
							}
							System.out.println("Je kapitaal is nu: \u20ac"  + calculateWinner());
							
							
							String playAgain = "";
							while(!playAgain.equals("j") || !playAgain.equals("n")) {
								System.out.print("\nWil je nog een keer spelen (j/n)? ");
								playAgain = c.nextLine();
								
								if (playAgain.equals("n")) {
									System.out.println("\nTot de volgende keer!");
									break outerloop;
								}
								else if (playAgain.equals("j")) {
									System.out.println("\n");
									// Start een nieuw blackjack spel.
									//c.close();
									if (player.getMoney() < (playerHand[i].getInzet() * playerHand.length)) {
										System.out.println("Sorry, u heeft niet genoeg kapitaal om door te spelen!\nTot de volgende keer!");
										break outerloop;
									}
									new Blackjack(playerHand.length, player);
								}
								else {
									System.out.println("Dat begrijp ik niet!");
								}
							}
						}
					}
				}	
			}
		}
	}
	
	private void giveCards() {
		int indexCard = 0;

		for (int i = 0; i < playerHand.length; i++) {
			playerHand[i] = new Hand();
			playerHand[i].addCardToHand(givenCards.get(indexCard));
			givenCards.remove(indexCard);
			indexCard++;
			
			playerHand[i].addCardToHand(givenCards.get(indexCard));
			givenCards.remove(indexCard);
			indexCard++;
			
			// Zet de inzet voor elke hand aan de hand van de inzet van de speler.
			playerHand[i].setInzet(player.getInzet());
		}
		
	
		delerHand.addCardToHand(givenCards.get(indexCard));
		givenCards.remove(indexCard);
		indexCard++;
	}
	
	private void showHands() {		
		System.out.println("********************************************************************************************");
		
		System.out.print("Deler: ");
	
		delerHand.getHand("Speler");
		System.out.println();
		
		System.out.println("--------------------------------------------------------------------------------------------");
		
		for (int j = 0; j < playerHand.length; j++) {
			System.out.print(player.getName());
			System.out.print(", hand " + (j + 1) + ": ");
			
			playerHand[j].getHand("Speler");
			System.out.print("\t\t\t\t\t Inzet = " + playerHand[j].getInzet() + "  " + playerHand[j].getAction() + "\n");
		}
		System.out.println("********************************************************************************************");
	}
	
	private boolean gameFinished() {
	    for(boolean b : handsFinished) {
	    	if(!b) {
	    		return false;
	    	}
	    }
	    
	    // Als die hier komt weten we dat alle posities in de array true zijn, wat betekent dat het spel afgelopen is.
	    return true;
	}
	
	private int getTurnsInRound() {
		int turnsInRound = 0;
		
		for (int i = 0; i < playerHand.length; i++) {
			if (playerHand[i].getAction().equals("") || playerHand[i].getAction().equals("draaien")) {
				turnsInRound++;
			}
		}
			
		return turnsInRound;
	}

	
	private int calculateWinner() {
		// De deler heeft 17 punten of meer dus past die automatisch.
		if (delerHand.getTotalPoints() >= 17) {
			System.out.println("De deler heeft gepast en is geeindigd met " + delerHand.getTotalPoints() 
					+ " punten");
			
			for (int i = 0; i < playerHand.length; i++) {		
				// Haal de inzet van het kapitaal van de speler af.
				player.setMoney(player.getMoney() - playerHand[i].getInzet());
								
				if (playerHand[i].getAction().equals("BLACKJACK!") && delerHand.getTotalPoints() != 21) {
					System.out.println(player.getName() + ", je wint hand " + (i + 1) + " met een inzet van " + playerHand[i].getInzet());
					
					// Geef hem zijn inzet 2.5* terug.
					player.setMoney(player.getMoney() + (int)(playerHand[i].getInzet() * 2.5));
				}
				
				else if (playerHand[i].getAction().equals("Dood")) {
					System.out.println(player.getName() + ", je verliest hand " + (i + 1) + " met een inzet van " + playerHand[i].getInzet());
					// Geef hem zijn inzet niet terug want hij heeft verloren
				}
				else if (delerHand.getTotalPoints() == 21 && playerHand[i].getTotalPoints() == 21) {
					if (delerHand.getTotalPoints() == 21 && delerHand.getTotalCardsInHand() == 2 && playerHand[i].getTotalCardsInHand() > 2) {
						// deler heeft blackjack, speler niet. Speler verliest de inzet dus.
						System.out.println(player.getName() + ", je verliest hand " + (i + 1) + " met een inzet van " + playerHand[i].getInzet());
						// Geef hem zijn inzet niet terug want hij heeft verloren
					}
					else {
						// deler en speler hebben allebei geen blackjack, maar wel 21 punten dus is het een gelijkspel.
						System.out.println(player.getName() + ", hand " + (i + 1) + " is een gelijkspel " 
								+ "met een inzet van " + playerHand[i].getInzet());
						
						// Omdat het een gelijkspel is krijgt hij zijn inzet terug.
						player.setMoney(player.getMoney() + playerHand[i].getInzet());
					}
				}
				else if (playerHand[i].getTotalPoints() == 21 && delerHand.getTotalPoints() < 21 || delerHand.getTotalPoints() > 21) {
					System.out.println(player.getName() + ", je wint hand " + (i + 1) + " met een inzet van " + playerHand[i].getInzet());
					
					// Geef hem zijn inzet 2* terug.
					player.setMoney(player.getMoney() + (playerHand[i].getInzet() * 2));
				}
				else if (playerHand[i].getTotalPoints() < 22 && delerHand.getTotalPoints() > 21) {
					System.out.println(player.getName() + ", je wint hand " + (i + 1) + " met een inzet van " + playerHand[i].getInzet());
					
					// Geef hem zijn inzet 2* terug.
					player.setMoney(player.getMoney() + (playerHand[i].getInzet() * 2));
				}
				else if (playerHand[i].getTotalPoints() > delerHand.getTotalPoints()) {
					if (playerHand[i].getTotalPoints() < 22) {
						System.out.println(player.getName() + ", je wint hand " + (i + 1) + " met een inzet van " + playerHand[i].getInzet());
						// Geef hem 2* zijn inzet terug want hij heeft gewonnen
						player.setMoney(player.getMoney() + (playerHand[i].getInzet() * 2));
					}
				}
				else if (delerHand.getTotalPoints() > playerHand[i].getTotalPoints()) {
					if (delerHand.getTotalPoints() < 22) {
						System.out.println(player.getName() + ", je verliest hand " + (i + 1) + " met een inzet van " + playerHand[i].getInzet());
						// Geef hem zijn inzet niet terug want hij heeft verloren
					}
				}
				else if (delerHand.getTotalPoints() == playerHand[i].getTotalPoints()) {
					if (delerHand.getTotalPoints() < 22 && playerHand[i].getTotalPoints() < 22) {
						System.out.println(player.getName() + ", hand " + (i + 1) + " is een gelijkspel " 
								+ "met een inzet van " + playerHand[i].getInzet());
						
						// Omdat het een gelijkspel is krijgt hij zijn inzet terug.
						player.setMoney(player.getMoney() + playerHand[i].getInzet());
					}
				}
			}
		}
		else {
			// De deler heeft de 17 punten nog niet bereikt dus pakt hij nog een kaart.
			delerHand.addCardToHand(givenCards.get(0));
			// Verwijder de kaart uit de lijst, zodat je hem niet nog een keer kan gebruiken.
			givenCards.remove(0);
			
			if (delerHand.getTotalPoints() > 21) {
				delerHand.setAction("Dood");
				
				System.out.print("Deler: ");
				delerHand.getHand("bereken");
				
				System.out.println("\t\t\t\t " + delerHand.getAction());
				return 0;
			}
			else if (delerHand.getTotalPoints() == 21 && delerHand.getTotalCardsInHand() == 2) {
				delerHand.setAction("BLACKJACK!");
				
				System.out.print("Deler: ");
				delerHand.getHand("bereken");
				
				System.out.println("\t\t\t\t " + delerHand.getAction());
				return 0;
			}
			
			System.out.print("Deler: ");
			delerHand.getHand("bereken");
			System.out.println();
		}
		
		return player.getMoney();
	}
}
