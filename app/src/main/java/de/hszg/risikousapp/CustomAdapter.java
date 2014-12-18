package de.hszg.risikousapp;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.TextView;

        import java.util.ArrayList;

        import de.hszg.risikousapp.models.PublicationForList;

        import static de.hszg.risikousapp.R.layout.list_testing;

public  class CustomAdapter extends BaseAdapter {
    private static ArrayList<PublicationForList> searchArrayList;
    private static ArrayList<String> erstellungsdatumm;
    private static ArrayList<String> meldungenn;
    private static ArrayList<String> statuss;
    private static ArrayList<String> kommentarenn;
    private LayoutInflater mInflater;

    public CustomAdapter(Context context, ArrayList<PublicationForList> results) {
        searchArrayList = results;
        // erstellungsdatumm = erstellungsdatum;
        // meldungenn = meldungen;
        // statuss=status;
        // kommentarenn=kommmentare;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return searchArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return searchArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(list_testing, null);
            holder = new ViewHolder();
            holder.txtid = (TextView) convertView.findViewById(R.id.titel);
            holder.txtentrydate = (TextView) convertView
                    .findViewById(R.id.erstellungsdatumfield);
            holder.txtmeldungen = (TextView) convertView.findViewById(R.id.textView9);

            holder.txtstatus = (TextView) convertView.findViewById(R.id.textView7);

            holder.txtkommentaren = (TextView) convertView.findViewById(R.id.textView11);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtid.setText((CharSequence) searchArrayList.get(position).getId());


        holder.txtentrydate.setText((CharSequence)searchArrayList.get(position).getEntrydate());


        holder.txtmeldungen.setText((CharSequence) searchArrayList.get(position).getReports());

        holder.txtstatus.setText((CharSequence) searchArrayList.get(position).getStatus());

        holder.txtkommentaren.setText((CharSequence) searchArrayList.get(position).getComments());


        return convertView;
    }

    static class ViewHolder {
        TextView txtid;
        TextView txtentrydate;
        TextView txtmeldungen;
        TextView txtstatus;
        TextView txtkommentaren;
    }
}