package com.areab.fk2selects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;

public class Main {

    final private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws FileNotFoundException {
//        logger.info("args: " + String.join(", ", args));
//
//        if (args.length == 0) {
//            throw new IllegalArgumentException("Path to setting.yaml is required.");
//        }

        try {
            new Executor().execute();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
