import bean.Card;
import bean.Player;
import bean.PokerSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class MainGame {

	private static PokerSet pokerset = new PokerSet();
	private static List<Player> playerList = new ArrayList<Player>();
	
	public static void main(String[] args) {
		System.out.println("-------------Created a pack of new Poker----------------");
		System.out.println("-------------Shuffled the Poker-------------------------");
		//洗牌
		pokerset.shuffleTheCard();

		//创建玩家
		createPlayer();
		System.out.println("====     Welcome, " + playerList.get(0).getPlayerInfo() + ", "
				+ playerList.get(1).getPlayerInfo()+ "      ====");

		//每个玩家抽几张
		System.out.println("Each player gets how many cards? (Less than 26 inclusive): ");
		int cardsToPull = enterPullNum();
		
		System.out.println("================    Game Starts   =====================");

		//抽牌
		pullCards(cardsToPull);
		for (Player player : playerList) {
			player.sortCards();
		}
		System.out.println("========== Cards  Sorted  ==========");

		//比牌
		compareMax();

		System.out.println("=============================");

		//玩家展示手牌
		for (Player player : playerList) {
			player.displayCards();
		}
	}
	
	static void compareMax() {
		List<Card> maxCardsSet = new ArrayList<Card>();
		maxCardsSet.add(playerList.get(0).getPlayerCard()
									.get(playerList.get(0).getPlayerCard().size()-1));
		System.out.println("The biggest of Player 1 is: "
				+ playerList.get(0).getPlayerCard()
									.get(playerList.get(0).getPlayerCard().size()-1).getColorPoint());
		maxCardsSet.add(playerList.get(1).getPlayerCard()
									.get(playerList.get(1).getPlayerCard().size()-1));
		System.out.println("The biggest of Player 2 is: "
				+ playerList.get(1).getPlayerCard()
									.get(playerList.get(1).getPlayerCard().size()-1).getColorPoint());
		Collections.sort(maxCardsSet, Card.comparator);
		if (playerList.get(0).getPlayerCard()
							.contains(maxCardsSet.get(maxCardsSet.size()-1))){
			System.out.println(" " + playerList.get(0).getPlayerInfo() + " Wins!");
		} else {
			System.out.println(" " + playerList.get(1).getPlayerInfo() + " Wins!");
		}
	}
	

	
	static void pullCards(int num) {
		for (int i = 0; i < num; i++){
			playerList.get(0).getPlayerCard()
					.add(pokerset.getCardList().get(pokerset.getCardList().size()-1));
			pokerset.getCardList()
					.remove(pokerset.getCardList().size()-1);

			playerList.get(1).getPlayerCard()
					.add(pokerset.getCardList().get(pokerset.getCardList().size()-1));
			pokerset.getCardList()
					.remove(pokerset.getCardList().size()-1);
		}
		System.out.println("==============   Dealt " + num*2 + " cards in total ==============");
	}
	

	
	static int enterPullNum(){
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
	
	static void createPlayer() {
		System.out.println("-------------Creating new players------------------------");
		int playerNum = 2;
		for (int i = 0; i < playerNum; i++) {
			Player p = new Player();
			playerList.add(p);
		}
		/*while (true){
		try{
			Scanner in = new Scanner(System.in);
			System.out.println("Fisrt bean.Player:");
			System.out.print("Enter ID:");
			int newId = in.nextInt();
			System.out.print("Enter name:");
			String newName = in.next();
			player1 = new bean.Player(newId, newName);
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
			System.out.println("Second bean.Player:");
			System.out.print("Enter ID:");
			int newId = in.nextInt();
			System.out.print("Enter name:");
			String newName = in.next();
			player2 = new bean.Player(newId, newName);
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
