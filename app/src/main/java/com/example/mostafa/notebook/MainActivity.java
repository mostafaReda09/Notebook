package com.example.mostafa.notebook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    public static final String NOTE_TITTLE_EXTRA="com.example.mostafa.notebook.note tittle";
    public static final String NOTE_BODY_EXTRA="com.example.mostafa.notebook.note body";
    public static final String NOTE_IMAGE_EXTRA="com.example.mostafa.notebook.note image";
    public static final String NOTE_FRAGMENT_TO_LOAD_EXTRA="com.example.mostafa.notebook.fragment to load";
    public static final String NEW_NOTE_EXTRA="com.example.mostafa.notebook.new note extra";
    public static final String NOTE_ID_EXTRA="com.example.mostafa.notebook.new note id extra";

    public enum FragmentToLaunch{VIEW,EDIT,CREATE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loadPreferences();

    /*    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent=new Intent(this,AppPreferences.class);
            startActivity(intent);

            return true;
        }else if(id==R.id.add_note){
            Intent intent=new Intent(this,NoteDetailActivity.class);
            intent.putExtra(NOTE_FRAGMENT_TO_LOAD_EXTRA,FragmentToLaunch.CREATE);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    private void loadPreferences(){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        boolean dark_background=sharedPreferences.getBoolean("background_color",false);
        if(dark_background) {
            LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_layout);
            mainLayout.setBackgroundColor(Color.parseColor("#3c3f41"));
        }
        String notebookTittle=sharedPreferences.getString("tittle","Notebook");
        setTitle(notebookTittle);
    }
}
