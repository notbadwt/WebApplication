package com.application.controller;

import com.logger.LoggerTest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by pgwt on 2017/3/21.
 */
@RestController
public class TestController {


    @RequestMapping({"/log4jTest"})
    public String log4jTest() throws Exception {
        LoggerTest loggerTest = new LoggerTest();
        loggerTest.configurationLog4j();
        return "1";
    }

}
