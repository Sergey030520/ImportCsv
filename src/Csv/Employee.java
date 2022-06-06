package Csv;

import Dao.TableDao;
import Table.Positions;
import com.opencsv.bean.CsvBindByPosition;

public class Employee extends CsvBase{
    @CsvBindByPosition(position = 0)
    private String nameEmployee;
    @CsvBindByPosition(position = 1)
    private String namePosition;

    public Employee(){

    }
    public String getNameEmployee() {
        return nameEmployee;
    }

    public String getNamePosition() {
        return namePosition;
    }

    @Override
    public Table.Employee TransformCsvToDB() {
        return new Table.Employee(nameEmployee,
                new TableDao().FindIdForName("Positions", "name",
                        namePosition, new Positions()).getId());
    }

    @Override
    public void showInfo() {
        System.out.println("name: " + nameEmployee + " position: " + namePosition);
    }
}
