CREATE TABLE EX_USER_WITH_IDCLASS (GROUPID BIGINT NOT NULL, USERID BIGINT NOT NULL, FIRSTNAME VARCHAR, LASTNAME VARCHAR, PRIMARY KEY (GROUPID, USERID))
CREATE TABLE EX_SIMPLE_USER_AUTO (ID BIGINT NOT NULL, FIRSTNAME VARCHAR, LASTNAME VARCHAR, PRIMARY KEY (ID))
CREATE TABLE USER (USER_ID BIGINT NOT NULL, FIRSTNAME VARCHAR NOT NULL, LASTNAME VARCHAR NOT NULL, UUID LONGVARBINARY NOT NULL UNIQUE, PRIMARY KEY (USER_ID))
CREATE TABLE TASK (TASK_ID BIGINT NOT NULL, CREATION_TIME TIME NOT NULL, DUE_DATE DATE, STATE VARCHAR NOT NULL, TITLE VARCHAR, UPDATE_TIME TIMESTAMP NOT NULL, UUID LONGVARBINARY NOT NULL UNIQUE, CREATOR_ID BIGINT, TABULAR_ID BIGINT, TASK_CONTENT_ID BIGINT, PRIMARY KEY (TASK_ID))
CREATE TABLE EX_SIMPLE_USER (ID BIGINT NOT NULL, FIRSTNAME VARCHAR, LASTNAME VARCHAR, PRIMARY KEY (ID))
CREATE TABLE TASKCONTENT (ID BIGINT NOT NULL, DESCRIPTION VARCHAR, PRIMARY KEY (ID))
CREATE TABLE EX_TASK_TEMPORAL (ID BIGINT NOT NULL, CREATION_TIME TIME, DESCRIPTION VARCHAR, DUE_DATE DATE, TITLE VARCHAR, UPDATE_TIME TIMESTAMP, PRIMARY KEY (ID))
CREATE TABLE TABULAR (ID BIGINT NOT NULL, TITLE VARCHAR NOT NULL, UUID LONGVARBINARY UNIQUE, PRIMARY KEY (ID))
CREATE TABLE EX_USER_WITH_EMBEDDED_ID (FIRSTNAME VARCHAR, LASTNAME VARCHAR, GROUPID BIGINT NOT NULL, USERID BIGINT NOT NULL, PRIMARY KEY (GROUPID, USERID))
CREATE TABLE TASK_COLLABORATOR (COLLABORATOR_ID BIGINT NOT NULL, TASK_ID BIGINT NOT NULL, PRIMARY KEY (COLLABORATOR_ID, TASK_ID))
ALTER TABLE EX_SIMPLE_USER ADD CONSTRAINT firstname_lastname UNIQUE (lastname, firstname)
ALTER TABLE TASK ADD CONSTRAINT FK_TASK_CREATOR_ID FOREIGN KEY (CREATOR_ID) REFERENCES USER (USER_ID)
ALTER TABLE TASK ADD CONSTRAINT FK_TASK_TASK_CONTENT_ID FOREIGN KEY (TASK_CONTENT_ID) REFERENCES TASKCONTENT (ID)
ALTER TABLE TASK ADD CONSTRAINT FK_TASK_TABULAR_ID FOREIGN KEY (TABULAR_ID) REFERENCES TABULAR (ID)
ALTER TABLE TASK_COLLABORATOR ADD CONSTRAINT FK_TASK_COLLABORATOR_TASK_ID FOREIGN KEY (TASK_ID) REFERENCES TASK (TASK_ID)
ALTER TABLE TASK_COLLABORATOR ADD CONSTRAINT FK_TASK_COLLABORATOR_COLLABORATOR_ID FOREIGN KEY (COLLABORATOR_ID) REFERENCES USER (USER_ID)
CREATE TABLE SEQUENCE (SEQ_NAME VARCHAR(50) NOT NULL, SEQ_COUNT NUMERIC(38), PRIMARY KEY (SEQ_NAME))
INSERT INTO SEQUENCE(SEQ_NAME, SEQ_COUNT) values ('SEQ_GEN', 0)
