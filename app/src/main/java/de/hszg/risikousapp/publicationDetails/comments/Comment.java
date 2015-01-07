package de.hszg.risikousapp.publicationDetails.comments;

/**
 * Simple model class for a comment.
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

}
