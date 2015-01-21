package de.hszg.risikousapp.publicationDetails.comments;

import java.util.ArrayList;

/**
 * Simple model class for a comment.
 */
public class Comment {
    private String id;
    private String author;
    private String text;
    private String timeStamp;
    private ArrayList<Comment> listOfAnswers;

    public Comment(String id,String author, String timeStamp, String text){
        this.id = id;
        this.author = author;
        this.timeStamp = timeStamp;
        this.text = text;
    }

    /**
     * @return comment id
     */
    public String getId(){
        return id;
    }

    /**
     * @return time stamp
     */
    public String getTimeStamp() {
        return timeStamp;
    }

    /**
     * @return author of the comment
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @return comment text
     */
    public String getText() {
        return text;
    }

    /**
     * @return list of Answers (comments)
     */
    public ArrayList<Comment> getListOfAnswers() {
        return listOfAnswers;
    }

    public void setListOfAnswers(ArrayList<Comment> listOfAnswers) {
        this.listOfAnswers = listOfAnswers;
    }

}
