package com.example.mostafa.notebook;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteViewFragment extends Fragment {


    public NoteViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_note_view, container, false);
        TextView noteTittle=(TextView)view.findViewById(R.id.note_detail_tittle);
        TextView noteBody=(TextView)view.findViewById(R.id.note_detail_body);
        ImageView noteImage=(ImageView)view. findViewById(R.id.note_detail_image);




            noteTittle.setText(getActivity().getIntent().getExtras().getString(MainActivity.NOTE_TITTLE_EXTRA,""));
            noteBody.setText(getActivity().getIntent().getExtras().getString(MainActivity.NOTE_BODY_EXTRA,""));
            noteImage.setImageResource(getActivity().getIntent().getExtras().getInt(MainActivity.NOTE_IMAGE_EXTRA,0));

        return view;

}


}
