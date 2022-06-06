package Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Arrays;

@Entity
@Table
public class Positions extends DbBase{
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "total_hours")
    private int total_hours;

    public Positions(){}

    public Positions(String name, int total_hours){
        setName(name);
        setTotal_hours(total_hours);
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTotal_hours(int total_hours) {
        this.total_hours = total_hours;
    }

    public int getTotal_hours() {
        return total_hours;
    }

    @Override
    public void SetId(int newId) {
        this.id = newId;
    }
    @Override
    public ArrayList<String> getNameColumns(){
        return new ArrayList<>(Arrays.asList("name", "total_hours"));
    }
    @Override
    public ArrayList<String> showInfo(){
        return new ArrayList<>(Arrays.asList(name, String.valueOf(total_hours)));
    }

    @Override
    public String GetNameTable() {
        return "Positions";
    }
}
