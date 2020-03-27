package com.datascience.bigmovie.base;

import com.datascience.bigmovie.base.UserInterface.UserInterface;

import javax.swing.*;
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
            // Force Windows style for the Swing application
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            // Start the main UI
            new UserInterface();
        } catch (IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
