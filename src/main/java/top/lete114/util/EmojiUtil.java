package top.lete114.util;

/**
 * @author Lete乐特
 * @createDate 2020- 11-21 13:14
 */
public class EmojiUtil {
    /*判断字符串字符串中是否又Emoji表情*/
    public static boolean isEmoji(String content) {
        int len = content.length();
        boolean isEmoji = false;
        for (int i = 0; i < len; i++) {
            char hs = content.charAt(i);
            if (0xd800 <= hs && hs <= 0xdbff) {
                if (content.length() > 1) {
                    char ls = content.charAt(i + 1);
                    int uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;
                    if (0x1d000 <= uc && uc <= 0x1f77f) {
                        return true;
                    }
                }
            } else {
                // non surrogate
                if (0x2100 <= hs && hs <= 0x27ff && hs != 0x263b) {
                    return true;
                } else if (0x2B05 <= hs && hs <= 0x2b07) {
                    return true;
                } else if (0x2934 <= hs && hs <= 0x2935) {
                    return true;
                } else if (0x3297 <= hs && hs <= 0x3299) {
                    return true;
                } else if (hs == 0xa9 || hs == 0xae || hs == 0x303d
                        || hs == 0x3030 || hs == 0x2b55 || hs == 0x2b1c
                        || hs == 0x2b1b || hs == 0x2b50 || hs == 0x231a) {
                    return true;
                }
                if (!isEmoji && content.length() > 1 && i < content.length() - 1) {
                    char ls = content.charAt(i + 1);
                    if (ls == 0x20e3) {
                        return true;
                    }
                }
            }
        }
        return isEmoji;
    }
}
