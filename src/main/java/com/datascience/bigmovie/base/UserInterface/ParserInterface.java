package com.datascience.bigmovie.base.UserInterface;

import com.datascience.bigmovie.base.Logic.Parser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ParserInterface{
    private JPanel rootPanel;
    private JPanel content_panel;
    private JPanel buttons_panel;
    private JButton button_parse;
    private JButton button_cancel;
    private JProgressBar progressBar;

    private JFrame parserInterfaceFrame = new JFrame("Project DataScience - Groep 4 - Parser");

    private Parser parser = new Parser();

    public ParserInterface(){
        // Create required elements for the JFrame
        createInterfaceElements();

        // Event listeners that respond to button clicks
        button_parse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                runParser();
            }
        });
        button_cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                exitPage();
            }
        });
    }

    /**
     * Main function that contains all of the base UI settings
     */
    private void createInterfaceElements() {
        parserInterfaceFrame.setContentPane(rootPanel);
        parserInterfaceFrame.setSize(720,240);
        parserInterfaceFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        parserInterfaceFrame.setVisible(true);
    }

    /**
     * Return to the main UI view
     */
    private void exitPage() {
        parserInterfaceFrame.setVisible(false);
        new UserInterface();
        parserInterfaceFrame.dispose();
    }

    /**
     * Run the tsv to csv parser, this disables the buttons until its done
     */
    private void runParser() {
        button_parse.setEnabled(false);
        button_cancel.setEnabled(false);
        progressBar.setIndeterminate(true);

        new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                parser.setupParser();
                return null;
            }

            @Override
            protected void done() {
                progressBar.setIndeterminate(false);
                button_parse.setEnabled(true);
                button_cancel.setEnabled(true);
            }
        }.execute();
    }
}
