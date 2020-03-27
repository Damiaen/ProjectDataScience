package com.datascience.bigmovie.base.DBBuild;

import java.io.*;
import java.sql.*;

public class JDBCUtil4 {

    public static void main() {
        String jdbcURL = "jdbc:mysql://localhost:3306/moviedb";
        String username = "root";
        String password = "";

        String csvFilePath = "src/main/resources/database/csv/Principals.csv";

        int batchSize = 20;
        Connection connection = null;

        try {

            connection = DriverManager.getConnection(jdbcURL, username, password);
            //foreign key checks uitzetten
            Statement stmt = connection.createStatement();
            stmt.execute("SET FOREIGN_KEY_CHECKS=0");
            stmt.close();

            connection.setAutoCommit(false);

            String sql = "INSERT INTO Principals (personId, ordening, titleId, category, job, characters) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText = null;

            int count = 0;

            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                //ervoor zorgen dat hij uitzondering maakt voor "," in de tekst
                String[] data = lineText.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                String personId = data[0];
                String ordening = data[1];
                String titleId = data[2];
                String category = data[3];
                String job = data[4];
                String characters = data[5];

                personId = personId.replaceAll("\"","");
                ordening = ordening.replaceAll("\"","");
                titleId = titleId.replaceAll("\"","");
                category = category.replaceAll("\"","");
                job = job.replaceAll("\"","");
                characters = characters.replaceAll("\"","");

                statement.setString(1, personId);
                statement.setInt(2, Integer.parseInt(ordening));
                statement.setString(3, titleId);
                statement.setString(4, category);
                statement.setString(5, job);
                statement.setString(6, characters);



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