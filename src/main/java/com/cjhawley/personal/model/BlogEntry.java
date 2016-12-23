package com.cjhawley.personal.model;

import io.norberg.automatter.AutoMatter;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by cjhawley on 11/14/16.
 */
@AutoMatter
public interface BlogEntry extends Serializable {
    String title();

    String shortDescription();

    String content();

    Date date();
}
