package com.game.pkg;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String name;
    protected List<Integer> scores;

    public Player(String name, int size) {
        this.name = name;
        scores = new ArrayList<>(size);
    }

    public String getName() {
        return name;
    }

    public Integer getTotalScore() {
        return scores.stream().reduce(0, Integer::sum);
    }

}
