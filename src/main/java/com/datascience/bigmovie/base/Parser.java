package com.datascience.bigmovie.base;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.io.FileWriter;

/**
 * @author Team ??, Project Data Science
 */
public class Parser {

    private List<NewColumn> newColumnsList = new ArrayList<>();

    /**
     * Prepare the parser by setting the raw data location and ignored columns
     * <p>
     * Place the raw data files in the following folder: resources/database/raw
     * Extract the zip files in separate folders.
     * Example of correct filepath: ProjectDataScience/src/main/resources/database/raw/title.ratings.tsv/data.tsv
     */
    public void setupParser() throws IOException {

        // All data files, requires original filename and integer array with ignored indexes.
        // If no value given for the integer array it will parse all the data.
        this.newColumnsList.add(new NewColumn("name.basics.tsv/data", new Integer[]{1, 2}));
        this.newColumnsList.add(new NewColumn("title.akas.tsv/data", new Integer[]{}));
        this.newColumnsList.add(new NewColumn("title.basics.tsv/data", new Integer[]{}));
        this.newColumnsList.add(new NewColumn("title.crew.tsv/data", new Integer[]{}));
        this.newColumnsList.add(new NewColumn("title.episode.tsv/data", new Integer[]{}));
        this.newColumnsList.add(new NewColumn("title.principals.tsv/data", new Integer[]{}));
        this.newColumnsList.add(new NewColumn("title.ratings.tsv/data", new Integer[]{}));

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
            File file = getFileFromResources("database/raw/" + newColumn.originalFileName + ".tsv");
            String newFileName = newColumn.originalFileName.split(".tsv/")[0];
            String newFilePath = "src/main/resources/database/csv/" + newFileName + ".csv";

            //Try to read the existing data and write it to a new csv file
            System.out.println("Starting converting file '" + newFileName + "' to csv.");

            try (BufferedReader br = new BufferedReader(new FileReader(file));
                 PrintWriter writer = new PrintWriter(new FileWriter(newFilePath))) {

                // For loop through each line that exists in de tsv file
                for (String line; (line = br.readLine()) != null; ) {
                    tokenizer = new StringTokenizer(line, "\t");
                    String token;

                    // Temporary stored new line of data
                    StringBuilder newLine = new StringBuilder();

                    // New ArrayList used for temporary storing the newly generated csv column data
                    ArrayList<String> newColumnArray = new ArrayList<>();

                    while (tokenizer.hasMoreTokens()) {
                        token = tokenizer.nextToken().replaceAll("\"", "'");
                        newColumnArray.add("\"" + token + "\",");
                    }

                    // Done with converting all data, check if it ends with an period (",")
                    String lastRow = newColumnArray.get(newColumnArray.size() - 1);
                    if (lastRow.endsWith(",")) {
                        lastRow = lastRow.substring(0, lastRow.length() - 1); // Remove "," from string
                        newColumnArray.set(newColumnArray.size() - 1, lastRow); // set at the last index value
                    }

                    // For each string in the new column append it and check if it has to be removed or not by referencing the ignored column list
                    for (int i = 0; i < newColumnArray.size(); i++) {
                        if (!ignoreColumnCheck(newColumn.ignoreColumns, i)) {
                            newLine.append(newColumnArray.get(i));
                        }
                    }

                    // Clear the newColumns ArrayList and write the new data to the csv file
                    newColumnArray.clear();
                    writer.write(newLine + System.getProperty("line.separator"));

                    // Log complete newline for debug purposes
//                    System.out.println(newLine);

                }
                // Confirmation that parsing has been completed
                System.out.println("Done with converting file '" + newFileName + "' to csv. Path to new file: 'src/main/resources/database/csv/" + newFileName + ".csv");
            }
        }
        System.out.println("---------------------------------------- DONE WITH CONVERSION OF DATA TO CSV ----------------------------------------");
    }

    /**
     * Check if integer exists in the ignorecolumns array
     *
     * @param arr         = Given array
     * @param targetValue = search value
     */
    public static boolean ignoreColumnCheck(Integer[] arr, int targetValue) {
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
