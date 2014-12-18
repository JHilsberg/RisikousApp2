package de.hszg.risikousapp.models;

/**
 * Created by Hannes on 18.12.2014.
 */
public class Comment {
    private String author;
    private String text;
    private String timeStamp;

    public Comment(String author, String timeStamp, String text){
        this.author = author;
        this.timeStamp = timeStamp;
        this.text = text;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

}
