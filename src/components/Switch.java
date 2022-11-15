package src.components;
import java.io.Serializable;

public class Switch implements Serializable{
	private String id,def,text;

	public Switch(String id, String def, String text) {
		this.id = id;
		this.def = def;
		this.text = text;
	}
	//getters and setters
	public String getId() {
		return id;
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
		return "SW#"+id+"#"+def+"#"+text;
	}
	
	
	
}
