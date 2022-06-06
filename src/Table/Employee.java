package Table;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Employee")

@NamedNativeQueries({
        @NamedNativeQuery(
                name = "top5employees",
                query = "SELECT DISTINCT (E.name) as nameEmp, SUM(HOUR (TIMEDIFF(time_end_working, time_start_working))) " +
                        " OVER (PARTITION BY id_employee) as total_hours FROM Timesheet " +
                        " JOIN Employee E on Timesheet.id_employee = E.id " +
                        " ORDER BY total_hours DESC limit 5;",
                resultSetMapping = "company_top5employees"
        ),
        @NamedNativeQuery(
                name = "select_employee",
                query = "SELECT * FROM Employee;",
                resultClass = Employee.class
        )
})

@SqlResultSetMapping(
        name = "company_top5employees",
        columns = {
                @ColumnResult(name = "nameEmp", type = String.class),
                @ColumnResult(name = "total_hours", type = BigInteger.class)
        }
)
public class Employee extends DbBase{
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "id_positions")
    private int id_positions;

    public Employee(){

    }

    public Employee(String name, int id_positions){
        setName(name);
        setId_positions(id_positions);
    }

    public int getId() {
        return id;
    }


    public void setName(String name) { this.name = name; }

    public String getName() {
        return name;
    }

    public void setId_positions(int id_positions) {
        this.id_positions = id_positions;
    }

    public int getId_positions() {
        return id_positions;
    }

    @Override
    public ArrayList<String> getNameColumns(){
        return new ArrayList<>(List.of("name"));
    }

    @Override
    public ArrayList<String> showInfo(){
        return new ArrayList<>(List.of(name));
    }

    @Override
    public void SetId(int newId) {
        this.id = newId;
    }

    @Override
    public String GetNameTable() {
        return "Employee";
    }
}
