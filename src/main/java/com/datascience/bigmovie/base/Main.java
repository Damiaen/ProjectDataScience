package com.datascience.bigmovie.base;

import com.datascience.bigmovie.base.Logic.Parser;

import java.io.IOException;

/**
 * @author Damiaen Toussaint, team 4,  Project Data Science
 */
public class Main {

    /**
     * @param args;
     */
    public static void main(String[] args) {
        try {
            new Parser().setupParser();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
