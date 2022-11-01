package com.practice.parkapp.config;

import org.springframework.core.convert.converter.Converter;

import com.practice.parkapp.dao.SLOTNUMBERS;
import com.practice.parkapp.dao.SLOTSTATUS;

public class StringToSLOTSTATUSEnumConverter implements Converter<String, SLOTSTATUS> {

	@Override
	public SLOTSTATUS convert(String source) {
		// TODO Auto-generated method stub
		return SLOTSTATUS.valueOf(source.toUpperCase());
	}

}
