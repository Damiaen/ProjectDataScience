package com.datascience.bigmovie.base.UserInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * @author Damiaen Toussaint, team 4,  Project Data Science
 */
public class UserInterface extends JFrame{
    private JPanel rootPanel;
    private JButton button_parse;
    private JButton button_build;
    private JPanel buttons_panel;
    private JPanel questions_panel;
    private JComboBox<String> questions_list;
    private JButton ask_question;
    private JPanel content_panel;
    private JTextPane answer_content;

    public String[] questionSelect = {
        "1. Welke actrices en acteurs spelen in meer dan 15 films met een ranking vanaf 8 sterren?",
        "2. Geef een top 15 lijst van films met een budget van onder de 30 miljoen met een ranking vanaf 8,5 sterren?",
        "3. Geef een top 25 lijst van films met de meeste uitgaven? (Betekent een hoger budget per se een betere/succesvollere film?)",
        "4. Geef een overzicht waarbij de schrijver ook de regisseur was bij het maken een film? Neem hier een rating boven de 9.",
        "5. Welke genre wordt het meest bekeken als je alle films pakt vanaf 8,8 sterren?",
        "6. Geef een top 15 lijst met films waar de draai tijd het langs heeft geduurd?(geeft dit uitzonderlijk verschil)",
        "7. Geef top 15 lijst met films met een ranking vanaf 9,5sterren maar hebben het laagste productiebudget?",
    };

    /**
     * Main JFrame IU, everything in the view is stored here
     */
    private JFrame userInterfaceFrame = new JFrame("Project DataScience - Groep 4");

    public UserInterface(){
        // Create required elements for the JFrame
        createInterfaceElements();

        // Event listeners that respond to button clicks
        button_parse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) { runParser(); }
        });
        button_build.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) { runDatabaseBuilder(); }
        });
        ask_question.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                runQuestionBuilder();
            }
        });

        // Set Combobox content
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(questionSelect);
        questions_list.setModel(model);
    }

    /**
     * Main function that contains all of the base UI settings
     */
    private void createInterfaceElements() {
        userInterfaceFrame.setContentPane(rootPanel);
        userInterfaceFrame.setSize(720,480);
        userInterfaceFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userInterfaceFrame.setVisible(true);
    }

    /**
     * Dispose of the main UI and open the Parser UI
     */
    private void runParser() {
        userInterfaceFrame.setVisible(false);
        new ParserInterface();
        userInterfaceFrame.dispose();
    }

    /**
     * Dispose of the main UI and open the DatabaseBuilder UI
     */
    private void runDatabaseBuilder() {
        userInterfaceFrame.setVisible(false);
        new DatabaseInterface();
        userInterfaceFrame.dispose();
    }

    /**
     * Run the question Builder function and ask an question to the database
     */
    private void runQuestionBuilder() {
        String comboBoxValue = Objects.requireNonNull(questions_list.getSelectedItem()).toString();
        int comboBoxSelectedIndex = questions_list.getSelectedIndex();

        // Log question index and value, also change text field to comboBox value
        System.out.println("selected question index:" + comboBoxSelectedIndex + ". Question: " + comboBoxValue);
        answer_content.setText(comboBoxValue);
    }
}
