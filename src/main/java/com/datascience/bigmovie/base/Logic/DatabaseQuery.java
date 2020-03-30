package com.datascience.bigmovie.base.Logic;

import com.datascience.bigmovie.base.Models.Answer;
import com.datascience.bigmovie.base.Models.Question;

import java.io.IOException;

/**
 * @author Damiaen Toussaint, team 4,  Project Data Science
 */
public class DatabaseQuery {

    /**
     * Get the question and run the query
     */
    public Answer askQuestion(Question question) {
        try {
            return runQuery(question);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Run the query
     * TODO: implement something to run query here
     */
    private Answer runQuery(Question question) throws IOException{
        System.out.println("Running Query on database: " + question.getQuery());
        return new Answer(question.getTitle(), "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut volutpat nulla enim, non ornare nunc faucibus nec. Suspendisse ut elit et nisi molestie efficitur. Donec sollicitudin ultricies nisl, ultricies interdum ex lacinia in.", question.getType(),  question.getImagePath());
    }
}
