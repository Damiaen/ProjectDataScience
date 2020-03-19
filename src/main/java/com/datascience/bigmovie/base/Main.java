package com.datascience.bigmovie.base;

import java.io.*;

/**
 * @author Team ??, Project Data Science
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
