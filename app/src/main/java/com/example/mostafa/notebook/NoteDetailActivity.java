package com.example.mostafa.notebook;

import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class NoteDetailActivity extends AppCompatActivity {
private String tittle,body;
    private int image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        android.support.v4.app.Fragment f=createAndAddFragment();
       /*  tittle=getIntent().getStringExtra(MainActivity.NOTE_TITTLE_EXTRA);
         body=getIntent().getStringExtra(MainActivity.NOTE_BODY_EXTRA);
         image=getIntent().getIntExtra(MainActivity.NOTE_IMAGE_EXTRA,0);
*/






    }
    public android.support.v4.app.Fragment createAndAddFragment(){
        MainActivity.FragmentToLaunch fragmentToLaunch=(MainActivity.FragmentToLaunch)getIntent().getSerializableExtra(MainActivity.NOTE_FRAGMENT_TO_LOAD_EXTRA);

        android.support.v4.app.Fragment myFragment=null;



        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        switch (fragmentToLaunch)
        {

            case VIEW:
                NoteViewFragment noteViewFragment=new NoteViewFragment();
                fragmentTransaction.add(R.id.fragmentContainer,noteViewFragment,"NOTE_VIEW_FRAGMENT");
                setTitle("VIEW NOTE");
                myFragment=noteViewFragment;

             /*   bundle.putString(MainActivity.NOTE_TITTLE_EXTRA,tittle);
                bundle.putInt(MainActivity.NOTE_IMAGE_EXTRA,image);
                bundle.putString(MainActivity.NOTE_BODY_EXTRA,body);
                myFragment.setArguments(bundle);*/
                break;
            case EDIT:
                NoteEditFragment noteEditFragment=new NoteEditFragment();
                fragmentTransaction.add(R.id.fragmentContainer,noteEditFragment,"NOTE_Edit_FRAGMENT");
                setTitle("EDIT NOTE");
                myFragment=noteEditFragment;

                break;
            case CREATE:
                NoteEditFragment noteCreateFragment=new NoteEditFragment();
                fragmentTransaction.add(R.id.fragmentContainer,noteCreateFragment,"NOTE_CREATE_FRAGMENT");
                setTitle("ADD NOTE");
                Bundle bundle=new Bundle();
                bundle.putBoolean(MainActivity.NEW_NOTE_EXTRA,true);
                myFragment=noteCreateFragment;
                myFragment.setArguments(bundle);
                break;

        }
        fragmentTransaction.commit();


            return myFragment;


    }

}
