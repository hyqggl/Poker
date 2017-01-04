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
	//-1空座  1玩家正常在座  2弃牌 3allIn 0起身(观战，下局开始前离开）
	private static int[] playerStatus = new int[Rule.MaxPlayernum];
	private static List<Card> Ref;   // 5张牌
	
	public static void main(String[] args) {

		initialization(); //playerStatus
		//创建玩家
		createPlayer(4);
		int cardsToPull = Rule.PCardNum;

		while (playerList.size() > 1) {


			chargeTableFee();      //收取开局费
			System.out.println("-------------Created a pack of new Poker----------------");
			pokerset = new PokerSet();       //新牌
			pokerset.shuffleTheCard();	     //洗牌

			playerBet = new ArrayList<>();
			betPool = new ArrayList<>();
			Ref = new ArrayList<>();
			clearPlayerCardsNCacheNPower();     //清空玩家手牌 和 最佳牌型缓存

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
			List<List<Player>> fc = finalCompare();
			for (List<Player> l : fc) {
				for (Player p : l) {
					System.out.print(p.getCardPower() + " ");
				}
				System.out.println();
			}
			pause(10);
			for (Player p : playerList) {
				System.out.println(p.toString());
			}
			checkPlayerStatus();	//检查玩家筹码是否为0, 踢出筹码不足的玩家。
		} //while
	}



	private static List<List<Player>> finalCompare(){
		List<Integer> avaliablePlayerIndex = new ArrayList<>();
		avaliablePlayerIndex.clear();
		for (int i = 0; i < playerList.size(); i++) {
			if (playerStatus[playerList.get(i).getSeatNo()] == 1
					|| playerStatus[playerList.get(i).getSeatNo()] == 3 )
				avaliablePlayerIndex.add(i);
		}
		fullPlayersRankCache(avaliablePlayerIndex,Ref);
		int s = avaliablePlayerIndex.size();
		int[] api_copy = new int[s];
		for (int i = 0; i < s; i++) { api_copy[i] = avaliablePlayerIndex.get(i); }
		for (int i = 0; i < s - 1; i++) {
			int maxidx = i;
			for (int j = i + 1; j < s; j++) {
				if (Compare.comp2p(playerList.get(api_copy[maxidx]), playerList.get(api_copy[j])) < 0) {
					maxidx = j;
				}
			}
			int temp = api_copy[i];
			api_copy[i] = api_copy[maxidx];
			api_copy[maxidx] = temp;
		}
		// 上面 得到按牌力从大到小排序的 PlayerIndex
		// 下方 得到奖金池，瓜分顺序
		List<List<Player>> finalPlayerRank = new ArrayList<>();
		int rank = 0;
		List<Player> l = new ArrayList<>();
		l.add(playerList.get(api_copy[0]));
		finalPlayerRank.add(l);
		int j = 1;
		while (j < api_copy.length) {
			if (Compare.comp2p(playerList.get(api_copy[j-1]), playerList.get(api_copy[j])) == 0) {
				finalPlayerRank.get(rank).add(playerList.get(api_copy[j]));
			} else {
				rank++;
				l = new ArrayList<>();
				l.add(playerList.get(api_copy[j]));
				finalPlayerRank.add(l);
			}
			j++;
		}

		return finalPlayerRank;
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

	private static void fullPlayersRankCache(List<Integer> l, List<Card> ref) {
		int rank;
		for (Integer ln : l) {
			rank = CardRank.getRank(ref,playerList.get(ln).getPlayerCard());
			playerList.get(ln).setRankCache(BestComb.getCombIdx(rank, ref, playerList.get(ln).getPlayerCard()));
			playerList.get(ln).generateCardPower();
		}
	}

	private static void checkPlayerStatus() {
		List<Player> p = new ArrayList<>();
		for (Player player : playerList) {
			if (player.getChips() <= Rule.TableFee
					|| playerStatus[player.getSeatNo()] == 0) {
				playerStatus[player.getSeatNo()] = -1;
				System.out.println("Player on seat No." + (player.getSeatNo()+1) + ", " + player.toString() + "leaves.");
			} else {
				p.add(player);
			}
		}
		playerList = p;
	}

	private static void clearPlayerCardsNCacheNPower() {
		for (Player player : playerList) {
			player.getPlayerCard().clear();
			player.getRankCache().clear();
			player.setCardPower2Zero();
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
			for (int j = 0; j < playerStatus.length; j++) {
				if (playerStatus[j] == -1) {
					playerStatus[j] = 1;
					p.setSeatNo(j);
					break;
				}
			}
			if (p.getSeatNo() != -1 )
			{
				playerList.add(p);
			} else {
				System.out.println("something wrong happens: creating players.");
			}
		}
	}

	private static void chargeTableFee() {
		for (Player p : playerList) {
			p.reduceChips(Rule.TableFee);
			if (p.getChips() <= 0) System.out.println("Something wrong happens: chargeTableFee.");
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
