package com.qsoft.pilotproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import com.qsoft.pilotproject.R;
import com.qsoft.pilotproject.model.Comment;

/**
 * User: binhtv
 * Date: 10/18/13
 * Time: 1:42 PM
 */
public class NewCommentFragment extends Activity
{
    public  static final String COMMENT_EXTRA = "comment";
    private ImageButton ibCancel;
    private ImageButton ibPost;
    private EditText etNewComment;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program_add_comment);
        ibCancel = (ImageButton) findViewById(R.id.ibNewCommentCancel);
        ibCancel.setOnClickListener(ibCancelOnClickListener);
        ibPost = (ImageButton) findViewById(R.id.ibNewCommentPost);
        ibPost.setOnClickListener(ibPostOnClickListener);
        etNewComment = (EditText) findViewById(R.id.etAddNewComment);
    }
    View.OnClickListener ibCancelOnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            Intent intent = getIntent();
            setResult(RESULT_CANCELED,intent);
            finish();
        }
    };

    View.OnClickListener ibPostOnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            Intent intent = getIntent();
            Comment newComment = new Comment();
            newComment.setTitle("User");
            newComment.setContent(etNewComment.getText().toString());
            newComment.setTimeCreated("1 month ago");
            intent.putExtra(COMMENT_EXTRA,newComment);
            setResult(RESULT_OK, intent);
            finish();
        }
    };
}