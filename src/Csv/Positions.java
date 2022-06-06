package Csv;

import Table.DbBase;
import com.opencsv.bean.CsvBindByPosition;

public class Positions extends CsvBase{
    @CsvBindByPosition(position = 0)
    private String namePosition;
    @CsvBindByPosition(position = 1)
    private int total_hours;

    public Positions(){

    }

    public String getNamePosition() {
        return namePosition;
    }

    public int getTotal_hours() {
        return total_hours;
    }

    @Override
    public Table.Positions TransformCsvToDB() {
        return new Table.Positions(namePosition, total_hours);
    }

    @Override
    public void showInfo() {
        System.out.println("position: " + namePosition + " total hours: " + total_hours);
    }
}
