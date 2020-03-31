package com.datascience.bigmovie.base.Logic;

import com.datascience.bigmovie.base.models.Answer;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Damiaen Toussaint, team 4,  Project Data Science
 */
public class QuestionContentBuilder {
    /**
     * Get the question and run the query
     */
    public String buildAnswer(Answer answer) {
        try {
            return buildContent(answer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Build the question content based on values in answer and question id
     * TODO: Build questions based on sql values and not hardcode them here
     */
    private String buildContent(Answer answer) throws IOException {
        System.out.println("Building answer for question id: " + answer.getId());
        ArrayList<String[]> results = answer.getResults();
        switch (answer.getId()) {
            case 1:
                return question1(results);
            case 2:
                return question2(results);
            default:
                return "Error met het uitvoeren van de query/ophalen van de data. Dit is de default debugg value";
        }
    }

    private String question1(ArrayList<String[]> results) {
        String template = "Het antwoordt op de vraag is: Persoon %s is geboren op %s en is overleden op %s. Deze persoon had de volgende rollen: %s.";
        return String.format(template, results.get(0)[1], results.get(0)[2], results.get(0)[3], results.get(0)[4]);
    }

    private String question2(ArrayList<String[]> results) {
        String template = "Het antwoordt op de vraag is: %s %s %s %s";
        return String.format(template, results.get(0)[0], results.get(0)[1], results.get(1)[0], results.get(1)[1]);
    }
}
