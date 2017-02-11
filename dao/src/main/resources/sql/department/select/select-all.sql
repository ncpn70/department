SELECT departmentId, departmentName,
AVG(EMPLOYEE.salary) AS "averageSalary"
FROM DEPARTMENT LEFT OUTER JOIN EMPLOYEE
ON DEPARTMENT.departmentId=EMPLOYEE.departmentId
GROUP BY DEPARTMENT.departmentId;