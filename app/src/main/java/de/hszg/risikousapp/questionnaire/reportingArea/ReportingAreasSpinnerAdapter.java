package de.hszg.risikousapp.questionnaire.reportingArea;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.hszg.risikousapp.R;

/**
 * Adapter class for publication areas spinner.
 */
public class ReportingAreasSpinnerAdapter extends ArrayAdapter<ReportingArea> {

    private Context context;
    private ArrayList<ReportingArea> reportingAreas;

    /**
     * constructor, set context and reporting area list as class variables
     * @param context
     * @param textViewResourceId
     * @param reportingAreas
     */
    public ReportingAreasSpinnerAdapter(Context context, int textViewResourceId,
                                        ArrayList<ReportingArea> reportingAreas) {
        super(context, textViewResourceId, reportingAreas);
        this.context = context;
        this.reportingAreas = reportingAreas;
    }

    /**
     * Get size of reporting area list.
     * @return size
     */
    public int getCount(){
        return reportingAreas.size();
    }

    /**
     * Get reporting area on selected position.
     * @param position
     * @return ReportingArea
     */
    public ReportingArea getItem(int position){
        return reportingAreas.get(position);
    }

    /**
     * Get id of item on selected position.
     * @param position
     * @return
     */
    public long getItemId(int position){
        return position;
    }

    /**
     * Create the view for the selected spinner item.
     * @param position
     * @param convertView
     * @param parent
     * @return view for a spinner item
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View itemLayout = inflater.inflate(R.layout.reporting_area_spinner_item, parent, false);
        TextView label = (TextView) itemLayout.findViewById(R.id.reportingAreaItem);
        label.setText(reportingAreas.get(position).getName());

        return itemLayout;
    }

    /**
     * Create view for all spinner items shown in dropdown view.
     * @param position
     * @param convertView
     * @param parent
     * @return view for a spinner item
     */
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
