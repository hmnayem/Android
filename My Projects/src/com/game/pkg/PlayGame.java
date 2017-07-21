package com.game.pkg;

import java.util.Scanner;

public class PlayGame {


    public static void main(String[] args) {

        String player1, player2, player3, player4, command;
        int totalRound;
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Name Of Player One : ");
        player1 = sc.nextLine();
        System.out.print("Enter Name Of Player Two : ");
        player2 = sc.nextLine();
        System.out.print("Enter Name Of Player Three : ");
        player3 = sc.nextLine();
        System.out.print("Enter Name Of Player Four : ");
        player4 = sc.nextLine();
        System.out.print("Enter How Many Round You Want to Play : ");
        totalRound = sc.nextInt();


        Game game = new Game(player1, player2, player3, player4, totalRound);
        sc.nextLine();

        System.out.println("\n\nEnter R or r to play and S or s to Check Score Board");
        do {
            System.out.println("Current : " + game.getCurrentRound() + "  Available : " + game.getAvailableRound());

            System.out.print("Commands >> ");
            command = sc.nextLine();

            if (command.toLowerCase().equals("r")) {
                if (game.isGameOver()) {
                    System.out.println("\nGame Over");
                    displayScore(game, player1, player2, player3, player4);
                    System.out.println("\n");
                    displayWinners(game);

                } else {
                    String lastBoard = game.play();
                    System.out.println(lastBoard);
                }
            }
            else if (command.toLowerCase().equals("s")) {
                displayScore(game, player1, player2, player3, player4);
            }
            else if (command.toLowerCase().equals("a")) {

                if (!game.isGameOver()) {
                    System.out.println("You Can't Start A new Game At This Moment");
                }
                else {
                    System.out.println("You Are Starting A New Game");
                    System.out.println("Again Enter The Required Information");

                    System.out.print("Enter Name Of Player One : ");
                    player1 = sc.nextLine();
                    System.out.print("Enter Name Of Player Two : ");
                    player2 = sc.nextLine();
                    System.out.print("Enter Name Of Player Three : ");
                    player3 = sc.nextLine();
                    System.out.print("Enter Name Of Player Four : ");
                    player4 = sc.nextLine();
                    System.out.print("Enter How Many Round You Want to Play : ");
                    totalRound = sc.nextInt();
                    sc.nextLine();
                    game.scoreBoard.scoresList.clear();
                    game = new Game(player1, player2, player3, player4, totalRound);
                }
            }
            else {

                System.out.println("Invalid Command");
            }

        } while (!command.toLowerCase().equals("x"));

    }

    private static void displayScore(Game game, String p1, String p2, String p3, String p4) {
        System.out.println(String.format("\n\n%10s%10s%10s%10s\n\n", p1, p2, p3, p4));

        System.out.println(game.scoreBoard);
    }

    private static void displayWinners(Game game) {

        String result = game.generateResult();

        if (result.equals(null)) {
            System.out.println("Game is Still Running");
            return;
        }

        System.out.println("\nGame Summary\n");
        System.out.println(result);
    }

}
