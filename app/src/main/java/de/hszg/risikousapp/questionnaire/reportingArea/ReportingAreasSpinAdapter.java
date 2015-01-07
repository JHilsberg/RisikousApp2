package de.hszg.risikousapp.questionnaire.reportingArea;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.hszg.risikousapp.R;
import de.hszg.risikousapp.questionnaire.reportingArea.ReportingArea;

/**
 * Created by Julian on 17.12.2014.
 */
public class ReportingAreasSpinAdapter extends ArrayAdapter<ReportingArea> {

    private Context context;
    private ArrayList<ReportingArea> reportingAreas;

    public ReportingAreasSpinAdapter(Context context, int textViewResourceId,
                                     ArrayList<ReportingArea> reportingAreas) {
        super(context, textViewResourceId, reportingAreas);
        this.context = context;
        this.reportingAreas = reportingAreas;
    }

    public int getCount(){
        return reportingAreas.size();
    }

    public ReportingArea getItem(int position){
        return reportingAreas.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View itemLayout = inflater.inflate(R.layout.reporting_area_spinner_item, parent, false);
        TextView label = (TextView) itemLayout.findViewById(R.id.reportingAreaItem);
        label.setText(reportingAreas.get(position).getName());

        return itemLayout;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View itemLayout = inflater.inflate(R.layout.reporting_area_spinner_item, parent, false);
        TextView label = (TextView) itemLayout.findViewById(R.id.reportingAreaItem);
        label.setText(reportingAreas.get(position).getName());

        return itemLayout;
    }
}
