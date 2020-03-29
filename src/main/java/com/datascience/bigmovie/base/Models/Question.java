package com.datascience.bigmovie.base.Models;

/**
 * @author Damiaen Toussaint, team 4,  Project Data Science
 */
public class Question {

    private String title;
    private String query;
    private String type;
    private String imagePath;


    /**
     * @param title = Long name of question
     * @param query = SQL query
     * @param type = Type of question, can be: "R", "VISUAL" or "REGULAR"
     * @param imagePath = Path to optional image or different visualisation type
     */
    public Question(String title, String query, String type, String imagePath) {
        this.title = title;
        this.query = query;
        this.type = type;
        this.imagePath = imagePath;
    }

    /**
     * @param title = Long name of question
     * @param query = SQL query
     */
    public Question(String title, String query, String type) {
        this.title = title;
        this.type = type;
        this.query = query;
    }

    /**
     * @return type = type of question ("R", "REGULAR", "VISUAL")
     */
    public String getType() { return type; }

    /**
     * @return  title = Long title of question
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return  query = query of question
     */
    public String getQuery() {
        return query;
    }

    /**
     * @return  imagePath = path to image
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * We override this so the comboboxModel can get the correct field
     *
     * * @return title = Long title of question
     */
    @Override
    public String toString() {
        return title;
    }

}
