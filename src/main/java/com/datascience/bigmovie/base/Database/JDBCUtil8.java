package com.datascience.bigmovie.base.Database;

import java.io.*;
import java.sql.*;

public class JDBCUtil8 extends JDBCUtilMaster {

    public void main() {

        String csvFilePath = "src/main/resources/database/csv/crewData.csv";

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(jdbcURL, username, password);
            //foreign key checks uitzetten
            DisableFKChecks(connection,"crew");

            connection.setAutoCommit(false);
            String sql = "INSERT INTO crew (titleId, directorsid) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText = null;

            int count = 0;

            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                //ervoor zorgen dat hij uitzondering maakt voor "," in de tekst
                String[] data = lineText.split(",", -1);
                String titleId = data[0];
                for (int i = 1; i < data.length; i++ ) {
                    if (data[i].length() < 3){
                        continue;
                    }
                    String directorsid = data[i];

                    titleId = titleId.replaceAll("\"", "");
                    directorsid = directorsid.replaceAll("\"", "");

                    statement.setString(1, titleId);
                    statement.setString(2, directorsid);

                    statement.addBatch();
                }
                statement.executeBatch();
            }
            System.out.println("Done with crew");
            lineReader.close();

            // execute the remaining queries
            statement.executeBatch();

            //en weer aanzetten..
            EnableFKChecks(connection,"crew");

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
