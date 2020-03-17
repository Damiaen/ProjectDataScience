package com.datascience.bigmovie.base;

import java.io.*;
import java.net.URL;
import java.util.StringTokenizer;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args) {
        // Array with location of current raw data, uncomment this to test all files
        // String[] rawDataPaths = {"name.basics.tsv/data", "title.akas.tsv/data", "title.basics.tsv/data", "title.crew.tsv/data", "title.episode.tsv/data", "title.principals.tsv/data", "title.ratings.tsv/data"};
        String[] rawDataPaths = {"name.basics.tsv/data"};

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
            String newFilePath = "src/main/resources/database/csv/" + rawDataPath.split(".tsv/")[0] + ".csv";

            //Try to read the existing data and write it to a new csv file
            try (BufferedReader br = new BufferedReader(new FileReader(file));
                 PrintWriter writer = new PrintWriter(new FileWriter(newFilePath))) {

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
                        System.out.println(line);

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

}
