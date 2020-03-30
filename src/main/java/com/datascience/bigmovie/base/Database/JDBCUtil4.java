package com.datascience.bigmovie.base.Database;

import java.io.*;
import java.sql.*;

public class JDBCUtil4 extends JDBCUtilMaster {

    public void main() {

        String csvFilePath = "src/main/resources/database/csv/Principals.csv";

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(jdbcURL, username, password);
            //foreign key checks uitzetten
            DisableFKChecks(connection,"Principals");

            connection.setAutoCommit(false);

            String sql = "INSERT INTO Principals (personId, titleId, ordening, category) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText = null;

            int count = 0;

            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                //ervoor zorgen dat hij uitzondering maakt voor "," in de tekst
                String[] data = lineText.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                String personId = data[2];
                String ordening = data[1];
                String titleId = data[0];
                String category = data[3];

                personId = personId.replaceAll("\"","");
                ordening = ordening.replaceAll("\"","");
                titleId = titleId.replaceAll("\"","");
                category = category.replaceAll("\"","");

                statement.setString(1, personId);
                statement.setInt(3, Integer.parseInt(ordening));
                statement.setString(2, titleId);
                statement.setString(4, category);

                statement.addBatch();

                if (count % batchSize == 0) {
                    statement.executeBatch();
                }
            }
            System.out.println("Done with Principals");
            lineReader.close();

            // execute the remaining queries
            statement.executeBatch();

            //en weer aanzetten..
            EnableFKChecks(connection,"Principals");

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