package com.datascience.bigmovie.base.models;

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
     *
     * TODO: Make it so it can base the ignored values on column name instead of index
     */
    public Column(String dataSource, String newFileName, Integer[] ignoreColumns, Integer[] splitColumns) {
        this.dataSource = dataSource;
        this.newFileName = newFileName;
        this.ignoreColumns = ignoreColumns;
        this.splitColumns = splitColumns;
    }

    public Integer[] getIgnoreColumns() {
        return ignoreColumns;
    }

    public String getDataSource() {
        return dataSource;
    }

    public String getNewFileName() {
        return newFileName;
    }

    public Integer[] getSplitColumns() { return splitColumns; }
}
