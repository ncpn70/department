SELECT departmentId, departmentName, AVG (EMPLOYEE.salary) AS "averageSalary"
FROM DEPARTMENT LEFT OUTER JOIN EMPLOYEE
ON DEPARTMENT.departmentId=EMPLOYEE.departmentId
WHERE departmentId = :departmentId
GROUP BY DEPARTMENT.departmentId;