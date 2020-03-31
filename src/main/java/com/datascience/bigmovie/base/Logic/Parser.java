package com.datascience.bigmovie.base.Logic;

import com.datascience.bigmovie.base.models.Column;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author Damiaen Toussaint, team 4,  Project Data Science
 */
public class Parser {

    private List<Column> columnsList = new ArrayList<>();

    /**
     * Prepare the parser by setting the raw data location and ignored columns
     * Place the raw data files in the following folder: resources/database/raw
     * Extract the zip files in separate folders.
     * Example of correct filepath: ProjectDataScience/src/main/resources/database/raw/title.ratings.tsv/data.tsv
     */
    public void setupParser() throws IOException {

        // Define the new csv files here, all data files, requires dataSource, newFileName, IgnoredColumns and SplitColumns
        // IgnoredColumns and SplitColumns are based on an integer array, given integers will be used as indexes.
        // If no value is given for any of the integer arrays it will parse all the data.

        // All names and info
        this.columnsList.add(new Column("name.basics.tsv/data", "NameBasics", new Integer[]{5}, new Integer[]{}));
        // Movie title also know as
        this.columnsList.add(new Column("title.akas.tsv/data", "TitleAKAS", new Integer[]{}, new Integer[]{}));
        // Movie title basics
        this.columnsList.add(new Column("title.basics.tsv/data", "TitleBasics", new Integer[]{}, new Integer[]{}));
        // Amount of seasons and episodes
        this.columnsList.add(new Column("title.episode.tsv/data", "Episodes", new Integer[]{}, new Integer[]{}));
        // Movie roles and data
        this.columnsList.add(new Column("title.principals.tsv/data", "Principals", new Integer[]{4,5}, new Integer[]{}));
        // Ratings for the movie/show
        this.columnsList.add(new Column("title.ratings.tsv/data", "Ratings", new Integer[]{}, new Integer[]{}));
        //know for movies data
        this.columnsList.add(new Column("name.basics.tsv/data", "TitlesKnowFor", new Integer[]{1,2,3,4}, new Integer[]{}));

        try {
            convertTSVToCSVFile(this.columnsList);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Since the raw data files are tsv, we only need to replace certain characters to convert them to csv files.
     *
     * @param columns = raw data path
     * TODO: Meer logica toevoegen aan deze functie als de database designs en sql setup klaar is.
     */
    private static void convertTSVToCSVFile(List<Column> columns) throws IOException {

        StringTokenizer tokenizer;
        String lastRowData = null;

        for (Column column : columns) {
            // Get raw data file from from resources folder and set new file path
            String originalFile = "src/main/resources/database/raw/" + column.getDataSource() + ".tsv";
            String newFilePath = "src/main/resources/database/csv/" + column.getNewFileName() + ".csv";
            System.out.println("Starting converting file '" + column.getDataSource() + ".tsv' to '" + column.getNewFileName() + ".csv'");

            try (BufferedReader br = new BufferedReader(new FileReader(originalFile));
                 PrintWriter writer = new PrintWriter(new FileWriter(newFilePath))) {

                // For loop through each line that exists in de tsv file
                for (String line; (line = br.readLine()) != null; ) {
                    StringBuilder newLine = new StringBuilder();
                    tokenizer = new StringTokenizer(line, "\t");
                    String token;

                    // Multiple new ArrayLists used for temporary storing data, we will clear them afterwards
                    ArrayList<String> newColumnArray = new ArrayList<>();
                    List<String> splitColumnsArray = new ArrayList<>();

                    // Check for special characters and replace them
                    while (tokenizer.hasMoreTokens()) {
                        token = tokenizer.nextToken().replaceAll("\"", "'");
                        if (token.contains("\\N")) token = "NULL";
                        newColumnArray.add("\"" + token + "\",");
                    }

                    // Done with converting all data, check if it ends with an period (",") and remove it
                    String lastRow = newColumnArray.get(newColumnArray.size() - 1);
                    if (lastRow.endsWith(",")) {
                        lastRow = lastRow.substring(0, lastRow.length() - 1); // Remove "," from string
                        newColumnArray.set(newColumnArray.size() - 1, lastRow); // set at the last index value
                    }

                    // For each string in the new column append it and check if it has to be removed or not by referencing the ignored column list.
                    for (int i = 0; i < newColumnArray.size(); i++) {
                        // Check if it has to ignore this column, if we need to ignore it skip it.
                        if (!checkIfContains(column.getIgnoreColumns(), i)) {
                            // Check if we need to split the string into an array of strings or not.
                            if (checkIfContains(column.getSplitColumns(), i)) {
                                splitColumnsArray.addAll(Arrays.asList(newColumnArray.get(i).split("\\s*,\\s*")));
                            } else {
                                newLine.append(newColumnArray.get(i));
                            }
                        }
                    }

                    // Get the complete newLine and check if it is not duplicate, then write it in the new file.
                    String completedNewLine = getNewLine(splitColumnsArray, newLine, column.getIgnoreColumns());
                    if (!checkIfDuplicateRow(completedNewLine, lastRowData)) {
                        writer.write(completedNewLine);
                        lastRowData = completedNewLine;
                    }

                }
                System.out.println("Done with converting file '" + column.getNewFileName() + "' to csv. Path to new file: 'src/main/resources/database/csv/" + column.getNewFileName() + ".csv");
            }
        }
        System.out.println("---------------------------------------- DONE WITH CONVERSION OF DATA TO CSV ----------------------------------------");
    }

    /**
     * Do the final checks and return the final completed string
     *
     * @param splitColumnsArray = Given data
     * @param newLine           = NewLine data
     * @param ignoreColumns     = Array of columns to ignore
     */
    private static String getNewLine(List<String> splitColumnsArray, StringBuilder newLine, Integer[] ignoreColumns) {
        if (ignoreColumns.length >= 1) {
            for (String splitColumn : splitColumnsArray) {
                String colData = splitColumn.replaceAll("\"", "");
                // Ignore if data is null or empty, since we dont need that for linking tables together
                if (!colData.equals("NULL") && !colData.equals(" ")) {
                    return (newLine + "\"" + splitColumn.replaceAll("\"", "") + "\"" + System.getProperty("line.separator"));
                }
            }
        }
        return (newLine + System.getProperty("line.separator"));
    }

    /**
     * Check if integer exists in the integer array
     *
     * @param arr         = Given array
     * @param targetValue = search value
     */
    private static boolean checkIfContains(Integer[] arr, int targetValue) {
        return Arrays.asList(arr).contains(targetValue);
    }

    /**
     * Check if the current row is duplicate compared to the last written row.
     * Since the data is sorted, we only need to check the last written row and not all the data.
     *
     * @param newCsvLine  = New csv row
     * @param lastRowData = Last written csv row
     */
    public static boolean checkIfDuplicateRow(String newCsvLine, String lastRowData) {
        return newCsvLine.equals(lastRowData);
    }

    /**
     * Get file from classpath, resources folder
     *
     * @param fileName = name of file
     */
    private static File getFileFromResources(String fileName) {
        ClassLoader classLoader = Parser.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("ERROR: file not found!, is the path to the raw tsv file correct?");
        } else {
            return new File(resource.getFile());
        }
    }
}
