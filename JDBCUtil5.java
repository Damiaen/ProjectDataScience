package com.datascience.bigmovie.base;

import java.io.*;
import java.sql.*;

public class JDBCUtil5 {

    public static void main(String[] args) {
        String jdbcURL = "jdbc:mysql://localhost:3306/moviedb";
        String username = "root";
        String password = "";

        String csvFilePath = "src/main/resources/database/csv/Episodes.csv";

        int batchSize = 20;
        Connection connection = null;

        try {

            connection = DriverManager.getConnection(jdbcURL, username, password);
            //foreign key checks uitzetten
            Statement stmt = connection.createStatement();
            stmt.execute("SET FOREIGN_KEY_CHECKS=0");
            stmt.close();

            connection.setAutoCommit(false);

            String sql = "INSERT INTO titleepisode (id, titleId, seasonNumber, episodeNumber) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText = null;

            int count = 0;

            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                //ervoor zorgen dat hij uitzondering maakt voor "," in de tekst
                String[] data = lineText.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                String id = data[0];
                String titleId = data[1];
                String seasonNumber = data[2];
                String episodeNumber = data[3];

                id = id.replaceAll("\"", "");
                titleId = titleId.replaceAll("\"","");
                seasonNumber = seasonNumber.replaceAll("\"","");
                episodeNumber = episodeNumber.replaceAll("\"","");

                statement.setString(1, id);
                statement.setString(2, titleId);
                if(seasonNumber.equals("NULL")){
                    statement.setInt(3, 0);
                }else {
                    statement.setInt(3, Integer.parseInt(seasonNumber));
                }
                if(episodeNumber.equals("NULL")){
                    statement.setInt(4, 0);
                }else {
                    statement.setInt(4, Integer.parseInt(episodeNumber));
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
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}