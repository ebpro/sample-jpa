ALTER TABLE TASK DROP CONSTRAINT FK_TASK_CREATOR_ID
ALTER TABLE TASK DROP CONSTRAINT FK_TASK_TASKCONTENT_ID
ALTER TABLE TASK DROP CONSTRAINT FK_TASK_TABULAR_ID
ALTER TABLE EX_SIMPLE_USER DROP CONSTRAINT firstname_lastname
ALTER TABLE TASK_COLLABORATOR DROP CONSTRAINT FK_TASK_COLLABORATOR_TASK_ID
ALTER TABLE TASK_COLLABORATOR DROP CONSTRAINT FK_TASK_COLLABORATOR_COLLABORATOR_ID
DROP TABLE EX_USER_WITH_IDCLASS
DROP TABLE EX_SIMPLE_USER_AUTO
DROP TABLE USER
DROP TABLE TASK
DROP TABLE EX_SIMPLE_USER
DROP TABLE TASKCONTENT
DROP TABLE EX_TASK_TEMPORAL
DROP TABLE TABULAR
DROP TABLE EX_USER_WITH_EMBEDDED_ID
DROP TABLE TASK_COLLABORATOR
DELETE FROM SEQUENCE WHERE SEQ_NAME = 'SEQ_GEN'