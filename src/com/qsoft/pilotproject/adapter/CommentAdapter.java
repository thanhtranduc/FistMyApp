package com.qsoft.pilotproject.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.PilotProject.R;
import com.qsoft.pilotproject.model.Comment;

import java.util.List;

/**
 * User: BinkaA
 * Date: 10/18/13
 * Time: 2:06 AM
 */
public class CommentAdapter extends ArrayAdapter<Comment>
{
    private final Activity context;
    private final List<Comment> comments;

    static class CommentHolder
    {
        public ImageView ivCommentIcon;
        public TextView tvTitle;
        public TextView tvContent;
        public TextView tvTimeCreated;
    }

    public CommentAdapter(Activity context, List<Comment> comments)
    {
        super(context, R.layout.program_comment_list,comments);
        this.context = context;
        this.comments = comments;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View rowComment = convertView;
        if (convertView == null)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            rowComment = inflater.inflate(R.layout.program_comment_list, null);
            CommentHolder commentHolder = new CommentHolder();
            commentHolder.ivCommentIcon = (ImageView) rowComment.findViewById(R.id.ivCommentIcon);
            commentHolder.tvTitle = (TextView) rowComment.findViewById(R.id.tvCommentTitle);
            commentHolder.tvContent = (TextView) rowComment.findViewById(R.id.tvCommentContent);
            commentHolder.tvTimeCreated = (TextView) rowComment.findViewById(R.id.tvCommentTimeCreate);
            rowComment.setTag(commentHolder);
        }
        CommentHolder commentHolder = (CommentHolder) rowComment.getTag();
        commentHolder.tvTitle.setText(comments.get(position).getTitle());
        commentHolder.tvContent.setText(comments.get(position).getContent());
        commentHolder.tvTimeCreated.setText(comments.get(position).getTimeCreated());
        return rowComment;
    }


}
