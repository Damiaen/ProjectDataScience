package com.datascience.bigmovie.base.Database;

import java.io.*;
import java.sql.*;

public class JDBCUtil2 extends JDBCUtilMaster {

    public void main() {

        String csvFilePath = "src/main/resources/database/csv/TitleAKAS.csv";

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(jdbcURL, username, password);
            //foreign key checks uitzetten
            DisableFKChecks(connection,"TitleAKAS");

            connection.setAutoCommit(false);

            String sql = "INSERT INTO TitleAKAS (titleId, ordening, title, region, language, types, attributes, isOriginalTitle) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText;

            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                //ervoor zorgen dat hij uitzondering maakt voor "," in de tekst
                String[] data = lineText.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                String id = data[0];
                String ordening = data[1];
                String title = data[2];
                String region = data[3];
                String language = data[4];
                String types = data[5];
                String attributes = data[6];
                String isOriginalTitle = data[7];


                id = id.replaceAll("\"","");
                ordening = ordening.replaceAll("\"","");
                title = title.replaceAll("\"","");
                region = region.replaceAll("\"","");
                language = language.replaceAll("\"","");
                types = types.replaceAll("\"","");
                attributes = attributes.replaceAll("\"","");
                isOriginalTitle = isOriginalTitle.replaceAll("\"","");

                statement.setString(1, id);
                statement.setInt(2, Integer.parseInt(ordening));
                statement.setString(3, title);
                statement.setString(4, region);
                statement.setString(5, language);
                statement.setString(6, types);
                statement.setString(7, attributes);
                //wanneer original titel null is = originele titel niet bekend? dus 2 = niet bekend
                if(isOriginalTitle.equals("NULL")){
                    isOriginalTitle = "2";
                }
                statement.setInt(8, Integer.parseInt(isOriginalTitle));

                statement.addBatch();

                statement.executeBatch();
            }

            System.out.println("Done with TitleAKAS");
            lineReader.close();
            // execute the remaining queries
            statement.executeBatch();
            //en weer aanzetten..
            EnableFKChecks(connection,"TitleAKAS");
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