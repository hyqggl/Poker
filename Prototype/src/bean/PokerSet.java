package bean;

import util.ColorAndPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 卡牌集,共52张
 */
public class PokerSet {

	private List<Card> cardList = new ArrayList<Card>();
	
	public PokerSet() {
	
	//Create all cards
	for (int i = 0; i < 4; i++) {
		for (int j = 0; j < 13; j++) {
			Card aCard = new Card(i, j);
			cardList.add(aCard);
		}
	}
	}

	//洗牌
	public void shuffleTheCard() {
		Random random = new Random();
		List<Card> temp = new ArrayList<Card>();
		int j = 0;
		int size = cardList.size();
		for (int i = 0; i < size; i++) {
			j = random.nextInt(cardList.size());
			temp.add(cardList.get(j));
			cardList.remove(j);
		}
		cardList = temp;
	}

	//打印所有牌
	public void printAllCards() {
		int i = 0;
		for (Card c : cardList) {
			System.out.print(c.getColor() + "-" + c.getPoint() + " ");
			i++;
			if (i == 13) {
				System.out.println();
				i = 0;
			}
		}
	}

	public List<Card> getCardList() {
		return cardList;
	}

	@Override
	public String toString() {
		return "PokerSet{" +
				"cardList=" + cardList +
				'}';
	}
}
