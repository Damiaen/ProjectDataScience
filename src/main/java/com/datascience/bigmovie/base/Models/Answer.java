package com.datascience.bigmovie.base.Models;

/**
 * @author Damiaen Toussaint, team 4,  Project Data Science
 */
public class Answer {

    private String title;
    private String content;
    private String type;
    private String imagePath;


    /**
     * @param title = Long name of question
     * @param content = answer of question content
     * @param type = type of question
     * @param imagePath = Path to optional image or different visualisation type
     */
    public Answer(String title, String content, String type, String imagePath) {
        this.title = title;
        this.content = content;
        this.type = type;
        this.imagePath = imagePath;
    }

    /**
     * @param title = Long name of question
     * @param type = type of question
     * @param content = answer of question content
     */
    public Answer(String title, String type, String content) {
        this.title = title;
        this.type = type;
        this.content = content;
    }

    /**
     * @return type = type of question
     */
    public String getType() { return type; }

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
