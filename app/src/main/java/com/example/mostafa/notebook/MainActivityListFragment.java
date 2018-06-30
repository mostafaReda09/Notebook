package com.example.mostafa.notebook;


import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.util.ArrayList;

import static com.example.mostafa.notebook.MainActivity.FragmentToLaunch.VIEW;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityListFragment extends ListFragment {
 private ArrayList<Note>notes;
 private NoteAdapter noteAdapter;


 @Override
 public void onCreate(@Nullable Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
 NotebookDbAdapter notebookDbAdapter=new NotebookDbAdapter(getActivity().getBaseContext());
  notebookDbAdapter.open();
  notes=notebookDbAdapter.getAllNotes();
  notebookDbAdapter.close();
  noteAdapter=new NoteAdapter(getActivity(),notes);
  setListAdapter(noteAdapter);

 }

 @Override
 public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
  super.onCreateContextMenu(menu, v, menuInfo);
  MenuInflater menuInflater=getActivity().getMenuInflater();
  menuInflater.inflate(R.menu.long_press_menue,menu);
 }

 @Override
 public void onListItemClick(ListView l, View v, int position, long id) {
  super.onListItemClick(l, v, position, id);
  launchNoteDetailActivity(MainActivity.FragmentToLaunch.VIEW,position);
 }
 private void launchNoteDetailActivity(MainActivity.FragmentToLaunch fragment,int pos)
 {
  Note note=(Note)getListAdapter().getItem(pos);
  Intent intent=new Intent(getActivity(),NoteDetailActivity.class);
  intent.putExtra(MainActivity.NOTE_TITTLE_EXTRA,note.getTittle());
  intent.putExtra(MainActivity.NOTE_BODY_EXTRA,note.getBody());
  intent.putExtra(MainActivity.NOTE_IMAGE_EXTRA,note.getImage());
  intent.putExtra(MainActivity.NOTE_ID_EXTRA,note.getId());
  switch (fragment) {
   case VIEW:
    intent.putExtra(MainActivity.NOTE_FRAGMENT_TO_LOAD_EXTRA,MainActivity.FragmentToLaunch.VIEW);
    break;
   case EDIT:
    intent.putExtra(MainActivity.NOTE_FRAGMENT_TO_LOAD_EXTRA,MainActivity.FragmentToLaunch.EDIT);
    break;
  }
  startActivity(intent);

 }

 @Override
 public boolean onContextItemSelected(MenuItem item) {
  AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
  Note note=(Note) getListAdapter().getItem(info.position);

  switch (item.getItemId()){
   case R.id.edit:
    launchNoteDetailActivity(MainActivity.FragmentToLaunch.EDIT,info.position);
    return true;
   case R.id.delete:
    NotebookDbAdapter notebookDbAdapter=new NotebookDbAdapter(getActivity().getBaseContext());
    notebookDbAdapter.open();
    notebookDbAdapter.deleteNote(note.getId());
    notes.clear();
    notes.addAll(notebookDbAdapter.getAllNotes());
    noteAdapter.notifyDataSetChanged();
    notebookDbAdapter.close();

  }

  return super.onContextItemSelected(item);
 }

 @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        registerForContextMenu(getListView());
    }
}


