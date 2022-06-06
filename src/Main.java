import Csv.CsvBase;
import Csv.Employee;
import Csv.Positions;
import Csv.Timesheet;
import Dao.TableDao;
import Table.DbBase;
import ToolsTable.TextTable;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {
    public static <T> CsvToBean<T> CSVReaderFile(T object, String fileName) {
        try {
            return new CsvToBeanBuilder<T>(new
                    FileReader(System.getProperty("user.dir") + "/src/LoadData/" +
                    fileName.toLowerCase())).withType((Class<? extends T>) object.getClass()).build();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }
    public static <T> void ImportData( T object, String table){
        TableDao tableDao = new TableDao();
        List<T> listObjects = null;
        int incorrect = 0;
        try {
            listObjects = Objects.requireNonNull(CSVReaderFile(object, table)).parse();
            for (var objDb: listObjects) {
                if(!tableDao.SaveObjectDB(((CsvBase) objDb).TransformCsvToDB()))  ++incorrect;
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
        System.out.println("Imported " + (listObjects != null ? listObjects.size() - incorrect : 0) + " positions");
        if(incorrect > 0) {
            System.out.println("Incorrect: " + incorrect);
        }
    }
    public static<T> void ShowSelect(ArrayList<T> objects){
        TextTable textTable = new TextTable(((DbBase) objects.get(0)).getNameColumns());
        for (var object: objects){
            textTable.addCol(((DbBase) object).showInfo());
        }
        textTable.printTable();
    }
    public static void main(String[] args) {
        Dao.TableDao tableDao = new Dao.TableDao();
        switch (args[0].toLowerCase()) {
            case "imports" -> {
                switch (args[1].toLowerCase()) {
                    case "positions.csv" -> ImportData(new Positions(), args[1]);
                    case "employees.csv" -> ImportData(new Employee(), args[1]);
                    case "timesheet.csv" -> ImportData(new Timesheet(), args[1]);
                }
            }
            case "list" -> ShowSelect(tableDao.SelListEmployee());
            case "get" -> ShowSelect(tableDao.SelTimesheetEmployee(args[1]));
            case "remove" -> tableDao.RemoveEmployeeDataTimesheet(args[1]);
            case "report" -> {
                switch (args[1].toLowerCase()){
                    case "top5longtasks" -> tableDao.Top5LongTasks();
                    case "top5costtasks" -> tableDao.Top5CostTasks();
                    case "top5employees" -> tableDao.Top5Employees();
                }
            }
        }
    }
}