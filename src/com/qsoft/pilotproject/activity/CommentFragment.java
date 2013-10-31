package com.qsoft.pilotproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.qsoft.pilotproject.R;
import com.qsoft.pilotproject.adapter.CommentAdapter;
import com.qsoft.pilotproject.model.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * User: binhtv
 * Date: 10/17/13
 * Time: 2:20 PM
 */
public class CommentFragment extends Fragment
{
    public static final int REQUEST_CODE = 1;
    private TextView tvAddNewComment;
    private ListView lvComment;
    private List<Comment> commentList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.program_comment, container, false);
        tvAddNewComment = (TextView) view.findViewById(R.id.tvNewComment);
        tvAddNewComment.setOnClickListener(tvAddNewCommentOnClickListener);
        lvComment = (ListView) view.findViewById(R.id.lvComment);
        commentList = getModel();
        CommentAdapter commentAdapter = new CommentAdapter(getActivity(), commentList);
        lvComment.setAdapter(commentAdapter);
        return view;
    }

    View.OnClickListener tvAddNewCommentOnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            Intent intent = new Intent(CommentFragment.this.getActivity(),NewCommentFragment.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
       if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE)
       {
           if (data.hasExtra(NewCommentFragment.COMMENT_EXTRA))
           {
               Comment comment = (Comment) data.getExtras().get(NewCommentFragment.COMMENT_EXTRA);
               commentList.add(comment);

           }
       }
    }

    List<Comment> getModel()
    {
        List<Comment> comments = new ArrayList<Comment>();
        for (int i = 0; i < 10; i++)
        {
            comments.add(getComment());
        }
        return comments;

    }

    public Comment getComment()
    {
        Comment comment = new Comment();
        comment.setTitle("Mr. Michale");
        comment.setContent("The Phrase Became a fundamental element");
        comment.setTimeCreated("1 minute ago");
        return comment;
    }
}