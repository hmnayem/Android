package com.game.string.stringgame;

/**
 * Created by guru on 5/16/17.
 */

public class Questions {

    public String question;
    public String answer;

    public Questions(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    @Override
    public String toString() {
        return answer + " : " + question;
    }
}
