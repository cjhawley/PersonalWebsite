package com.cjhawley.personal.model;

import io.norberg.automatter.AutoMatter;

import java.io.Serializable;
import java.util.Date;

/**
 * Have a model for a personal event. This model can serialize to json.
 * 
 * @author cjhawley
 *
 */
@AutoMatter
public interface PersonalEvent extends Serializable {

	/**
	 * @return Title of the personal event
     */
	String title();

	/**
	 * @return Date of the personal event
     */
	Date date();

	/**
	 * @return Description of the personal event
     */
	String description();
}
