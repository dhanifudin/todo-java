/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhanifudin.todo.percobaan2.lib;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author dhanifudin
 */
public class TextUtil {

	public static String join(Object[] values, String optional, String delimiter) {
		StringBuilder sb = new StringBuilder();
		String loopDelimiter = "";
		for (Object o : values) {
			sb.append(loopDelimiter);
			sb.append(o).append(optional);
			loopDelimiter = delimiter;
		}
		return sb.toString();
	}

	public static String join(Collection<String> values, String delimiter) {
		return TextUtil.join(values.toArray(), "", delimiter);
	}

	public static String join(Collection<String> values, String optional, String delimiter) {
		return TextUtil.join(values.toArray(), optional, delimiter);
	}

	public static String join(Object[] values, String delimiter) {
		return TextUtil.join(values, "", delimiter);
	}
}
