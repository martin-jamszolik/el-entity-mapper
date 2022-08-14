/*
 * Copyright 2009-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.viablespark.example.ui;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Format {
	public static String currency(BigDecimal amount){
		if(amount == null){
			amount = BigDecimal.ZERO;
		}
		
		return new DecimalFormat("$#,##0.00;-$#,##0.00").format(amount);
	}
	
	public static String currencyNoCents(BigDecimal amount){
		if(amount == null){
			amount = BigDecimal.ZERO;
		}
		
		return new DecimalFormat("$#,##0;-$#,##0").format(amount);
	}
	
	public static Date getDate(String date){
		try {
			return new SimpleDateFormat("MM/dd/yyyy").parse(date);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Invalid date " + date + ". Expected format is MM/dd/yyyy.");
		}
	}
	
	
	public static String getDate(Date date) {
		if( date == null )
			return "";
		SimpleDateFormat simpledateformat = new SimpleDateFormat("MM/dd/yyyy");
		return simpledateformat.format(date);
	}

    public static String stripNonAsciiCharacters(String in ){
			return in.replaceAll("[^\\x20-\\x7E]"," ");
	}
	
	
}
