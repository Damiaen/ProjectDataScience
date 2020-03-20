package com.datascience.bigmovie.base;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.io.FileWriter;

/**
 * @author Damiaen Toussaint, team ??,  Project Data Science
 */
public class Parser {

    private List<NewColumn> newColumnsList = new ArrayList<>();

    /**
     * Prepare the parser by setting the raw data location and ignored columns
     * Place the raw data files in the following folder: resources/database/raw
     * Extract the zip files in separate folders.
     * Example of correct filepath: ProjectDataScience/src/main/resources/database/raw/title.ratings.tsv/data.tsv
     *
     * TODO: Er is nog geen database design, dus ik heb zoveel mogelijk functionaliteit geprobeerd in de parser te stoppen
     */
    public void setupParser() throws IOException {

        // All data files, requires dataSource, newFileName, int array with ignored indexes and integer array for indexes to split.
        // If no value is given for any of the integer arrays it will just parse all the data as normal.

        // Actors table
        this.newColumnsList.add(new NewColumn("name.basics.tsv/data", "Actors", new Integer[]{5}, new Integer[]{}));
        // Has played in movie table
        this.newColumnsList.add(new NewColumn("name.basics.tsv/data", "ActorHasPlayedIn", new Integer[]{1,2,3,4}, new Integer[]{5}));
        // Movie title in original name
        this.newColumnsList.add(new NewColumn("title.akas.tsv/data","OriginalMovieName", new Integer[]{}, new Integer[]{}));
        // Movie english title and general data (genre/playtime)
        this.newColumnsList.add(new NewColumn("title.basics.tsv/data","MovieName", new Integer[]{}, new Integer[]{}));

        // Crew belonging to movie
        this.newColumnsList.add(new NewColumn("title.crew.tsv/data","MovieDirectors", new Integer[]{2}, new Integer[]{1}));
        // Crew belonging to movie
        this.newColumnsList.add(new NewColumn("title.crew.tsv/data","MovieWriters", new Integer[]{1}, new Integer[]{2}));

        // Amount of seasons and episodes
        this.newColumnsList.add(new NewColumn("title.episode.tsv/data","Episodes", new Integer[]{}, new Integer[]{}));
        // Movie roles and data
        this.newColumnsList.add(new NewColumn("title.principals.tsv/data","Principals", new Integer[]{}, new Integer[]{}));
        // Ratings for the movie/show
        this.newColumnsList.add(new NewColumn("title.ratings.tsv/data","Ratings", new Integer[]{}, new Integer[]{}));
        try {
            convertTSVToCSVFile(this.newColumnsList);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Since the raw data files are tsv, we only need to replace certain characters to convert them to csv files.
     *
     * @param newColumns = raw data path
     */
    private static void convertTSVToCSVFile(List<NewColumn> newColumns) throws IOException {

        StringTokenizer tokenizer;

        for (NewColumn newColumn : newColumns) {
            // Get file from resources folder
            File file = getFileFromResources("database/raw/" + newColumn.getDataSource() + ".tsv");
            String newFilePath = "src/main/resources/database/csv/" + newColumn.getNewFileName() + ".csv";

            //Try to read the existing data and write it to a new csv file
            System.out.println("Starting converting file '" + newColumn.getDataSource() + ".tsv' to '" + newColumn.getNewFileName() + ".csv'");

            try (BufferedReader br = new BufferedReader(new FileReader(file));
                 PrintWriter writer = new PrintWriter(new FileWriter(newFilePath))) {

                // For loop through each line that exists in de tsv file
                for (String line; (line = br.readLine()) != null; ) {
                    tokenizer = new StringTokenizer(line, "\t");
                    String token;

                    // Temporary stored single line of data
                    StringBuilder newLine = new StringBuilder();

                    // New ArrayList used for temporary storing the newly generated csv column data
                    ArrayList<String> newColumnArray = new ArrayList<>();

                    while (tokenizer.hasMoreTokens()) {
                        token = tokenizer.nextToken().replaceAll("\"", "'");

                        // If value of token is '\\N' replace it with NULL
                        if (token.contains("\\N")) token = "NULL";
                        newColumnArray.add("\"" + token + "\",");
                    }

                    // Done with converting all data, check if it ends with an period (",")
                    String lastRow = newColumnArray.get(newColumnArray.size() - 1);
                    if (lastRow.endsWith(",")) {
                        lastRow = lastRow.substring(0, lastRow.length() - 1); // Remove "," from string
                        newColumnArray.set(newColumnArray.size() - 1, lastRow); // set at the last index value
                    }


                    List<String> splitColumnsArray = new ArrayList<>();
                    // For each string in the new column append it and check if it has to be removed or not by referencing the ignored column list
                    for (int i = 0; i < newColumnArray.size(); i++) {
                        // Check if it has to ignore this column
                        if (!checkIfContains(newColumn.getIgnoreColumns(), i)) {
                            // Check if it has to split the string into multiple or not
                            if (checkIfContains(newColumn.getSplitColumns(), i)) {
                                splitColumnsArray.addAll(Arrays.asList(newColumnArray.get(i).split("\\s*,\\s*")));
                            } else {
                                newLine.append(newColumnArray.get(i));
                            }
                        }
                    }

                    // Check if splitColumnsArray has been filled, if not continue as normal
                    if (splitColumnsArray.size() != 0) {
                        for (String splitColumn: splitColumnsArray ) {
                            String colData = splitColumn.replaceAll("\"", "");
                            // Ignore if data is null, since we dont need that for linking tables together
                            if (!colData.equals("NULL")) {
                                writer.write(newLine + "\"" + splitColumn.replaceAll("\"", "") + "\"" + System.getProperty("line.separator"));
                            }
                        }
                    } else {
                        newColumnArray.clear();
                        writer.write(newLine + System.getProperty("line.separator"));
                    }
                }
                // Confirmation that parsing has been completed
                System.out.println("Done with converting file '" + newColumn.getNewFileName() + "' to csv. Path to new file: 'src/main/resources/database/csv/" + newColumn.getNewFileName() + ".csv");
            }
        }
        System.out.println("---------------------------------------- DONE WITH CONVERSION OF DATA TO CSV ----------------------------------------");
    }

    /**
     * Check if integer exists in the integer array
     *
     * @param arr         = Given array
     * @param targetValue = search value
     */
    public static boolean checkIfContains(Integer[] arr, int targetValue) {
        return Arrays.asList(arr).contains(targetValue);
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
            throw new IllegalArgumentException("ERROR: file not found!, is the path to the raw tsc correct?");
        } else {
            return new File(resource.getFile());
        }
    }
}
