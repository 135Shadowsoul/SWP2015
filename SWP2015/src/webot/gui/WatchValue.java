package webot.gui;

import javafx.beans.property.SimpleStringProperty;

public class WatchValue 
{
	private String value;
	private String name;

	public WatchValue(String name, String value)
	{
		this.name=name;
		this.value=value;
	}
	
	public String getNames()
	{
		return this.name;
	}
	
	public String getValues()
	{
		return this.value;
	}
	
	public SimpleStringProperty getNameProperty()
	{
		return new SimpleStringProperty(this.name);
	}
	
	public SimpleStringProperty getValueProperty()
	{
		return new SimpleStringProperty(this.value);
	}
}
