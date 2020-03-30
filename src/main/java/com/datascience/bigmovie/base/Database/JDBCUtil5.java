package com.datascience.bigmovie.base.Database;

import java.io.*;
import java.sql.*;

public class JDBCUtil5 extends JDBCUtilMaster {

    public void main() {

        String csvFilePath = "src/main/resources/database/csv/Episodes.csv";

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(jdbcURL, username, password);
            //foreign key checks uitzetten
            DisableFKChecks(connection,"TitleEpisode");

            connection.setAutoCommit(false);

            String sql = "INSERT INTO TitleEpisode (id, titleId, seasonNumber, episodeNumber) VALUES (?, ?, ?, ?)";
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
                JDBCUtil.BirthYearNullCheck(statement, seasonNumber, episodeNumber);

                statement.addBatch();

                if (count % batchSize == 0) {
                    statement.executeBatch();
                }
            }
            System.out.println("Done with Episodes");
            lineReader.close();

            // execute the remaining queries
            statement.executeBatch();

            //en weer aanzetten..
            EnableFKChecks(connection,"TitleEpisode");

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