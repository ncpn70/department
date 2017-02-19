package by.task.model;

/**
 * POJO class
 */
public class Department {
    private long departmentId;
    private String departmentName;

    private long averageSalary = 0;

    public Department(){}

    public Department(String departmentName, long averageSalary) {
        this.departmentName = departmentName;
        this.averageSalary = averageSalary;
    }

    public Department(long departmentId, String departmentName, long averageSalary) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.averageSalary = averageSalary;
    }

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }


    public long getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(long averageSalary) {
        this.averageSalary = averageSalary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Department that = (Department) o;

        if (departmentId != that.departmentId) return false;
        return departmentName.equals(that.departmentName);
    }

    @Override
    public int hashCode() {
        int result = (int)departmentId;
        result = 31 * result + departmentName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Department{" +
                "departmentId=" + departmentId +
                ", departmentName='" + departmentName + '\'' +
                ", averageSalary=" + averageSalary +
                '}';
    }
}
