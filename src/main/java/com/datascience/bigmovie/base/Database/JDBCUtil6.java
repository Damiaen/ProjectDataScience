package com.datascience.bigmovie.base.Database;

import java.io.*;
import java.sql.*;

public class JDBCUtil6 {

    public static void main() {
        String jdbcURL = "jdbc:mysql://localhost:3306/moviedb";
        String username = "root";
        String password = "";

        String csvFilePath = "src/main/resources/database/csv/Ratings.csv";

        int batchSize = 20;
        Connection connection = null;

        try {

            connection = DriverManager.getConnection(jdbcURL, username, password);
            //foreign key checks uitzetten
            Statement stmt = connection.createStatement();
            stmt.execute("SET FOREIGN_KEY_CHECKS=0");
            stmt.close();

            connection.setAutoCommit(false);

            String sql = "INSERT INTO TitleRating (titleId, averageRating, numVotes) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText = null;

            int count = 0;

            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                //ervoor zorgen dat hij uitzondering maakt voor "," in de tekst
                String[] data = lineText.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                String titleId = data[0];
                String averageRating = data[1];
                String numVotes = data[2];

                titleId = titleId.replaceAll("\"","");
                averageRating = averageRating.replaceAll("\"","");
                numVotes = numVotes.replaceAll("\"","");

                statement.setString(1, titleId);
                if(averageRating.equals("NULL")){
                    statement.setDouble(2, 0);
                }else {
                    statement.setDouble(2, Double.parseDouble(averageRating));
                }
                if(numVotes.equals("NULL")){
                    statement.setInt(3, 0);
                }else {
                    statement.setInt(3, Integer.parseInt(numVotes));
                }

                statement.addBatch();

                if (count % batchSize == 0) {
                    statement.executeBatch();
                    System.out.println(titleId);
                }
            }

            lineReader.close();

            // execute the remaining queries
            statement.executeBatch();

            //en weer aanzetten..
            Statement stmt2 = connection.createStatement();
            stmt2.execute("SET FOREIGN_KEY_CHECKS=1");
            stmt2.close();

            connection.commit();
            connection.close();

        } catch (IOException ex) {
            System.err.println(ex);
        } catch (SQLException ex) {
            ex.printStackTrace();

            try {
                assert connection != null;
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}