package by.task.model;

import java.sql.Date;
import java.time.LocalDate;

/**
 * POJO class
 */
public class Employee {
    private long employeeId;
    private String fullName;
    private Date birthDate;
    private long salary;
    private long departmentId;

    public Employee(){}

    public Employee(long employeeId, String fullName, java.sql.Date birthDate, long salary, long departmentId) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.salary = salary;
        this.departmentId = departmentId;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public java.sql.Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(java.sql.Date birthDate) {
        this.birthDate = birthDate;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (employeeId != employee.employeeId) return false;
        if (salary != employee.salary) return false;
        if (departmentId != employee.departmentId) return false;
        if (!fullName.equals(employee.fullName)) return false;
        return birthDate.equals(employee.birthDate);
    }

    @Override
    public int hashCode() {
        int result = (int)employeeId;
        result = 31 * result + fullName.hashCode();
        result = 31 * result + birthDate.hashCode();
        result = 31 * result + (int)(salary ^ (salary >>> 32));
        result = 31 * result + (int)departmentId;
        return result;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", fullName='" + fullName + '\'' +
                ", birthDate=" + birthDate +
                ", salary=" + salary +
                ", departmentId=" + departmentId +
                '}';
    }
}
