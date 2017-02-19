Department
=====================
###### it is small application that allows work with two entities - Department and Employee.
###Structure
-----------------------------------
Project consists of five modules:
* **`model`**   -> contain POJOs.
* **`dao`**     -> contain DAOs.
* **`service`** -> contain business logic.
* **`rest`**    -> contain REST controllers.
* **`web`**     -> contain web controllers, which contact REST controllers.

`web` and `rest` work as not associated by code applications.

###System requirements
* Ubuntu 16.04(recommended, but not necessarily)
* maven 3.3.9
* git 2.7.4(recommended, but not necessarily)
* java 1.8
* tomcat 9

**I suppose that the above mentioned soft is already installed and configured.**


###Downloads
to get sources with git:
* executed command
  `git clone https://github.com/ncpn70/department.git`
or
* [download project in archive](https://codeload.github.com/ncpn70/department/zip/master)

Than you should [download](https://sourceforge.net/projects/hsqldb/files/hsqldb/hsqldb_2_3/hsqldb-2.3.4.zip/download) hsqldb.jar. We need it to create DB.

###Starting db and building project.
* First of all we must start DB, for what you should execute in terminal:(if necessary change the path to jar)

`java -cp hsqldb.jar org.hsqldb.Server -database.0 file:mydb -dbname.0 xdb`
* go to folder with project and execute
`mvn clean package` or `mvn clean package`
after successful buiding war files must be in target folders of web and rest.
Copy those war files in webapp folder of tomcat.

###Ссылки
*  **REST**
* `http://localhost:8080/rest/restDepartment/` selects all departments(GET)
* `http://localhost:8080/rest/restDepartment/` add a department(POST)
* `http://localhost:8080/rest/restDepartment/` update the department(PUT)
* `http://localhost:8080/rest/restDepartment/id` remove the department(DELETE), where id is a number of removed department.

* `http://localhost:8080/rest/restEmployee/` selects all employees(GET)
* `http://localhost:8080/rest/restEmployee/` add an employee(POST)
* `http://localhost:8080/rest/restEmployee/` update the employee(PUT)
* `http://localhost:8080/rest/restEmployee/id` remove the employee(DELETE), where id is a number of removed employee.
* `http://localhost:8080/rest/restEmployee/employeesByDepartmentId/depId` select employees by department number

*  **WEB**
* `http://localhost:8080/web/departments/` show main page to work with departments
* `http://localhost:8080/web/employees/`   show main page to work with employees

