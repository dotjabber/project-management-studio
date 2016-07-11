package pl.org.opi.management.utils;

import com.google.common.base.Charsets;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateUtils {
    private static final Pattern MARKER_PATTERN = Pattern.compile("\\{([A-Z0-9_]+)\\}");

    public static String getTemplate(String templateName, Map<String, String> parameters) {
        String result = "";

        try {
            String templateContent = IOUtils.toString(FileUtils.getResourceAsInputStream(templateName), Charsets.UTF_8);
            result = getContent(templateContent, parameters);

        } catch (IOException e) {
        }

        return result;
    }

    public static String getContent(String messageTemplate, Map<String, String> parameters) {
        Matcher matcher = MARKER_PATTERN.matcher(messageTemplate);
        String result = messageTemplate;

        while(matcher.find()) {
            String marker = matcher.group(1);

            if(!parameters.containsKey(marker)) {
                parameters.put(marker, "");
            }

            result = result.replace("{" + marker + "}", parameters.get(marker));
        }

        return result;
    }
}
