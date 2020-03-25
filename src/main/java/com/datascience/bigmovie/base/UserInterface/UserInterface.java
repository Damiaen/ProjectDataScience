package com.datascience.bigmovie.base.UserInterface;

import com.datascience.bigmovie.base.Logic.Parser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

public class UserInterface {
    private JPanel rootPanel;
    private JButton button_parse;
    private JButton button_build;
    private JPanel buttons_panel;
    private JPanel questions_panel;
    private JComboBox<String> questions_list;
    private JButton ask_question;
    private JPanel content_panel;
    private JTextPane answer_content;
    private JProgressBar progressBar;

    public String[] questionSelect = {
        "1. Welke actrices en acteurs spelen in meer dan 15 films met een ranking vanaf 8 sterren?",
        "2. Geef een top 15 lijst van films met een budget van onder de 30 miljoen met een ranking vanaf 8,5 sterren?",
        "3. Geef een top 25 lijst van films met de meeste uitgaven? (Betekent een hoger budget per se een betere/succesvollere film?)",
        "4. Geef een overzicht waarbij de schrijver ook de regisseur was bij het maken een film? Neem hier een rating boven de 9.",
        "5. Welke genre wordt het meest bekeken als je alle films pakt vanaf 8,8 sterren?",
        "6. Geef een top 15 lijst met films waar de draai tijd het langs heeft geduurd?(geeft dit uitzonderlijk verschil)",
        "7. Geef top 15 lijst met films met een ranking vanaf 9,5sterren maar hebben het laagste productiebudget?",
    };

    public UserInterface() {
        button_parse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                runParser();
            }
        });
        button_build.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                runDatabaseBuilder();
            }
        });
        ask_question.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                runQuestionBuilder();
            }
        });

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(questionSelect);
        questions_list.setModel(model);
    }

    public void main() throws IOException{
        try {
            // Force windows style upon the graphical interface
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            createUI();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    private void createUI() {
        JFrame frame = new JFrame("Project DataScience - Groep 4");
        frame.setContentPane(new UserInterface().rootPanel);
        frame.setSize(720,480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void runParser() {
        // Hide elements
        questions_panel.setVisible(false);
        answer_content.setVisible(false);
        buttons_panel.setVisible(false);
        // Show loader
        progressBar.setVisible(true);
        progressBar.setIndeterminate(true);

        new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                new Parser().setupParser();
                return null;
            }

            @Override
            protected void done() {
                // Turn of loader
                progressBar.setIndeterminate(false);
                progressBar.setVisible(false);
                // Show elements
                questions_panel.setVisible(true);
                answer_content.setVisible(true);
            }
        }.execute();
    }

    private void runDatabaseBuilder() {
        System.out.println("Starting up Database builder");
    }

    private void runQuestionBuilder() {
        String comboBoxValue = Objects.requireNonNull(questions_list.getSelectedItem()).toString();
        int comboBoxSelectedIndex = questions_list.getSelectedIndex();

        // Log question index and value, also change text field to comboBox value
        System.out.println("selected question index:" + comboBoxSelectedIndex + ". Question: " + comboBoxValue);
        answer_content.setText(comboBoxValue);
    }
}
