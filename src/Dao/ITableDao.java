package Dao;

import Table.Employee;
import Table.Task;
import Table.Timesheet;
import java.sql.SQLException;
import java.util.ArrayList;


public interface ITableDao {
    <T> T FindIdForName(String table, String column, String value, T classTb);

    void InsertNewTask(Task obj);

    <T> boolean SaveObjectDB(T obj) throws SQLException;

    ArrayList<Timesheet> SelTimesheetEmployee(String nameEmp);

    <T> Object CreateNewIndex(T object);

    void RemoveEmployeeDataTimesheet(String nameEmployee);

    void Top5LongTasks();

    void Top5CostTasks();

    void Top5Employees();

    ArrayList<Employee> SelListEmployee();
}
