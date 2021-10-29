package com.zhuqielinode.furnituremall.furnituremall.utils;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtil {
    private static final Pattern mobile_pattern =Pattern.compile("^1(3[0-9]|4[01456879]|5[0-35-9]|6[2567]|7[0-8]|8[0-9]|9[0-35-9])\\d{8}$");

    public static boolean isMobile(String moblie){
        if(StringUtils.isEmpty(moblie)){
            return false;
        }
        Matcher matcher = mobile_pattern.matcher(moblie);
        return matcher.matches();
    }
}
