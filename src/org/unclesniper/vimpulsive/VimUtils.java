package org.unclesniper.vimpulsive;

public class VimUtils {

	private VimUtils() {}

	public static String singleString(String raw, boolean quote) {
		StringBuilder builder = new StringBuilder();
		VimUtils.singleString(raw, builder, quote);
		return builder.toString();
	}

	public static void singleString(String raw, StringBuilder cooked, boolean quote) {
		if(quote)
			cooked.append('\'');
		final int length = raw.length();
		for(int i = 0; i < length; ++i) {
			char c = raw.charAt(i);
			if(c == '\'')
				cooked.append("''");
			else
				cooked.append(c);
		}
		if(quote)
			cooked.append('\'');
	}

}
