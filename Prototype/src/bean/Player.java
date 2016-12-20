package bean;

import bean.Card;
import util.NamePool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Player {

	private int id;
	private String name;
	private long chips;
	private List<Card> playerCard;

	/**
	 * 随机玩家，随机ID和名字
	 */
	public Player() {
		Random ran = new Random();
		id = ran.nextInt(100);
		name = NamePool.nameList.get(ran.nextInt(NamePool.nameList.size()));
		chips = ran.nextInt(100) * 100;
		this.playerCard = new ArrayList<Card>();
	}
	
	public Player(int id, String name, long chips) {
		this.id = id;
		this.name = name;
		this.chips = chips;
		this.playerCard = new ArrayList<Card>();
	}
	
	public String getPlayerInfo(){
		return "["+this.id+"]" + " " + this.name;
	}

	public void displayCards() {
		System.out.print(this.name + ":[ ");
		for (Card c : this.playerCard) {
			System.out.print(c.getColor() + "-" + c.getPoint() + " ");
		}
		System.out.println("]");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getChips() {
		return chips;
	}

	public void setChips(long chips) {
		this.chips = chips;
	}

	public List<Card> getPlayerCard() {
		return playerCard;
	}

	public void setPlayerCard(List<Card> playerCard) {
		this.playerCard = playerCard;
	}
}
