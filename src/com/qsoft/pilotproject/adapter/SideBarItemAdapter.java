package com.qsoft.pilotproject.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.PilotProject.R;
import com.qsoft.pilotproject.activity.HomeFragment;

/**
 * User: binhtv
 * Date: 10/16/13
 * Time: 10:45 AM
 */
public class SideBarItemAdapter extends ArrayAdapter<String>
{
    private Activity context;
    private String[] name;
    private int viewResourceId;
    private LayoutInflater inflater;

    static class ItemHolder
    {
        public ImageView ivIcon;
        public TextView tvName;
    }

    public SideBarItemAdapter(Activity context, int viewResourceId, String[] name)
    {
        super(context, viewResourceId, name);
        this.context = context;
        this.name = name;
        this.viewResourceId = viewResourceId;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View rowItem = convertView;
        if (convertView == null)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            if (position == 6)
            {
                rowItem = inflater.inflate(R.layout.menu_helpcenter, null);
            }
            else
            {
                rowItem = inflater.inflate(viewResourceId, null);
            }
            ItemHolder itemHolder = new ItemHolder();
            itemHolder.ivIcon = (ImageView) rowItem.findViewById(R.id.imSideBarIcon);
            itemHolder.tvName = (TextView) rowItem.findViewById(R.id.tvSideBarItem);
            rowItem.setTag(itemHolder);

        }

        ItemHolder itemHolder = (ItemHolder) rowItem.getTag();
        itemHolder.ivIcon.setImageResource(HomeFragment.SIDE_BAR_ICONS[position]);
        itemHolder.tvName.setText(name[position]);
        return rowItem;
    }
}
