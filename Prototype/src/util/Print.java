package util;

import bean.Card;
import logic.CardRank;

import java.util.List;

/**
 * Created by huyiqing on 16/12/21.
 */
public class Print {

    public static void printRank(int rank) {
        System.out.println(rank + " " + CardRank.PName[rank]);
    }

    public static void printIntArray(List<Integer> inta) {
        System.out.print(" [ ");
        for (int i = 2; i < inta.size(); i = i + 2)
            System.out.print(inta.get(i) + " ");
        System.out.println("] ");
    }

    public static void printComb(List<Integer> idx, List<Card> ref, List<Card> pCard) {

        int refN = ref.size();

        try {
            for (int i = 2; i < idx.size(); i = i + 2) {
                if (idx.get(i) >= refN) System.out.print(pCard.get(idx.get(i) - refN).toString() + " ");
                else System.out.print(ref.get(idx.get(i)).toString() + " ");
            }
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
