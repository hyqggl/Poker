package logic;

import bean.Player;

/**
 * Created by huyiqing on 16/12/22.
 */
public class Compare {

    public static int comp2p(Player p1, Player p2) {
        if ((p1.getRankCache().size() != 0) && (p2.getRankCache().size() != 0)
                && (p1.getRankCache().size() == p2.getRankCache().size())) {
            if (p1.getRankCache().get(0) == p2.getRankCache().get(0)) {
                int i = 1;
                while (i < p1.getRankCache().size()) {
                    if (p1.getRankCache().get(i) != p2.getRankCache().get(i))
                        return p1.getRankCache().get(i) - p2.getRankCache().get(i);
                    i = i + 2;
                }
                return 0;
            } else {
                return - p1.getRankCache().get(0) + p2.getRankCache().get(0);
            }
        } else
            return 99;
    }
}
