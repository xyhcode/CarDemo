package com.util;

import java.util.List;

import net.sf.json.JSONArray;
/**
 * json转换成字符串
 * @author 林沐
 *
 */
public class JsonTool {
	public static String jsontool(List list) {
		//把list转换成字符串
		JSONArray js=JSONArray.fromObject(list);
		return js.toString();
	}
}
