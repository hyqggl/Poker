package util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huyiqing on 16/12/18.
 * 用于储存卡牌花色以及点数
 * 花色靠前的更大 （Spade > Heart > Club > Diamond)
 * 点数靠后的更大  (A > K > ... > 3 > 2)
 */
public class PokerSetColorAndPoint {

    public static List<String> colors = new ArrayList<String>();
    public static List<String> points = new ArrayList<String>();

    static {
        colors.add("Spade");
        colors.add("Heart");
        colors.add("Club");
        colors.add("Diamond");
        for (int i = 2; i <= 10; i++) {
            points.add(String.valueOf(i));
        }
        points.add("J");
        points.add("Q");
        points.add("K");
        points.add("A");
    }

}
