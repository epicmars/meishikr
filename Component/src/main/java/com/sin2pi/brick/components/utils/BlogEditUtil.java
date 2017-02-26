package com.sin2pi.brick.components.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yinhang on 16/3/17.
 */
public class BlogEditUtil {

    /**
     * 将编辑器中原始文本转换为HTML格式
     * version 0.3: 原始文本未添加任何格式标记，仅适用换行符进行分段，然后替换文本中的图像，
     * 显示时将html文本从数据库读出，直接显示或写入到asset文件再显示。
     * TODO 后期在格式化文本中添加格式化标记，转换为更加复杂的html文本
     *      照片后加入了文字
     * @param content
     * @return
     */
    public static String toHtml(String content){
        if(null == content)
            return null;
        StringBuilder html = new StringBuilder(content);
        Pattern pattern = Pattern.compile("([^-~]+?\\s*\\n)");
        Matcher matcher = pattern.matcher(content);
        for (int i = 0; matcher.find(); i += 7){
            String paragraph = "<p>" + matcher.group() + "</p>";
            html.replace(matcher.start() + i, matcher.end() + i, paragraph);
        }
        return html.toString();
    }
}
