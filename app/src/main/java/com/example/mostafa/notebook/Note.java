package com.example.mostafa.notebook;

/**
 * Created by Mostafa on 6/20/2018.
 */

public class Note {
    private String tittle;
    private String body;
    private int image;
    private Category category;
    private int note_id;
    public enum Category{PERSONAL,TECHNICAL,QUOTE,FINANCE};
    public Note(String tittle, String body, Category category,int note_id) {
        this.tittle = tittle;
        this.body = body;
        this.category=category;
        this.note_id=note_id;
    }


    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getImage() {

        switch (category){
            case PERSONAL:
                image= R.drawable.p;
                  break;
            case TECHNICAL:
                image= R.drawable.t;
                  break;
            case QUOTE:
                image= R.drawable.q;
                 break;
            case FINANCE:
                image= R.drawable.f;
                  break;
            default:image=0;
        }
            return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

public int getId(){
    return note_id;
}



}
