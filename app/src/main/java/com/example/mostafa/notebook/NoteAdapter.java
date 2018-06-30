package com.example.mostafa.notebook;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mostafa on 6/20/2018.
 */

public class NoteAdapter extends ArrayAdapter <Note>{

    public static class ViewHolder
    {
         TextView tittle,body;
         ImageView note_image;
    }
   public NoteAdapter(Context context, ArrayList<Note>notes)
   {
       super(context,0,notes);
   }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Note note=getItem(position);
        ViewHolder viewHolder;
        if (convertView==null)
        {
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.list_view_item,parent,false);
            viewHolder.tittle=(TextView) convertView.findViewById(R.id.note_tittle);
            viewHolder.body=(TextView) convertView.findViewById(R.id.note_body);
            viewHolder.note_image=(ImageView)convertView.findViewById(R.id.note_image);

            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) convertView.getTag();
        }

        viewHolder.tittle.setText(note.getTittle());
        viewHolder.body.setText(note.getBody());
        viewHolder.note_image.setImageResource(note.getImage());
        return convertView;

    }
}
