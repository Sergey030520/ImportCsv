CREATE DATABASE outsourcing_companies;

USE outsourcing_companies;

Create TABLE  Positions (
    id smallint primary key auto_increment,
    name varchar(30) unique not null,
    total_hours int not null
                        );

CREATE TABLE Employee(
    id smallint primary key unique auto_increment,
    name varchar(30) not null,
    id_positions smallint not null,
    FOREIGN KEY (id_positions) REFERENCES Positions(id) on DELETE CASCADE on UPDATE CASCADE
);


CREATE TABLE Task(
    id int primary key unique auto_increment,
    title varchar(50) unique not null
);


CREATE TABLE Timesheet(
    id int primary key unique auto_increment,
    id_employee smallint not null,
    id_task int not null,
    time_start_working timestamp not null,
    time_end_working timestamp not null,
    FOREIGN KEY (id_employee) REFERENCES Employee(id) on DELETE CASCADE on UPDATE CASCADE,
    FOREIGN KEY (id_task) REFERENCES Task(id) on DELETE CASCADE on UPDATE CASCADE,
    Check (time_end_working > time_start_working)
);

CREATE Table timesheet_history(
    id integer primary key auto_increment not null,
    id_employee smallint not null,
    task_title varchar(50) not null,
    time_start_working timestamp not null,
    time_end_working timestamp not null,
    UNIQUE (id_employee, task_title, time_end_working, time_start_working),
    FOREIGN KEY (id_employee) REFERENCES Employee(id),
    Check (time_end_working > time_start_working)
);

CREATE PROCEDURE CheckIntersectionTimesheet(id_employee smallint, new_time_start_working TIMESTAMP,
new_time_end_working TIMESTAMP)
BEGIN
    DECLARE countOverlap smallint;
        SET countOverlap = (SELECT  COUNT(*) as res FROM Timesheet as T
    where T.id_employee = id_employee AND
          ((new_time_start_working BETWEEN T.time_start_working AND T.time_end_working) OR
           (new_time_end_working BETWEEN T.time_start_working AND T.time_end_working)));
    if(countOverlap > 0) THEN
        signal sqlstate '45000' set message_text = 'Error: Do the employees timesheet overlap!';
    end if;
end;


DELIMITER //
CREATE TRIGGER InsertCheckIntersectTimesheetTrigger BEFORE INSERT ON Timesheet FOR EACH ROW
    BEGIN
        call CheckIntersectionTimesheet(New.id_employee ,NEW.time_start_working,
            NEW.time_end_working);
    end //
    DELIMITER ;


DELIMITER //
CREATE TRIGGER UpdateCheckIntersectTimesheetTrigger BEFORE UPDATE ON Timesheet FOR EACH ROW
    BEGIN
        call CheckIntersectionTimesheet(NEW.id_employee, NEW.time_start_working,
            NEW.time_end_working);
    end //
    DELIMITER ;

CREATE TRIGGER DelTimesheetTrigger BEFORE  DELETE on Timesheet FOR EACH ROW
    BEGIN
        DECLARE taskTitle varchar(40);
        SET taskTitle = (SELECT title FROM Task where OLD.id_task = Task.id);
        INSERT INTO timesheet_history (id, id_employee, task_title, time_start_working, time_end_working)
        VALUES (OLD.id, OLD.id_employee, taskTitle, OLD.time_start_working, OLD.time_end_working);
    end;
CREATE TRIGGER CheckingTasksDelete AFTER DELETE on Timesheet FOR EACH ROW
    BEGIN
        DELETE  FROM Task where (SELECT count(*) FROM Timesheet where id_task = Task.id) = 0;
    end;
