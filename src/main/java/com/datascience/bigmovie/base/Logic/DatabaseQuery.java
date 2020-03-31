package com.datascience.bigmovie.base.Logic;

import com.datascience.bigmovie.base.models.Answer;
import com.datascience.bigmovie.base.models.Question;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

/**
 * @author Damiaen Toussaint, team 4,  Project Data Science
 */
public class DatabaseQuery {

    private String jdbcURL = "jdbc:postgresql://localhost:5432/postgres";
    private String username = "postgres";
    private String password = "1234";

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
     */
    private Answer runQuery(Question question) throws IOException, SQLException {
        System.out.println("Running Query on database: " + question.getQuery());
        Connection connection = DriverManager.getConnection(jdbcURL, username, password);
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(question.getQuery());

        // Temp array list to store query results
        ArrayList<String[]> results = new ArrayList<>();

        int columnCount = rs.getMetaData().getColumnCount();
        while (rs.next()) {
            String[] row = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                row[i] = rs.getString(i + 1);
                System.out.println(row[i]);
            }
            results.add(row);
        }
        return new Answer(question.getId(), question.getTitle(), question.getDescription(), question.getType(), results);
    }
}
