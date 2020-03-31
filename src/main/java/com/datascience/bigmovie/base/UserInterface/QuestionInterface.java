package com.datascience.bigmovie.base.UserInterface;

import com.datascience.bigmovie.base.Logic.QuestionContentBuilder;
import com.datascience.bigmovie.base.Logic.QuestionGraphBuilder;
import com.datascience.bigmovie.base.models.Answer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
    }

    /**
     * Check if we need to create an graph or not, else show default question mark
     * TODO: XY_CHART and CATEGORY_CHART
     */
    private void createAnswerElements() throws IOException {
        switch (answer.getType()) {
            case "XY_CHART":
                createXYChart();
                break;
            case "CATEGORY_CHART":
                createCategoryChart();
                break;
            case "PIE_CHART":
                createPieChart();
                break;
            default:
                addImage();
        }
    }

    private void createCategoryChart() {
        // Create Chart
        CategoryChart chart =
                new CategoryChartBuilder()
                        .width(800)
                        .height(600)
                        .title("Staafdiagram")
                        .xAxisTitle("X - waarde")
                        .yAxisTitle("Y - waarde")
                        .build();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setHasAnnotations(true);
        chart.getStyler().setPlotGridLinesVisible(false);
        chart.getStyler().setXAxisTickMarkSpacingHint(200);

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
        chart.addSeries("data", firstRow, secondRow);

        // Add the element to the imagePanel
        addToPanel(new XChartPanel<>(chart));
    }

    /**
     * create an XY Chart based on xData and yData
     */
    private void createXYChart() {
        // Below is some temporary test data, this should be replaced later on
        double[] xData = new double[]{0.0, 1.0, 2.0};
        double[] yData = new double[]{2.0, 1.0, 0.0};
        // Create XYChart
        XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", xData, yData);
        // Add the element to the imagePanel
        addToPanel(new XChartPanel<>(chart));
    }

    /**
     * create an Pie Chart based on data from the answer, we give the answers to questionGraphBuilder to generate the
     * correct data for the pie chart
     */
    private void createPieChart() {
        // Generate new PieChart
        PieChart chart = new PieChartBuilder().width(800).height(600).title("Pie Chart").build();

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
     * Add element to imagePanel
     */
    private void addToPanel(JPanel chartPanel) {
        imagePanel.setLayout(new java.awt.BorderLayout());
        imagePanel.add(chartPanel, BorderLayout.CENTER);
        imagePanel.validate();
    }

    /**
     * Add image, this is the default option
     */
    private void addImage() throws IOException {
        BufferedImage questionImage = ImageIO.read(new File("src/main/resources/images/questionmark.jpg"));
        imageLabel.setIcon(new ImageIcon(questionImage.getScaledInstance(280, 280, Image.SCALE_FAST)));
    }

    /**
     * Main function that contains all of the base UI settings
     */
    private void createInterfaceElements() throws IOException {
        questionInterfaceFrame.setContentPane(rootPanel);
        questionInterfaceFrame.setSize(940, 480);
        questionInterfaceFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        questionInterfaceFrame.setVisible(true);

        answer_content.setText(answer.getDescription() + System.lineSeparator() + System.lineSeparator() + buildAnswerContent());
        pageTitle.setText(answer.getTitle());
    }

    private String buildAnswerContent() {
        String finalContent = questionContentBuilder.buildAnswer(answer);
        System.out.println("Final content string: " + finalContent);
        return finalContent;
    }

    /**
     * Return to the main UI view
     */
    private void exitPage() {
        questionInterfaceFrame.setVisible(false);
        questionInterfaceFrame.dispose();
    }
}
