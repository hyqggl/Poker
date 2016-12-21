package util;

import bean.Card;

import java.util.List;

/**
 * Created by huyiqing on 16/12/21.
 */
public class Print {

    public static void printIntArray(int[] inta) {
        System.out.print(" [ ");
        for (int i : inta) {
            System.out.print(i + " ");
        }
        System.out.println("] ");
    }

    public static void printComb(int[] idx, List<Card> ref, List<Card> pCard) {

        int refN = ref.size();
        int pN = pCard.size();

        try {
            for (int i : idx) {
                if (i >= refN) System.out.print(pCard.get(i - refN).toString() + " ");
                else System.out.print(ref.get(i).toString() + " ");
            }
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
