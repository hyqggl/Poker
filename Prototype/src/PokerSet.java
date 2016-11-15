import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PokerSet {
	public List<String> colors = new ArrayList<String>();
	public List<String> points = new ArrayList<String>();
	public List<Card> cardList = new ArrayList<Card>();
	public List<Card> shuffled = new ArrayList<Card>();
	
	public PokerSet() {
	colors.add("Spade");
	colors.add("Heart");
	colors.add("Club");
	colors.add("Diamond");
	for (int i = 2; i <= 10; i++) 
		points.add(String.valueOf(i));
	points.add("J");
	points.add("Q");
	points.add("K");
	points.add("A");
	
	//Create all cards
	for (int i = 0; i < 4; i++) {
		for (int j = 0; j < 13; j++) {
			Card aCard = new Card(colors.get(i), points.get(j));
			cardList.add(aCard);
		}
	}
	}
	
	public void shuffleTheCard() {
		Random random = new Random();
		int j = 0;
		int size = cardList.size();
		for (int i = 0; i < size; i++) {
			j = random.nextInt(cardList.size());
			shuffled.add(cardList.get(j));
			cardList.remove(j);
		}
	}
	
	public void printAllCards(List<Card> li) {
		int i = 0;
		for (Card c : li) {
			System.out.print(c.color + "-" + c.point + " ");
			i++;
			if (i == 13) {
				System.out.println();
				i = 0;
			}
		}
	}
}
