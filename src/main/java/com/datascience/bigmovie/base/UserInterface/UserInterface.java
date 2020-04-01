package com.datascience.bigmovie.base.UserInterface;

import com.datascience.bigmovie.base.Logic.DatabaseQuery;
import com.datascience.bigmovie.base.models.Answer;
import com.datascience.bigmovie.base.models.Question;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
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
     * TODO: 5 reworken
     */
    private void getQuestions() {
        this.questions.add(new Question(1, "1. Welke top 10 genres, op basis van aantal keren voorgekomen, behoren tot series die meer dan 5 seizoenen hebben uitgebracht?", "De uitkomst is gebaseerd op een max van 10 genres met een minimaal van 5 seizoenen.", new String[]{"SELECT DISTINCT titlebasics.genres as genres,count(genres) as genres_count FROM titlebasics INNER JOIN titleepisode ON titleepisode.titleid=titlebasics.id where titleepisode.seasonnumber > '5' AND genres != 'NULL' group by genres order by genres_count DESC LIMIT 10;"}, "PIE_CHART"));
        this.questions.add(new Question(2, "2. Welke serie heeft over de loop van al zijn seizoenen het meeste verschil in regisseurs gehad?", "In de X - waarde van de vinden we de series terug, met als Y - waarde het aantal regiseurs dat deze serie heeft gehad.", new String[]{"SELECT DISTINCT titlebasics.originaltitle, count (DISTINCT person.id) AS count FROM titlebasics INNER JOIN crew ON crew.titleid = titlebasics.id INNER JOIN person ON crew.directorsid = person.id WHERE titlebasics.titletype LIKE 'tvSeries' GROUP BY titlebasics.id  ORDER BY count DESC LIMIT 10;"}, "CATEGORY_CHART"));
        this.questions.add(new Question(3, "3. Welke regisseur heeft in zijn carrière de meest verschillende series geregisseerd?", "We zoeken hier 1 regiseur die de meeste series heeft geregisseerd.", new String[]{"SELECT person.primaryname, count(titleid) AS count FROM crew INNER JOIN titlebasics t ON crew.titleid = t.id INNER JOIN person ON person.id = directorsid WHERE t.titletype = 'tvSeries' AND directorsid != 'NULL' GROUP BY person.primaryname ORDER BY count DESC LIMIT 1;"}, "REGULAR"));
        this.questions.add(new Question(4, "4. Hoeveel acteurs/actrices hebben er gespeeld in de top 5 genres?", "De uitkomst is gebaseerd op de top 5 meest voorkomende genres in de database.", new String[]{"SELECT DISTINCT t.genres, count( principals.category) as actorActress_count  FROM principals  inner join titlebasics t on principals.titleid = t.id  WHERE (principals.category = 'actor' OR principals.category = 'actress')  AND t.titletype = 'tvSeries' AND t.genres != 'NULL' GROUP BY t.genres ORDER BY actorActress_count DESC LIMIT 5;"}, "PIE_CHART"));
        this.questions.add(new Question(5, "5. Welke serie met minimaal 3 seizoenen heeft de meeste acteurs/actrices?", "Test description here", new String[]{"SELECT DISTINCT titlebasics.originaltitle, COUNT(DISTINCT principals.id) AS actors_count, COUNT(DISTINCT titleepisode.id) AS episodes FROM titlebasics INNER JOIN principals on principals.titleid = titlebasics.id INNER JOIN titleepisode on titleepisode.titleid = titlebasics.id WHERE (principals.category = 'actor' OR principals.category = 'actress') AND titlebasics.titletype = 'tvSeries' AND titleepisode.seasonnumber > '20' group by titlebasics.id order by episodes DESC LIMIT 10;"}, "REGULAR"));
        this.questions.add(new Question(6, "6. Is er een verband tussen het aantal seizoenen en de rating die is gegeven voor een serie?", "(Druk op de knop 'Open R scripts' om de gebruikte R code te zien) \n\nAls er meer seizoenen zijn gaat het blijkbaar goed met de serie, maar is dan ook gelijk de rating hoger of niet? Maak dit in R.", "R", "vraag_6.png", "vraag_6.R"));
        this.questions.add(new Question(7, "7. Is er een relatie tussen de series die meerdere regisseurs hebben gehad en de gemiddelde rating van een aflevering?", "(Druk op de knop 'Open R scripts' om de gebruikte R code te zien) \n\nGaat de rating naar beneden of juist omhoog als je meerdere regiseurs hebt? Maak dit in R.", "R", "vraag_7.png", "vraag_7.R"));
        this.questions.add(new Question(8, "8. Kun je de rating van een serie voorspellen op basis van het aantal seizoenen.", "(Druk op de knop 'Open R scripts' om de gebruikte R code te zien) \n\nHebben deze seizoenen dan ook een hogere rating? Maak dit met R.", "R", "vraag_8.png", "vraag_8.R"));
        this.questions.add(new Question(9, " 9. Welke acteurs hebben in de meeste verschillende series gespeeld? Geef de top 3", "We zoeken hier specifiek onder de categorie 'tv-series'.", new String[]{"SELECT distinct p.primaryname, count(DISTINCT titleid) as count FROM principals  INNER JOIN titlebasics ON principals.titleid = titlebasics.id INNER JOIN person p ON principals.personid = p.id WHERE titleType = 'tvSeries' AND (category = 'actress' OR category = 'actor') GROUP BY personid,p.primaryname ORDER BY count DESC  LIMIT 3;"}, "CATEGORY_CHART"));
        this.questions.add(new Question(10, "10. Wat is de gemiddelde man/vrouw verhouding van de actrices/acteurs van series uit 1975-1980 en 2015-2020?", "Mogelijk interessant om te zien of er door de jaren heen een verschil is ontstaan tussen de hoeveelheid acteurs en actrices als we kijken naar alle series van die tijd.", new String[]{
                "SELECT count(personid) as total, principals.category FROM principals INNER JOIN titlebasics ON principals.titleid = titlebasics.id WHERE category = 'actor' AND titleType = 'tvSeries' AND startYear BETWEEN 1975 AND 1980 GROUP BY principals.category;",
                "SELECT count(personid) as total, principals.category FROM principals INNER JOIN titlebasics ON principals.titleid = titlebasics.id WHERE category = 'actress' AND titleType = 'tvSeries' AND startYear BETWEEN 1975 AND 1980 GROUP BY principals.category;",
                "SELECT count(personid) as total, principals.category FROM principals INNER JOIN titlebasics ON principals.titleid = titlebasics.id WHERE category = 'actor' AND titleType = 'tvSeries' AND startYear BETWEEN 2015 AND 2020 GROUP BY principals.category;",
                "SELECT count(personid) as total, principals.category FROM principals INNER JOIN titlebasics ON principals.titleid = titlebasics.id WHERE category = 'actress' AND titleType = 'tvSeries' AND startYear BETWEEN 2015 AND 2020 GROUP BY principals.category;"
        }, "REGULAR"));
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

    /**
     * Get question by index
     */
    private Question getQuestion(Integer index) {
        return Objects.requireNonNull(questions.get(index));
    }

    /**
     * Toggle the enabled value of the buttons on and off
     */
    private void toggleButtons(Boolean toggle) {
        button_parse.setEnabled(toggle);
        button_build.setEnabled(toggle);
        ask_question.setEnabled(toggle);
        questions_list.setEnabled(toggle);
    }

    /**
     * Toggle the loader enabled value of the buttons on and off
     */
    private void toggleLoader(Boolean toggle) {
        questionProgressBar.setVisible(toggle);
        questionProgressBar.setIndeterminate(toggle);
    }

    /**
     * Dispose of the main UI and open the QuestionBuilder UI
     */
    private void runQuestionBuilder() throws IOException {
        // Turn buttons off and start loader
        toggleButtons(false);
        toggleLoader(true);

        final Answer[] questionAnswer = new Answer[1];

        new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Get question from questions list and run the query, since we are in an inner class we do this
                questionAnswer[0] = databaseQuery.askQuestion(getQuestion(questions_list.getSelectedIndex()));
                return null;
            }

            @Override
            protected void done() {
                // Re-enable buttons off and turn the loader off
                toggleButtons(true);
                toggleLoader(false);

                try {
                    new QuestionInterface(questionAnswer[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }
}
