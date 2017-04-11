package com.iso.app.templating.handler;

import com.iso.app.templating.beans.SQLTemplate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 * 
 * @author ivo
 */
public class SQLTemplatesHandler extends DefaultHandler {
	private final static String SQLTEMPLATE_ROOT_TAG = "sqltemplates";
	private final static String SQLTEMPLATE_TAG = "sqltemplate";
	private final static String SQLTEMPLATE_TEST_TAG = "sqltemplatetest";
	private final static String NAME_ATTRIBUTE = "name";
	
	private SQLTemplate sqlTemplate = null;
	private List<SQLTemplate> templates = new ArrayList<SQLTemplate>();

	private final Stack<String> tagsStack = new Stack<String>();
	private final StringBuilder tempVal = new StringBuilder();
	private final StringBuilder tempTestVal = new StringBuilder();

        @Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) {
		String parentTag = peekTag();
		pushTag(qName);

		if(qName.equalsIgnoreCase(SQLTEMPLATE_TEST_TAG) && parentTag.equalsIgnoreCase(SQLTEMPLATE_TAG)){
			tempTestVal.setLength(0);
		}
		else if (qName.equalsIgnoreCase(SQLTEMPLATE_TAG) && parentTag.equalsIgnoreCase(SQLTEMPLATE_ROOT_TAG)) {
			tempVal.setLength(0);
			sqlTemplate = new SQLTemplate();
			if (attributes != null) {
				sqlTemplate.setName(attributes.getValue(NAME_ATTRIBUTE));
			}
		}

	}

        @Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		String tag = peekTag();
		if (!qName.equals(tag)) {
			throw new InternalError();
		}

		popTag();

		if (qName.equalsIgnoreCase(SQLTEMPLATE_TEST_TAG) && containsTag(SQLTEMPLATE_TAG)) {
			sqlTemplate.setTestValue(tempTestVal.toString().trim());
		}
		else if (qName.equalsIgnoreCase(SQLTEMPLATE_TAG) && containsTag(SQLTEMPLATE_ROOT_TAG)) {
			sqlTemplate.setValue(tempVal.toString().trim());
			templates.add(sqlTemplate);
		}

	}

        @Override
	public void characters(char ch[], int start, int length) {
		String tag = peekTag();
		if(tag.equalsIgnoreCase(SQLTEMPLATE_TEST_TAG)){
			tempTestVal.append(ch, start, length);
		}
		else if(tag.equalsIgnoreCase(SQLTEMPLATE_TAG)){
			tempVal.append(ch, start, length);
		}
	}

	private void pushTag(String tag) {
		tagsStack.push(tag);
	}

	private String popTag() {
		return tagsStack.pop();
	}

	private boolean containsTag(String tag) {
		if (!tagsStack.isEmpty()) {
			return Arrays.asList(tagsStack.toArray()).contains(tag);
		} else {
			return false;
		}
	}

	private String peekTag() {
		if (!tagsStack.isEmpty()) {
			return tagsStack.peek();
		} else {
			return "";
		}
	}
	
	public List<SQLTemplate> getTemplates() {
		return templates;
	}

	public void setTemplates(List<SQLTemplate> templates) {
		this.templates = templates;
	}
}
