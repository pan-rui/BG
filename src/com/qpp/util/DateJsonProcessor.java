package com.qpp.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class DateJsonProcessor implements JsonValueProcessor {

	public static final String Default_DATE_PATTERN = "yyyy-MM-dd hh:mm:ss";
	private DateFormat dateFormat;

	public DateJsonProcessor() {
		dateFormat = new SimpleDateFormat(Default_DATE_PATTERN);
	}

	public DateJsonProcessor(String format) {
		try {
			dateFormat = new SimpleDateFormat(format);
		} catch (Exception e) {
			dateFormat = new SimpleDateFormat(Default_DATE_PATTERN);
		}
	}

	@Override
	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		return process(value);
	}

	@Override
	public Object processObjectValue(String key, Object value,
			JsonConfig jsonConfig) {
		return process(value);
	}

	private Object process(Object value) {
		if (value == null) {
			return "";
		}
		return dateFormat.format((Date) value);
	}

}
