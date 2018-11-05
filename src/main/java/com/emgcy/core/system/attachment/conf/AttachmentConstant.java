package com.emgcy.core.system.attachment.conf;

import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by ganxf on 2017/10/31.
 */
public class AttachmentConstant {


    //文件名中不允许出现的字符 \ / : | ? < > "
    public static final Pattern fileNameKeyWordPattern = Pattern.compile("(\\\\)|(/)|(:)(|)|(\\?)|(>)|(<)|(\")");

    public static final Map<String, String> mediaTypeMapper = new HashMap<>();

    static {
        mediaTypeMapper.put(".png", MediaType.IMAGE_PNG_VALUE);
        mediaTypeMapper.put(".jpg", MediaType.IMAGE_JPEG_VALUE);
        mediaTypeMapper.put(".jpeg", MediaType.IMAGE_JPEG_VALUE);
        mediaTypeMapper.put(".gif", MediaType.IMAGE_GIF_VALUE);
        mediaTypeMapper.put(".bmp", MediaType.IMAGE_JPEG_VALUE);
        mediaTypeMapper.put(".json", MediaType.APPLICATION_JSON_VALUE);
        mediaTypeMapper.put(".txt", MediaType.TEXT_PLAIN_VALUE);
        mediaTypeMapper.put(".css", MediaType.TEXT_PLAIN_VALUE);
        mediaTypeMapper.put(".js", "application/javascript");
        mediaTypeMapper.put(".html", MediaType.TEXT_HTML_VALUE);
        mediaTypeMapper.put(".xml", MediaType.TEXT_XML_VALUE);
    }
   
}
