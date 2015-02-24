package com.gmail.jasekurasz.contactlist;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Person> personList;
    ImageLoader mImageLoader;
    ImageView pic;

    public CustomAdapter(Activity activity, List<Person> pList) {
        this.activity = activity;
        this.personList = pList;
    }

    @Override
    public int getCount() {
        return personList.size();
    }

    @Override
    public Object getItem(int i) {
        return personList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (convertView == null) {
            if (inflater == null)
                inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_row, parent, false);
        }

        pic = (ImageView) row.findViewById(R.id.networkImageView);
        TextView name = (TextView) row.findViewById(R.id.listName);
        TextView num = (TextView) row.findViewById(R.id.listNumber);

        Person p = personList.get(position);
        name.setText(p.getName());
        num.setText(p.getworkNum());
        mImageLoader = RequestSingleton.getInstance().getImageLoader();
        mImageLoader.get(p.getSmallImgURL(), ImageLoader.getImageListener(pic, 0, 0));
        return row;
    }
}
