package com.datascience.bigmovie.base.models;

/**
 * @author Damiaen Toussaint, team 4,  Project Data Science
 */
public class Question {

    private Integer id;
    private String title;
    private String description;
    private String query;
    private String type;


    /**
     * @param id = Unique id for question
     * @param title = Long name of question
     * @param query = SQL query
     * @param type = Type of question, can be: "R", "VISUAL" or "REGULAR"
     */
    public Question(Integer id, String title, String description, String query, String type) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.query = query;
        this.type = type;
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

    public String getDescription() {
        return description;
    }

    public Integer getId() {
        return id;
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
