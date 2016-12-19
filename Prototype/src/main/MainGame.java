package main;

import bean.Card;
import bean.Player;
import bean.PokerSet;

import java.io.IOException;
import java.util.*;


public class MainGame {

	private static PokerSet pokerset;
	private static List<Player> playerList = new ArrayList<Player>();
	private static List<Map<Integer,Integer>> playerBet;
	private static List<Card> Ref;   // 5张牌
	
	public static void main(String[] args) {

		System.out.println("================    Game Starts   =====================");
		//创建玩家
		createPlayer(2);
		int cardsToPull = 2;

		while (playerList.size() > 1) {

			System.out.println("-------------Created a pack of new Poker----------------");
			pokerset = new PokerSet();
			pokerset.shuffleTheCard();

			playerBet = new ArrayList<>();
			Ref = new ArrayList<>();

			pause(5);

			//荷官牌
			pullRefCards(5);
			//玩家抽牌
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
	

	static void pullRefCards(int num) {
		for (int i = 0; i < num; i++) {
			Ref.add(pokerset.getCardList()
					.get(pokerset.getCardList().size() - 1));
			pokerset.getCardList()
					.remove(pokerset.getCardList().size() - 1);
		}
	}
	
	static void pullCards(int num) {
		for (int i = 0; i < num; i++){
			for (Player player : playerList) {
				player.getPlayerCard()
						.add(pokerset.getCardList().get(pokerset.getCardList().size() - 1));
				pokerset.getCardList()
						.remove(pokerset.getCardList().size() - 1);
			}
		}
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


	static void pause(int n) {
		try {
			Thread.sleep(1000 * n);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	static void createPlayer(int playerNum) {
		System.out.println("-------------Creating new players------------------------");
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
