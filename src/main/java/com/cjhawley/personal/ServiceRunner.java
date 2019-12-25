package com.cjhawley.personal;

public class ServiceRunner {

    /**
     * Main entry point into running the service
     * @param args arguments passed into the execution of this program
     * @throws Exception Throws on unsuccessful (and unexpected) init and start of the program
     */
    public static void main(final String[] args) throws Exception {
        PersonalWebsiteService personalWebsiteService = new PersonalWebsiteService(args);
        personalWebsiteService.init();
    }
}
