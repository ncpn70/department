DROP TABLE DEPARTMENT IF EXISTS;
DROP TABLE EMPLOYEE IF EXISTS;

CREATE TABLE DEPARTMENT(
  departmentId BIGINT IDENTITY,
  departmentName VARCHAR(50) NOT NULL);

  CREATE TABLE EMPLOYEE(
    employeeId BIGINT IDENTITY,
    fullName VARCHAR(50) NOT NULL,
    birthDate Date NOT NULL,
    salary BIGINT NOT NULL,
    departmentId BIGINT NOT NULL);