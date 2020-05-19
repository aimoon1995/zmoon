package com.project_study.my.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//加密敏感数据
public class SensitiveEncoder {

	private final static String reg_email = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
	private final static String reg_tw_idcard = "\\[A-Z]\\d{9}";
	private final static String reg_ch_idcard = "(\\d{6})(18|19|20)?(\\d{2})([01]\\d)([0123]\\d)(\\d{3})(\\d|X)?";
	private final static String reg_ch_bank = "(\\d{16})|(\\d{19})";
	private final static String reg_number_5 = "\\d{5,}";

	private final static String reg_highlight = "<[\\s]*?em[^>]*?>([\\s\\S]*?)<[\\s]*?\\/[\\s]*?em[\\s]*?>";

	private final static List<Pattern> patterns = new ArrayList<Pattern>();

	static {
		patterns.add(Pattern.compile(reg_email));
		patterns.add(Pattern.compile(reg_ch_idcard));
		patterns.add(Pattern.compile(reg_tw_idcard));
		patterns.add(Pattern.compile(reg_ch_bank));
		patterns.add(Pattern.compile(reg_number_5));
	}

	private static class MatcherResult {
		public int start;
		public String value;
		public int patternIndex;
	}

	private static class HighLight {
		public int start;
		public int end;
	}

	private static String replace(String value, List<MatcherResult> matchResults) {
		String result = value;
		for (MatcherResult mr : matchResults) {
			String replacement = mr.value;
			switch (mr.patternIndex) {
			case 0:
				// Email
				replacement = encodeEmail(replacement);
				break;
			case 1:
				// 大陆身份证
				replacement = encodeChIdCard(replacement);
				break;
			case 2:
				// 台湾身份证
				replacement = encodeTwIdCard(replacement);
				break;
			case 3:
				// 银行帐号
				replacement = encodeBank(replacement);
				break;
			case 4:
				// 其他数字
				replacement = encodeNumber(replacement);
				break;
			}
			result = StringUtil.replaceByIndex(result, mr.start, replacement);
		}
		return result;
	}

	private static final String encodeEmail(String email) {
		// @符号前的3位数字换成*
		int atIndex = email.indexOf("@");
		if (atIndex < 1) {
			return email;
		}
		if (atIndex < 4) {
			return StringUtil.buildString(atIndex, '*') + email.substring(atIndex);
		}
		return email.substring(0, atIndex - 3) + StringUtil.buildString(3, '*')
				+ email.substring(atIndex);
	}

	private static final String encodeChIdCard(String idCard) {
		// 生日的6位数隐藏
		if (idCard.length() == 15) {
			return idCard.substring(0, 6) + StringUtil.buildString(6, '*')
					+ idCard.substring(12);
		} else if (idCard.length() == 18) {
			return idCard.substring(0, 8) + StringUtil.buildString(6, '*')
					+ idCard.substring(14);
		}
		return idCard;
	}

	private static final String encodeTwIdCard(String idCard) {
		return encodeNumber(idCard);
	}

	private static final String encodeBank(String bankNo) {
		return encodeNumber(bankNo);
	}

	private static final String encodeNumber(String num) {
		// 隐藏中间一半
		int cnt = num.length();
		int len = cnt / 2;
		if (len < 2) {
			return num;
		}
		int startIndex = (cnt - len) / 2;
		return num.substring(0, startIndex) + StringUtil.buildString(len, '*')
				+ num.substring(startIndex + len);
	}

	public static final String removeHighLights(String value,
			List<HighLight> highLights) {
		if (StringUtil.isEmpty(value)) {
			return "";
		}
		StringBuffer buf = new StringBuffer();
		Pattern p = Pattern.compile(reg_highlight);
		Matcher matcher = p.matcher(value);
		while (matcher.find()) {
			HighLight hl = new HighLight();
			String group = matcher.group(1);
			if (highLights != null) {
				hl.start = matcher.start();
				hl.end = hl.start + group.length() + 4;
				highLights.add(hl);
			}
			matcher.appendReplacement(buf, group);
		}
		matcher.appendTail(buf);
		return buf.toString();
	}

	public static String addHighLights(String value, List<HighLight> highLights) {
		StringBuffer result = new StringBuffer(value);
		for (HighLight hl : highLights) {
			result.insert(hl.start, "<em>");
			result.insert(hl.end, "</em>");
		}
		return result.toString();
	}

	public static final String encode(String value, boolean highLight) {
		if (StringUtil.isEmpty(value)) {
			return "";
		}
		List<MatcherResult> matchResults = new ArrayList<MatcherResult>();
		List<HighLight> highLights = new ArrayList<HighLight>();
		StringBuffer buf = new StringBuffer();
		// 去高亮
		String nvalue = value;
		if (highLight) {
			nvalue = removeHighLights(value, highLights);
		}
		String matcherString = nvalue;

		int i = 0;
		for (Pattern p : patterns) {
			Matcher matcher = p.matcher(matcherString);
			while (matcher.find()) {
				MatcherResult mr = new MatcherResult();
				String group = matcher.group(0);
				mr.start = matcher.start();
				mr.value = group;
				mr.patternIndex = i;
				matchResults.add(mr);
				matcher.appendReplacement(buf,
						StringUtil.buildString(group.length(), ' '));
			}
			matcher.appendTail(buf);
			matcherString = buf.toString();
			buf = new StringBuffer();
			i++;
		}

		String result = replace(nvalue, matchResults);
		if (highLight) {
			result = addHighLights(result, highLights);
		}
		return result;
	}

//	public static void main(String[] args) {
//		String result = "请问这是谁的邮箱？<em>who@126.com 这是一个邮箱地址</em> <em>chinaren</em>@gmail.com我的<em>身份证号码是4220851995</em>05101237<em>哈哈还有QQ号哦888888";
//		System.out.println(result);
//		List<HighLight> hls = new ArrayList<HighLight>();
//		result = SensitiveEncoder.removeHighLights(result, hls);
//		System.out.println(result);
//		result = SensitiveEncoder.addHighLights(result, hls);
//		System.out.println(result);
//		System.out.println(SensitiveEncoder.encode(result, true));
//		System.out.println(SensitiveEncoder.encode(result, false));
//		System.out.println(System.getProperty("file.encoding")); 
//	}
}
