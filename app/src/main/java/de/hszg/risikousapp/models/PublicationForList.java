package de.hszg.risikousapp.models;


/**
 * Created by besitzer on 18.12.14.
 */
public class PublicationForList {


    private String id;
    private String reports;
    private String status;
    private String comments;
    private String entrydate;
    public PublicationForList(String ids, String reports, String status, String commments, String entrydate) {
        this.id = ids;
        this.reports = reports;
        this.status = status;
        this.comments = commments;
        this.entrydate = entrydate;
    }

    public String getId() {
        return id;
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
