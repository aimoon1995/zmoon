package com.project_study.my.common.utils;


import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class JacksonJsonUtil {
	
	private static ObjectMapper obMapper;

	protected static ObjectMapper getObMapperInstance() {

		if (obMapper == null) {
			obMapper = new ObjectMapper();
			
			// 设置默认日期格式
			obMapper.setDateFormat(new JacksonDateFormatter());
			// 提供其它默认设置 支持单引号
			obMapper.configure(Feature.ALLOW_SINGLE_QUOTES,true);
			//支持fieldName 不带引号
			obMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES,true);
		}
		return obMapper;
	}
	
	public static ObjectMapper createObMapperInstance(String dataFormat) {
		ObjectMapper obMap = new ObjectMapper();
		// 设置默认日期格式
		obMap.setDateFormat(new SimpleDateFormat(dataFormat));
		// 提供其它默认设置 支持单引号
		obMap.configure(Feature.ALLOW_SINGLE_QUOTES,true);
		//支持fieldName 不带引号
		obMap.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES,true);
		return obMap;
	}
	
	public static <T> T parse(String content, Class<T> t){
		T result = null;
		try {
			result = getObMapperInstance().readValue(content, t);
		} catch (Exception e) {
			log.error("parse by content exception", e);
		} 
		return result;
	}
	public static <T> T parse(ObjectMapper ObjectMapper, String content, Class<T> t){
		T result = null;
		try {
			result = ObjectMapper.readValue(content, t);
		} catch (Exception e) {
			log.error("parse by ObjectMapper,content exception", e);
		} 
		return result;
	}

	public static void filterBeanTree(JsonNode jsonNode, String... excludedProperties) {
        filterBeanTreeRecursive(jsonNode, Arrays.asList(excludedProperties));
    }
	
	public static ArrayNode filterArrayNode(ArrayNode arry, String fieldName, String changeValue, String mustAddValue){
		if(arry != null && StringUtil.isNotEmpty(changeValue)){
			ArrayNode result = arrayNode();
			for(int i = 0; i < arry.size(); i++){
				JsonNode node = arry.get(i);
				if(node.isObject()){
					ObjectNode obj = (ObjectNode)node;
					boolean change = obj.has(fieldName)&&obj.get(fieldName).isTextual()&& changeValue.equals(obj.get(fieldName).asText());
					boolean mustAdd = StringUtil.isNotEmpty(mustAddValue) && obj.has(fieldName)&&obj.get(fieldName).isTextual()&& mustAddValue.equals(obj.get(fieldName).asText());
					if(change || mustAdd){
						result.add(obj);
					}
				}
			}
			return result;
		}
		return arry;
	}
	
	public static boolean mergeArrayRow(ArrayNode parent, JsonNode json, String sameFieldName){
		if(parent == null || json == null || !json.isObject() || !json.has(sameFieldName)){
			return false;
		}
		String mergeSameFieldValue = StringUtil.getOrElse(json.get(sameFieldName).asText());
		for(int i = 0; i < parent.size(); i++){
			JsonNode node = parent.get(i);
			if(node.isObject()){
				ObjectNode obj = (ObjectNode)node;
				ObjectNode change = (ObjectNode)json;
				if(obj.has(sameFieldName)){
					String sameFieldValue = StringUtil.getOrElse(obj.get(sameFieldName).asText());
					if(mergeSameFieldValue.equals(sameFieldValue)){
						Iterator<String> fields = change.fieldNames();
						while(fields.hasNext()){
							String fieldName = fields.next();
							obj.set(fieldName, change.get(fieldName));
						}
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static boolean hasRowInArray(ArrayNode parent, String fieldName, String fieldValue){
		if(parent == null || StringUtil.isEmpty(fieldName)){
			return false;
		}
		String compareValue = StringUtil.getOrElse(fieldValue);
		for(int i = 0; i < parent.size(); i++){
			JsonNode node = parent.get(i);
			if(node.isObject()){
				ObjectNode obj = (ObjectNode)node;
				if(obj.has(fieldName)){
					String sameFieldValue = StringUtil.getOrElse(obj.get(fieldName).asText());
					if(compareValue.equals(sameFieldValue)){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static boolean removeArrayRow(ArrayNode parent, JsonNode json, String sameFieldName){
		if(parent == null || json == null || !json.isObject() || !json.has(sameFieldName)){
			return false;
		}
		String mergeSameFieldValue = StringUtil.getOrElse(json.get(sameFieldName).asText());
		for(int i = 0; i < parent.size(); i++){
			JsonNode node = parent.get(i);
			if(node.isObject()){
				ObjectNode obj = (ObjectNode)node;
				
				if(obj.has(sameFieldName)){
					String sameFieldValue = StringUtil.getOrElse(obj.get(sameFieldName).asText());
					if(mergeSameFieldValue.equals(sameFieldValue)){
						parent.remove(i);
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static JsonNode mergeArray(JsonNode oldJson, JsonNode newJson, String fieldName, String changeValue, String mustAddValue, String sameFieldName, boolean forceNew){
		if(oldJson == null || !oldJson.isArray()){
			return newJson;
		}
		if(newJson == null || !newJson.isArray()){
			return oldJson;
		}
		ArrayNode old = (ArrayNode)oldJson;
		ArrayNode newNode = (ArrayNode)newJson;
		Map<String, ObjectNode> changeNode = new HashMap<String, ObjectNode>();
		Map<String, ObjectNode> allNewNode = new HashMap<String, ObjectNode>();
		for(int i = 0; i < newNode.size(); i++){
			JsonNode node = newNode.get(i);
			if(node.isObject()){
				ObjectNode obj = (ObjectNode)node;
				String sameFieldValue = obj.get(sameFieldName).asText();
				allNewNode.put(sameFieldValue, obj);
				if(obj.has(sameFieldName)){
					if(obj.has(fieldName) && obj.get(fieldName).isTextual()&& changeValue.equals(obj.get(fieldName).asText())){
						changeNode.put(sameFieldValue, obj);
					}
				}
			}
		}
		ArrayNode result = arrayNode();
		for(int j=0; j < old.size(); j++){
			JsonNode node = old.get(j);
			String sameFieldValue = node.get(sameFieldName).asText();
			ObjectNode change = changeNode.get(sameFieldValue);
			if(change!=null){
				result.add(change);
			}else{
				if(!(!allNewNode.containsKey(sameFieldValue) && forceNew)){
					result.add(node);
				}
			}
			if(allNewNode.containsKey(sameFieldValue)){
				allNewNode.remove(sameFieldValue);
			}
		}
		Iterator<String> keyIterator = allNewNode.keySet().iterator();
		while(keyIterator.hasNext()){
			ObjectNode node = allNewNode.get(keyIterator.next());
			boolean mustAdd = StringUtil.isNotEmpty(mustAddValue) &&
					node.has(fieldName) && node.get(fieldName).isTextual()&& mustAddValue.equals(node.get(fieldName).asText());
			if(forceNew || mustAdd){
				result.add(node);
			}
		}
		return result;
	}
	
	public static JsonNode mergeArray(JsonNode oldJson, JsonNode newJson, String sameFieldName, boolean forceNew){
		if(oldJson == null || !oldJson.isArray()){
			return newJson;
		}
		if(newJson == null || !newJson.isArray()){
			return oldJson;
		}
		ArrayNode old = (ArrayNode)oldJson;
		ArrayNode newNode = (ArrayNode)newJson;
		Map<String, ObjectNode> changeNode = new HashMap<String, ObjectNode>();
		Map<String, ObjectNode> allNewNode = new HashMap<String, ObjectNode>();
		for(int i = 0; i < newNode.size(); i++){
			JsonNode node = newNode.get(i);
			if(node.isObject()){
				ObjectNode obj = (ObjectNode)node;
				String sameFieldValue = obj.get(sameFieldName).asText();
				allNewNode.put(sameFieldValue, obj);
				if(obj.has(sameFieldName)){
					changeNode.put(sameFieldValue, obj);
				}
			}
		}
		ArrayNode result = arrayNode();
		for(int j=0; j < old.size(); j++){
			JsonNode node = old.get(j);
			String sameFieldValue = node.get(sameFieldName).asText();
			ObjectNode change = changeNode.get(sameFieldValue);
			if(change!=null){
				result.add(change);
			}else{
				if(!(!allNewNode.containsKey(sameFieldValue) && forceNew)){
					result.add(node);
				}
			}
			if(allNewNode.containsKey(sameFieldValue)){
				allNewNode.remove(sameFieldValue);
			}
		}
		if(forceNew){
			Iterator<String> keyIterator = allNewNode.keySet().iterator();
			while(keyIterator.hasNext()){
				ObjectNode node = allNewNode.get(keyIterator.next());
				result.add(node);
			}
		}
		return result;
	}

    public static void filterBeanTreeRecursive(JsonNode tree,
                                               List<String> excludedProperties) {
        if(tree != null){
        	if(tree.isObject()){
        		((ObjectNode)tree).remove(excludedProperties);
        	}else if(tree.isArray()){
        		ArrayNode arry = (ArrayNode)tree;
        		for(int i = 0; i < arry.size(); i++){
        			JsonNode jsonNode = arry.get(i);
        			filterBeanTreeRecursive(jsonNode, excludedProperties);
        		}
        	}
        }
    }
	
	public static JsonNode toJsonNode(String content){
		JsonNode jsonNode = null;
		try {
			if(StringUtil.isNotEmpty(content)){
				jsonNode = getObMapperInstance().readTree(content);
			}
		} catch (Exception e) {
			log.error("toJsonNode exception", e);
		} 
		return jsonNode;
	}
	
	public static JsonNode toTextNode(String content){
		String value = StringUtil.getOrElse(content);
		return TextNode.valueOf(value);
	}
	
	public static JsonNode toJsonNodeAnyway(String content){
		JsonNode jsonNode = null;
		try {
			if((content.startsWith("{") && content.endsWith("}"))
					|| (content.startsWith("[") && content.endsWith("]"))){
				jsonNode = getObMapperInstance().readTree(content);
			}else{
				jsonNode = TextNode.valueOf(content);
			}
		} catch (Exception e) {
			jsonNode = TextNode.valueOf(content);
		} 
		return jsonNode;
	}
	
	public static String getFieldValue(JsonNode jsonNode, String fieldName){
		String result = "";
		if(jsonNode!= null && jsonNode.isObject()){
			ObjectNode obj = (ObjectNode)jsonNode;
			if(obj.has(fieldName)){
				result = StringUtil.getOrElse(obj.get(fieldName).asText());
			}
		}
		return result;
	}
	
	public static JsonNode toJsonNode(URL url){
		JsonNode jsonNode = null;
		try {
			jsonNode = getObMapperInstance().readTree(url);
		} catch (JsonProcessingException e) {
			log.error("toJsonNode JsonProcessingException", e);
		} catch (IOException e) {
			log.error("toJsonNode IOException", e);
		}
		return jsonNode;
	}
	
	public static JsonNode find(JsonNode parent, String fieldName){
		if(parent == null){
			return MissingNode.getInstance();
		}
		return parent.findValue(fieldName);
	}
	
	public static JsonNode unique(JsonNode parent, String fieldName){
		List<JsonNode> results = findArray(parent, fieldName);
		if(results.size() > 1){
			throw new RuntimeException("存在多个同名数据节点" + fieldName);
		}else if(results.size() == 1){
			return results.get(0);
		}
		return MissingNode.getInstance();
	}
	
	public static List<JsonNode> allPath(JsonNode parent, String fieldName){
		List<JsonNode> results = new ArrayList<JsonNode>();
		List<JsonNode> first = null;
		String[] fieldNames = StringUtil.splitTwo(fieldName, ".");
		
		String find = fieldNames[0];
		String left = fieldNames[1];
		
		if(StringUtil.isNotEmpty(find)){
			first = findArray(parent, find);
		}
		if(first != null){
			if(StringUtil.isNotEmpty(left)){
				for(JsonNode subNode : first){
					results.addAll(allPath(subNode, left));
				}
			}else{
				results.addAll(first);
			}
		}
		return results;
	}
	
	public static ArrayNode toArrayNode(List<JsonNode> jsonNodes){
		ArrayNode arrayNode = null;
		if(jsonNodes != null){
			arrayNode = arrayNode();
			for(JsonNode jsonNode:jsonNodes){
				if(jsonNode.isArray()){
					arrayNode.addAll((ArrayNode)jsonNode);
				}else{
					arrayNode.add(jsonNode);
				}
			}
		}
		return arrayNode;
	}
	
	public static ArrayNode toArrayNode(JsonNode jsonNode){
		ArrayNode arrayNode = null;
		if(jsonNode != null && !jsonNode.isNull() && !jsonNode.isMissingNode()){
			if(jsonNode.isArray()){
				arrayNode = (ArrayNode)jsonNode;
			}else{
				arrayNode = arrayNode();
				arrayNode.add(jsonNode);
			}
		}
		return arrayNode;
	}
	
	public static JsonNode findParent(JsonNode node, String fieldName){
		if(node == null || node.isMissingNode()){
			return MissingNode.getInstance();
		}
		return node.findParent(fieldName);
	}
	
	public static JsonNode findAncestor(JsonNode node, String path){
		if(node == null || node.isMissingNode()){
			return MissingNode.getInstance();
		}
		String newPath = StringUtil.replaceRepeat(path, "//", "/");
		String[] args = newPath.split("/");
		// /a
		if(args.length > 1){
			for(int i = args.length - 1; i > -1; i--){
				if(StringUtil.isNotEmpty(args[i])){
					JsonNode p = node.findParent(args[i]);
					if(p != null && !p.isMissingNode()){
						return p;
					}
				}
			}
		}
		return node;
	}
	
	public static List<JsonNode> findArray(JsonNode parent, String fieldName){
		if(parent == null || StringUtil.isEmpty(fieldName)){
			return new ArrayList<JsonNode>();
		}
		return parent.findValues(fieldName);
	}
	
	public static JsonNode path(JsonNode parent, String path){
		if(parent == null){
			return MissingNode.getInstance();
		}
		if(StringUtil.isEmpty(path) || "/".equals(path)){
			return parent;
		}
		//换掉空的path
		String newPath = StringUtil.replaceRepeat(path, "//", "/");
		return parent.at(newPath);
	}
	
	public static ArrayNode arrayNode(){
		return getObMapperInstance().createArrayNode();
	}
	
	public static ObjectNode objectNode(){
		return getObMapperInstance().createObjectNode();
	}
	
	public static Map<String, Object> jsonToMap(String jsonString) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        if(StringUtil.isNotEmpty(jsonString)){
	        JSONObject json = JSONObject.fromObject(jsonString);
	        if(json != null && !json.isNullObject()) {
	            retMap = toMap(json);
	        }
        }
        return retMap;
    }
	
	public static String mapToJson(Map<String, Object> map){
		if(map == null || map.isEmpty()){
			return "";
		}
		
		JSONObject json = new JSONObject();
		json.putAll(map);
		return json.toString();
	}
	
	public static Map<String, Object> toMap(JSONObject object) {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keySet().iterator();
        while(keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }
	
	public static List<Object> toList(JSONArray array) {
        List<Object> list = new ArrayList<Object>();
        for(int i = 0; i < array.size(); i++) {
            Object value = array.get(i);
            if(value instanceof JSONArray) {
                value = toList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }
	
	public static void main(String[] args){
		String abc = "{abc:\"abc\", efg:123}";
		Map<String, Object> map = jsonToMap(abc);
		String efg = mapToJson(map);
		System.out.print(efg);
	}

	
	public static String getNotEmptyValue(JSONObject json, String key){
		try{
			if(json != null && json.containsKey(key)){
					String v = StringUtil.getOrElse(json.getString(key));
					if("null".equals(v) && json.getJSONObject(key).isNullObject()){
						return "";
					}
					return v;
			}
		}catch(Exception ex){
			
		}
		return "";
	}
	
	public static boolean getBoolean(JSONObject json, String key){
		if(json != null && json.containsKey(key)){
			return json.getBoolean(key);
		}
		return false;
	}
	
	public static JSONObject getJsonObject(JSONObject json, String key){
		if(json != null && json.containsKey(key)){
			return json.getJSONObject(key);
		}
		return null;
	}
	
	public static String toJsonString(Object object) {
		try {
			return getObMapperInstance().writeValueAsString(object);
		} catch (Exception e) {
			return "";
		}

	}
}
