package com.university.shell;

import com.university.service.MainService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Shell {
    private final static Logger logger = LogManager.getLogger(Shell.class);
    private final static String EXCEPTION = "wrong input parameters";
    private final MainService service;

    public Shell() {
        service = new MainService();
    }

        public String execute(String line) {
        if(line == null) {
            logger.warn(EXCEPTION);
            return "";
        }
        List<String> list = ShellUtils.lineToWords(line);
        return execute(list);
    }

    public String execute(List<String> list) {
        if(list == null || list.size() == 0) {
            logger.warn(EXCEPTION);
            return "";
        }

        switch (list.get(0)){
            case "clang":{
                if(list.size() != 3) {
                    logger.warn(EXCEPTION);
                    return "";
                }
                String city = list.get(1);
                String lang = list.get(2);
                return service.getPeopleWithParams(city, lang);
            }

            case "lang":{
                if(list.size() != 2) {
                    logger.warn(EXCEPTION);
                    return "";
                }
                String lang = list.get(1);
                return service.getCitiesWithLanguage(lang);
            }

            case "popul":
                if(list.size() != 2) {
                    logger.warn(EXCEPTION);
                    return "";
                }
                Long population = Long.parseLong(list.get(1));
                return service.getCitiesWithPopulation(population);
            case "old":
                if(list.size() != 1) {
                    logger.warn(EXCEPTION);
                    return "";
                }
                return service.getOldestPeople();
            default:
                logger.warn(EXCEPTION);
        }

        return EXCEPTION;
    }
}
