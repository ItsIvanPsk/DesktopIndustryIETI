import java.io.Serializable;

public class Sensor implements Serializable{
	private String id,units,thresholdlow,thresholdhigh,text;

	public Sensor(String id, String units, String thresholdlow,String thresholdhigh,String text) {
		this.id = id;
		this.units = units;
		this.thresholdlow = thresholdlow;
		this.thresholdhigh= thresholdhigh;
		this.text = text;
	}
	//getters and setters

	public String getId() {
		return id;
	}


	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public String getThresholdlow() {
		return thresholdlow;
	}

	public void setThresholdlow(String thresholdlow) {
		this.thresholdlow = thresholdlow;
	}

	public String getThresholdhigh() {
		return thresholdhigh;
	}

	public void setThresholdhigh(String thresholdhigh) {
		this.thresholdhigh = thresholdhigh;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		return "SS#"+id+"#"+units+"#"+ thresholdlow+"#"+thresholdhigh+"#"+text;
	}
	
	
	
	
}
