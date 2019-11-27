package com.cjhawley.personal;

/**
 * Created by cjhawley on 12/23/16.
 */
public class ServiceRunner {

    public static void main(final String[] args) throws Exception {
        PersonalWebsiteService personalWebsiteService = new PersonalWebsiteService(args);
        personalWebsiteService.init();
    }
}
