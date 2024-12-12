```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

@Service
public class EmployeeService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void getEmployeeDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter employee number: ");
        int empNumber = scanner.nextInt();

        String sql = "SELECT ename, sal, comm FROM EMP WHERE EMPNO = ?";
        try {
            Employee employee = jdbcTemplate.queryForObject(sql, new Object[]{empNumber}, this::mapRowToEmployee);

            System.out.println("\n\nEmployee   Salary    Commission");
            System.out.println("--------   -------   ----------");
            System.out.printf("%s      %7.2f      ", employee.getEmpName(), employee.getSalary());

            if (employee.getCommission() == null) {
                System.out.println("NULL");
            } else {
                System.out.printf("%7.2f\n", employee.getCommission());
            }

        } catch (Exception e) {
            System.err.printf("Error: Employee with number [%d] does not exist.\n", empNumber);
        }
    }

    private Employee mapRowToEmployee(ResultSet rs, int rowNum) throws SQLException {
        Employee employee = new Employee();
        employee.setEmpName(rs.getString("ename"));
        employee.setSalary(rs.getFloat("sal"));
        employee.setCommission(rs.getFloat("comm"));
        return employee;
    }
}

// Employee.java
public class Employee {
    private String empName;
    private Float salary;
    private Float commission;

    // Getters and Setters
    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public Float getCommission() {
        return commission;
    }

    public void setCommission(Float commission) {
        this.commission = commission;
    }
}
```