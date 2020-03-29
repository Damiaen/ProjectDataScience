package com.datascience.bigmovie.base.Models;

/**
 * @author Damiaen Toussaint, team 4,  Project Data Science
 */
public class Answer {

    private String title;
    private String content;
    private String imagePath;


    /**
     * @param title = Long name of question
     * @param content = answer of question content
     * @param imagePath = Path to optional image or different visualisation type
     */
    public Answer(String title, String content, String imagePath) {
        this.title = title;
        this.content = content;
        this.imagePath = imagePath;
    }

    /**
     * @param title = Long name of question
     * @param content = answer of question content
     */
    public Answer(String title, String content) {
        this.title = title;
        this.content = content;
    }

    /**
     * @return  title = Long title of question
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return  getUniqueId = unique id of question
     */
    public String getContent() {
        return content;
    }

    /**
     * @return  imagePath = path to image
     */
    public String getImagePath() {
        return imagePath;
    }

}
