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
        switch (answer.getId()) {
            case 1:
            case 2:
                return answer.getResults();
            case 3:
                return generateCategoryChartQuestion3(answer);
            default:
                return null;
        }
    }

    /**
     * Answer 3 data is already clean, return it all
     */
    private ArrayList<String[]> generateCategoryChartQuestion3(Answer answer) {
        return answer.getResults();
    }

}
