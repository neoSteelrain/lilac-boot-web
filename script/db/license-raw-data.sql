-- 자격증 정보
insert into tbl_license(code, name, key_word) values
(1320, '정보처리기사', '정보처리기사'),
(2290, '정보처리산업기사', '정보처리산업기사'),
(6921, '정보처리기능사', '정보처리기능사');

-- 주제 정보
insert into tbl_subject(code, name, key_word, key_word_book) values
(100,'자바', '자바 java', '자바 java'),
(200,'자바스크립트', '자바스크립트 javascript', '자바스크립트 javascript'),
(300,'스프링 프레임워크', '자바 스프링프레임워크', '자바 java 스프링프레임워크'),
(400,'SQL', 'SQLD SQLP sql', 'sql'),
(500,'데이터베이스', '데이터베이스 database', '데이터베이스 database'),
(600,'코딩테스트', '코딩테스트', '코딩 테스트');

-- 관리자 등록
insert into tbl_member(nickname, email, password, grade) value('admin', 'admin@admin.com', '123456yt', 1);