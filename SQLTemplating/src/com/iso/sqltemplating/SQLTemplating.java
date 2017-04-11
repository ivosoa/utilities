/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.sqltemplating;

import com.iso.app.templating.TemplateUtility;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ivo
 */
public class SQLTemplating {
    private static final String CLASSNAME = SQLTemplating.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //new TemplateUtility.Builder().setUseTestStatements(true).build();
        LOGGER.log(Level.INFO, "Template 1: {0}", TemplateUtility.getSQLStatement("TEMPLATE_1"));
        LOGGER.log(Level.INFO, "Template 2: {0}", TemplateUtility.getSQLStatement("TEMPLATE_2"));
        LOGGER.log(Level.INFO, "Template 3: {0}", TemplateUtility.getSQLStatement("TEMPLATE_3"));
        LOGGER.log(Level.INFO, "Template 4: {0}", TemplateUtility.getSQLStatement("TEMPLATE_4"));
        
    }

}
