package com.danozzo.paymybuddy.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * MainController get login page
 */
@Controller
@RequestMapping
public class MainController {
    private static final Logger logger = LogManager.getLogger("MainController");

    /**
     * endpoint to show logging page
     * @return login page
     */
    @GetMapping("/login")
    public String login(){
        logger.info("get login page");
        return "login";
    }
    /**
     * endpoint to show index page
     * @return index=login page
     */
    @GetMapping("/")
    public String home() {
        logger.info("get index page");
        return "index";
    }







}
