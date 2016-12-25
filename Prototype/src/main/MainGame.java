package main;

import bean.Card;
import bean.Player;
import bean.PokerSet;
import logic.BestComb;
import logic.CardRank;
import logic.Compare;
import util.Print;
import util.Rule;

import java.util.*;


public class MainGame {

	private static PokerSet pokerset;
	private static List<Player> playerList = new ArrayList<Player>();
	private static List<Map<Integer, Long>> playerBet;    //记录玩家下注
	private static List<Long> betPool;
	//-1空座  1玩家正常在座  2弃牌  0起身
	private static int[] playerStatus = new int[Rule.MaxPlayernum];
	private static List<Card> Ref;   // 5张牌
	
	public static void main(String[] args) {

		initialization();
		//创建玩家
		createPlayer(2);
		int cardsToPull = Rule.PCardNum;

		while (playerList.size() > 1) {

			System.out.println("-------------Created a pack of new Poker----------------");
			pokerset = new PokerSet();       //新牌
			pokerset.shuffleTheCard();	     //洗牌

			playerBet = new ArrayList<>();
			betPool = new ArrayList<>();
			Ref = new ArrayList<>();
			clearPlayerCardsAndCache();     //清空玩家手牌 和 最佳牌型缓存

			//荷官牌
			pullRefCards(Rule.RefNum);

			//玩家抽牌
			pullCards(cardsToPull);
			//RankCache 用于记录玩家最终牌型、牌力、位置


			System.out.println("=============================");

			for (int round = 1; round <= 3; round++) {
				System.out.println("Round " + round + ":");
				showRefCards(round + 2);

				int rank = 9;
				//玩家展示手牌
				for (Player player : playerList) {
					player.displayCards();
					rank = CardRank.getRank(Ref.subList(0, round+2), player.getPlayerCard());
					Print.printRank(rank);
					List<Integer> idx = BestComb.getCombIdx(rank, Ref.subList(0, round+2), player.getPlayerCard());
					if ((round == 3) && (idx != null)) {
						System.out.print(rank);
						Print.printIntArray(idx);
						Print.printComb(idx, Ref.subList(0, round+2), player.getPlayerCard());
					}
				}
				playerDecision(round);
			} //round
			fullAllPlayersRankCache(Ref);
			finalCompare();
			for (Player p : playerList) {
				System.out.println(p.toString());
			}
			checkPlayerStatus();
		} //while
	}

	private static void finalCompare(){
		int maxIdx = -1;
		int s = playerList.size();
		do {maxIdx++;} while ((maxIdx < s) && (playerStatus[playerList.get(maxIdx).getSeatNo()] != 1));
		for (int i = maxIdx + 1; i < s; i++) {
			if (Compare.comp2p(playerList.get(i), playerList.get(maxIdx)) > 0) {
				maxIdx = i;
			}
		}
		for (Long l : betPool) {
			playerList.get(maxIdx).addChips(l);
		}
	}

	private static void playerDecision(int round) {
		long total = 0;
		long chip;
		Player p;
		for (int i = 0; i < playerList.size(); i++) {
			p = playerList.get(i);
			if (playerStatus[p.getSeatNo()] == 1) {
				if (p.getChips() <= 500) {
					chip = p.getChips();
				} else {
					chip = 500;
				}
				//playerBet.get(i).put(round, chip); //todo
				total += chip;
				p.reduceChips(chip);
			}
		}
		betPool.add(total);
	}

	private static void initialization() {
		for (int i = 0; i < playerStatus.length; i++) {playerStatus[i] = -1;}
	}

	private static void fullAllPlayersRankCache(List<Card> ref) {
		int rank;
		for (Player player : playerList) {
			rank = CardRank.getRank(ref,player.getPlayerCard());
			player.setRankCache(BestComb.getCombIdx(rank, ref, player.getPlayerCard()));
		}
	}

	//检查玩家筹码是否为0
	private static void checkPlayerStatus() {
		List<Player> p = new ArrayList<>();
		for (Player player : playerList) {
			if (player.getChips() <= 0) {
				playerStatus[player.getSeatNo()] = -1;
				System.out.println("Player on seat No." + (player.getSeatNo()+1) + ", " + player.toString() + "leaves.");
			} else {
				p.add(player);
			}
		}
		playerList = p;
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



	private static void createPlayer(int NewPlayerNum) {
		System.out.println("-------------Creating new players------------------------");
		if ((playerList.size() + NewPlayerNum) > Rule.MaxPlayernum) {
			System.out.println("Too many players, " + (Rule.MaxPlayernum - playerList.size()) + "seats remain.");
			return;
		}
		for (int i = 0; i < NewPlayerNum; i++) {
			Player p = new Player();
			playerList.add(p);
			for (int j = 0; j < playerStatus.length; j++) {
				if (playerStatus[j] == -1) {
					playerStatus[j] = 1;
					p.setSeatNo(j);
					break;
				}
			}
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
