package com.qsoft.pilotproject.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.qsoft.pilotproject.R;

/**
 * User: binhtv
 * Date: 10/17/13
 * Time: 11:52 AM
 */
public class ThumbnailFragment extends Fragment
{
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.program_thumnail,container,false);
        return view;
    }
}