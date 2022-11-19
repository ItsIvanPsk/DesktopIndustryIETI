package src.components;

import java.util.ArrayList;

public class Dropdown {
    private int id, def;
    private String text;
    private ArrayList<String> listOpt;
    
    //Constructor
    public Dropdown(int id, int def, String text, ArrayList<String> listOpt) {
        super();
        this.id = id;
        this.def = def;
        this.text=text;
        this.listOpt = listOpt;
    }
    
    //Setters and Getters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<String> getListOpt() {
        return listOpt;
    }

    public void setListOpt(ArrayList<String> listOpt) {
        this.listOpt = listOpt;
    }

    //ToString
    public String toString() {
    	return "DD#" + id + "#" + def + "#" + text + "#" + listOpt.toString();
    }
    
    
    
}