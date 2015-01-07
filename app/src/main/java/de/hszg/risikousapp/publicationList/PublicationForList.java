package de.hszg.risikousapp.publicationList;


/**
 * Created by besitzer on 18.12.14.
 */
public class PublicationForList {


    private String id;
    private String title;
    private String reports;
    private String status;
    private String comments;
    private String entrydate;

    public PublicationForList(String id, String title, String reports, String status, String commments, String entrydate) {
        this.id = id;
        this.title = title;
        this.reports = reports;
        this.status = status;
        this.comments = commments;
        this.entrydate = entrydate;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getReports() {
        return reports;
    }

    public String getStatus() {
        return status;
    }

    public String getComments() {
        return comments ;
    }

    public String getEntrydate() {
        return entrydate;
    }
}
