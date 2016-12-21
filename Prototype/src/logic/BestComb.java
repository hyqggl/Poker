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

    public static String[] PName =
            {"RoyalFlush", "StraightFlush", "FourOfAKind", "Fullhouse", "Flush",
                    "Straight", "ThreeOfAKind", "TwoPairs", "OnePair", "HighCard"};

    /**
     * 参考牌5张，玩家牌2张
     * 依次0, 1, 2, 3, 4, 5, 6
     * @return 返回一个列表
     * 0: rank
     * 1: 第一张点数   2: 第一张位置   3: 第二张点数  4: 第二张位置 .....
     */
    public static List<Integer> getCombIdx(int rank, List<Card> ref, List<Card> playerCards)  {

        cards = new ArrayList<>();
        for (Card c : ref) {
            cards.add(c);
        }
        for (Card c : playerCards) {
            cards.add(c);
        }
        List<Integer> idx = new ArrayList<>();
        switch (rank) {
            case 0:
            case 1:
            case 4:
                idx = comb014(rank, cards);
                break;
            //四条FourOfAKind
            case 2:
                idx = comb2(rank, cards);
                break;
            //葫芦FullHouse
            case 3:
            //三条
            case 6:
                idx = comb36(rank, cards);
                break;
            //顺子
            case 5:
                idx = comb5(rank, cards);
                break;
            //两对
            case 7:
            //一对
            case 8:
                idx = comb78(rank, cards);
                break;
            default: idx = comb9(rank, cards);
        }

        return idx;
    }

    private static List<Integer> comb5(int rank, List<Card> cards) {
        List<Card> temp = new ArrayList<>();
        for (Card c : cards) {
            temp.add(c);
        }
        List<Integer> idx = new ArrayList<>();
        idx.add(rank);
        Collections.sort(temp, Card.comparatorByPoint);
        for (int end = temp.size()-1; end >= Rule.combCardNum - 1; end--) {
            int x = 1;
            int j = end - 1;
            while ((x <= Rule.combCardNum - 1) && ((temp.get(end).getPointIdx() - x) == (temp.get(j).getPointIdx()))) {
                x++;
                do {j--;}
                while ((j >= 0) && (temp.get(j).getPointIdx() == temp.get(j+1).getPointIdx()));
                if (j < 0) break;
            }
            if (x == Rule.combCardNum) {
                idx.add(temp.get(end).getPointIdx());
                idx.add(cards.indexOf(temp.get(end)));
                x = 1;
                j = end - 1;
                while ((x <= Rule.combCardNum - 1) && ((temp.get(end).getPointIdx() - x) == (temp.get(j).getPointIdx()))) {
                    idx.add(temp.get(j).getPointIdx());
                    idx.add(cards.indexOf(temp.get(j)));
                    x++;
                    do {j--;}
                    while ((j >= 0) && (temp.get(j).getPointIdx() == temp.get(j+1).getPointIdx()));
                    if (j < 0) break;
                }
                break;
            }
        }
        return idx;
    }

    private static List<Integer> comb78(int rank, List<Card> cards) {
        List<Card> temp = new ArrayList<>();
        for (Card c : cards) {
            temp.add(c);
        }
        List<Integer> idx = new ArrayList<>();
        idx.add(rank);
        Collections.sort(temp, Card.comparatorByPoint);
        for (int i = temp.size()-1; i >= 1; i--) {
            if (temp.get(i).getPointIdx() == temp.get(i - 1).getPointIdx()) {
                idx.add(temp.get(i).getPointIdx());
                idx.add(cards.indexOf(temp.get(i)));
                idx.add(temp.get(i - 1).getPointIdx());
                idx.add(cards.indexOf(temp.get(i - 1)));
            }
        }
            if (rank == 7) {
                for (int j = temp.size()-1; j >= 0; j--)
                    if ((temp.get(j).getPointIdx() != idx.get(1)) && (temp.get(j).getPointIdx() != idx.get(3)))
                    {
                        idx.add(temp.get(j).getPointIdx());
                        idx.add(cards.indexOf(temp.get(j)));
                        break;
                    }
            } else {
                int count = 0;
                for (int j = temp.size()-1; j >= 0; j--) {
                    if ((count < 3)&&((temp.get(j).getPointIdx() != idx.get(1)) && (temp.get(j).getPointIdx() != idx.get(3)))) {
                        idx.add(temp.get(j).getPointIdx());
                        idx.add(cards.indexOf(temp.get(j)));
                        count ++;
                    }
                    if (count == 3) break;
                }
            }
        return idx;

    }

    private static List<Integer> comb014(int rank, List<Card> cards) {
        List<Card> temp = new ArrayList<>();
        for (Card c : cards) {
            temp.add(c);
        }
        List<Integer> idx = new ArrayList<>();
        idx.add(rank);
        Collections.sort(temp, Card.comparatorByColor);
        for (int end = temp.size()-1; end >= Rule.combCardNum-1; end--) {
            if (CardRank.MaxSeqColor(temp, end + 2 - Rule.combCardNum, end + 1) == Rule.combCardNum){
                if ((rank == 4) ||
                        ((rank == 0) && (temp.get(end).getPoint().equals("A")))
                                ||
                        ((rank == 1) && (CardRank.SameColorHaveSeq(temp,end + 2 - Rule.combCardNum, end + 1))))
                {
                    int i = 0;
                    while (i <= Rule.combCardNum-1) {
                        idx.add(temp.get(end - i).getPointIdx());
                        idx.add(cards.indexOf(temp.get(end - i)));
                        i++;
                    }
                }
                break;
            }
        }
        return idx;
    }


    private static List<Integer> comb2(int rank, List<Card> cards) {
        List<Card> temp = new ArrayList<>();
        for (Card c : cards) {
            temp.add(c);
        }
        List<Integer> idx = new ArrayList<>();
        idx.add(rank);
        Collections.sort(temp, Card.comparatorByPoint);
        for (int end = temp.size()-1; end >= Rule.combCardNum-2; end--) {
            if ((CardRank.MaxSeqPoint(temp, end + 3 - Rule.combCardNum, end + 1) == 4)) {
                    int i = 0;
                    while (i <= 3) {
                        idx.add(temp.get(end - i).getPointIdx());
                        idx.add(cards.indexOf(temp.get(end - i)));
                        i++;
                    }
                    if (end != temp.size()-1) {
                        idx.add(temp.get(temp.size()-1).getPointIdx());
                        idx.add(cards.indexOf(temp.get(temp.size()-1)));
                    } else {
                        idx.add(temp.get(end + 1 - Rule.combCardNum).getPointIdx());
                        idx.add(cards.indexOf(temp.get(end + 1 - Rule.combCardNum)));
                    }
                    break;
                }
            }
        return idx;
    }

    private static List<Integer> comb36(int rank, List<Card> cards) {
        List<Card> temp = new ArrayList<>();
        for (Card c : cards) {
            temp.add(c);
        }
        List<Integer> idx = new ArrayList<>();
        idx.add(rank);
        Collections.sort(temp, Card.comparatorByPoint);
        for (int end = temp.size()-1; end >= Rule.combCardNum-3; end--) {
            if ((CardRank.MaxSeqPoint(temp, end + 4 - Rule.combCardNum, end + 1) == 3)) {
                if (rank == 3) {
                    int i = 0;
                    while (i <= 2) {
                        idx.add(temp.get(end - i).getPointIdx());
                        idx.add(cards.indexOf(temp.get(end - i)));
                        i++;
                    }
                    i = temp.size() - 1;
                    while (i >= 1) {
                        if ((temp.get(i).getPointIdx() != temp.get(end).getPointIdx())
                                && (temp.get(i).getPointIdx() == temp.get(i-1).getPointIdx())) {
                            idx.add(temp.get(i).getPointIdx());
                            idx.add(cards.indexOf(temp.get(i)));
                            idx.add(temp.get(i-1).getPointIdx());
                            idx.add(cards.indexOf(temp.get(i-1)));
                            break;
                        }
                        i--;
                    }
                    break;
                } else {
                    //rank == 6
                    int i = 0;
                    while (i <= 2) {
                        idx.add(temp.get(end - i).getPointIdx());
                        idx.add(cards.indexOf(temp.get(end - i)));
                        i++;
                    }
                    if (end < temp.size() - 2) {
                        idx.add(temp.get(temp.size() - 1).getPointIdx());
                        idx.add(cards.indexOf(temp.get(temp.size() - 1)));
                        idx.add(temp.get(temp.size() - 2).getPointIdx());
                        idx.add(cards.indexOf(temp.get(temp.size() - 2)));
                    } else if (end == temp.size() - 2) {
                        idx.add(temp.get(temp.size() - 1).getPointIdx());
                        idx.add(cards.indexOf(temp.get(temp.size() - 1)));
                        idx.add(temp.get(end + 2 - Rule.combCardNum).getPointIdx());
                        idx.add(cards.indexOf(temp.get(end + 2 - Rule.combCardNum)));
                    } else {
                        idx.add(temp.get(end + 2 - Rule.combCardNum).getPointIdx());
                        idx.add(cards.indexOf(temp.get(end + 2 - Rule.combCardNum)));
                        idx.add(temp.get(end + 1 - Rule.combCardNum).getPointIdx());
                        idx.add(cards.indexOf(temp.get(end + 1 - Rule.combCardNum)));
                    }
                    break;
                }
            }
        }
        return idx;
    }

    private static List<Integer> comb9(int rank, List<Card> cards) {
        List<Card> temp = new ArrayList<>();
        for (Card c : cards) {
            temp.add(c);
        }
        List<Integer> idx = new ArrayList<>();
        idx.add(rank);
        Collections.sort(temp, Card.comparatorByPoint);
        for (int i = temp.size() - 1; i >= temp.size() - Rule.combCardNum; i--) {
            idx.add(temp.get(i).getPointIdx());
            idx.add(cards.indexOf(temp.get(i)));
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
