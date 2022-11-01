/**
 * 
 */
package com.practice.parkapp.config;

import org.springframework.core.convert.converter.Converter;

import com.practice.parkapp.dao.SLOTTYPE;

/**
 * @author kmahendr
 *
 */
public class StringToSLOTTYPEEnumConverter implements Converter<String, SLOTTYPE	> {

	@Override
	public SLOTTYPE convert(String source) {
		// TODO Auto-generated method stub
		return SLOTTYPE.valueOf(source.toUpperCase());
	}
	
	

}
