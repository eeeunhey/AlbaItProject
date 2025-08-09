CREATE TABLE APPLY (
    APPLY_ID    NUMBER PRIMARY KEY,
    USER_NO     NUMBER NOT NULL,  -- FK: 지원한 사용자
    POST_ID     NUMBER NOT NULL,  -- FK: 공고 ID
    STATUS      VARCHAR2(20) DEFAULT '접수'
                CHECK (STATUS IN ('접수', '검토 중', '합격', '불합격')),
    APPLY_DATE  DATE DEFAULT SYSDATE,
    
    CONSTRAINT FK_APPLY_USER FOREIGN KEY (USER_NO)
        REFERENCES USER_TABLE(USER_NO),
    
    CONSTRAINT FK_APPLY_POST FOREIGN KEY (POST_ID)
        REFERENCES JOB_POST(POST_ID)
);

CREATE SEQUENCE SEQ_APPLY_ID
START WITH 1
INCREMENT BY 1;

INSERT INTO APPLY (APPLY_ID, USER_NO, POST_ID)
VALUES (SEQ_APPLY_ID.NEXTVAL, 1, 1);


CREATE TABLE user_info (
    user_id NUMBER PRIMARY KEY,
    username VARCHAR2(30) NOT NULL,
    password VARCHAR2(30) NOT NULL,
    nickname VARCHAR2(30),
    user_type VARCHAR2(10), -- '개인' or '기업'
    status VARCHAR2(10) DEFAULT '정상', -- '정상', '정지', '탈퇴'
    report_count NUMBER DEFAULT 0
);

ALTER TABLE user_table ADD (
    company_name     VARCHAR2(100),
    manager_name     VARCHAR2(100),
    business_number  VARCHAR2(30)
);

ALTER TABLE user_table ADD (
    resume_job_title     VARCHAR2(100),
    resume_location      VARCHAR2(100),
    resume_has_project   VARCHAR2(10),
    resume_project       VARCHAR2(300),
    resume_education     VARCHAR2(500)
);

SELECT * FROM user_table;

INSERT INTO user_table (
    user_no, user_id, password, name, nickname, user_type,
    company_name, manager_name, business_number,
    resume_job_title, resume_location, resume_has_project, resume_project, resume_education
)
VALUES (
    user_seq.NEXTVAL, 'url', 'url', '김개인', 'g_person', 'U',
    NULL, NULL, NULL,
    '백엔드 개발자', '서울 강남', '있음', '자바 웹 게시판 프로젝트', '부트캠프 수료'
);

commit;