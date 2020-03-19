package com.datascience.bigmovie.base;

/**
 * @author Team ??, Project Data Science
 */
public class NewColumn {

    Integer[] ignoreColumns;
    String originalFileName;

    /**
     * @param originalFileName = Original/New filename
     * @param ignoreColumns    = Columns to ignore, based on index value
     *                         <p>
     *                         TODO: Make it so it can base the ignored values on column name instead of index
     */
    public NewColumn(String originalFileName, Integer[] ignoreColumns) {
        this.originalFileName = originalFileName;
        this.ignoreColumns = ignoreColumns;
    }
}
