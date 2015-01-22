package de.hszg.risikousapp.publicationDetails.comments;

import android.content.Context;
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

/**
 * Adapter class for comment list.
 */
public class CommentAdapter extends ArrayAdapter<Comment> {
    private ArrayList<Comment> commentList;
    private LayoutInflater mInflater;

    public CommentAdapter(Context context, int textViewResourceId, ArrayList<Comment> result) {
        super(context, textViewResourceId, result);
        this.commentList = result;
        mInflater = LayoutInflater.from(context);
    }

    /**
     * Sets the view elements and format date.
     * @param position
     * @param convertView
     * @param parent
     * @return list view with all comments of the selected publication
     */
    public View getView(int position, View convertView, ViewGroup parent){
        convertView = mInflater.inflate(R.layout.comment_item, null);
        ViewHolder viewHolder = new ViewHolder();

        String outputDate = "";
        String inputDate = commentList.get(position).getTimeStamp();
        SimpleDateFormat dateFormat  = new SimpleDateFormat("dd.MM.yyyy, HH:mm");

        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(inputDate);
            outputDate = dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        viewHolder.commentHeader = (TextView) convertView.findViewById(R.id.commentHeader);
        viewHolder.comment = (TextView) convertView.findViewById(R.id.comment);

        viewHolder.commentHeader.setText(commentList.get(position).getAuthor() + " schrieb am " + outputDate);
        viewHolder.comment.setText(commentList.get(position).getText());
        return convertView;
    }

    /**
     * @return comment list size
     */
    public int getCount(){
        return commentList.size();
    }

    /**
     * @param position
     * @return comment on specific position
     */
    public Comment getItem(int position){
        return commentList.get(position);
    }

    /**
     * @param position
     * @return id of comment on specific position
     */
    public long getItemId(int position){
        return position;
    }

    /**
     * Inner ViewHolder class for list elements.
     */
    static class ViewHolder {
        TextView commentHeader;
        TextView comment;
        TextView answers;
    }
}
