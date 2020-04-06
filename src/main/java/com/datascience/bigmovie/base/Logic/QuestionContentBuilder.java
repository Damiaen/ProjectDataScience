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
     * TODO: If we ever implement an chatbot replace this
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
        stringBuilder.append("Onder de categorie 'Comedy' staan de volgende shows in de top 3: \n \n");
        for (String[] result : results) {
            if (result[0].equals("Comedy")) {
                stringBuilder.append("De show '").append(result[1]).append("' met in totaal '").append(result[2]).append("' seizoenen.\n");
            }
        }
        stringBuilder.append("\nOnder de categorie 'Drama' staan de volgende shows in de top 3: \n \n");
        for (String[] result : results) {
            if (result[0].equals("Drama")) {
                stringBuilder.append("De show '").append(result[1]).append("' met in totaal '").append(result[2]).append("' seizoenen.\n");
            }
        }
        return stringBuilder.toString();
    }

    private String question6(ArrayList<String[]> results) {
        return "Rode lijn: Projectie lineaire regressie \nHet antwoordt op de vraag is: Door naar de ratings te kijken is het duidelijk dat zelfs als een serie maar 1 seizoen heeft een hogere rating kan hebben dan series met veel seizoenen. Daarom kan de conclusie worden getrokken dat er geen verband is tussen de rating en seizoenen van een serie. \n \n";
    }

    private String question7(ArrayList<String[]> results) {
        return "Rode lijn: Projectie lineaire regressie \nHet antwoordt op de vraag is: Er is te zien dat er niet veel verschil zit tussen het aantal directors en de rating van een serie. Soms is de rating van een serie zelfs lager met meerdere directors dan de series met minder directors. Daarom kan dan ook de conclusie worden getrokken dat er geen relatie zit tussen het aantal directors en de rating van een serie. \n \n";
    }

    private String question8(ArrayList<String[]> results) {
        return "Blauwe stippen: Gegeven data \nRode Stippen: Voorspelde data \nHet antwoordt op de vraag is: Omdat de rating zo veel verschild is de voorspelling ook niet echt accuraat, er zal wel een moment zijn waarbij de voorspelling goed is maar de keren dat het verkeerd is voorspeld is vele malen meer. Daarom kan de conclusie worden getrokken dat je rating niet kan voorspellen door alleen maar te kijken naar de seizoenen. \n \n";
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
