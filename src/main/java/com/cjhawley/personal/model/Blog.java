package com.cjhawley.personal.model;

import io.norberg.automatter.AutoMatter;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by cjhawley on 11/14/16.
 */
@AutoMatter
public interface Blog extends Serializable {

    String name();

    String description();

    Set<BlogEntry> blogEntrySet();
}
