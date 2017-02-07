package by.task;

import java.time.LocalDate;

/**
 * Created by Kolbik Viktor on 7.2.17.
 */
public class Employee {
    private int employeeId;
    private String fullName;
    private LocalDate birthDate;
    private long salary;
    private int departmentId;

    public Employee(){}

    public Employee(int employeeId, String fullName, LocalDate birthDate, long salary, int departmentId) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.salary = salary;
        this.departmentId = departmentId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
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
        int result = employeeId;
        result = 31 * result + fullName.hashCode();
        result = 31 * result + birthDate.hashCode();
        result = 31 * result + (int) (salary ^ (salary >>> 32));
        result = 31 * result + departmentId;
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
