package com.datascience.bigmovie.base.Logic;

import com.datascience.bigmovie.base.Models.Answer;
import com.datascience.bigmovie.base.Models.Question;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

/**
 * @author Damiaen Toussaint, team 4,  Project Data Science
 */
public class DatabaseQuery {

    private final String jdbcURL = "jdbc:postgresql://localhost:5432/movieDB";
    private final String username = "postgres";
    private final String password = "";

    /**
     * Get the question and run the query
     */
    public Answer askQuestion(Question question) {
        try {
            return runQuery(question);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Run the query
     * We check if its an R question, since R questions dont have Database queries in them
     */
    private Answer runQuery(Question question) throws IOException, SQLException {
        ArrayList<String[]> results = new ArrayList<>();

        if (!checkIfR(question)) {
            for (String query : question.getQuery()) {
                System.out.println("Running Query on database: " + query);
                Connection connection = DriverManager.getConnection(jdbcURL, username, password);
                connection.setAutoCommit(false);
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(query);

                int columnCount = rs.getMetaData().getColumnCount();
                while (rs.next()) {
                    String[] row = new String[columnCount];
                    for (int i = 0; i < columnCount; i++) {
                        row[i] = rs.getString(i + 1);
                        System.out.println(row[i]);
                    }
                    results.add(row);
                }
            }
            return new Answer(question.getId(), question.getTitle(), question.getDescription(), question.getType(), results);
        }
        return new Answer(question.getId(), question.getTitle(), question.getDescription(), question.getType(), question.getImageName(), question.getrCodePath());
    }

    /**
     * Check if its an R question
     */
    private Boolean checkIfR(Question question) {
        return question.getType().equals("R");
    }
}
