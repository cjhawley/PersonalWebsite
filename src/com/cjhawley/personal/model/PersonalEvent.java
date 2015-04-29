package com.cjhawley.personal.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Have a model for a personal event. This model can serialize to json.
 * 
 * @author cjhawley
 *
 */
@XmlRootElement
public class PersonalEvent implements Serializable {
	/**
	 * auto generated UID 
	 */
	private static final long serialVersionUID = 3131724447231596147L;
	
	private String title;
	private Date date;
	private String description;
	
	public PersonalEvent() {}
	
	public PersonalEvent(String title, Date date, String description) {
		
		if (title == null || date == null || description == null) {
			throw new IllegalArgumentException("Personal Event must have a title, date, and description");
		}
		
		this.title = title;
		this.date = date;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + title.hashCode();
		result = 31 * result + date.hashCode();
		result = 31 * result + description.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(Object o) {
		if (! (o instanceof PersonalEvent)) {
			return false;
		}
		
		PersonalEvent other = (PersonalEvent)o;

		return title.equals(other.title) && 
				date.equals(other.date) &&
				description.equals(other.description);
	}
}
