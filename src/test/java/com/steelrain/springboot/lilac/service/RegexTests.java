package com.steelrain.springboot.lilac.service;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.assertj.core.api.Assertions.assertThat;

public class RegexTests {

    @Test
    public void 정규식_비밀번호_매칭실패_테스트(){
        String regexStr = "^([a-zA-Z0-9]){5,19}$";
        Pattern regexPattern = Pattern.compile(regexStr);
        Matcher matcher = regexPattern.matcher("123비번45");

        assertThat(matcher.matches()).isFalse();
    }
    
    @Test
    public void 정규식_비밀번호_매칭성공_테스트(){
        String regexStr = "^([a-zA-Z0-9]){5,19}$";
        Pattern regexPattern = Pattern.compile(regexStr);
        Matcher matcher = regexPattern.matcher("123yt45");

        assertThat(matcher.matches()).isTrue();
    }

    @Test
    public void 정규식_아이디_매칭성공_테스트(){
        String regexStr = "^([가-힝a-zA-Z0-9]){1,20}$";
        Pattern regexPattern = Pattern.compile(regexStr);
        List<String> srcList = new ArrayList<>(10);
        srcList.add("user1234567890123456");
        srcList.add("사용자1234567890123456");
        srcList.add("1234567890유저123456");
        srcList.add("1234567890user123456");
        srcList.add("1234567890123456user");
        srcList.add("1234567890123456사용자");

        boolean[] results = new boolean[srcList.size()];
        for(int i=0, length=srcList.size() ; i < length ; i++){
            results[i] = regexPattern.matcher(srcList.get(i)).matches();
        }

        assertThat(results).contains(true);
    }

    @Test
    public void 정규식_아이디_매칭실패_테스트(){
        String regexStr = "^([가-힝a-zA-Z0-9]){1,20}$";
        Pattern regexPattern = Pattern.compile(regexStr);
        List<String> srcList = new ArrayList<>(10);
        srcList.add("!@#$1234567890123456");
        srcList.add("1234567890123456!@%");
        srcList.add("1234567890!~@#$123456");

        boolean[] results = new boolean[srcList.size()];
        for(int i=0, length=srcList.size() ; i < length ; i++){
            results[i] = regexPattern.matcher(srcList.get(i)).matches();
        }

        assertThat(results).contains(false);
    }
}
