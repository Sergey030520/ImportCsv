package Table;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table (name = "Task")
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "top5CostTasks",
                query = "SELECT DISTINCT (title) as title, SUM(total_hours*(" +
                        "HOUR(TIMEDIFF(time_end_working, time_start_working)))) OVER (PARTITION BY id_task) " +
                        " as total_cost FROM Timesheet JOIN Employee E on Timesheet.id_employee = E.id " +
                        " JOIN Positions P on P.id = E.id_positions " +
                        " JOIN Task T on T.id = Timesheet.id_task " +
                        " ORDER BY total_cost DESC limit 5;",
                resultSetMapping = "company_top5CostTasks"
        ),
        @NamedNativeQuery(
                name = "top5LongTasks",
                query = "SELECT DISTINCT (title), SUM(HOUR(TIMEDIFF(time_end_working, time_start_working))) " +
                        " OVER (PARTITION BY id_task) as spent_hours FROM Timesheet " +
                        " JOIN Task T on Timesheet.id_task = T.id ORDER BY spent_hours DESC limit 5;",
                resultSetMapping = "company_top5longTasks"
        ),
        @NamedNativeQuery(
                name = "select_task",
                query = "SELECT * FROM Task;",
                resultClass = Task.class
        )
})

@SqlResultSetMapping(
        name = "company_top5CostTasks",
        columns = {
                @ColumnResult(name = "title", type = String.class),
                @ColumnResult(name = "total_cost", type = BigInteger.class)
        }
)
@SqlResultSetMapping(
        name = "company_top5longTasks",
        columns = {
                @ColumnResult(name = "title", type = String.class),
                @ColumnResult(name = "spent_hours", type = BigInteger.class)
        }
)

public class Task extends DbBase{
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "title")
    private String title;

    public Task(){

    }
    public Task(String title){
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public void SetId(int newId) {
        this.id = newId;
    }

    @Override
    public ArrayList<String> getNameColumns(){
        return new ArrayList<>(List.of("title"));
    }
    @Override
    public ArrayList<String> showInfo(){
        return new ArrayList<>(List.of(title));
    }

    @Override
    public String GetNameTable() {
        return "Task";
    }
}



