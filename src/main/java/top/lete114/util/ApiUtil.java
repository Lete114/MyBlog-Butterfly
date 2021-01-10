package top.lete114.util;

import java.io.*;
import java.net.URL;

/**
 * @author Lete乐特
 * @createDate 2020- 12-16 22:09
 *
 * Api
 */
public class ApiUtil {

    // 获取gravatar头像
    public static String getGravatar(String mail){
        String Gravatar = "https://gravatar.loli.net/avatar/"+ MD5Util.MD5Encode(mail);
        return Gravatar;
    }

    // 获取qq昵称
    public static String getName(Integer qq) throws IOException {
        String url = "https://users.qzone.qq.com/fcg-bin/cgi_get_portrait.fcg?uins="+qq;
        String result = readStringFromUrl(url);
        // 获取字符串中的第4个 "
        int index=result.indexOf("\"");
        for (int i = 1; i <= 4; i++) {
            index = result.indexOf("\"",index+1);
        }
        // 获得name并返回
        String Name = result.substring(index+1);
        return Name.substring(0,Name.indexOf("\""));
    }

    // 获取qq头像
    public static String getHeadImage(Integer qq) throws IOException {
        String url = "https://ptlogin2.qq.com/getface?appid=1006102&uin="+qq+"&imgtype=3";
        String result = readStringFromUrl(url);
        // 获取字符串中的第4个 "
        int index=result.indexOf("\"");
        for (int i = 1; i <= 2; i++) {
            index = result.indexOf("\"",index+1);
        }
        // 获得name并返回
        String HeadImage = result.substring(index+1);
        return HeadImage.substring(0,HeadImage.indexOf("\""));
    }

    // 获取url内容
    private static String readStringFromUrl(String url) throws IOException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "GBK"));
            StringBuilder jsonText = new StringBuilder();
            int result;
            while ((result = br.read()) != -1) {
                jsonText.append((char) result);
            }
            return jsonText.toString();
        } finally {
            is.close();
        }
    }
}
