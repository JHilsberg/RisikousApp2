package de.hszg.risikousapp.models;

/**
 * Created by Hannes on 18.12.2014.
 */
public class PublicationForList {
    private String title, status, entryDate;
    private int id;

    public PublicationForList(int id, String title, String status, String entryDate){
        this.id = id;
        this.title = title;
        this.status = status;
        this.entryDate = entryDate;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }
}
