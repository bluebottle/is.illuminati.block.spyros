package is.illuminati.block.spyros.util;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.idega.util.IWTimestamp;

@Service("spyrosUtil")
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class SpyrosUtil {

	public static String concat(String arg1, String arg2) {
		return arg1.concat(arg2);
	}

	public static String formatDate(Date date, String pattern) {
		if (date != null) {
			return new IWTimestamp(date).getDateString(pattern);
		}
		return "";
	}

	public static String escapeXML(String string) {
		string = StringEscapeUtils.unescapeHtml(string.replaceAll("\\<[^>]*>",
				""));
		return string;
	}

	public static boolean contains(List<?> objects, Object object) {
		boolean contains = objects.contains(object);
		return contains;
	}
}