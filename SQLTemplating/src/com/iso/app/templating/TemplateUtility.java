package com.iso.app.templating;

import com.iso.app.templating.beans.SQLTemplate;
import com.iso.app.templating.handler.SQLTemplatesHandler;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ivo
 */
public class TemplateUtility {

    private static final String CLASSNAME = TemplateUtility.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASSNAME);

    private static String templatePath = "templates/sqltemplates.xml";
    private static boolean useTestStatements = false;
    private static Map<String, String> statementsMap = new HashMap<String, String>();

    private static TemplateUtility instance;

    private static void init() {
        if (instance == null) {
            LOGGER.info("Initializing Instance");
            instance = new TemplateUtility();
        }
    }

    public TemplateUtility(Builder builder) {
        if (builder.path != null) {
            TemplateUtility.templatePath = builder.path;
        }
        if (builder.useTestStatements) {
            TemplateUtility.useTestStatements = builder.useTestStatements;
        }
        init();
    }

    public TemplateUtility() {
        try {
            File templateFile = new File(templatePath);

            SAXParser parser;
            SAXParserFactory spf = SAXParserFactory.newInstance();
            parser = spf.newSAXParser();

            SQLTemplatesHandler templateHandler = new SQLTemplatesHandler();

            parser.parse(templateFile, templateHandler);

            for (SQLTemplate template : templateHandler.getTemplates()) {
                if (useTestStatements && template.getTestValue() != null) {
                    statementsMap.put(template.getName(), template.getTestValue());
                } else {
                    statementsMap.put(template.getName(), template.getValue());
                }
            }

        } catch (ParserConfigurationException e) {
            LOGGER.log(Level.SEVERE, CLASSNAME, e);
        } catch (SAXException e) {
            LOGGER.log(Level.SEVERE, CLASSNAME, e);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, CLASSNAME, e);
        }
    }

    public static class Builder {

        private String path;
        private boolean useTestStatements = false;

        public Builder setPath(String path) {
            this.path = path;
            return this;
        }

        public Builder setUseTestStatements(boolean useTestStatements) {
            this.useTestStatements = useTestStatements;
            return this;
        }

        public TemplateUtility build() {
            return new TemplateUtility(this);
        }
    }

    public static String getSQLStatement(String name) {
        init();
        return statementsMap.get(name);
    }
}
