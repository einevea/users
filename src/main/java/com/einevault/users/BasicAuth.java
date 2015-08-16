package com.einevault.users;

import com.einevault.users.model.UserPWD;

import java.util.Base64;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by einevea on 16/08/15.
 */
public class BasicAuth {
    private Pattern headerPatt = Pattern.compile("Basic\\s+(.+)");
    private Pattern userPwdPatt = Pattern.compile("([^:]+):(.+)");


    public Optional<UserPWD> extract(String authorization) {
        Optional<UserPWD> ret = Optional.empty();

        Matcher matcher = headerPatt.matcher(authorization);
        if(matcher.matches()){
            String userPwdB64 = matcher.group(1);
            String userPwd = new String(Base64.getDecoder().decode(userPwdB64));
            Matcher userPwdMatcher = userPwdPatt.matcher(userPwd);

            if(userPwdMatcher.matches()){
                String user = userPwdMatcher.group(1);
                String pwd = userPwdMatcher.group(2);
                UserPWD userPWD = new UserPWD(user, pwd);
                ret = Optional.of(userPWD);
            }
        }

        return ret;
    }
}
