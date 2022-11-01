/**
 * 
 */
package com.practice.parkapp.config;

import org.springframework.core.convert.converter.Converter;

import com.practice.parkapp.dao.USERTYPE;

/**
 * @author kmahendr
 *
 */
public class StringToUSERTYPEEnumConverter implements Converter<String,USERTYPE> {

	@Override
	public USERTYPE convert(String source) {
		// TODO Auto-generated method stub
		return USERTYPE.valueOf(source.toUpperCase());
	}

}
