package de.hszg.risikousapp.publicationDetails;

import java.util.ArrayList;

/**
 * Created by Hannes on 18.12.2014.
 */

public class PublicationForDetails {
    private String title;
    private String incidentReport;
    private String differenceStatement;
    private String category;
    private String action;
    private String minRPZofReporter;
    private String avgRPZofReporter;
    private String maxRPZofReporter;
    private String minRPZofQBM;
    private String avgRPZofQBM;
    private String maxRPZofQBM;

    public String getAssignedReports() {
        return assignedReports;
    }

    public void setAssignedReports(String assignedReports) {
        this.assignedReports = assignedReports;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIncidentReport() {
        return incidentReport;
    }

    public void setIncidentReport(String incidentReport) {
        this.incidentReport = incidentReport;
    }

    public String getDifferenceStatement() {
        return differenceStatement;
    }

    public void setDifferenceStatement(String differenceStatement) {
        this.differenceStatement = differenceStatement;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMinRPZofReporter() {
        return minRPZofReporter;
    }

    public void setMinRPZofReporter(String minRPZofReporter) {
        this.minRPZofReporter = minRPZofReporter;
    }

    public String getAvgRPZofReporter() {
        return avgRPZofReporter;
    }

    public void setAvgRPZofReporter(String avgRPZofReporter) {
        this.avgRPZofReporter = avgRPZofReporter;
    }

    public String getMaxRPZofReporter() {
        return maxRPZofReporter;
    }

    public void setMaxRPZofReporter(String maxRPZofReporter) {
        this.maxRPZofReporter = maxRPZofReporter;
    }

    public String getMinRPZofQBM() {
        return minRPZofQBM;
    }

    public void setMinRPZofQBM(String minRPZofQBM) {
        this.minRPZofQBM = minRPZofQBM;
    }

    public String getAvgRPZofQBM() {
        return avgRPZofQBM;
    }

    public void setAvgRPZofQBM(String avgRPZofQBM) {
        this.avgRPZofQBM = avgRPZofQBM;
    }

    public String getMaxRPZofQBM() {
        return maxRPZofQBM;
    }

    public void setMaxRPZofQBM(String maxRPZofQBM) {
        this.maxRPZofQBM = maxRPZofQBM;
    }

    private String assignedReports;
}