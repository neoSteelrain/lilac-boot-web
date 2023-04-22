package com.steelrain.springboot.lilac.datamodel;

/**
 * 회원의 등급을 나타내는 enum
 */
public enum USER_GRADE {
    ADMIN(1), NORMAL_MEMBER(2);
    private final int m_grade;
    USER_GRADE(int grade){
        this.m_grade = grade;
    }
    public int getGrade(){
        return m_grade;
    }
}
