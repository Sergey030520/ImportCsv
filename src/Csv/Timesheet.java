package Csv;

import Dao.TableDao;
import Table.DbBase;
import Table.Task;
import com.opencsv.bean.CsvBindByPosition;

import java.sql.Timestamp;

public class Timesheet extends CsvBase {
    @CsvBindByPosition(position = 0)
    private String titleTask;
    @CsvBindByPosition(position = 1)
    private String nameEmployee;
    @CsvBindByPosition(position = 2)
    private Timestamp time_start_working;
    @CsvBindByPosition(position = 3)
    private Timestamp time_end_working;

    public Timesheet(){

    }

    public String getTitleTask() {
        return titleTask;
    }

    public String getNameEmployee() {
        return nameEmployee;
    }

    public Timestamp getTime_start_working() {
        return time_start_working;
    }

    public Timestamp getTime_end_working() {
        return time_end_working;
    }

    @Override
    public Table.Timesheet TransformCsvToDB(){
        TableDao tableDao = new TableDao();
        var employee = tableDao.FindIdForName("Employee", "name", nameEmployee,
                new Table.Employee());
        var task = tableDao.FindIdForName("Task", "title", titleTask, new Task());
        if(task == null){
            tableDao.SaveObjectDB(new Task(titleTask));
            task = tableDao.FindIdForName("Task", "title", titleTask, new Task());
        }
        return new Table.Timesheet(
                (employee != null ? employee.getId() : -1),
                (task != null ? task.getId() : -1),
                time_start_working, time_end_working);
    }
    @Override
    public void showInfo(){
        System.out.println("Name employee: " + this.nameEmployee
                + " title task: " + this.titleTask
                + " time start: " + this.time_start_working.toString()
                + " time end: " +  this.time_end_working.toString());
    }
}
