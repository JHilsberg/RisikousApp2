package de.hszg.risikousapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import de.hszg.risikousapp.models.Comment;

/**
 * Created by Hannes on 19.12.2014.
 */
public class CommentAdapter extends ArrayAdapter<Comment> {
    private ArrayList<Comment> commentList;
    private LayoutInflater mInflater;

    public CommentAdapter(Context context, int textViewResourceId, ArrayList<Comment> result) {
        super(context, textViewResourceId, result);
        this.commentList = result;
        mInflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        convertView = mInflater.inflate(R.layout.comment_item, null);
        ViewHolder viewHolder = new ViewHolder();

        viewHolder.commentHeader = (TextView) convertView.findViewById(R.id.commentHeader);
        viewHolder.comment = (TextView) convertView.findViewById(R.id.comment);
        viewHolder.commentHeader.setText(commentList.get(position).getAuthor() + " schrieb am " + commentList.get(position).getTimeStamp() + ":");
        viewHolder.comment.setText(commentList.get(position).getText());

        return convertView;
    }

    public int getCount(){
        return commentList.size();
    }

    public Comment getItem(int position){
        return commentList.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    static class ViewHolder {
        TextView commentHeader;
        TextView comment;
    }
}
