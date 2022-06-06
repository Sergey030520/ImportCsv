package Csv;

import Table.DbBase;

public class CsvBase {
    public CsvBase(){}
    public DbBase TransformCsvToDB(){
        return new DbBase();
    }
    public void showInfo(){
        System.out.println("Base class!");
    }
}
