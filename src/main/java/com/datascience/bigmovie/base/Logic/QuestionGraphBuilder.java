package com.datascience.bigmovie.base.Logic;

import com.datascience.bigmovie.base.models.Answer;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Damiaen Toussaint, team 4,  Project Data Science
 *
 * Class: We clean the question data based on question id, so the graphs will work
 */
public class QuestionGraphBuilder {

    /**
     * Get the question and run the query
     */
    public ArrayList<String[]> buildGraph(Answer answer) {
        try {
            return getGraph(answer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Build the correct chart for the given question, this is based on question id
     * Some of the data doesnt need to be cleaned, so we can just skip that step and return the base data
     */
    private ArrayList<String[]> getGraph(Answer answer) throws IOException {
        System.out.println("Building answer for question id: " + answer.getId());
        switch (answer.getType()) {
            case "PIE_CHART":
                return generatePieChartContent(answer);
            case "CATEGORY_CHART":
                return generateCategoryChartContent(answer);
            default:
                return null;
        }
    }

    /**
     * Currently all data is clean already, so this is more for future proofing
     */
    private ArrayList<String[]> generatePieChartContent(Answer answer) {
        return answer.getResults();
    }

    /**
     * Currently all data is clean already, so this is more for future proofing
     */
    private ArrayList<String[]> generateCategoryChartContent(Answer answer) {
        return answer.getResults();
    }


}
