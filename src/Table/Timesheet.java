package Table;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

@Entity
@Table(name = "Timesheet")
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "select_timesheet",
                query = "SELECT * FROM Timesheet as T where T.id_employee in (:id_employee)",
                resultClass = Timesheet.class
        )
})
public class Timesheet extends DbBase{
    @Id
    @Column(name = "id")
    private int id = -1;
    @Column(name = "id_employee")
    private int id_employee = -1;
    @Column(name = "id_task")
    private int id_task = -1;
    @Column(name = "time_start_working")
    private Timestamp time_start_working;
    @Column(name = "time_end_working")
    private Timestamp time_end_working;

    public Timesheet(){}

    public Timesheet(int id_employee, int id_task,
                     Timestamp time_start_working,
                     Timestamp time_end_working){
        setId_employee(id_employee);
        setId_task(id_task);
        setTime_start_working(time_start_working);
        setTime_end_working(time_end_working);
    }

    public void setId_task(int id_task) {
        this.id_task = id_task;
    }

    public void setTime_start_working(Timestamp time_start_working) {
        this.time_start_working = time_start_working;
    }

    public void setTime_end_working(Timestamp time_end_working) {
        this.time_end_working = time_end_working;
    }

    public void setId_employee(int id_employee) {
        this.id_employee = id_employee;
    }

    public int getId() {
        return id;
    }

    public int getId_task() {
        return id_task;
    }

    public int getId_employee() {
        return id_employee;
    }

    public Timestamp getTime_start_working() {
        return time_start_working;
    }

    public Timestamp getTime_end_working() {
        return time_end_working;
    }

    @Override
    public void SetId(int newId) {
        this.id = newId;
    }

    @Override
    public ArrayList<String> getNameColumns(){
        return new ArrayList<>(Arrays.asList("id","Id employee", "Id task", "time_start_working", "time_end_working"));
    }
    @Override
    public ArrayList<String> showInfo(){
        return new ArrayList<>(Arrays.asList(String.valueOf(id), String.valueOf(id_employee), String.valueOf(id_task),
                String.valueOf(time_start_working), String.valueOf(time_end_working)));
    }

    @Override
    public String GetNameTable() {
        return "Timesheet";
    }
}
