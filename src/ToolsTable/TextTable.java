package ToolsTable;

import java.util.ArrayList;

public class TextTable {
    int maxSizeCol, countCol;
    ArrayList<String> nameColumns;

    ArrayList<ArrayList<String>> table;
    public TextTable(int col){
        countCol = col;
        nameColumns = new ArrayList<>(col);
        table = new ArrayList<>();
    }
    public TextTable(ArrayList<String> inNameColumns){
        countCol = inNameColumns.size();
        nameColumns = inNameColumns;
        for (var val: nameColumns){
            maxSizeCol = Math.max(val.length(), maxSizeCol);
        }
        table = new ArrayList<>();
    }
    private void resizeColumn(ArrayList<String> values){
        for (var val: values) {
            maxSizeCol = Math.max(val.length(), maxSizeCol);
        }
    }
    public void setNameColumns(ArrayList<String> nameColumns) {
        this.nameColumns = nameColumns;
        resizeColumn(nameColumns);
    }

    public void addCol(ArrayList<String> colObj){
        table.add(colObj);
        resizeColumn(colObj);
    }
    public int getMaxSizeTable(){
        return maxSizeCol*countCol + (2*countCol);
    }
    private void printColumn(ArrayList<String> column){
        for (var value : column){
            int freePos = maxSizeCol - value.length()+1;
            System.out.print("|" + " ".repeat(freePos/2) + value + " ".repeat(
                    (freePos/2) + (freePos % 2 != 0 ? 1 : 0)));
        }
        System.out.println("|");
    }
    public void printTable(){
        int maxSize = getMaxSizeTable();
        System.out.println("-".repeat(maxSize));
        printColumn(nameColumns);//вывод названия колонн
        System.out.println("|"+"=".repeat(maxSize-1)+"|");
        for (var column : table) {
            printColumn(column);
        }
        System.out.println("-".repeat(maxSize));
    }
}
