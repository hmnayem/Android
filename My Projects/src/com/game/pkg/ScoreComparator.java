package com.game.pkg;

import java.util.Comparator;

public class ScoreComparator implements Comparator {

    @Override
    public int compare(Object o, Object t1) {

        Player p1 = (Player) o;
        Player p2 = (Player) t1;

        if (p1.getTotalScore() == p2.getTotalScore()) {
            return 0;
        } else if (p1.getTotalScore() < p2.getTotalScore()) {
            return 1;
        }
        return -1;
    }
}
