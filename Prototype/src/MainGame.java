import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/*
 * Created on 11/14/2016   by hyq
 */
public class MainGame {
	PokerSet pokerset = new PokerSet();
	Player player1, player2;
	int cardsToPull;
	
	public static void main(String[] args) {
		System.out.println("Welcome.");
		MainGame game = new MainGame();
		System.out.println("-------------Created a pack of new Poker----------------");
		//game.pokerset.printAllCards(game.pokerset.cardList);
		System.out.println("-------------Shuffled the Poker-------------------------");
		game.pokerset.shuffleTheCard();
		//game.pokerset.printAllCards(game.pokerset.shuffled);
		game.createPlayer();
		System.out.println("====     Welcome, " + game.player1.getPlayerInfo() + ", " 
				+ game.player2.getPlayerInfo()+ "      ====");
		System.out.println("Each player deals how many cards? (Less than 26 inclusive): ");
		game.cardsToPull = game.enterPullNum();
		
		System.out.println("================    Game Starts   =====================");
		game.pullCards(game.cardsToPull);
		game.sortCards();
		game.compareMax();
		System.out.println("=============================");
		game.displayCards(game.player1);
		game.displayCards(game.player2);		
	}
	
	public void compareMax() {
		List<Card> maxCardsSet = new ArrayList<Card>();
		maxCardsSet.add(player1.playerCard.get(player1.playerCard.size()-1));
		System.out.println("The biggest Card of Player 1 is: " 
				+ player1.playerCard.get(player1.playerCard.size()-1).getColorPoint());
		maxCardsSet.add(player2.playerCard.get(player2.playerCard.size()-1));
		System.out.println("The biggest Card of Player 2 is: " 
				+ player2.playerCard.get(player2.playerCard.size()-1).getColorPoint());
		Collections.sort(maxCardsSet, Card.comparator);
		if (player1.playerCard.contains(maxCardsSet.get(maxCardsSet.size()-1))){
			System.out.println("Player 1 " + player1.getPlayerInfo() + " Wins!");
		} else {
			System.out.println("Player 2 " + player2.getPlayerInfo() + " Wins!");
		}
	}
	
	public void sortCards() {
		Collections.sort(player1.playerCard, Card.comparator);
		Collections.sort(player2.playerCard, Card.comparator);
		System.out.println("=========  Cards sorted  ===========");
	}
	
	public void pullCards(int num) {
		int remain = pokerset.shuffled.size();
		for (int i = 0; i < num; i++){
			player1.playerCard.add(pokerset.shuffled.get(pokerset.shuffled.size()-1));
			pokerset.shuffled.remove(pokerset.shuffled.size()-1);
			System.out.println("[Player 1] " + player1.name + " gets a card.");
			player2.playerCard.add(pokerset.shuffled.get(pokerset.shuffled.size()-1));
			pokerset.shuffled.remove(pokerset.shuffled.size()-1);
			System.out.println("[Player 2] " + player2.name + " gets a card.");
		}
		System.out.println("==============   Dealt " + num*2 + " cards in total ==============");
	}
	
	public void displayCards(Player player) {
		System.out.println("Display " +player.name+ "'s cards:");
		System.out.print(player.name + ":[ ");
		for (Card c : player.playerCard) {
			System.out.print(c.color + "-" + c.point + " ");
		}
		System.out.println("]");
	}
	
	public int enterPullNum(){
		int x;
		while (true){
		try{
			Scanner in = new Scanner(System.in);
			x = in.nextInt();
			if (x < 1 || x > 26) System.out.println("From 1 to 26. Please enter again."); 
			else return x;
		}   catch(InputMismatchException e){
			System.out.println("Oops.Please enter a integer.");		
		}
			catch(Exception e){
				System.out.println("Oops.Unexpected error.");			
		}
		}
	}
	
	public void createPlayer() {
		System.out.println("-------------Creating new players------------------------");
		player1 = new Player();
		player2 = new Player();
		/*while (true){
		try{
			Scanner in = new Scanner(System.in);
			System.out.println("Fisrt Player:");
			System.out.print("Enter ID:");
			int newId = in.nextInt();
			System.out.print("Enter name:");
			String newName = in.next();
			player1 = new Player(newId, newName);
			System.out.println();
			System.out.println("Welcome, " + newName);
			System.out.println();
			break;
			}   catch(InputMismatchException e){
				System.out.println("Oops.Please enter a integer.");		
			}
				catch(Exception e){
					System.out.println("Oops.Unexpected error.");			
			}
		}
		while (true){
		try{
			Scanner in = new Scanner(System.in);
			System.out.println("Second Player:");
			System.out.print("Enter ID:");
			int newId = in.nextInt();
			System.out.print("Enter name:");
			String newName = in.next();
			player2 = new Player(newId, newName);
			break;
			}    catch(InputMismatchException e){
				System.out.println("Oops.Please enter a integer.");		
			}
				catch(Exception e){
			System.out.println("Oops.Unexpected error.");			
			}
		}*/
	}
	

}
