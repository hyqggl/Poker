package util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huyiqing on 16/12/18.
 * 用于储存卡牌花色以及点数
 * 花色靠前的更大 （Spade > Heart > Club > Diamond)
 * 点数靠后的更大  (A > K > ... > 3 > 2)
 * 14, 13, ..., 2
 */
public class ColorAndPoint {
    //0,1,2,3
    public static String[] colors = {"Spade", "Heart", "Club", "Diamond"};
    //0,1,2,3,4,5,6,7,8,9,10,11,12
    public static String[] points = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

}
