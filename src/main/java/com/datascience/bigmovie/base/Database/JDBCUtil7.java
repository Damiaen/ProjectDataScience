package com.datascience.bigmovie.base.Database;

import java.io.*;
import java.sql.*;
/**
 * @author Mathijs Grafhorst, team 4,  Project Data Science
 */
public class JDBCUtil7 extends JDBCUtilSettings {

    public void main() {

        String csvFilePath = "src/main/resources/database/csv/TitlesKnowFor.csv";

        int batchSize = 20;
        Connection connection = null;

        try {

            connection = DriverManager.getConnection(jdbcURL, username, password);
            connection.setAutoCommit(false);

            //foreign key checks uitzetten
            DisableFKChecks(connection,"knowforTitles");

            String sql = "INSERT INTO knowfortitles (personid, titlebasicsid) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText = null;
            int count = 0;

            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                //ervoor zorgen dat hij uitzondering maakt voor "," in de tekst
                String[] data = lineText.split(",");
                String personid = data[0];
                for (int i = 1; i < data.length; i++ ) {
                    String titlebasicsid = data[i];

                    personid = personid.replaceAll("\"", "");
                    titlebasicsid = titlebasicsid.replaceAll("\"", "");

                    statement.setString(1, personid);
                    statement.setString(2, titlebasicsid);

                    statement.addBatch();


                    statement.executeBatch();
                    //System.out.println(personid+" and "+titlebasicsid);
                }
            }
            System.out.println("Done with KnowFor");

            lineReader.close();

            // execute the remaining queries
            statement.executeBatch();

            EnableFKChecks(connection,"knowfortitles");

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