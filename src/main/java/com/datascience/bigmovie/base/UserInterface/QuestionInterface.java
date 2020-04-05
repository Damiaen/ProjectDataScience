package com.datascience.bigmovie.base.UserInterface;

import com.datascience.bigmovie.base.Logic.QuestionContentBuilder;
import com.datascience.bigmovie.base.Logic.QuestionGraphBuilder;
import com.datascience.bigmovie.base.models.Answer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

/**
 * @author Damiaen Toussaint, team 4,  Project Data Science
 */
public class QuestionInterface {
    private JPanel content_panel;
    private JTextPane answer_content;
    private JPanel rootPanel;
    private JLabel pageTitle;
    private JLabel imageLabel;
    private JPanel titlePanel;
    private JPanel imagePanel;
    private JButton openExplorer;

    private JFrame questionInterfaceFrame = new JFrame("Project DataScience - Groep 4 - Ask Question");
    private QuestionContentBuilder questionContentBuilder = new QuestionContentBuilder();
    private QuestionGraphBuilder questionGraphBuilder = new QuestionGraphBuilder();
    private Answer answer;

    /**
     * Requires an question
     */
    public QuestionInterface(Answer answer) throws IOException {
        // Assign question data to the local question variable
        this.answer = answer;
        // Create required base elements for the JFrame
        createInterfaceElements();
        // Look at the answer itself and build the UI further based on the params
        createAnswerElements();

        openExplorer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    File file = new File("src/main/resources/r_code/" + answer.getrCodePath());
                    String path = file.getCanonicalPath();

                    ProcessBuilder pb = new ProcessBuilder("explorer.exe", "/select," + path);
                    pb.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Check if we need to create an graph or not
     */
    private void createAnswerElements() throws IOException {
        switch (answer.getType()) {
            case "CATEGORY_CHART":
                createCategoryChart();
                break;
            case "PIE_CHART":
                createPieChart();
                break;
            case "R":
                createR();
                break;
            default:
                imagePanel.setVisible(false);
        }
    }

    private void createCategoryChart() {
        // Create Chart
        CategoryChart chart =
                new CategoryChartBuilder()
                        .width(800)
                        .height(600)
                        .title(" ")
                        .xAxisTitle("X - waarde")
                        .yAxisTitle("Y - waarde")
                        .build();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setHasAnnotations(true);
        chart.getStyler().setPlotGridLinesVisible(false);
        chart.getStyler().setXAxisLabelRotation(90);

        // Get cleaned params from data, each questions has own function to return clean data
        ArrayList<String[]> pieChartParameters = questionGraphBuilder.buildGraph(answer);

        // Split data into 2, firstRow is X, secondRow is Y
        ArrayList<String> firstRow = new ArrayList<>();
        ArrayList<Integer> secondRow = new ArrayList<>();

        // Check if not empty, else do nothing
        if (!pieChartParameters.isEmpty()) {
            // Get ArrayList with an string array with 2 values (field name and value)
            for (String[] results : pieChartParameters) {
                firstRow.add(results[0]);
                secondRow.add(Integer.parseInt(results[1]));
            }
        }

        // Series
        chart.addSeries("Aantal", firstRow, secondRow);

        // Add the element to the imagePanel
        addToPanel(new XChartPanel<>(chart));
    }

    /**
     * create an Pie Chart based on data from the answer, we give the answers to questionGraphBuilder to generate the
     * correct data for the pie chart
     */
    private void createPieChart() {
        // Generate new PieChart
        PieChart chart = new PieChartBuilder().width(800).height(600).title(" ").build();

        // Customize options here
        chart.getStyler().setCircular(false);

        // Get cleaned params for PieChart, each questions has own function
        ArrayList<String[]> pieChartParameters = questionGraphBuilder.buildGraph(answer);

        // Check if not empty, else do nothing
        if (!pieChartParameters.isEmpty()) {
            // Get ArrayList with an string array with 2 values (field name and value)
            for (String[] results : pieChartParameters) {
                chart.addSeries(results[0], Integer.parseInt(results[1]));
            }
            addToPanel(new XChartPanel<>(chart));
        }
    }

    /**
     * Add element to imagePanel, update view with styling stuff
     */
    private void addToPanel(JPanel chartPanel) {
        content_panel.setMinimumSize(new Dimension(500, -1));
        imagePanel.setVisible(true);
        imagePanel.setLayout(new java.awt.BorderLayout());
        imagePanel.add(chartPanel, BorderLayout.CENTER);
        imagePanel.validate();
    }

    /**
     * Add Style the page so it works with R
     */
    private void createR() throws IOException {
        BufferedImage questionImage = ImageIO.read(new File("src/main/resources/images/" + answer.getImageName()));
        imageLabel.setVisible(true);
        imageLabel.setIcon(new ImageIcon(questionImage.getScaledInstance(540, 360, Image.SCALE_SMOOTH)));
        imageLabel.validate();
        openExplorer.setVisible(true);
    }

    /**
     * Main function that contains all of the base UI settings
     */
    private void createInterfaceElements() throws IOException {
        questionInterfaceFrame.setContentPane(rootPanel);
        questionInterfaceFrame.setSize(1200, 640);
        questionInterfaceFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        questionInterfaceFrame.setVisible(true);

        answer_content.setText(answer.getDescription() + System.lineSeparator() + System.lineSeparator() + buildAnswerContent());
        pageTitle.setText(answer.getTitle());

        System.out.println(titlePanel.getWidth());
    }

    /**
     * Build the answer content, content is based on unique id of question/answer
     */
    private String buildAnswerContent() {
        String finalContent = questionContentBuilder.buildAnswer(answer);
        System.out.println("Final content string: " + finalContent);
        return finalContent;
    }
}
