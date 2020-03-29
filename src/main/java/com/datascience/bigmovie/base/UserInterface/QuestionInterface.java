package com.datascience.bigmovie.base.UserInterface;

import com.datascience.bigmovie.base.Models.Answer;
import com.datascience.bigmovie.base.Models.Question;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Damiaen Toussaint, team 4,  Project Data Science
 */
public class QuestionInterface {
    private JPanel content_panel;
    private JTextPane answer_content;
    private JPanel rootPanel;
    private JLabel pageTitle;
    private JLabel imageLabel;

    private JFrame questionInterfaceFrame = new JFrame("Project DataScience - Groep 4 - Ask Question");
    private Answer answer;
    /**
     * Requires an question
     */
    public QuestionInterface(Answer answer) throws IOException {
        // Assign question data to the local question variable
        this.answer = answer;
        // Create required elements for the JFrame
        createInterfaceElements();
    }

    /**
     * Main function that contains all of the base UI settings
     */
    private void createInterfaceElements() throws IOException {
        questionInterfaceFrame.setContentPane(rootPanel);
        questionInterfaceFrame.setSize(940,480);
        questionInterfaceFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        questionInterfaceFrame.setVisible(true);

        //Temporary debug stuff
        answer_content.setText(answer.getContent());
        pageTitle.setText(answer.getTitle());

        BufferedImage questionImage = ImageIO.read(new File(answer.getImagePath()));
        imageLabel.setIcon(new ImageIcon(questionImage.getScaledInstance(280, 280, Image.SCALE_FAST)));
    }

    /**
     * Return to the main UI view
     */
    private void exitPage() {
        questionInterfaceFrame.setVisible(false);
        questionInterfaceFrame.dispose();
    }
}
