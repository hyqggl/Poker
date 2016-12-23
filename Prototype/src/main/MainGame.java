package main;

import bean.Card;
import bean.Player;
import bean.PokerSet;
import logic.BestComb;
import logic.CardRank;
import util.Print;
import util.Rule;

import java.util.*;


public class MainGame {

	private static PokerSet pokerset;
	private static List<Player> playerList = new ArrayList<Player>();
	private static List<Map<Integer,Integer>> playerBet;    //记录玩家下注
	private static List<Card> Ref;   // 5张牌
	
	public static void main(String[] args) {

		//创建玩家
		createPlayer(2);
		int cardsToPull = Rule.PCardNum;

		while (playerList.size() > 1) {

			System.out.println("-------------Created a pack of new Poker----------------");
			pokerset = new PokerSet();       //新牌
			pokerset.shuffleTheCard();	     //洗牌

			playerBet = new ArrayList<>();
			Ref = new ArrayList<>();
			clearPlayerCardsAndCache();     //清空玩家手牌 和 最佳牌型缓存

			//荷官牌
			pullRefCards(Rule.RefNum);
			showRefCards(Rule.RefNum);

			//玩家抽牌
			pullCards(cardsToPull);
			//RankCache 用于记录玩家最终牌型、牌力、位置


			System.out.println("=============================");

			for (int round = 3; round <= 5; round++) {

				int rank = 9;
				//玩家展示手牌
				for (Player player : playerList) {
					player.displayCards();
					rank = CardRank.getRank(Ref.subList(0, round), player.getPlayerCard());
					Print.printRank(rank);
					List<Integer> idx = BestComb.getCombIdx(rank, Ref.subList(0, round), player.getPlayerCard());
					if (idx != null) {
						System.out.print(rank);
						Print.printIntArray(idx);
						Print.printComb(idx, Ref.subList(0, round), player.getPlayerCard());
					}
				}

			} //round
		} //while
	}

	private static void fullAllPlayersRankCache(List<Card> ref) {
		int rank;
		for (Player player : playerList) {
			rank = CardRank.getRank(ref,player.getPlayerCard());
			player.setRankCache(BestComb.getCombIdx(rank, ref, player.getPlayerCard()));
		}
	}

	private static void clearPlayerCardsAndCache() {
		for (Player player : playerList) {
			player.getPlayerCard().clear();
			player.getRankCache().clear();
		}
	}

	private static void showRefCards(int num) {
		System.out.print("Ref Cards: ");
		for (int i = 0; i < num; i++) {
			System.out.print(Ref.get(i).toString() + " ");
		}
		System.out.println();
	}

	private static void pullRefCards(int num) {
		for (int i = 0; i < num; i++) {
			Ref.add(pokerset.getCardList()
					.get(pokerset.getCardList().size() - 1));
			pokerset.getCardList()
					.remove(pokerset.getCardList().size() - 1);
		}
	}
	
	private static void pullCards(int num) {
		for (int i = 0; i < num; i++){
			for (Player player : playerList) {
				player.getPlayerCard()
						.add(pokerset.getCardList().get(pokerset.getCardList().size() - 1));
				pokerset.getCardList()
						.remove(pokerset.getCardList().size() - 1);
			}
		}
	}



	private static void createPlayer(int playerNum) {
		System.out.println("-------------Creating new players------------------------");
		for (int i = 0; i < playerNum; i++) {
			Player p = new Player();
			playerList.add(p);
		}
	}

	private static void pause(int n) {
		try {
			Thread.sleep(1000 * n);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
