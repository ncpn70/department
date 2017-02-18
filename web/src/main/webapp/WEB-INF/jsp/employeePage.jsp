<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>

<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Department</title>

        <spring:url value="/resources/bootstrap/css/bootstrap.min.css" var="bootstrapMinCss"/>
        <spring:url value="/resources/bootstrap/js/bootstrap.min.js" var="bootstrapMinJs"/>
        <spring:url value="/resources/js/date.js" var="dateJs"/>
        <spring:url value="/departments/" var="departmentsPage"/>

        <link href="${bootstrapMinCss}" rel="stylesheet">
        <script src="http://code.jquery.com/jquery-latest.js"></script>
        <script src="${bootstrapMinJs}" type="text/javascript"></script>
        <script src="${dateJs}" type="text/javascript"></script>


    </head>
    <body>
        <div>
            <div>
                <div style="width:60%;float:left; padding: 10px;">
                    <table class="table" id="mainTable" summary="list of Departments.">
                        <tr class="active">
                            <td><h3>№</h3></td>
                            <td><h3>Имя</h3></td>
                            <td><h3>Дата рождения</h3></td>
                            <td><h3>Зарплата</h3></td>
                            <td><h3>Отдел</h3></td>
                            <td><h3>Обновить</h3></td>
                            <td><h3>Удалить</h3></td>
                        </tr>
                        <tbody>

                        </tbody>
                    </table>
                </div>
                <div style="padding: 10px;" class="form-inline">
                    <input type="date" id="from" class="form-control" name="from" value="2012-12-10" required="true"/>
                    <input type="date" id="to" class="form-control" name="to" value="2012-12-10" required="true"/>
                    <input class="btn btn-primary" type="button" id="findBtn" name="findBtn" value="Найти" onclick="filterByDate()"/>
                </div>
                <div style="padding: 10px;float:left; width: 20%">
                    <form action="<spring:url value='/employees/addEmployee'/>" method="post">
                        <div class="form-group">
                            <label for="name">Имя</label>
                            <input class="form-control" type="text" id="createName" name="fullName" value="" required="true" />
                        </div>
                        <div class="form-group">
                            <label for="date">Дата Рождения</label>
                            <input type="date" class="form-control" id="createDate" name="date" value="2012-12-10" required="true" />
                        </div>
                        <div class="form-group">
                            <label for="salary">Зарплата</label>
                            <input class="form-control" type="number" id="createSalary" name="salary" value="" required="true" />
                        </div>
                        <div class="form-group">
                            <label for="departmentId">Отдел</label>
                            <input class="form-control" type="number" id="departmentId" name="departmentId" value="" required="true" />
                        </div>
                        <input class="btn btn-secondary" type="reset" name="Reset" value="Reset">
                        <input class="btn btn-primary" type="submit" value="Add">
                    </form>
                </div>
                    <div class="updateForm" style="padding: 10px;float:left; width: 20%">
                        <form id="updateEmployeeForm" action="<spring:url value='/employees/updateEmployee'/>" method="put">
                            <input type="number" id="updateEmployeeId" name="employeeId" value="" required="true" hidden="true"/>
                            <div class="form-group">
                                <label for="fullName">Имя</label>
                                <input class="form-control" type="text" id="updateFullName" name="fullName" value="" required="true" />
                            </div>
                            <div class="form-group">
                                <label for="date">Дата Рождения</label>
                                <input type="date" class="form-control" id="updateDate" name="date" value="2012-12-10" required="true" />   
                            </div>
                            <div class="form-group">
                                <label for="salary">Зарплата</label>
                                <input type="number" class="form-control" id="updateSalary" name="salary" value="" required="true" />
                            </div>
                            <div class="form-group">
                                <label for="departmentId">Отдел</label>
                                <input type="number" class="form-control" id="upDepartmentId" name="departmentId" value="" required="true" />
                            </div>
                            <input class="btn btn-secondary" type="reset" name="Reset" value="Reset">
                            <input class="btn btn-primary" type="submit" value="Update">
                        </form>
                    </div>
            </div>

            <div style="float: bottom; padding: 10px;">
                
                <div></div>
                <a class="btn btn-primary btn-large" href="<spring:url value='/departments/'/>">Все Отделы</a>   
            </div>
        </div>

        <script type="text/javascript">
            $(fillInTable);

            function fillInTable() {
                var stringHtml = "";

            <c:forEach var="employee" items="${employees}">
                stringHtml += setStringHtml(${employee.employeeId}, '${employee.fullName}', '${employee.birthDate}', ${employee.salary}, ${employee.departmentId});
            </c:forEach>
                $(document.getElementById("mainTable").getElementsByTagName("tbody")[0]).append(stringHtml);
            }


            function cleanMainTable(){

                var table = document.getElementById("mainTable");
                var rows = table.getElementsByTagName("tr");
                var length = rows.length;

                for ( var i = 1; i < length; i++ )  {   
                    table.deleteRow(1);                                                                               
                }
            }

            function filterByDate() {
                var tbody = document.getElementById("mainTable").getElementsByTagName("tbody")[0];
                var from = $("#from").val();
                var to = $("#to").val();
                var actionFlag;
                var stringHtml;

                if (from == "" && to == "") {
                    return;
                } else if(from != "" && to == ""){
                    actionFlag = 0;
                } else if(from == ""){
                    actionFlag = 1;
                } else if (from > to){
                    return;
                } else {
                    actionFlag = 2;
                }
                
                cleanMainTable();
                
            <c:forEach var="employee" items="${employees}">
                if(actionFlag == 0 && "${employee.birthDate}" >= from){
                    stringHtml += setStringHtml(${employee.employeeId}, '${employee.fullName}', '${employee.birthDate}', ${employee.salary}, ${employee.departmentId});
                } else if(actionFlag == 1 && "${employee.birthDate}" <= to){
                    stringHtml += setStringHtml(${employee.employeeId}, '${employee.fullName}', '${employee.birthDate}', ${employee.salary}, ${employee.departmentId});
                } else if(actionFlag == 2 && ("${employee.birthDate}" >= from && "${employee.birthDate}" <= to)){
                    stringHtml += setStringHtml(${employee.employeeId}, '${employee.fullName}', '${employee.birthDate}', ${employee.salary}, ${employee.departmentId});
                }     
            </c:forEach>
            
            $(document.getElementById("mainTable").getElementsByTagName("tbody")[0]).append(stringHtml);

            }


            function fillInUpdateForm(employeeId, updateFullName, birthDate, salary, departmentId) {

                $('#updateEmployeeId').val(employeeId);
                $('#updateFullName').val(updateFullName);
                $('#updateDate').val(birthDate);
                $('#updateSalary').val(salary);
                $('#upDepartmentId').val(departmentId);
            }
            ;

            function setStringHtml(employeeId, fullName, birthDate, salary, departmentId) {
                var tempStr = "";
                var tempStr2 = "";
                var strName = "\'" + fullName + "\'";
                var birthDate = "\'" + birthDate + "\'";


                tempStr += "<tr class=\"info\"><td>" + (employeeId + 1);

                tempStr += "<td>" + strName;

                tempStr += "<td>" + birthDate;

                tempStr += "<td>" + salary;

                tempStr += "<td>" + departmentId;


                tempStr += "<td>" + "<input type=\"button\" class=\"btn btn-primary\" value=\"update\" onClick=" + "\"fillInUpdateForm(" + employeeId + "," + strName + "," + birthDate + "," + salary + "," + departmentId + ")\"" + ">";
                tempStr += "<td>" + "<form action=\"<spring:url value='/employees/removeEmployee'/>\" method=\"delete\">" +
                        "<input type=\"text\" id=\"employeeId\" name=\"employeeId\" value=" + employeeId + " hidden=\"true\">" +
                        "<input type=\"submit\" class=\"btn btn-danger\" value=\"remove\" ></form>";

                return tempStr;

            }

        </script>
    </body>
</html>
