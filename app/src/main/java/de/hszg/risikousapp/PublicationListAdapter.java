package de.hszg.risikousapp;

import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

        import java.util.ArrayList;

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
            //viewHolder.report = (TextView) convertView.findViewById(R.id.textView9);
            viewHolder.status = (TextView) convertView.findViewById(R.id.publication_status);
            //viewHolder.comments = (TextView) convertView.findViewById(R.id.textView11);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(publicationList.get(position).getTitle());
        //viewHolder.id.setText(publicationList.get(position).getId());
        viewHolder.date.setText(publicationList.get(position).getEntrydate());
        //viewHolder.report.setText(publicationList.get(position).getReports());
        viewHolder.status.setText(publicationList.get(position).getStatus());
        //viewHolder.comments.setText(publicationList.get(position).getComments());

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
        //TextView id;
        TextView date;
        //TextView report;
        TextView status;
        //TextView comments;
    }
}