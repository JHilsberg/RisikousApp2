package de.hszg.risikousapp;

import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

        import java.util.ArrayList;

        import de.hszg.risikousapp.models.PublicationForList;

        import static de.hszg.risikousapp.R.layout.list_testing;

public  class CustomAdapter extends ArrayAdapter<PublicationForList> {
    private ArrayList<PublicationForList> publicationList;
    private LayoutInflater mInflater;

    public CustomAdapter(Context context, int textViewResourceId, ArrayList<PublicationForList> results) {
        super(context, textViewResourceId, results);
        publicationList = results;
        mInflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(list_testing, null);
            viewHolder = new ViewHolder();

            viewHolder.txtid = (TextView) convertView.findViewById(R.id.titel);
            viewHolder.txtentrydate = (TextView) convertView.findViewById(R.id.erstellungsdatumfield);
            viewHolder.txtmeldungen = (TextView) convertView.findViewById(R.id.textView9);
            viewHolder.txtstatus = (TextView) convertView.findViewById(R.id.textView7);
            viewHolder.txtkommentaren = (TextView) convertView.findViewById(R.id.textView11);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.txtid.setText(publicationList.get(position).getId());
        viewHolder.txtentrydate.setText(publicationList.get(position).getEntrydate());
        viewHolder.txtmeldungen.setText(publicationList.get(position).getReports());
        viewHolder.txtstatus.setText(publicationList.get(position).getStatus());
        viewHolder.txtkommentaren.setText(publicationList.get(position).getComments());

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
        TextView txtid;
        TextView txtentrydate;
        TextView txtmeldungen;
        TextView txtstatus;
        TextView txtkommentaren;
    }
}