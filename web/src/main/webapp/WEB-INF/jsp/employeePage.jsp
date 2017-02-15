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
                <div style="width:50%;float:left">
                    <table class="table" id="mainTable" summary="list of Departments.">
                        <tr>
                            <td><h2>№</h2></td>
                            <td><h2>Имя</h2></td>
                            <td><h2>Дата рождения</h2></td>
                            <td><h2>Зарплата</h2></td>
                            <td><h2>Обновить</h2></td>
                            <td><h2>Удалить</h2></td>
                        </tr>
                        <tbody>

                        </tbody>
                    </table>
                </div>
                <div style="with: 50%; float:left">
                    <form action="<spring:url value='/employees/addEmployee'/>" method="post">
                        <table>
                            <tbody>
                                <tr>
                                    <td><label for="name">Имя</label></td>
                                    <td><input type="text" id="createName" name="fullName" value="" required="true" /></td>
                                </tr>
                                <tr>
                                    <td><label for="date">Дата Рождения</label></td>
                                    <td><input type="date" class="dateInput" id="createDate" name="date" value="" required="true" /></td>
                                </tr>
                                <tr>
                                    <td><label for="name">Зарплата</label></td>
                                    <td><input type="number" id="createSalary" name="salary" value="" required="true" /></td>
                                </tr>
                                <tr>
                                    <td><label for="name">Отдел</label></td>
                                    <td><input type="number" id="departmentId" name="departmentId" value="" required="true" /></td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td><input type="reset" name="Reset" value="Reset">
                                        <input type="submit" value="Add">
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </form>
                </div>

                <div class="updateForm" style="float:left">
                    <form id="updateEmployeeForm" action="<spring:url value='/employees/updateEmployee'/>" method="put">
                        <table>
                            <tbody>
                            <input type="number" id="updateEmployeeId" name="employeeId" value="" hidden="true"/>
                            <tr>
                                <td><label for="fullName">Имя</label></td>
                                <td><input type="text" id="updateFullName" name="fullName" value="" required="true" /></td>
                            </tr>
                            <tr>
                                <td><label for="date">Дата Рождения</label></td>
                                <td><input type="date" class="dateInput" id="updateDate" name="date" value="" required="true" /></td>
                            </tr>
                            <tr>
                                <td><label for="name">Зарплата</label></td>
                                <td><input type="text" id="updateSalary" name="salary" value="" required="true" /></td>
                            </tr>
                            <tr>
                                <td><label for="name">Отдел</label></td>
                                <td><input type="number" id="upDepartmentId" name="departmentId" value="" required="true" /></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td><input type="reset" name="Reset" value="reset">
                                    <input type="submit" value="update">
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
            </div>
            <div>
                <table>
                    <tbody>
                        <tr>
                            <td><label for="from">От</label></td>
                            <td><input type="date" id="from" class="dateInput" name="from" required="true"/></td>
                        </tr>
                        <tr>
                            <td><label for="to">До</label></td>
                            <td><input type="date" id="to" class="dateInput" name="to" required="true"/></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td><input type="button" id="findBtn" name="findBtn" value="Найти" onclick="filterByDate()"/></td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div>
                <a class="btn btn-primary btn-large" href="<spring:url value='/departments/'/>">Отделы"</a>   
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


                tempStr += "<tr><td>" + employeeId;

                tempStr += "<td>" + strName;

                tempStr += "<td>" + birthDate;

                tempStr += "<td>" + salary;

                tempStr += "<td>" + departmentId;


                tempStr += "<td>" + "<input type=\"button\" value=\"update\" onClick=" + "\"fillInUpdateForm(" + employeeId + "," + strName + "," + birthDate + "," + salary + "," + departmentId + ")\"" + ">";
                tempStr += "<td>" + "<form action=\"<spring:url value='/employees/removeEmployee'/>\" method=\"delete\">" +
                        "<input type=\"text\" id=\"employeeId\" name=\"employeeId\" value=" + employeeId + " hidden=\"true\">" +
                        "<input type=\"submit\" value=\"remove\" ></form>";

                return tempStr;

            }

        </script>
    </body>
</html>
