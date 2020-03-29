package com.datascience.bigmovie.base.UserInterface;

import com.datascience.bigmovie.base.Models.Question;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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
    private JProgressBar questionProgressBar;

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
                try {
                    runQuestionBuilder();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
     * Dispose of the main UI and open the QuestionBuilder UI
     * TODO: Implement SQL Query Builder here
     */
    private void runQuestionBuilder() throws IOException {
        button_parse.setEnabled(false);
        button_build.setEnabled(false);
        ask_question.setEnabled(false);
        questionProgressBar.setVisible(true);
        questionProgressBar.setIndeterminate(true);

        new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Temporary slowdown to simulate query
                Thread.sleep(2000);
                return null;
            }

            @Override
            protected void done() {
                questionProgressBar.setVisible(false);
                questionProgressBar.setIndeterminate(false);
                button_parse.setEnabled(true);
                button_build.setEnabled(true);
                ask_question.setEnabled(true);

                try {
                    // Get data from combobox and set question, this is temporary for testing only
                    String comboBoxValue = Objects.requireNonNull(questions_list.getSelectedItem()).toString();
                    Question question = new Question(questions_list.getSelectedIndex(), comboBoxValue.toString(), "123", "src/main/resources/images/questionmark.jpg");

                    new QuestionInterface(question);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }
}
