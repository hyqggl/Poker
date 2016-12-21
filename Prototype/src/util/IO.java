package util;

import bean.Player;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by huyiqing on 16/12/20.
 */
public class IO {

    //输入抽牌数
    public static int enterPullNum(){
        int x;
        while (true){
            try{
                Scanner in = new Scanner(System.in);
                x = in.nextInt();
                if (x < 1 || x > 26) System.out.println("From 1 to 26. Please enter again.");
                else return x;
            }   catch(InputMismatchException e){
                System.out.println("Oops.Please enter a integer.");
            }
            catch(Exception e){
                System.out.println("Oops.Unexpected error.");
            }
        }
    }

    //输入玩家姓名与ID
    public static Player EnterAndCreatePlayer() {
        Player player;
        while (true){
            try{
                Scanner in = new Scanner(System.in);
                System.out.println("Fisrt bean.Player:");
                System.out.print("Enter ID:");
                int newId = in.nextInt();
                System.out.print("Enter name:");
                String newName = in.next();
                System.out.print("Enter bank roll:");
                int initChips = in.nextInt();
                player = new bean.Player(newId, newName, initChips);
                System.out.println();
                System.out.println("Welcome, " + newName);
                System.out.println();
                return player;
            }   catch(InputMismatchException e){
                System.out.println("Oops.Please enter a integer.");
            }
            catch(Exception e){
                System.out.println("Oops.Unexpected error.");
            }
        }
    }
}
