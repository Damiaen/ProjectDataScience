package com.datascience.bigmovie.base.Models;

/**
 * @author Damiaen Toussaint, team 4,  Project Data Science
 */
public class Question {

    private Integer uniqueId;
    private String title;
    private String query;
    private String imagePath;


    /**
     * @param uniqueId = Unique id for the question
     * @param title = Long name of question
     * @param query = SQL query
     * @param imagePath = Path to optional image or different visualisation type
     */
    public Question(Integer uniqueId, String title, String query, String imagePath) {
        this.uniqueId = uniqueId;
        this.title = title;
        this.query = query;
        this.imagePath = imagePath;
    }

    /**
     * @param uniqueId = Unique id for the question
     * @param title = Long name of question
     * @param query = SQL query
     */
    public Question(Integer uniqueId, String title, String query) {
        this.uniqueId = uniqueId;
        this.title = title;
        this.query = query;
    }

    /**
     * @return  getUniqueId = unique id of question
     */
    public Integer getUniqueId() {
        return uniqueId;
    }

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

}
