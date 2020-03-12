package com.datascience.bigmovie.base;

import java.io.*;
import java.net.URL;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
        //Array with location of current raw data
        String[] rawDataPaths = {"name.basics.tsv/data", "title.akas.tsv/data", "title.basics.tsv/data"};

        try {
            convertTSVToCSVFile(rawDataPaths);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param rawDataPaths = raw data path
     * @throws IOException
     */
    private static void convertTSVToCSVFile(String[] rawDataPaths) throws IOException {

        StringTokenizer tokenizer;

        for (String rawDataPath : rawDataPaths) {
            // Get file from resources folder
            File file = getFileFromResources("database/raw/" + rawDataPath + ".tsv");

            //Try to read the existing data and write it to a new csv file
            try (BufferedReader br = new BufferedReader(new FileReader(file));
                 PrintWriter writer = new PrintWriter(new FileWriter("database/csv/" + rawDataPath + ".csv"))) {

                int i = 0;
                for (String line; (line = br.readLine()) != null; ) {
                    i++;
                    if (i % 10000 == 0) {
                        System.out.println("Processed: " + i);

                    }
                    tokenizer = new StringTokenizer(line, "\t");

                    String csvLine = "";
                    String token;
                    while (tokenizer.hasMoreTokens()) {
                        token = tokenizer.nextToken().replaceAll("\"", "'");
                        csvLine += "\"" + token + "\",";
                    }

                    if (csvLine.endsWith(",")) {
                        csvLine = csvLine.substring(0, csvLine.length() - 1);
                    }

                    writer.write(csvLine + System.getProperty("line.separator"));

                }

            }
        }
    }

    // get file from classpath, resources folder
    private static File getFileFromResources(String fileName) {
        System.out.println("Getting data file from resources");

        ClassLoader classLoader = Main.class.getClassLoader();

        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            System.out.println("Data file found, returning data");
            return new File(resource.getFile());
        }

    }

    private static String convertToCSV(String line) {
        String csv = "";
        line = line.replaceAll("\t", ",");
        return line;
    }
}
