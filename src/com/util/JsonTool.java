package com.util;

import java.util.List;

import net.sf.json.JSONArray;
/**
 * jsonת�����ַ���
 * @author ����
 *
 */
public class JsonTool {
	public static String jsontool(List list) {
		//��listת�����ַ���
		JSONArray js=JSONArray.fromObject(list);
		return js.toString();
	}
}
