package com.datascience.bigmovie.base.UserInterface;

import com.datascience.bigmovie.base.Logic.QuestionContentBuilder;
import com.datascience.bigmovie.base.models.Answer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
     * Create and display answer elements based on type value
     * TODO: Replace temp data with data form question
     */
    private void createAnswerElements() throws IOException {
        // Below is some temporary test data, this should be replaced later on
        double[] xData = new double[]{0.0, 1.0, 2.0};
        double[] yData = new double[]{2.0, 1.0, 0.0};

        switch (answer.getType()) {
            case "XY_CHART":
                createXYChart(xData, yData);
                break;
            case "CATEGORY_CHART":
                createCategoryChart();
                break;
            case "PIE_CHART":
                createPieChart(xData, yData);
                break;
            case "IMAGE":
//                AddImage();
                break;
            default:
        }
    }

    private void createCategoryChart() {
        // Create Chart
        CategoryChart chart =
                new CategoryChartBuilder()
                        .width(800)
                        .height(600)
                        .title("Score Histogram")
                        .xAxisTitle("Score")
                        .yAxisTitle("Number")
                        .build();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setHasAnnotations(true);
        chart.getStyler().setPlotGridLinesVisible(false);

        // Series
        chart.addSeries("test 1", Arrays.asList(0, 1, 2, 3, 4), Arrays.asList(4, 5, 9, 6, 5));

        // Add the element to the imagePanel
        addToPanel(new XChartPanel<>(chart));
    }

    /**
     * create an XY Chart based on xData and yData
     *
     * @param xData = data for x axis
     * @param yData = data for y axis
     */
    private void createXYChart(double[] xData, double[] yData) {
        // Create XYChart
        XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", xData, yData);
        // Add the element to the imagePanel
        addToPanel(new XChartPanel<>(chart));
    }

    /**
     * create an Pie Chart based on data from the answer
     *
     * @param xData = data for x axis
     * @param yData = data for y axis
     */
    private void createPieChart(double[] xData, double[] yData) {
        PieChart chart = new PieChartBuilder().width(800).height(600).title("Pie Chart with 4 Slices").build();

        // Customize Chart
        chart.getStyler().setCircular(false);

        // Series
        // TODO: for loop through list of items from query
        chart.addSeries("Pennies", 100);
        chart.addSeries("Nickels", 100);
        chart.addSeries("Dimes", 100);
        chart.addSeries("Quarters", 100);

        // Add the element to the imagePanel
        addToPanel(new XChartPanel<>(chart));
    }

    /**
     * Add element to imagePanel
     */
    private void addToPanel(JPanel chartPanel) {
        imagePanel.setLayout(new java.awt.BorderLayout());
        imagePanel.add(chartPanel, BorderLayout.CENTER);
        imagePanel.validate();
    }
//
//    /**
//     * Add image, path is from answer
//     */
//    private void AddImage() throws IOException {
//        BufferedImage questionImage = ImageIO.read(new File(answer.getImagePath()));
//        imageLabel.setIcon(new ImageIcon(questionImage.getScaledInstance(280, 280, Image.SCALE_FAST)));
//    }

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
