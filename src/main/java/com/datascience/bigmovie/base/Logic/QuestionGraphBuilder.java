package com.datascience.bigmovie.base.Logic;

import com.datascience.bigmovie.base.models.Answer;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Damiaen Toussaint, team 4,  Project Data Science
 */
public class QuestionGraphBuilder {

    /**
     * Get the question and run the query
     */
    public String buildGraph(Answer answer) {
        try {
            return checkGraphType(answer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Build the correct content for the charts so they will work, based on questions id
     */
    private String checkGraphType(Answer answer) throws IOException {
        System.out.println("Building answer for question id: " + answer.getId());
        ArrayList<String[]> results = answer.getResults();
        switch (answer.getType()) {
            case "PIE_CHART":
                return generatePieChart(answer);
            case "REGULAR":
                return generateRegular(answer);
            default:
                return "Error met het uitvoeren van de query/ophalen van de data. Dit is de default debugg value";
        }
    }

    private String generatePieChart(Answer answer) {
        return "test";
    }

    private String generateRegular(Answer answer) {
        return "jdwajdaowij";
    }
}
