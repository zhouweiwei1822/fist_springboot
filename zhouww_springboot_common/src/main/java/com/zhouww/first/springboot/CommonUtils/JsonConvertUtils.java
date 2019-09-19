package com.zhouww.first.springboot.CommonUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JsonConvertUtils {

	private static ObjectMapper oMapper = new ObjectMapper();

	/**
	 * 对象转json的时候时间的格式，默认为yyyy-MM-dd HH:mm:ss
	 */
	private static String defaultDateFormat = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 将实体对象转换成json字符串
	 *
	 * @param t
	 * @return
	 */
	public static <T> String writeTAsJson(T t) {
		String json = "{}";
		try {
			oMapper.setDateFormat(new SimpleDateFormat(defaultDateFormat));
			if (t != null)
				return oMapper.writeValueAsString(t);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 将实体对象转换成json字符串
	 *
	 * @param t
	 * @return
	 */
	public static <T> String writeTAsJson(T t, Boolean showNull) {
		String json = "{}";
		try {
			oMapper.setDateFormat(new SimpleDateFormat(defaultDateFormat));
			if(!showNull){
				oMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);//为空不参加序列化
			}
			if (t != null)
				return oMapper.writeValueAsString(t);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 将实体对象转换成json字符串
	 *
	 * @param t
	 * @param dateFormat
	 * @return
	 */
	public static <T> String writeTAsJson(T t, String dateFormat) {
		String json = "{}";
		try {
			if (StringUtils.isNoneBlank(dateFormat))
		oMapper.setDateFormat(new SimpleDateFormat(dateFormat));
			else
		oMapper.setDateFormat(new SimpleDateFormat(defaultDateFormat));
		if (t != null)
			return oMapper.writeValueAsString(t);
	} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 字符串数组转list
	 *
	 * @param json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> writeJson2List(String json) {
		List<T> list = new ArrayList<>();
		try {
			oMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			if (StringUtils.isNotBlank(json)) {
				list = oMapper.readValue(json, List.class);
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 字符串数组转Object
	 *
	 * @param json
	 * @param target
	 * @return
	 */
	public static <T> Object writeJson2Object(String json, Object target) {
		Object t = null;
		try {
			oMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			if (StringUtils.isNotBlank(json)) {
				t = oMapper.readValue(json, Object.class);
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * 字符串转成实体类
	 *
	 * @param content
	 * @param valueType
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T readValue(String content, Class<T> valueType) {
		Object result = null;
		try {
			oMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			if (StringUtils.isNotBlank(content)) {
				result = oMapper.readValue(content, valueType);
			}
		} catch (UnrecognizedPropertyException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (T) result;
	}

	/**
	 * 字符串转成实体类
	 *
	 * @param content
	 * @param valueTypeRef
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T readValue(String content, @SuppressWarnings("rawtypes") TypeReference valueTypeRef) {
		Object result = null;
		try {
			oMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			if (StringUtils.isNotBlank(content)) {
				result = oMapper.readValue(content, valueTypeRef);
			}
		} catch (UnrecognizedPropertyException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (T) result;
	}

	public static void main(String[] args) {
		Test test = new Test();
		test.setTripDate(new Date());
		oMapper.setDateFormat(new SimpleDateFormat(defaultDateFormat));
		try {
			String str = oMapper.writeValueAsString(new Date());
			System.out.println(str);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static class Test {

		@JsonFormat(pattern = "yyyy-MM-dd")
		private Date tripDate;

		public Date getTripDate() {
			return tripDate;
		}

		public void setTripDate(Date tripDate) {
			this.tripDate = tripDate;
		}

	}
}
