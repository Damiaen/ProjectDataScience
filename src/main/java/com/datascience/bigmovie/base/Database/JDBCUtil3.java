package com.datascience.bigmovie.base.Database;

import java.io.*;
import java.sql.*;

public class JDBCUtil3 extends JDBCUtilMaster {

    public void main() {

        String csvFilePath = "src/main/resources/database/csv/TitleBasics.csv";

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(jdbcURL, username, password);
            connection.setAutoCommit(false);

            String sql = "INSERT INTO TitleBasics (id, titleType, primaryTitle, originalTitle, isAdult, startYear, endYear, runtimeMinutes, genres) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText;

            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                //ervoor zorgen dat hij uitzondering maakt voor "," in de tekst
                String[] data = lineText.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                String id = data[0];
                String titleType = data[1];
                String primaryTitle = data[2];
                String originalTitle = data[3];
                String isAdult = data[4];
                String startYear = data[5];
                String endYear = data[6];
                String runtimeMinutes = data[7];
                String genres = data[8];

                id = id.replaceAll("\"","");
                titleType = titleType.replaceAll("\"","");
                primaryTitle = primaryTitle.replaceAll("\"","");
                originalTitle = originalTitle.replaceAll("\"","");
                isAdult = isAdult.replaceAll("\"","");
                startYear = startYear.replaceAll("\"","");
                endYear = endYear.replaceAll("\"","");
                runtimeMinutes = runtimeMinutes.replaceAll("\"","");
                genres = genres.replaceAll("\"","");

                statement.setString(1, id);
                statement.setString(2, titleType);
                statement.setString(3, primaryTitle);
                statement.setString(4, originalTitle);
                statement.setInt(5, Integer.parseInt(isAdult));
                if(startYear.equals("NULL")){
                    statement.setInt(6, 0);
                }else {
                    statement.setInt(6, Integer.parseInt(startYear));
                }
                if(endYear.equals("NULL")){
                    statement.setInt(7, 0);
                }else {
                    statement.setInt(7, Integer.parseInt(endYear));
                }
                if(runtimeMinutes.equals("NULL")){
                    statement.setInt(8, 0);
                }else {
                    statement.setInt(8, Integer.parseInt(runtimeMinutes));
                }
                statement.setString(9, genres);

                statement.addBatch();

                statement.executeBatch();
            }
            System.out.println("Done with TitleBasics");

            lineReader.close();

            // execute the remaining queries
            statement.executeBatch();

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