package by.task.model;

/**
 * Created by Kolbik Viktor on 7.2.17.
 */
public class Department {
    private long departmentId;
    private String departmentName;

    public Department(){}

    public Department(String departmentName) {
        this.departmentName = departmentName;
    }

    public Department(long departmentId, String departmentName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
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
                '}';
    }
}
