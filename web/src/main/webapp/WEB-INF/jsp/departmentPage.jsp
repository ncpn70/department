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
                    <div style="padding: 10px;width:50%;float:left">
                        <table class="table" id="mainTable" summary="list of Departments.">
                                <tr class="active">
                                    <td><h2>№</h2></td>
                                    <td><h2>Название</h2></td>
                                    <td><h2>Средняя зп</h2></td>
                                    <td><h2>Обновить</h2></td>
                                    <td><h2>Удалить</h2></td>
                                </tr>
                                <tbody>

	                            </tbody>
                        </table>
                    </div>
                   <div style="padding: 10px; with: 50%; float:bottom">
                       <form class="form-inline" action="<spring:url value='/departments/addDepartment'/>" method="post">

                           <input type="text" class="form-control mb-2 mr-sm-2 mb-sm-0" id="createName" name="name" placeholder="Department name" value="" required="true" />
                           <input class="btn btn-secondary" type="reset" name="Reset" value="Reset">
                           <input class="btn btn-primary" type="submit" value="Add">
                       </form>
                   </div>
                       <p></p>

                   <div style="padding: 10px; with: 50%; float:bottom" class="updateForm">
                       <form class="form-inline" id="updateDepartmentForm" action="<spring:url value='/departments/updateDepartment'/>" method="put">
                           <input type="text" id="updateDepartmentId" name="departmentId" value="" required="true" hidden="true"/>
                           <input type="text"  class="form-control mb-2 mr-sm-2 mb-sm-0" id="updateDepartmentName" placeholder="Department name" name="departmentName" value="" required="true" /></td>
                           <input class="btn btn-secondary" type="reset" name="Reset" value="Reset">
                           <input class="btn btn-primary" type="submit" value="update">
                       </form>
                   </div>
               </div>
        </div>
            <script type="text/javascript">
            $(fillInTable);
            
            function fillInTable(){
                var stringHtml = "";
                
                <c:forEach var="department" items="${departments}">
                    stringHtml += setStringHtml(${department.departmentId}, '${department.departmentName}', ${department.averageSalary});
                </c:forEach>
            
                $(document.getElementById("mainTable").getElementsByTagName("tbody")[0]).append(stringHtml);
            
            }
            
            function fillInUpdateForm(departmentId, departmentName){

                $('#updateDepartmentId').val(departmentId);
                $('#updateDepartmentName').val(departmentName);
            };
            
            function setStringHtml(departmentId, name, averageSalary){
                var tempStr = "";
                var tempStr2 = "";
                var strName = "\'" + name + "\'";
                
                
                tempStr += "<tr  class=\"info\" ><td>" + "<form action=\"<spring:url value='/employees/employeesOfDepartment/'/>\">" +
                "<input type=\"text\" id=\"departmentId\" name=\"departmentId\" value=" + departmentId + " hidden=\"true\">" + 
                "<input type=\"submit\" class=\"btn btn-link\" value=" + departmentId + " ></form>";
                
                tempStr += "<td>"  + name;
                
                tempStr += "<td>" + averageSalary;


                 tempStr += "<td>" + "<input type=\"button\" class=\"btn btn-primary\" value=\"update\" onClick=" + "\"fillInUpdateForm(" + departmentId + "," + strName + ")\"" + ">";
             tempStr += "<td>" + "<form action=\"<spring:url value='/departments/removeDepartment'/>\" method=\"delete\">" +
                                                                 "<input type=\"text\" id=\"departmentId\" name=\"departmentId\" value=" + departmentId + " hidden=\"true\">" +
                                                                 "<input type=\"submit\" class=\"btn btn-danger\" value=\"remove\" ></form>";
                                                                 
                return tempStr;
                
            }
            
            </script>
    </body>
</html>
