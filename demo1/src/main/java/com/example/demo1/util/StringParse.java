package com.example.demo1.util;

/**
 * Created by liudianbing on 2017/10/9.
 */

import org.apache.cxf.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringParse {


    private static final Logger logger = LoggerFactory.getLogger(StringParse.class);

    public static String eatHtmlCommecnt(String sBuffer) {
        if (sBuffer == null || sBuffer.length() == 0) {
            return "";
        }
        return eatHtmlTagMarkNoMatch(sBuffer, "<!--", "-->");
    }

    public static String eatHtmlMark(String sBuffer) {
        if (sBuffer == null || sBuffer.length() == 0) {
            return "";
        }
        return eatHtmlTagMarkNoMatch(sBuffer, "<", ">");
    }

    public static String eatHtmlLinkMark(String sBuffer) {
        if (sBuffer == null || sBuffer.length() == 0) {
            return "";
        }
        sBuffer = eatHtmlTagMarkNoMatch(sBuffer, "<a", ">");
        sBuffer = eatHtmlTagMarkNoMatch(sBuffer, "</a", ">");
        return sBuffer;
    }

    public static String eatHtmlTagMarkNoMatch(String sBuffer, String nameBegin, String nameEnd) {
        if (sBuffer == null || sBuffer.length() == 0) {
            return "";
        }
        String lowerBuffer = sBuffer.toLowerCase();
        String newBuffer = "";
        int posStart = 0;
        int blkStart = 0;
        int blkEnd = 0;

        while (true) {
            if ((blkStart = lowerBuffer.indexOf(nameBegin, posStart)) == -1) {
                break;
            }
            if ((blkEnd = lowerBuffer.indexOf(nameEnd, blkStart + nameBegin.length())) == -1) {
                break;
            }
            newBuffer += sBuffer.substring(posStart, blkStart);
            // theBlock=sBuffer.substring(blkStart+nameBegin.length(),blkEnd);
            // if(theBlock.toLowerCase().equals("br")) newBuffer+="<br>";
            posStart = blkEnd + nameEnd.length();
        }
        newBuffer += sBuffer.substring(posStart);
        return newBuffer;
    }

    /**
     * 清除相对应的HTML
     *
     * @param sBuffer
     * @param nameBegin
     * @param nameEnd
     * @return
     */
    public static String eatHtmlTagMark(String sBuffer, String nameBegin, String nameEnd) {
        if (sBuffer == null || sBuffer.length() == 0) {
            return "";
        }
        // String lowerBuffer =sBuffer.toLowerCase();
        int posStart = 0;
        int pEnd = 0;
        int pIndex = -1;
        int pos = 0;
        StringBuffer sb = new StringBuffer();

        while ((pIndex = sBuffer.indexOf(nameBegin, posStart)) != -1) {
            posStart = pIndex + 2;
            pEnd = sBuffer.indexOf(nameEnd, posStart);
            String theBlock = "";
            while (pEnd > posStart) {
                theBlock = sBuffer.substring(posStart, pEnd);
                int innerPos = theBlock.indexOf(nameBegin);
                if (innerPos >= 0) {
                    pEnd = sBuffer.indexOf(nameEnd, pEnd + 1);
                    posStart = posStart + innerPos + 1;
                } else {
                    break;
                }

            }
            if (pEnd > posStart) {
                String textSubString = sBuffer.substring(pos, pIndex);
                // theBlock=sBuffer.substring(pIndex+2,pEnd);
                sb.append(textSubString);
                pos = pEnd + nameEnd.length();
                posStart = pEnd + nameEnd.length();
            } else {
                break;
            }

        }
        sb.append(sBuffer.substring(posStart));
        return sb.toString();
    }

    /**
     * 清除相对应的HTML
     *
     * @param sBuffer
     * @param nameBegin
     * @param nameEnd
     * @return
     */
    public static String eatHtmlTagMarkIngoreCase(String sBuffer, String nameBegin, String nameEnd) {
        if (sBuffer == null || sBuffer.length() == 0) {
            return "";
        }
        String lowerBuffer = sBuffer.toLowerCase();
        int posStart = 0;
        int pEnd = 0;
        int pIndex = -1;
        int pos = 0;
        StringBuffer sb = new StringBuffer();
        nameBegin = nameBegin.toLowerCase();
        nameEnd = nameEnd.toLowerCase();
        while ((pIndex = lowerBuffer.indexOf(nameBegin, posStart)) != -1) {
            posStart = pIndex + 2;
            pEnd = lowerBuffer.indexOf(nameEnd, posStart);
            String theBlock = "";
            while (pEnd > posStart) {
                theBlock = lowerBuffer.substring(posStart, pEnd);
                int innerPos = theBlock.indexOf(nameBegin);
                if (innerPos >= 0) {
                    pEnd = lowerBuffer.indexOf(nameEnd, pEnd + 1);
                    posStart = posStart + innerPos + 1;
                } else {
                    break;
                }

            }
            if (pEnd > posStart) {
                String textSubString = sBuffer.substring(pos, pIndex);
                // theBlock=sBuffer.substring(pIndex+2,pEnd);
                sb.append(textSubString);
                pos = pEnd + nameEnd.length();
                posStart = pEnd + nameEnd.length();
            } else {
                break;
            }

        }
        sb.append(sBuffer.substring(posStart));
        return sb.toString();
    }

    public static String getLastRightString(String content, String startstr) {
        String midstr = "";
        if (content == null || content.length() == 0) {
            return midstr;
        }
        int pos1 = content.lastIndexOf(startstr);
        if (pos1 >= 0) {
            midstr = content.substring(pos1 + startstr.length());
        }

        return midstr;
    }

    public static String getRightString(String content, String startstr) {
        String midstr = "";
        if (content == null || content.length() == 0) {
            return midstr;
        }
        int pos1 = content.indexOf(startstr);
        if (pos1 >= 0) {
            midstr = content.substring(pos1 + startstr.length());
        }

        return midstr;
    }

    public static String getLeftString(String content, String endstr) {
        String midstr = "";
        if (content == null || content.length() == 0) {
            return midstr;
        }
        int pos1 = content.indexOf(endstr);
        if (pos1 > 0) {
            midstr = content.substring(0, pos1);
        }
        return midstr;
    }

    public static String getlastLeftString(String content, String endstr) {
        String midstr = "";
        if (content == null || content.length() == 0) {
            return midstr;
        }
        int pos1 = content.lastIndexOf(endstr);
        if (pos1 > 0) {
            midstr = content.substring(0, pos1);
        }
        return midstr;
    }

    public static String getMidString(String content, String startstr, String endstr) {
        String midstr = "";
        if (content == null || content.length() == 0) {
            return midstr;
        }
        int pos1 = content.indexOf(startstr);
        if (pos1 >= 0) {
            int pos2 = content.indexOf(endstr, pos1 + startstr.length());
            if (pos2 >= 0) {
                midstr = content.substring(pos1 + startstr.length(), pos2);
            }
        }
        return midstr;
    }

    public static String[] getMidStringList(String content, String startstr, String endstr) {
        ArrayList list = new ArrayList();
        String midstr = "";
        if (content == null || content.length() == 0) {
            return null;
        }
        int pos1 = content.indexOf(startstr);

        while (pos1 >= 0) {
            midstr = "";
            int pos2 = content.indexOf(endstr, pos1 + startstr.length());
            if (pos2 >= pos1) {
                midstr = content.substring(pos1 + startstr.length(), pos2);
                list.add(midstr);
                pos1 = content.indexOf(startstr, pos2);
            } else {
                break;
            }

        }
        return (String[]) list.toArray(new String[0]);
    }

    public static String[] getImgList(String content) {
        // ArrayList<String> list = new ArrayList<String>();
        java.util.Hashtable<String, Boolean> list = new java.util.Hashtable<String, Boolean>();
        String midstr = "";
        String startstr = "<img";
        String endstr = ">";
        if (content == null || content.length() == 0) {
            return null;
        }
        String lowercontent = content.toLowerCase();

        int pos1 = lowercontent.indexOf(startstr);

        while (pos1 >= 0) {
            midstr = "";
            int pos2 = lowercontent.indexOf(endstr, pos1);
            if (pos2 > pos1 + startstr.length()) {
                midstr = content.substring(pos1 + startstr.length(), pos2);

                midstr = midstr.trim();
                midstr = midstr.replaceAll("['\"]", "");
                midstr = midstr.replaceAll("(\\s)+", " ");

                String midstrlower = midstr.toLowerCase();

                int start = midstrlower.indexOf("src=");
                if (start >= 0) {
                    int end = midstrlower.indexOf(" ", start + 5);
                    if (end > start) {
                        midstr = midstr.substring(start + 4, end).trim();
                    } else {
                        midstr = midstr.substring(start + 4).trim();
                    }
                    if (midstr.endsWith("/")) {
                        midstr = midstr.substring(0, midstr.length() - 1);
                    }

                    if (midstr.startsWith("'") || midstr.startsWith("\"")) {
                        midstr = midstr.substring(1);
                    }
                    if (midstr.endsWith("\"") || midstr.endsWith("'")) {
                        midstr = midstr.substring(0, midstr.length() - 1);
                    }
                    list.put(midstr, new Boolean(true));
                    // list.add(midstr);
                }
                pos1 = lowercontent.indexOf(startstr, pos2);
            } else {
                break;
            }

        }
        String[] results = new String[list.size()];
        java.util.Enumeration<String> keys = list.keys();
        int i = 0;
        while (keys.hasMoreElements()) {
            results[i++] = keys.nextElement();
        }
        list.clear();
        list = null;
        return results;
        // return (String[]) list.toArray(new String[0]);
    }

    public static String getRegStr(String content, String regexp) {

        Pattern p = Pattern.compile(regexp);
        Matcher m = p.matcher(content);
        if (m.find()) {
            String str = m.group(0);
            return str;
        } else {
            return "";
        }
    }

    public static String[] getRegStrList(String content, String regexp) {
        Pattern p = Pattern.compile(regexp);
        Matcher m = p.matcher(content);
        ArrayList list = new ArrayList();
        while (m.find()) {
            String tmp = m.group();
            list.add(tmp);
        }
        return (String[]) list.toArray(new String[0]);
    }

    public static ArrayList getRegStrList(String content, String regexp, int[] groupindex) {
        Pattern p = Pattern.compile(regexp);
        Matcher m = p.matcher(content);
        ArrayList list = new ArrayList();
        while (m.find()) {
            String[] tmp = new String[groupindex.length];
            for (int i = 0; i < groupindex.length; i++) {
                tmp[i] = m.group(groupindex[i]);
            }
            list.add(tmp);
        }
        return list;
    }

    public static String getRealUrl(String basepath, String file) {
        try {
            java.net.URL url = new java.net.URL(new java.net.URL(basepath), file);
            return url.toString();
        } catch (Exception e) {
            logger.error("getRealUrl e.printStackTrace：{}",e);
        }
        return "";
    }

    /**
     * 去除html中的命名空间 xmlns，这样dom4j使用xpath读取的时候就不会需要命名空间了
     *
     * @param html
     * @return
     */
    public static String removeNamespace(String html) {
        if (StringUtils.isEmpty(html)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int pos = html.indexOf("<html");
        int pos2 = 0;
        while (pos >= 0) {
            pos2 = html.indexOf(">", pos + 4);
            if (pos2 > pos) {
                // if(pos>0)
                // sb.append(html.substring(0,pos));
                sb.append("<html>");
                html = html.substring(pos2 + 1);
            } else {
                break;
            }
            pos = html.indexOf("<html");
        }

        sb.append(html);
        return sb.toString();
    }

    public static String getPatterString(String patternString) {
        String normalizedPatternString = new String();

        int pStart = 0;
        int pEnd = 0;
        int pIndex = -1;
        int pos = 0;
        while ((pIndex = patternString.indexOf("${", pStart)) != -1) {
            pStart = pIndex + 2;
            pEnd = patternString.indexOf("}", pStart);
            String theBlock = "";
            while (pEnd > pStart) {
                theBlock = patternString.substring(pStart, pEnd);
                int innerPos = theBlock.indexOf("{");
                if (innerPos >= 0) {

                    pEnd = patternString.indexOf("}", pEnd + 1);
                    pStart = pStart + innerPos + 1;
                } else {
                    break;
                }

            }
            if (pEnd > pStart) {
                String textSubString = patternString.substring(pos, pIndex);
                textSubString = textSubString.replaceAll("[\\?]", "\\\\?");
                textSubString = textSubString.replaceAll("[\\+]", "\\\\+");
                textSubString = textSubString.replaceAll("[\\*]", "\\\\*");

                theBlock = patternString.substring(pIndex + 2, pEnd);
                normalizedPatternString += textSubString + theBlock;

                pos = pEnd + 1;
                pStart = pEnd + 1;
            } else {

                return "";
            }

        }
        normalizedPatternString += patternString.substring(pStart);
        // System.out.println("normalizedPatternString="+normalizedPatternString);
        return normalizedPatternString;
    }

    public static ArrayList getRegGroupStrList(String content, String regexp) {
        Pattern p = Pattern.compile(regexp);
        Matcher m = p.matcher(content);
        ArrayList list = new ArrayList();
        int length = m.groupCount();

        while (m.find()) {
            String[] tmp = new String[length + 1];
            for (int i = 0; i <= length; i++) {
                tmp[i] = m.group(i);
            }
            list.add(tmp);
        }
        return list;
    }


    /**
     * 截取一段html文本，其中需要将html代码考虑进去进行闭合
     *
     * @param content
     * @param start
     * @param end
     * @return
     */
    public static String htmlSubString(String content, int start, int end) {
        if (content == null || content.length() == 0)
            return "";
        if (start < 0)
            start = 0;



        if (end >= content.length())
            end = content.length();

        String midstr = content.substring(start, end);

        String mid2 = midstr.toLowerCase();
        int m1 = mid2.lastIndexOf("</p>");
        int m2 = mid2.lastIndexOf("<p");
        if (m1 > 0 && (m2 == -1 || m1 < m2)) {
            midstr = midstr.substring(0,m1 + 4);
            mid2 = mid2.substring(0,m1 + 4);
        }
        m1 = mid2.lastIndexOf("</div>");
        m2 = mid2.lastIndexOf("<div");
        if (m1 > 0 && (m2 == -1 || m1 < m2)) {
            midstr = midstr.substring(0,m1 + 6);
            mid2 = mid2.substring(0,m1 + 6);
        }

        m1 = mid2.lastIndexOf("</a>");
        m2 = mid2.lastIndexOf("<a");
        if (m1 > 0 && (m2 == -1 || m1 < m2)) {
            midstr = midstr.substring(0,m1 + 4);
            mid2 = mid2.substring(0,m1 + 4);
        }

        //图片处理是例外，不是闭合标签
        m1 = mid2.lastIndexOf("<img");
        m2 = mid2.lastIndexOf(">");
        if (m1 > 0 && (m2 == -1 || m1 > m2)) {
            midstr = midstr.substring(0,m1);
            mid2 = mid2.substring(0,m1);
        }
        // 处理通用的HTML代码，把html代码闭合
        int htmlStartTag = midstr.lastIndexOf("<");
        int htmlEndTag = midstr.lastIndexOf(">");
        if (htmlEndTag < htmlStartTag) {// html结束符在开始符之前，说明截取了一段html代码
            if (htmlStartTag < midstr.length() - 1) {
                char c = midstr.charAt(htmlStartTag + 1);
                if (c == '/') {

                    int start2 = midstr.indexOf(">", htmlStartTag + 1);
                    if (start2 > 0 && start2 <= midstr.length()) {
                        midstr = midstr.substring(start2 + 1);
                    } else {
                        midstr = "";
                    }

                } else {

                    midstr = midstr.substring(htmlEndTag + 1);
                }
            }
        }
        if (midstr.length() == 0) {
            return midstr;
        }



        return midstr;
    }
  


    public static String firstCharToLowerCase(String substring) {
        if (substring!=null&& substring.charAt(0)>='A' && substring.charAt(0)<='Z'){
            char[] arr = substring.toCharArray();
            arr[0] = (char)(arr[0] + 32);
            return new String(arr);
        }else {
            return substring;
        }
    }







}
