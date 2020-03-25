package com.datascience.bigmovie.base;

import com.datascience.bigmovie.base.UserInterface.UserInterface;

import java.io.*;

/**
 * @author Damiaen Toussaint, team 4,  Project Data Science
 */
public class Main {

    /**
     * @param args;
     */
    public static void main(String[] args) {
        try {
            new UserInterface().main();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
