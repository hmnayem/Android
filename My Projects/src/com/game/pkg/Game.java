package com.game.pkg;

import java.util.*;

public class Game {

    private final Integer DAROGA = 1600;
    private final Integer POLICE = 1000;
    private final Integer DAKAT = 600;
    private final Integer CHOR = 400;
    public ScoreBoard scoreBoard;

    private Player player1, player2, player3, player4;
    private List<Integer> values;
    private int totalRound;
    private int countRound;


    public Game(String name1, String name2, String name3, String name4, int size) {

        player1 = new Player(name1, size);
        player2 = new Player(name2, size);
        player3 = new Player(name3, size);
        player4 = new Player(name4, size);

        scoreBoard = ScoreBoard.getInstance();

        totalRound = size;
        countRound = 0;

        values = new ArrayList<>();
        values.add(DAROGA);
        values.add(POLICE);
        values.add(DAKAT);
        values.add(CHOR);
    }

    public String play() {

        if (isGameOver()) {

            return scoreBoard.toString();
        }

        Collections.shuffle(values);

        Integer playerOneGet = values.get(0);
        Integer playerTwoGet = values.get(1);
        Integer playerThreeGet = values.get(2);
        Integer playerFourGet = values.get(3);

        player1.scores.add(playerOneGet);
        player2.scores.add(playerTwoGet);
        player3.scores.add(playerThreeGet);
        player4.scores.add(playerFourGet);

        scoreBoard.scoresList.push(new Scores(playerOneGet, playerTwoGet, playerThreeGet, playerFourGet));

        StringBuffer sb = new StringBuffer();
        sb.append(String.format("%10s%10s%10s%10s\n", player1.getName(),
                player2.getName(), player3.getName(), player4.getName()));

        sb.append(scoreBoard.lastBoard());

        countRound++;


        return sb.toString();
    }

    public boolean isGameOver() {
        return countRound == totalRound;
    }

    public int getCurrentRound() {
        return countRound;
    }

    public int getAvailableRound() {
        return totalRound - countRound;
    }

    public String generateResult() {

        StringBuffer sb = new StringBuffer();
        List<Player> list = new LinkedList<>();
        list.add(player1);
        list.add(player2);
        list.add(player3);
        list.add(player4);

        list.sort(new ScoreComparator());

        if (isGameOver()) {
            sb.append(list.get(0).getName()).append(" Earned : ").append(list.get(0).getTotalScore()).append(" Rank : ACE \n");
            sb.append(list.get(1).getName()).append(" Earned : ").append(list.get(1).getTotalScore()).append(" Rank : KING \n");
            sb.append(list.get(2).getName()).append(" Earned : ").append(list.get(2).getTotalScore()).append(" Rank : QUEEN \n");
            sb.append(list.get(3).getName()).append(" Earned : ").append(list.get(3).getTotalScore()).append(" Rank : JOKER \n");

            return sb.toString();
        }

        return null;
    }


}
