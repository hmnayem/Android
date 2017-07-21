package com.game.string.stringgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StringHelper {

    private StringHelper() {

    }

    public static String shuffleIt(String text) {

        List<Character> textList = new ArrayList<>();

        for (int i=0; i<text.length(); i++) {
            textList.add(text.charAt(i));
        }

        Collections.shuffle(textList);

        StringBuffer sb = new StringBuffer();

        for (Character ch : textList) {
            sb.append(ch);
        }

        return sb.toString();
    }
}
