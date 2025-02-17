package com.datascience.bigmovie.base.Database;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Mathijs Grafhorst, team 4,  Project Data Science
 */

class JDBCUtilMaster
{
    //enter database connection settings here
    String jdbcURL = "jdbc:postgresql://localhost:5432/movieDB";
    String username = "postgres";
    String password = "";

    void DisableFKChecks(Connection connection,String table) throws SQLException {
        //foreign key checks uitzetten
        Statement stmt = connection.createStatement();
        stmt.execute("ALTER TABLE "+table+" DISABLE TRIGGER ALL;");
        stmt.close();
    }

    void EnableFKChecks(Connection connection,String table) throws SQLException {
        //en weer aanzetten..
        Statement stmt = connection.createStatement();
        stmt.execute("ALTER TABLE "+table+" ENABLE TRIGGER ALL;");
        stmt.close();
    }
}
