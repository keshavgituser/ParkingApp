package com.practice.parkapp.config;

import org.springframework.core.convert.converter.Converter;

import com.practice.parkapp.dao.SLOTNUMBERS;
import com.practice.parkapp.dao.SLOTSTATUS;

public class StringToSLOTNUMBERSEnumConverter implements Converter<String, SLOTNUMBERS> {

	@Override
	public SLOTNUMBERS convert(String source) {
		// TODO Auto-generated method stub
		return SLOTNUMBERS.valueOf(source.toUpperCase());
	}

}
