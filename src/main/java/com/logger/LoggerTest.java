package com.logger;

import org.slf4j.LoggerFactory;


/**
 * Created by pgwt on 2017/3/21.
 */
public class LoggerTest {

    static public org.slf4j.Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    public void configurationLog4j() throws Exception {
        logger.trace("trace 信息");
        logger.debug("debug 信息");
        logger.info("info 信息");
        logger.warn("warn 信息");
        logger.error("error 信息");
    }


    public static void main(String[] args) {



    }


}
