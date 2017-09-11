package blackjack;

import java.util.Scanner;

public class Casino {
	private Player player;
	private int aantalHanden;
	private int inzet;
	private final int START_KAPITAAL = 1000;
	private int maxInzet;
	
	public Casino() {
		player = new Player(START_KAPITAAL, "Speler");
		aantalHanden = 0;

		startBlackjack();
	}
	
	// De surpresswarnings resource heb ik erbij gezet, omdat volgens internet je de resersouces(System.in) niet hoeft te sluiten.
	// Dat doet Java zelf. 
	@SuppressWarnings("resource")
	public void startBlackjack() {
		System.out.println("Welkom in het iCasino! We gaan fijn Blackjack spelen!!\n");
                System.out.println("Commando's");
                System.out.println("----------");
                System.out.println("p = passen");
                System.out.println("d = draaien");
                System.out.println("2 = inzet verdubbelen");
        
                try {
			Scanner scan = new Scanner(System.in);
			System.out.print("\nWat is je naam? ");
			player.setName(scan.nextLine());

			System.out.println("Welkom, " + player.getName() + ". Je startkapitaal is \u20ac 1000,-");

			while(aantalHanden == 0) {
				try {
					Scanner c = new Scanner(System.in);
				System.out.print("Met hoeveel handen wil je spelen (1-5)? ");

					aantalHanden = c.nextInt();
					maxInzet = START_KAPITAAL / aantalHanden;

					if (aantalHanden < 0 || aantalHanden > 5) {
						aantalHanden = 0;
						System.out.println("Dat aantal ligt niet tussen 1 en 5");
						continue;
					}

					break;
				}
				catch(Exception e) {
					System.out.println("Dat begrijp ik niet.");
				}
			}

			while(inzet == 0) {
				try {
					Scanner c = new Scanner(System.in);
					System.out.print("Met welke inzet wil je spelen (1-" + maxInzet + ")");
					inzet = c.nextInt();

					if (inzet > maxInzet || inzet < 0) {
						System.out.println("Dat aantal ligt niet tussen 1 en " + maxInzet);
						inzet = 0;
						continue;
					}

					System.out.println();

					player.setInzet(inzet);

					break;
				}
				catch(Exception e) {
					System.out.println("Dat begrijp ik niet.");
				}
			}

			// Start een spelletje Blackjack
		    new Blackjack(aantalHanden, player);
                }
                catch(Exception e) {
        		System.out.println("Er is iets fout gegaan!");
        		e.printStackTrace();
                }
	}
}
