package com.datascience.bigmovie.base.Models;

/**
 * @author Damiaen Toussaint, team 4,  Project Data Science
 */
public class Column {

    private Integer[] ignoreColumns;
    private Integer[] splitColumns;
    private String dataSource;
    private String newFileName;

    /**
     * @param dataSource = Data source name/location
     * @param newFileName = New filename
     * @param ignoreColumns    = Columns to ignore, based on index value
     */
    public Column(String dataSource, String newFileName, Integer[] ignoreColumns, Integer[] splitColumns) {
        this.dataSource = dataSource;
        this.newFileName = newFileName;
        this.ignoreColumns = ignoreColumns;
        this.splitColumns = splitColumns;
    }

    /**
     * @return  ignoreColumns = Columns to ignore
     */
    public Integer[] getIgnoreColumns() {
        return ignoreColumns;
    }

    /**
     * @return  dataSource = Location/name of original data source
     */
    public String getDataSource() {
        return dataSource;
    }

    /**
     * @return  newFileName = Name of new file and possible sql table name
     */
    public String getNewFileName() {
        return newFileName;
    }

    /**
     * @return  splitColumns = Which columns the application has to split for linked tables in the database
     */
    public Integer[] getSplitColumns() { return splitColumns; }
}
