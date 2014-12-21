package de.hszg.risikousapp.models;


/**
 * Created by besitzer on 18.12.14.
 */
public class PublicationForList {

    private String id;
    private String title;
    private String status;
    private String entryDate;

    public PublicationForList(String id, String title, String status, String entryDate) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.entryDate = entryDate;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public String getEntryDate() {
        return entryDate;
    }
}
