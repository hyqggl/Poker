package logic;

import bean.Card;
import util.Rule;

import java.util.*;

/**
 * Created by huyiqing on 16/12/19.
 *
 */
public class CardRank {

    private static List<Card> cards;

    public static String[] PName =
            {"RoyalFlush", "StraightFlush", "FourOfAKind", "Fullhouse", "Flush",
                    "Straight", "ThreeOfAKind", "TwoPairs", "OnePair", "HighCard"};

    /**
     *
     * @param ref
     * @param playerCards
     * @return 返回牌型
     */
    public static int getRank(List<Card> ref, List<Card> playerCards) {


        cards = new ArrayList<>();
        for (Card c : ref) {
            cards.add(c);
        }
        for (Card c : playerCards) {
            cards.add(c);
        }
        Collections.sort(cards, Card.comparatorByColor);

        int rank = 9;
        //是否是[0]皇家同花顺、[1]同花顺、[4]同花
        rank = isFlush(cards) < rank ? isFlush(cards) : rank;
        if (rank <= 1) return rank;

        Collections.sort(cards, Card.comparatorByPoint);

        //是否为[2]四条、[3]葫芦、[6]三条
        rank = fourOrThree(cards) < rank ? fourOrThree(cards) : rank;
        if (rank <= 4) return rank;

        //是否为[5]顺子
        rank = isStraight(cards) < rank ? isStraight(cards) : rank;
        if (rank <= 6) return rank;

        //是否为[7]两对、[8]一对
        rank = TwoOrOnePair(cards) < rank ? TwoOrOnePair(cards) : rank;
        return rank;
    }

    /**
     * 是否为顺子
     * @param cards
     * @return  9: NO
     *          7: TwoPairs
     *          8: OnePair
     */
    private static int TwoOrOnePair(List<Card> cards) {
        int count = 0;
        for (int i = cards.size()-1; i >= 1; i--) {
            if (cards.get(i).getPointIdx() == cards.get(i-1).getPointIdx()) {
                count++;
            }
        }
        if (count == 1) return 8;
        if (count > 1) return 7;
        return 9;
    }

    /**
     * 是否为顺子
     * @param cards
     * @return  9: NO
     *          5: Straight
     */
    private static int isStraight(List<Card> cards) {

        for (int end = cards.size()-1; end >= Rule.combCardNum - 1; end--) {
            int x = 1;
            int j = end - 1;
            while ((x <= Rule.combCardNum - 1) && ((cards.get(end).getPointIdx() - x) == (cards.get(j).getPointIdx()))) {
                x++;
                do {j--;}
                while ((j > 0) && (cards.get(j).getPointIdx() == cards.get(j+1).getPointIdx()));
                if (j < 0) break;
            }
            if (x == Rule.combCardNum) return 5;
        }
        return 9;
    }

    /**
     * @param cards
     * @return 9: NO
     * 2: FourOfAKind
     * 3: Fullhouse
     * 6: ThreeOfAKind
     */
    private static int fourOrThree(List<Card> cards) {
        //是否为四条
        for (int i = cards.size(); i >= 4; i--) {
            if (isSamePoint(cards, i - 3, i)) {
                return 2;
            }
        }

        //葫芦或三条
        for (int i = cards.size(); i >= 3; i--) {
            if (isSamePoint(cards, i - 2, i)) {
                //前段是否有对子
                if ((i >= 5) && (MaxSeqPoint(cards, 1, i-3) >= 2)){
                    return 3;
                }
                //后段是否有对子
                if ((i <= cards.size() - 2) && (MaxSeqPoint(cards, i+1, cards.size()) >= 2)) {
                    return 3;
                }
                return 6;
            }
        }

        return 9;
    }


    /**
     * @param cards
     * @return 9: No
     * 4: Flush
     * 1: Straight Flush
     * 0: Royal Flush
     */
    private static int isFlush(List<Card> cards) {
        for (int i = cards.size(); i >= 5; i--) {
            //有同花
            if (isSameColor(cards, i - 4, i)) {
                //同花顺
                if (SameColorHaveSeq(cards, i-4, i)) {
                    //同花大顺
                    if (cards.get(i-1).getPoint().equals("A")) {
                        return 0;
                    } else {
                        return 1;
                    }
                } else
                    return 4;
            }
        }
        return 9;
    }

    /**
     * @param start 第几张牌开始
     * @param end   第几张牌结束
     * @return 最大连续花色数
     */
    public static int MaxSeqColor(List<Card> cards, int start, int end) {
        int max = 1;
        int i = 1;
        for (int idx = start; idx < end; idx++) {
            if (cards.get(idx).getColorIdx()
                    == cards.get(idx - 1).getColorIdx()) {
                i++;
            } else {
                max = max < i ? i : max;
                i = 1;
            }
            if (idx == end - 1) {
                max = max < i ? i : max;
            }
        }
        return max;
    }

    public static int MaxSeqPoint(List<Card> cards, int start, int end) {
        int max = 1;
        int i = 1;
        for (int idx = start; idx < end; idx++) {
            if (cards.get(idx).getPointIdx()
                    == cards.get(idx - 1).getPointIdx()) {
                i++;
            } else {
                max = max < i ? i : max;
                i = 1;
            }
            if (idx == end - 1) {
                max = max < i ? i : max;
            }
        }
        return max;
    }

    /**
     * @param start 第几张牌开始
     * @param end   第几张牌结束
     * @return 是否有顺子（5张连续点数）
     */
    public static boolean SameColorHaveSeq(List<Card> cards, int start, int end) {
        if ((end - start + 1) < 5) return false;
        for (int i = start - 1; i < end - 4; i++) {
            int x = 1;
            int temp = i;
            while ((x <= 4) && (cards.get(temp).getPointIdx()
                    == cards.get(temp + 1).getPointIdx() - 1)) {
                x++;
                temp++;
            }

            //是同花顺
            if (x == 5) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSameColor(List<Card> cards, int start, int end) {
        for (int i = start - 1; i < end - 1; i++) {
            if (cards.get(i).getColorIdx() != cards.get(i+1).getColorIdx())
                return false;
        }
        return true;
    }

    public static boolean isSamePoint(List<Card> cards, int start, int end) {
        for (int i = start - 1; i < end - 1; i++) {
            if (cards.get(i).getPointIdx() != cards.get(i+1).getPointIdx())
                return false;
        }
        return true;
    }
}