package Dao;

import Table.DbBase;
import Table.Employee;
import Table.Task;
import Table.Timesheet;
import Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import ToolsTable.TextTable;

public class TableDao implements ITableDao {
    private Session session;

    public TableDao() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    @Override
    public <T> T FindIdForName(String table, String column, String value, T classTb) {
        try {
            T selFindId = (T) session.createSQLQuery("SELECT * From %s where %s = '%s'"
                            .formatted(table, column, value))
                    .addEntity(classTb.getClass())
                    .uniqueResult();
            if (selFindId != null) {
                return selFindId;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public void InsertNewTask(Task obj) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(obj);
            session.flush();
            transaction.commit();
        } catch (Exception exception) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            exception.printStackTrace();
        }
    }

    @Override
    public <T> boolean SaveObjectDB(T obj) {
        Transaction transaction = session.beginTransaction();
        try {
            Object objectResFindId = CreateNewIndex(obj);
            ((DbBase) obj).SetId((objectResFindId == null ? 1 : ((BigInteger) objectResFindId).intValue()));
            session.save((T) obj);
            session.flush();
            transaction.commit();
            return true;
        } catch (Exception exception) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        }
    }

    @Override
    public ArrayList<Timesheet> SelTimesheetEmployee(String nameEmp) {
        try {
            int id_employee = FindIdForName("Employee", "name", nameEmp, new Employee()).getId();
            return (ArrayList<Timesheet>) session.createNamedQuery("select_timesheet")
                    .setParameter("id_employee", id_employee).list();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> Object CreateNewIndex(T object) {
        try {
            var findIndex = session.createSQLQuery("SELECT min(id+1) as resId FROM( SELECT id," +
                    " LEAD(id) OVER(ORDER BY Id)as next_id FROM ( " +
                    "SELECT 0 Id UNION ALL SELECT Id FROM " + ((DbBase) object).GetNameTable() + " )T )T " +
                    "WHERE id+1 <> next_id;");
            if (findIndex.uniqueResult() != null) {
                return findIndex.addScalar("resId").uniqueResult();
            } else {
                return session.createSQLQuery("SELECT max(id+1) as resId " +
                                " from " + ((DbBase) object).GetNameTable() + ";")
                        .addScalar("resId").uniqueResult();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public void RemoveEmployeeDataTimesheet(String nameEmployee) {
        Transaction transaction = null;
        try {
            for (var timesheet : SelTimesheetEmployee(nameEmployee)) {
                transaction = session.beginTransaction();
                if (timesheet != null) {
                    session.delete(timesheet);
                    System.out.println("Timesheet id " + timesheet.getId() + " removed!");
                }
                session.flush();
                transaction.commit();
            }
        } catch (Exception exception) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public void Top5CostTasks() {
        try {
            TextTable textTable = new TextTable(new ArrayList<>(Arrays.asList("name", "total_cost")));
            session.createNamedQuery("top5CostTasks").getResultList().forEach((record) -> {
                textTable.addCol(new ArrayList<>(Arrays.asList(((Object[]) record)[0].toString(),
                        (((Object[]) record)[1]).toString())));
            });
            textTable.printTable();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public void Top5Employees() {
        try {
            TextTable textTable = new TextTable(new ArrayList<>(Arrays.asList("name", "total_hours")));
            session.createNamedQuery("top5employees").getResultList().forEach((record) -> {
                textTable.addCol(new ArrayList<>(Arrays.asList(((Object[]) record)[0].toString(),
                        (((Object[]) record)[1]).toString())));
            });
            textTable.printTable();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public void Top5LongTasks() {
        try {
            TextTable textTable = new TextTable(new ArrayList<>(Arrays.asList("title", "spent_hours")));
            session.createNamedQuery("top5LongTasks").getResultList().forEach((record) -> {
                textTable.addCol(new ArrayList<>(Arrays.asList(((Object[]) record)[0].toString(),
                        (((Object[]) record)[1]).toString())));
            });
            textTable.printTable();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public ArrayList<Employee> SelListEmployee() {
        try {
            return (ArrayList<Employee>) session.createNamedQuery("select_employee").list();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }
}

