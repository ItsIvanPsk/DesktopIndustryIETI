package src.components;

import java.util.ArrayList;

public class Dropdown {
    private String id, def;
    private ArrayList<String> listOpt;
    
    //Constructor
    public Dropdown(String id, String def, ArrayList<String> listOpt) {
        super();
        this.id = id;
        this.def = def;
        this.listOpt = listOpt;
    }
    
    //Setters and Getters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }

    public ArrayList<String> getListOpt() {
        return listOpt;
    }

    public void setListOpt(ArrayList<String> listOpt) {
        this.listOpt = listOpt;
    }

    //ToString
    public String toString() {
    	return "DD#" + id + "#" + def + "#" + listOpt.toString();
    }
    
    
    
}