package com.game.string.stringgame;

import java.util.Arrays;
import java.util.LinkedList;

public class GameQuestions {

    private String[] words;

    public GameQuestions(String [] words) {
        this.words = words;
    }

    public LinkedList<Questions> getList() {

        LinkedList<Questions> userQuestions = new LinkedList<>();

        String question;
        String answer;
        boolean isAlreadyExist;

        int[] arr = new int[10];
        Arrays.fill(arr, -1);

        int i=0, k=0;

        while (i<10) {
            isAlreadyExist = false;

            int randomIndex = (int) (Math.random() * words.length);

            for (int j=0; j<arr.length; j++) {
                if (arr[i] == randomIndex) {
                    isAlreadyExist = true;
                    break;
                }
            }

            if (!isAlreadyExist) {
                answer = words[randomIndex];
                question = StringHelper.shuffleIt(answer);

                userQuestions.add(new Questions(question, answer));

                arr[k++] = randomIndex;
            }

            i++;
        }

        return userQuestions;
    }
}
