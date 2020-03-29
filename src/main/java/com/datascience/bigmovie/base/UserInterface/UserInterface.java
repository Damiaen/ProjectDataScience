package com.datascience.bigmovie.base.UserInterface;

import com.datascience.bigmovie.base.Logic.DatabaseQuery;
import com.datascience.bigmovie.base.Models.Answer;
import com.datascience.bigmovie.base.Models.Column;
import com.datascience.bigmovie.base.Models.Question;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    private JComboBox<Question> questions_list;
    private JButton ask_question;
    private JPanel content_panel;
    private JProgressBar questionProgressBar;

    /**
     * Instance of databaseQuery class
     */
    private DatabaseQuery databaseQuery = new DatabaseQuery();

    /**
     * Instance of questions class
     */
    private List<Question> questions = new ArrayList<Question>();

    /**
     * Main JFrame IU, everything in the view is stored here
     */
    private JFrame userInterfaceFrame = new JFrame("Project DataScience - Groep 4");

    public UserInterface(){
        // Create required elements for the JFrame
        createInterfaceElements();

        // Get and set the available questions
        getQuestions();

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

        // Set Combobox content, based on questions in questions list
        DefaultComboBoxModel<Question> model = new DefaultComboBoxModel<>(questions.toArray(new Question[0]));
        questions_list.setModel(model);
    }

    /**
     * Set all the questions related stuff here
     * TODO: Clean this up?
     */
    private void getQuestions() {
        this.questions.add(new Question("1. Welke actrices en acteurs spelen in meer dan 15 films met een ranking vanaf 8 sterren?", "SELECT * FROM movies WHERE id = 1", "REGULAR", "src/main/resources/images/questionmark.jpg"));
        this.questions.add(new Question("2. Geef een top 15 lijst van films met een budget van onder de 30 miljoen met een ranking vanaf 8,5 sterren?", "SELECT * FROM movies WHERE id = 1", "REGULAR", "src/main/resources/images/questionmark.jpg"));
        this.questions.add(new Question("3. Geef een top 25 lijst van films met de meeste uitgaven? (Betekent een hoger budget per se een betere/succesvollere film?)", "SELECT * FROM movies WHERE id = 1", "REGULAR", "src/main/resources/images/questionmark.jpg"));
        this.questions.add(new Question("4. Geef een overzicht waarbij de schrijver ook de regisseur was bij het maken een film? Neem hier een rating boven de 9.", "SELECT * FROM movies WHERE id = 1", "REGULAR", "src/main/resources/images/questionmark.jpg"));
        this.questions.add(new Question("5. Welke genre wordt het meest bekeken als je alle films pakt vanaf 8,8 sterren?", "SELECT * FROM movies WHERE id = 1", "REGULAR", "src/main/resources/images/questionmark.jpg"));
        this.questions.add(new Question("6. Geef een top 15 lijst met films waar de draai tijd het langs heeft geduurd?(geeft dit uitzonderlijk verschil)", "SELECT * FROM movies WHERE id = 1", "REGULAR", "src/main/resources/images/questionmark.jpg"));
        this.questions.add(new Question("7. Geef top 15 lijst met films met een ranking vanaf 9,5sterren maar hebben het laagste productiebudget?", "SELECT * FROM movies WHERE id = 1", "REGULAR", "src/main/resources/images/questionmark.jpg"));
    }

    /**
     * Main function that contains all of the base UI settings
     */
    private void createInterfaceElements() {
        userInterfaceFrame.setContentPane(rootPanel);
        userInterfaceFrame.setSize(720,340);
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

    private Question getQuestion(Integer index) {
        return Objects.requireNonNull(questions.get(index));
    }

    /**
     * Dispose of the main UI and open the QuestionBuilder UI
     * TODO: Implement SQL Query Builder here
     * TODO: Cleanup disabling of buttons
     */
    private void runQuestionBuilder() throws IOException {
        button_parse.setEnabled(false);
        button_build.setEnabled(false);
        ask_question.setEnabled(false);
        questions_list.setEnabled(false);
        questionProgressBar.setVisible(true);
        questionProgressBar.setIndeterminate(true);

        final Answer[] questionAnswer = new Answer[1];

        new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Get question from questions list and run the query
                questionAnswer[0] = databaseQuery.askQuestion(getQuestion(questions_list.getSelectedIndex()));

                // Temporary slowdown to simulate query
                Thread.sleep(3000);
                return null;
            }

            @Override
            protected void done() {
                questionProgressBar.setVisible(false);
                questionProgressBar.setIndeterminate(false);
                button_parse.setEnabled(true);
                button_build.setEnabled(true);
                ask_question.setEnabled(true);
                questions_list.setEnabled(true);

                try {
                    new QuestionInterface(questionAnswer[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }
}
