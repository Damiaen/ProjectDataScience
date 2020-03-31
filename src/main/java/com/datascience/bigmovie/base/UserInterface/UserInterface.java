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
     * TODO: QUERY: 6,10
     */
    private void getQuestions() {
        this.questions.add(new Question(1, "1. Welke top 10 genres, op basis van aantal keren voorgekomen, behoren tot series die meer dan 5 seizoenen hebben uitgebracht?", "De uitkomst is gebaseerd op een max van 10 genres met een minimaal van 5 seizoenen.", "SELECT DISTINCT titlebasics.genres as genres,count(genres) as genres_count FROM titlebasics INNER JOIN titleepisode ON titleepisode.titleid=titlebasics.id where titleepisode.seasonnumber > '5' AND genres != 'NULL' group by genres order by genres_count DESC LIMIT 10;", "PIE_CHART"));
        this.questions.add(new Question(2, "2. Welke serie heeft over de loop van al zijn seizoenen het meeste verschil in regisseurs gehad?", "In de X - waarde van de vinden we de series terug, met als Y - waarde het aantal regiseurs dat deze serie heeft gehad", "SELECT DISTINCT titlebasics.originaltitle, count (DISTINCT person.id) AS count FROM titlebasics INNER JOIN crew ON crew.titleid = titlebasics.id INNER JOIN person ON crew.directorsid = person.id WHERE titlebasics.titletype LIKE 'tvSeries' GROUP BY titlebasics.id  ORDER BY count DESC LIMIT 10;", "CATEGORY_CHART"));
        this.questions.add(new Question(3, "3. Welke regisseur heeft in zijn carrière de meest verschillende series geregisseerd?", "Zit hier een verband dat makers dit duo een goede combi vinden, misschien steeds zelfde rol.", "SELECT DISTINCT titlebasics.genres as genres,count(genres) as genres_count FROM titlebasics INNER JOIN titleepisode ON titleepisode.titleid=titlebasics.id where titleepisode.seasonnumber > '5' AND genres != 'NULL' group by genres order by genres_count DESC LIMIT 10;", "REGULAR"));
        this.questions.add(new Question(4, "4. Hoeveel acteurs/actrices hebben er gespeeld in de top 5 genres?", "Test description here", "SELECT DISTINCT t.genres, count( principals.category) as actorActress_count  FROM principals  inner join titlebasics t on principals.titleid = t.id  WHERE (principals.category = 'actor' OR principals.category = 'actress')  AND t.titletype = 'tvSeries' AND t.genres != 'NULL' GROUP BY t.genres ORDER BY actorActress_count DESC LIMIT 5;", "PIE_CHART"));
        this.questions.add(new Question(5, "5. Welke serie met minimaal 3 seizoenen heeft de meeste acteurs/actrices?", "Test description here", "SELECT DISTINCT titlebasics.originaltitle, COUNT(DISTINCT principals.id) AS actors_count, COUNT(DISTINCT titleepisode.id) AS episodes FROM titlebasics INNER JOIN principals on principals.titleid = titlebasics.id INNER JOIN titleepisode on titleepisode.titleid = titlebasics.id WHERE (principals.category = 'actor' OR principals.category = 'actress') AND titlebasics.titletype = 'tvSeries' AND titleepisode.seasonnumber > '20' group by titlebasics.id order by episodes DESC LIMIT 10; ", "REGULAR"));
        this.questions.add(new Question(6, "6. Is er een verband tussen het aantal seizoenen en de rating die is gegeven voor een serie?", "Maak dit in R.", "SELECT * FROM person WHERE person.id = 'nm0000001'", "R"));
        this.questions.add(new Question(7, "7. Is er een relatie tussen de series die meerdere regisseurs hebben gehad en de gemiddelde rating van een aflevering?", "(ging het gemiddelde naar beneden toen er een andere regisseur kwam.) Maak dit in R.", "SELECT TitleBasics.primaryTitle as title, max(TitleEpisode.seasonNumber) as seasons, AVG(TitleRating.averageRating) as rating, avg(titlerating.numvotes) as votes FROM TitleBasics, TitleEpisode, TitleRating Where TitleEpisode.titleID = TitleBasics.ID AND titleepisode.titleid = TitleRating.titleID and titlerating.numvotes >= '125000' group by title ORDER BY title desc limit 10;", "R"));
        this.questions.add(new Question(8, "8. Is er een verband tussen de hoeveelheid seizoenen die een serie heeft en de gemiddelde rating?", "Hebben deze seizoenen dan ook een hogere rating? Maak dit met R.", "select TitleBasics.primaryTitle as title, count(TitleEpisode.seasonNumber) as seasons, AVG(TitleRating.averageRating) as rating, avg(titlerating.numvotes) FROM TitleBasics, TitleEpisode, TitleRating WHERE TitleBasics.ID = TitleEpisode.titleID AND TitleBasics.ID = TitleRating.titleID and titlerating.numvotes >= '125000' group by title ORDER BY seasons DESC limit 10;", "R"));
        this.questions.add(new Question(9, "9. Welke acteur heeft het vaakst in meerdere series gespeeld betreft verschillende genres?", "(ging het gemiddelde naar beneden toen er een andere regisseur kwam.) Maak dit in R.", "SELECT distinct p.primaryname, count(DISTINCT titleid) as count FROM principals  INNER JOIN titlebasics ON principals.titleid = titlebasics.id INNER JOIN person p ON principals.personid = p.id WHERE titleType = 'tvSeries' AND (category = 'actress' OR category = 'actor') GROUP BY personid,p.primaryname ORDER BY count DESC  LIMIT 3;", "CATEGORY_CHART"));
        this.questions.add(new Question(10, "10. Wat is de gemiddelde man/vrouw verhouding van de actrices/acteurs van series uit 1975-1980 en 2015-2020?", "Mogelijk interessant om te zien of er vroeger meer spelers waren van het zelfde geslacht in een serie vergelijken met nu", "SELECT * FROM person WHERE person.id = 'nm0000001'", "REGULAR"));
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
