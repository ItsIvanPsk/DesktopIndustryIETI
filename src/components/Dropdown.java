package src.components;

import java.util.ArrayList;

public class Dropdown extends Controls{
    private int id, def;
    private String label, blockName;
    private ArrayList<String> listOpt;

    //Constructor
    public Dropdown(){  }

    public Dropdown(int id, int def, String blockName, String label, ArrayList<String> listOpt) {
        super();
        this.id = id;
        this.def = def;
        this.blockName = blockName;
        this.label=label;
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

    public String getBlockId() {
        return blockName;
    }

    public void setBlockId(String blockName) {
        this.blockName = blockName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ArrayList<String> getListOpt() {
        return listOpt;
    }

    public void setListOpt(ArrayList<String> listOpt) {
        this.listOpt = listOpt;
    }

    //ToString
    public String toString() {
    	return "DD#" + id + "#" + blockName + "#" + def + "#" + label + "#" + listOpt.toString();
    }

}