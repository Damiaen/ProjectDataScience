package com.datascience.bigmovie.base.models;

import java.util.ArrayList;

/**
 * @author Damiaen Toussaint, team 4,  Project Data Science
 */
public class Answer {

    private Integer id;
    private String title;
    private String description;
    private String type;
    private String imageName;
    private String rCodePath;
    private ArrayList<String[]> results;


    /**
     * @param id          = id of question
     * @param title       = Long name of question
     * @param description = description of question
     * @param type        = type of question
     * @param imageName   = name of image
     */
    public Answer(Integer id, String title, String description, String type, String imageName, String rCodePath) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.imageName = imageName;
        this.rCodePath = rCodePath;
    }

    public String getrCodePath() {
        return rCodePath;
    }

    /**
     * @param id = id of question
     * @param title = Long name of question
     * @param description = description of question
     * @param type = type of question
     * @param results = Path to optional image or different visualisation type
     */
    public Answer(Integer id, String title, String description, String type, ArrayList<String[]> results) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.results = results;
    }


    public String getImageName() { return imageName; }

    /**
     * @return type = type of question
     */
    public String getType() { return type; }

    public ArrayList<String[]> getResults() {
        return results;
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
    public String getDescription() {
        return description;
    }

    public Integer getId() {
        return id;
    }
}
