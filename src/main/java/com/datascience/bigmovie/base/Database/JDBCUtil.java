package com.datascience.bigmovie.base.Database;

import java.io.*;
import java.sql.*;

public class JDBCUtil extends JDBCUtilMaster {

    public void main() {

        String csvFilePath = "src/main/resources/database/csv/NameBasics.csv";

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(jdbcURL, username, password);
            connection.setAutoCommit(false);

            String sql = "INSERT INTO Person (id, primaryName, birthYear, deathYear, primaryProfessions) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText = null;
            int count = 0;

            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                //ervoor zorgen dat hij uitzondering maakt voor "," in de tekst
                String[] data = lineText.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                String id = data[0];
                String primaryName = data[1];
                String birthYear = data[2];
                String deathYear = data[3];
                String primaryProfessions = data[4];
                for(int i = 5; i < data.length; i++)
                {
                    int strLength = primaryProfessions.length()+data[i].length();
                    primaryProfessions = primaryProfessions + "," + data[i];
                }
                id = id.replaceAll("\"","");
                primaryName = primaryName.replaceAll("\"","");
                birthYear = birthYear.replaceAll("\"","");
                deathYear = deathYear.replaceAll("\"","");
                primaryProfessions = primaryProfessions.replaceAll("\"","");
                statement.setString(1, id);
                statement.setString(2, primaryName);
                BirthYearNullCheck(statement, birthYear, deathYear);
                statement.setString(5, primaryProfessions);

                statement.addBatch();

                if (count % batchSize == 0) {
                    statement.executeBatch();
                }
            }
            System.out.println("Done with Persons");
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

    static void BirthYearNullCheck(PreparedStatement statement, String birthYear, String deathYear) throws SQLException {
        if(birthYear.equals("NULL"))
        {
            statement.setInt(3, 0);
        }else {
            statement.setInt(3, Integer.parseInt(birthYear));
        }
        if(deathYear.equals("NULL"))
        {
            statement.setInt(4, 0);
        }else {
            statement.setInt(4, Integer.parseInt(deathYear));
        }
    }
}