package com.springjwt.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class CookieUtil {
    public static String getCookieValueByName(HttpServletRequest request, String cookieName) {

        if (request.getCookies() != null) {
            Cookie ck = Arrays.asList(request.getCookies()).stream().filter(c -> c.getName().equals(cookieName))
                    .findFirst().orElse(null);
            if (ck != null) {
                return ck.getValue();
            }

        }
        return null;
    }
}
