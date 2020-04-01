package com.datascience.bigmovie.base.Logic;

import com.datascience.bigmovie.base.models.Answer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
        stringBuilder.append("Als we de database vragen om alle genres krijgen we een hele lijst terug. Ook bestaan er combinatie genres, zoals 'Genre Comedy,Music,Talk-Show'. Als we kijken naar de top 10 van populaire genres krijgen we de volgende genres terug: \n \n");
        for (String[] result : results) {
            stringBuilder.append("De genre '").append(result[0]).append("' komt ").append(result[1]).append(" keer voor.\n");
        }
        return stringBuilder.toString();
    }

    private String question2(ArrayList<String[]> results) {
        stringBuilder.append("Als wij kijken naar de top 10 series die het meeste verschil in regiseurs hebben gezien, komen de volgende series naar boven: \n \n");
        for (String[] result : results) {
            stringBuilder.append("De serie '").append(result[0]).append("' heeft in totaal ").append(result[1]).append(" regiseurs gehad.\n");
        }
        return stringBuilder.toString();
    }

    private String question3(ArrayList<String[]> results) {
        stringBuilder.append("We zoeken specifiek naar regisseurs die alleen in tv-series een rol hebben gehad als regisseur. Als we dan zoeken naar één regisseur komt de volgende persoon naar boven: \n \n");
        for (String[] result : results) {
            stringBuilder.append("De regiseur '").append(result[0]).append("' heeft totaal in ").append(result[1]).append(" series een rol als regiseur gehad. \n");
        }
        return stringBuilder.toString();
    }

    private String question4(ArrayList<String[]> results) {
        stringBuilder.append("Als we kijken naar de top 5 genres waar de meeste acteurs/actrices krijgen we een totaal van aantal acteurs/actrices van '178.600'. In de chart hier links kun je per genre zien wat zijn aandeel is in deze top 5. Als we deze opsplitsen per losse genre komt er het volgende naar boven: \n\n");
        for (String[] result : results) {
            stringBuilder.append("Genre '").append(result[0]).append("' heeft totaal ").append(result[1]).append(" acteurs/actrices gehad. \n");
        }
        return stringBuilder.toString();
    }

    private String question5(ArrayList<String[]> results) {
        stringBuilder.append("We hebben eerst gekeken naar series met minimaal 20 seizoenen, daarna welke er dan de meeste actors hadden. Hier liepen we wel tegen een probleem met de data aan, want er staan max 10 records in de database bijbehorende aan een serie. Dus de onderstaande info is gebaseerd op de data die wij van imdb hebben verkregen. \n \n");
        for (String[] result : results) {
            stringBuilder.append(result[0]).append(", heeft totaal: ").append(result[1]).append(" actors/actresses. Vanaf season 20 heeft deze serie ").append(result[2]).append(" afleveringen").append(".\n");
        }
        return stringBuilder.toString();
    }

    private String question6(ArrayList<String[]> results) {
        String template = "Het antwoordt op de vraag is: %s %s";
        return String.format(template, results.get(0)[0], results.get(0)[1]);
    }

    private String question7(ArrayList<String[]> results) {
        stringBuilder.append("Als wij kijken naar de top 10 series met de meeste seizoenen en hun gemiddelde rating, komen de volgende series + rating naar boven: \n \n");
        for (String[] result : results) {
            stringBuilder.append(result[0]).append(", episodes: ").append(result[1]).append(". Met een rating van:").append(result[2]).append(".\n");
        }
        return stringBuilder.toString();
    }

    private String question8(ArrayList<String[]> results) {
        stringBuilder.append("Als wij kijken naar de top 10 series met de meeste seizoenen en hun gemiddelde rating, komen de volgende series + rating naar boven: \n \n");
        for (String[] result : results) {
            stringBuilder.append(result[0]).append(", episodes: ").append(result[1]).append(". Met een rating van:").append(result[2]).append(".\n");
        }
        return stringBuilder.toString();
    }

    private String question9(ArrayList<String[]> results) {
        stringBuilder.append("Als we ophalen welke 3 acteurs in de meest verschillende series hebben gespeeld komt er het volgende naar boven: \n \n");
        for (String[] result : results) {
            stringBuilder.append("Acteur '").append(result[0]).append("' heeft in totaal in ").append(result[1]).append(" verschillende series gespeeld").append(".\n");
        }
        return stringBuilder.toString();
    }

    private String question10(ArrayList<String[]> results) {
        stringBuilder.append("Als we kijken naar de verhouding man/vrouw onder 1975 en 1980 en dan naar 2015 en 2020 komt het volgende naar boven:: \n \n");
        stringBuilder.append("In de jaren 1975 tot 1980 speelden er '").append(results.get(0)[0]).append("' acteurs en '").append(results.get(1)[0]).append("' actrices in films en series").append(".\n");
        stringBuilder.append("In de jaren 2015 tot 2020 speelden er '").append(results.get(2)[0]).append("' acteurs en '").append(results.get(3)[0]).append("' actrices in films en series").append(".\n\n");
        stringBuilder.append("Als we dit even verekenen naar percentages komen we tot de volgende data: \n\n").append("Als we kijken naar de jaren 1975-1980 zien we dat van alle rollen bij elkaar 62.8% man is en 37.2% vrouw is. \n").append("Als we kijken naar de jaren 2015-2020 zien we dat van alle rollen bij elkaar 58.8% man is en 41.2% vrouw is.").append(".\n\n");
        stringBuilder.append("Als we de twee verschillende tijdvakken met elkaar vergelijken kunnen we zien dat er over de jaren heen een verschil van 4% is ontstaan.");
        return stringBuilder.toString();
    }

}
