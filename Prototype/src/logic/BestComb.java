package logic;

import bean.Card;
import util.Rule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by huyiqing on 16/12/20.
 */
public class BestComb {

    private static List<Card> cards;
    private static int[] idx;

    public static String[] PName =
            {"RoyalFlush", "StraightFlush", "FourOfAKind", "Fullhouse", "Flush",
                    "Straight", "ThreeOfAKind", "TwoPairs", "OnePair", "HighCard"};

    /**
     * 参考牌5张，玩家牌2张
     * 依次0, 1, 2, 3, 4, 5, 6
     * @return 返回指数
     */
    public static int[] getCombIdx(int rank, List<Card> ref, List<Card> playerCards)  {

        cards = new ArrayList<>();
        for (Card c : ref) {
            cards.add(c);
        }
        for (Card c : playerCards) {
            cards.add(c);
        }
        int[] idx = new int[cards.size() - 2];
        switch (rank) {
            case 0:
            case 1:
            case 4:
                idx = comb014(rank, cards);
                break;
            case 2:
                break;
            case 3:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            default: idx = comb9(cards);
        }

        return sort(idx);
    }

    private static int[] comb014(int rank, List<Card> cards) {
        List<Card> temp = new ArrayList<>();
        for (Card c : cards) {
            temp.add(c);
        }
        int[] idx = new int[Rule.combCardNum];
        Collections.sort(temp, Card.comparatorByColor);
        for (int end = temp.size()-1; end >= Rule.combCardNum-1; end--) {
            if (CardRank.MaxSeqColor(temp, end + 2 - Rule.combCardNum, end + 1) == Rule.combCardNum){
                if ((rank == 4) ||
                        ((rank == 9) && (temp.get(end).getPoint().equals("A")))
                                ||
                        ((rank == 1) && (CardRank.SameColorHaveSeq(temp,end + 2 - Rule.combCardNum, end + 1))))
                {
                    int i = 0;
                    while (i <= idx.length - 1) {
                        idx[i] = cards.indexOf(temp.get(end + 1 - Rule.combCardNum + i));
                        i++;
                    }
                }
                break;
            }
        }
        return idx;
    }

    private static int[] comb9(List<Card> cards) {
        List<Card> temp = new ArrayList<>();
        for (Card c : cards) {
            temp.add(c);
        }
        int[] idx = new int[Rule.combCardNum];
        Collections.sort(temp, Card.comparatorByPoint);
        for (int i = temp.size() - 1; i >= temp.size() - Rule.combCardNum; i--) {
            idx[temp.size() - 1 - i] = cards.indexOf(temp.get(i));
        }
        return idx;
    }

    private static int[] sort(int[] idx) {
        int minIdx;
        int temp;
        for (int i = 0; i < idx.length; i++) {
            minIdx = i;
            for (int j = i + 1; j < idx.length; j++) {
                if (idx[j] < idx[minIdx]) {
                    minIdx = j;
                }
            }
//            idx[i] = idx[i] ^ idx[minIdx];
//            idx[minIdx] = idx[i] ^ idx[minIdx];
//            idx[i] = idx[i] ^ idx[minIdx];
            temp = idx[i];
            idx[i] = idx[minIdx];
            idx[minIdx] = temp;
        }
        return idx;
    }
}
