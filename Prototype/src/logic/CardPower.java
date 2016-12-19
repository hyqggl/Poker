package logic;

import bean.Card;
import util.PokerSetColorAndPoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by huyiqing on 16/12/19.
 */
public class CardPower {

    private static List<Card> cards;

    private static String[] PName =
            {"RoyalFlush", "StraightFlush", "FourOfAKind", "Fullhouse", "Flush",
             "Straight", "ThreeOfAKind", "TwoPairs", "OnePair", "HighCard"};

    //得到牌力级别，点数
    public static void getPowerRank(List<Card> ref, List<Card> playerCards) {
        cards = new ArrayList<>();
        for (Card c : ref) { cards.add(c); }
        for (Card c : playerCards) { cards.add(c); }
        Collections.sort(cards, Card.comparator);

        isFlush(cards);
    }

    /**
     *
     * @param cards
     * @return 0: No
     *         1: Flush
     *         2: Straight Flush
     *         3: Royal Flush
     */
    private static int isFlush(List<Card> cards) {
        for (int startIdx = cards.size()-1; startIdx >= cards.size()-3; startIdx--) {
            int j = 1;
            while ((j <= 4) && (cards.get(startIdx).getColor().equals(cards.get(startIdx-j).getColor()))) {j++;}
            //是同花
            if (j == 5) {
                int x = 1;
                int temp = startIdx;
                while ((x <= 4) &&
                        (PokerSetColorAndPoint.points.indexOf((cards.get(temp).getPoint()))
                        == PokerSetColorAndPoint.points.indexOf((cards.get(temp - 1).getPoint())) + 1)){
                    x++;
                    temp--;
                }

                //是同花顺
                if (x == 5) {
                    if (cards.get(startIdx).getPoint().equals("A")) return 3;
                    else return 2;
                } else
                    return 1;
            }
        }
        return 0;
    }
}
