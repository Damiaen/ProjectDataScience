package com.datascience.bigmovie.base.Logic;

import com.datascience.bigmovie.base.models.Answer;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Damiaen Toussaint, team 4,  Project Data Science
 */
public class QuestionContentBuilder {

    private StringBuilder stringBuilder = new StringBuilder();

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
     * Since there are differences in the structure of the data we decided to generate the content based on the specific question
     * TODO: clean this up and make it more modular
     */
    private String buildContent(Answer answer) throws IOException {
        ArrayList<String[]> results = answer.getResults();
        switch (answer.getId()) {
            case 1:
                return question1(results);
            case 2:
                return question2(results);
            case 3:
                return question3(results);
            case 4:
                return question4(results);
            case 5:
                return question5(results);
            case 6:
                return question6(results);
            case 7:
                return question7(results);
            case 8:
                return question8(results);
            case 9:
                return question9(results);
            case 10:
                return question10(results);
            default:
                return "Error met het uitvoeren van de query/ophalen van de data. Klopt het id van de vraag?";
        }
    }

    private String question1(ArrayList<String[]> results) {
        stringBuilder.append("Uit de vraag komt het volgende naar boven: Als we kijken naar de top 10 van populaire genres krijgen we de volgende genres op volgorde terug: \n \n");
        for (String[] result : results) {
            stringBuilder.append("Genre ").append(result[0]).append(", met een aantal van: ").append(result[1]).append("\n");
        }
        return stringBuilder.toString();
    }

    private String question2(ArrayList<String[]> results) {
        stringBuilder.append("Als wij kijken naar de top 10 series die het meeste verschil in regiseurs hebben gezien komt, komen de volgende series naar boven: \n \n");
        for (String[] result : results) {
            stringBuilder.append(result[0]).append(", heeft totaal: ").append(result[1]).append(" regiseurs gehad. \n");
        }
        return stringBuilder.toString();
    }

    private String question3(ArrayList<String[]> results) {
        stringBuilder.append("Als wij kijken naar de top 10 series die het meeste verschil in regiseurs hebben gezien komt, komen de volgende series naar boven: \n \n");
        for (String[] result : results) {
            stringBuilder.append(result[0]).append(", heeft totaal: ").append(result[1]).append(" regiseurs gehad. \n");
        }
        return stringBuilder.toString();
    }

    private String question4(ArrayList<String[]> results) {
        String template = "Het antwoordt op de vraag is: %s %s";
        return String.format(template, results.get(0)[0], results.get(0)[1]);
    }

    private String question5(ArrayList<String[]> results) {
        String template = "Het antwoordt op de vraag is: %s %s";
        return String.format(template, results.get(0)[0], results.get(0)[1]);
    }

    private String question6(ArrayList<String[]> results) {
        String template = "Het antwoordt op de vraag is: %s %s";
        return String.format(template, results.get(0)[0], results.get(0)[1]);
    }

    private String question7(ArrayList<String[]> results) {
        String template = "Het antwoordt op de vraag is: %s %s";
        return String.format(template, results.get(0)[0], results.get(0)[1]);
    }

    private String question8(ArrayList<String[]> results) {
        String template = "Het antwoordt op de vraag is: %s %s";
        return String.format(template, results.get(0)[0], results.get(0)[1]);
    }

    private String question9(ArrayList<String[]> results) {
        String template = "Het antwoordt op de vraag is: %s %s";
        return String.format(template, results.get(0)[0], results.get(0)[1]);
    }

    private String question10(ArrayList<String[]> results) {
        String template = "Het antwoordt op de vraag is: %s %s";
        return String.format(template, results.get(0)[0], results.get(0)[1]);
    }

}
