package blackjack;

public class Player {
	private String name;
	private int money;
	private int inzet;
	private String typeOfPlayer;
	private boolean hasMoneyBeenEntered;
	
	public Player(int money, String typeOfPlayer) {
		this.money = money;
		this.typeOfPlayer = typeOfPlayer;
		
		hasMoneyBeenEntered = false;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setMoney(int money) {
		this.money = money;
		
		hasMoneyBeenEntered = true;
	}
	
	public int getMoney() {
		return money;
	}
	
	public void setInzet(int inzet) {
		this.inzet = inzet;
	}
	
	public int getInzet() {
		return inzet;
	}
	
	public String getTypeOfPlayer() {
		return typeOfPlayer;
	}
	
	public boolean moneyEntered() {
		return hasMoneyBeenEntered;
	}
}
