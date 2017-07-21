package com.game.pkg;

import java.util.LinkedList;

public class ScoreBoard {

    private static ScoreBoard board;
    public LinkedList<Scores> scoresList;

    private ScoreBoard() {

        scoresList = new LinkedList<>();
    }


    public static ScoreBoard getInstance() {
        if (board == null) {
            board = new ScoreBoard();
        }

        return board;
    }

    public String lastBoard() {

        Scores sc = scoresList.peek();
        String sr = String.format("%10s%10s%10s%10s\n", checkStatus(sc.player1), checkStatus(sc.player2),
                checkStatus(sc.player3), checkStatus(sc.player4));

        return sr;

    }

    private String checkStatus(Integer value) {
        if (value.equals(1600)) {
            return "ACE";
        }

        if (value.equals(1000)) {
            return "KING";
        }

        if (value.equals(600)) {
            return "QUEEN";
        }
        if (value.equals(400)) {
            return "JOKER";
        }

        return null;
    }

    @Override
    public String toString() {

        StringBuffer sb = new StringBuffer();

        for (int i=0; i<scoresList.size(); i++) {
            sb.append(String.format("%10s", checkStatus(scoresList.get(i).player1)));
            sb.append(String.format("%10s", checkStatus(scoresList.get(i).player2)));
            sb.append(String.format("%10s", checkStatus(scoresList.get(i).player3)));
            sb.append(String.format("%10s", checkStatus(scoresList.get(i).player4)));
            sb.append("\n");
        }

        return sb.toString();
    }
}
