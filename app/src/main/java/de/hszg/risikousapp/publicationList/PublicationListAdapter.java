package de.hszg.risikousapp.publicationList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hszg.risikousapp.R;

        import static de.hszg.risikousapp.R.layout.publication_item;

/**
 * Adapter class for the publication list. Get elements out of the publication model and shows them in the list.
 */
public  class PublicationListAdapter extends ArrayAdapter<PublicationForList> {
    private ArrayList<PublicationForList> publicationList;
    private LayoutInflater mInflater;

    public PublicationListAdapter(Context context, int textViewResourceId, ArrayList<PublicationForList> results) {
        super(context, textViewResourceId, results);
        publicationList = results;
        mInflater = LayoutInflater.from(context);
    }

    /**
     * Returns a view element for one publication.
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(publication_item, null);
            viewHolder = new ViewHolder();

            viewHolder.title = (TextView) convertView.findViewById(R.id.publication_title);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date_creation);
            viewHolder.status = (TextView) convertView.findViewById(R.id.publication_status);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String status = publicationList.get(position).getStatus();

        // Datum umformatieren
        String outputDate = "";
        String inputDate = publicationList.get(position).getEntrydate();
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(inputDate);
            outputDate = new SimpleDateFormat("dd.MM.yyyy").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (status.equals("abgeschlossen")) {
            viewHolder.status.setTextColor(Color.parseColor("#33C204"));
        } else
        if (status.equals("ungültig")) {
            viewHolder.status.setTextColor(Color.RED);
        } else {
            viewHolder.status.setTextColor(Color.BLACK);
        }

        viewHolder.title.setText(publicationList.get(position).getTitle());
        viewHolder.date.setText(outputDate);
        viewHolder.status.setText(status);

        return convertView;
    }

    /**
     * @return size of publication list
     */
    @Override
    public int getCount() {
        return publicationList.size();
    }

    /**
     * @param position
     * @return publication at the selected position
     */
    @Override
    public PublicationForList getItem(int position) {
        return publicationList.get(position);
    }

    /**
     * @param position
     * @return id
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * static inner class to use ViewHolder pattern
     */
    static class ViewHolder {
        TextView title;
        TextView date;
        TextView status;
    }
}