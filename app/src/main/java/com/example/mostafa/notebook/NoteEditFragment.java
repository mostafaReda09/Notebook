package com.example.mostafa.notebook;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteEditFragment extends Fragment {

    private Note.Category categoryType;
    private  int category_image;
    private ImageButton image_edit;
    private AlertDialog categoryDialog,confirmDialog;
    private boolean newNote=false;
    private EditText tittle_edit,body_edit;
    private static final String MODIFIED_CATEGORY="modified_category";
    private static final String MODIFIED_IMAGE="modified_image";
    private int noteId=0;
    public NoteEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


      if(savedInstanceState!=null){
           category_image=savedInstanceState.getInt(MODIFIED_IMAGE);
          categoryType=(Note.Category)savedInstanceState.getSerializable(MODIFIED_CATEGORY);
       }



        Bundle bundle=getArguments();
        if (bundle!=null){
            newNote=bundle.getBoolean(MainActivity.NEW_NOTE_EXTRA,false);
        }


        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_note_edit, container, false);

        image_edit=(ImageButton)view.findViewById(R.id.edit_image);
        tittle_edit=(EditText)view.findViewById(R.id.tittle_edit);
        body_edit=(EditText)view.findViewById(R.id.body_edit);
        Button save_button=(Button)view.findViewById(R.id.save_note);
      if(categoryType!=null)
       {

           image_edit.setImageResource(category_image);

        }else if(!newNote){
          category_image = getActivity().getIntent().getExtras().getInt(MainActivity.NOTE_IMAGE_EXTRA);
          image_edit.setImageResource(category_image);
      }





        tittle_edit.setText(getActivity().getIntent().getExtras().getString(MainActivity.NOTE_TITTLE_EXTRA,""));
        body_edit.setText(getActivity().getIntent().getExtras().getString(MainActivity.NOTE_BODY_EXTRA,""));
        buildCategoryDialog();
        buildConfirmDialog();
        image_edit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                categoryDialog.show();
            }
        });

        save_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                confirmDialog.show();
            }
        });
        noteId=getActivity().getIntent().getExtras().getInt(MainActivity.NOTE_ID_EXTRA);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(MODIFIED_CATEGORY,categoryType);
        outState.putInt(MODIFIED_IMAGE,category_image);
    }

    public void buildCategoryDialog(){
            String [] categories={"personal","technical","quote","finance"};
        AlertDialog.Builder cBuilder=new AlertDialog.Builder(getActivity());
        cBuilder.setTitle("choose Note type");
        cBuilder.setSingleChoiceItems(categories,0,new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int item) {
                categoryDialog.cancel();
                switch (item){
                    case 0:
                        categoryType=Note.Category.PERSONAL;
                        image_edit.setImageResource(R.drawable.p);
                        category_image=R.drawable.p;


                        break;
                    case 1:
                        categoryType=Note.Category.TECHNICAL;
                        image_edit.setImageResource(R.drawable.t);
                        category_image=R.drawable.t;

                        break;
                    case 2:
                        categoryType=Note.Category.QUOTE;
                        image_edit.setImageResource(R.drawable.q);
                        category_image=R.drawable.q;

                        break;
                    case 3:
                        categoryType=Note.Category.FINANCE;
                        image_edit.setImageResource(R.drawable.f);
                        category_image=R.drawable.f;

                        break;
                }
            }
        });
        categoryDialog=cBuilder.create();
    }
        public void buildConfirmDialog(){

            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
            builder.setTitle("Are You Sure?");
            builder.setMessage("Are You Sure You Want To Save The Note?");
            builder.setPositiveButton("Confirm",new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    NotebookDbAdapter notebookDbAdapter=new NotebookDbAdapter(getActivity().getBaseContext());
                    notebookDbAdapter.open();
                   if(newNote){
                       notebookDbAdapter.addNote(tittle_edit.getText()+"",body_edit.getText()+"",
                             (categoryType==null)?Note.Category.PERSONAL:categoryType);


                   }else{
                        notebookDbAdapter.updateNote(noteId,tittle_edit.getText()+"",body_edit.getText()+"",
                                categoryType);
                   }
                   notebookDbAdapter.close();
                    Intent intent=new Intent(getActivity(),MainActivity.class);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            confirmDialog=builder.create();
        }
}
