package de.hszg.risikousapp;

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

import de.hszg.risikousapp.models.PublicationForList;

        import static de.hszg.risikousapp.R.layout.publication_item;

public  class PublicationListAdapter extends ArrayAdapter<PublicationForList> {
    private ArrayList<PublicationForList> publicationList;
    private LayoutInflater mInflater;

    public PublicationListAdapter(Context context, int textViewResourceId, ArrayList<PublicationForList> results) {
        super(context, textViewResourceId, results);
        publicationList = results;
        mInflater = LayoutInflater.from(context);
    }

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
            viewHolder.status.setTextColor(Color.GREEN);
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

    @Override
    public int getCount() {
        return publicationList.size();
    }

    @Override
    public PublicationForList getItem(int position) {
        return publicationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView title;
        TextView date;
        TextView status;
    }
}