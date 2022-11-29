package src.components;

import src.utils.ServerUtils;

public class Switch extends Controls implements ServerUtils{
    private int id;
    private String def,text, blockName;

    public Switch(int id, String blockName, String def, String text) {
        this.id = id;
        this.blockName = blockName;
        this.def = def;
        this.text = text;
    }
    public Switch() {
    }
    //getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBlockId() {
        return blockName;
    }

    public void setBlockId(String blockName) {
        this.blockName = blockName;
    }

    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return 
        "SW" + componentAttr
        + id + componentAttr
        + blockName + componentAttr
        + def + componentAttr
        + text;
    }

}