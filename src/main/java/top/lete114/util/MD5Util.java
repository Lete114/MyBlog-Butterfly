package top.lete114.util;

import org.springframework.util.DigestUtils;

/**
 * @author Lete乐特
 * @createDate 2020- 11-21 13:14
 */
public class MD5Util {
    public static String MD5Encode(String str) {
        String passwordMd5 = DigestUtils.md5DigestAsHex(str .getBytes());
        return passwordMd5;
    }
}
