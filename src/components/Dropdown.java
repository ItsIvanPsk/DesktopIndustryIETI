package src.components;

import java.util.ArrayList;

import src.utils.ServerUtils;

public class Dropdown extends Controls implements ServerUtils{
    private int id, def;
    private String label, blockName;
    private ArrayList<String> listOpt;

    //Constructor
    public Dropdown(){  }

    public Dropdown(int id, String blockName, int def, String label, ArrayList<String> listOpt) {
        super();
        this.id = id;
        this.blockName = blockName;
        this.def = def;

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
    	return
        "DD" + componentAttr
        + id + componentAttr
        + blockName + componentAttr
        + def + componentAttr
        + label + componentAttr
        + listOpt.toString();
    }

}