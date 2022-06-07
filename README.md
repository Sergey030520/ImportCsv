# ImportCsv

## Описание


Разработанная программа - инструмент аналитики данных о времени, потраченном сотрудниками на те или иные задачи. Информация о задачах, сотрудниках и потраченном на задачи времени
импортируется в этот инструмент из файлов формата CSV, которые предварительно экспортированы из системы управления задачами.
Пргограмма имеет следующую функциональность, доступную через командную строку:
1) добавляет в БД указанные в файле positions.csv должности и ставки оплаты труда;
2) добавляет в БД указанные в файле employees.csv должности и ставки оплаты труда;
3) добавляет в БД указанные в файле timesheet.csv периоды работы сотрудников над задачами;
4) выводит и перечисляет сотрудников по именам;
5) выводит таймшиты сотрудника по его имени;
6) удаляет данные по сотруднику из таймшита по его имени;
7) выводит пять задач, на которые потрачено больше всего времени;
8) выводит пять задач, на которые потрачено больше всего денег;
9) выводит пять сотрудников, отработавших наибольшее количество времени за всё время.

## Инструкция по установке зависимостей
Для установки библиотеки mysql-connector с помощью Maven, необходимо в pom.xml вставить следующую зависимость:
 
```
<dependency>
      	<groupId>mysql</groupId>
      	<artifactId>mysql-connector-java</artifactId>
	<version>8.0.26</version>
</dependency>
```

Для установки библиотеки opencsv с помощью Maven, необходимо в pom.xml вставить следующую зависимость:

```
<dependency>
	<groupId>com.opencsv</groupId>
	<artifactId>opencsv</artifactId>
	<version>5.5.2</version>
</dependency>
```

Для установки библиотеки hibernate-core с помощью Maven, необходимо в pom.xml вставить следующую зависимость:

```
<dependency>
     	<groupId>org.hibernate</groupId>
     	<artifactId>hibernate-core</artifactId>
     	<version>5.5.8.Final</version>
</dependency>
```
В рассматриваемом проекте уже сгенерирован файл конфигурации [pom.xml](https://github.com/Sergey030520/ImportCsv/blob/5de852d2987df26a0815fee41a14cea63f46ebc9/pom.xml), который находится в корне проекта.

## Инструкция по сборке

Сборка проекта производится с помощью инструмента [Maven](https://maven.apache.org/). В терминале компьютера необходимо прописать следующие команды: 

```
mvn compile 

mvn package
```

После выполнения команд проект будет скомпилирован в папку target.

## Инструкция по использованию приложения

Для импорта данных из csv файлов необходимо чтобы файлы лежали в папке LoadData. 
Команда для импорта в БД указанные в файле positions.csv должности и ставки оплаты труда:

```
java -jar ImportCsv.jar import positions.csv
```

Команда для импорта в БД указанные в файле employees.csv должности и ставки оплаты труда:

```
java -jar ImportCsv.jar import employees.csv
```

Команда для импорта в БД указанные в файле timesheet.csv периоды работы сотрудников над задачами:

```
java -jar ImportCsv.jar import timesheet.csv
```

Команда для вывода сотрудников по именам:

```
java -jar ImportCsv.jar list
```

Команда для вывода таймшитов сотрудников по его имени

```
java -jar ImportCsv.jar get Jonah
```

Комманда для удаления данные по сотрудника из таймшитов по его имени:

```
java -jar ImportCsv.jar remove 0
```

Команда для вывода пяти задач, на которые потрачено больше всего времени:

```
java -jar ImportCsv.jar report top5longtasks
```

Команда для вывода пяти задач, на которые  потрачено больше всего денег:

```
java -jar ImportCsv.jar report top5costtasks
```

Команда для вывода пяти сотрудников, отработавших наибольшее количество времени за всё время:

```
java -jar ImportCsv.jar report top5employees
```

# Разработка БД аутсорсинг-компании
------------------------

## Концептуальное моделирование

![Диаграмма концептуальной модели данных](https://github.com/Sergey030520/ImportCsv/blob/0ef0a4808769bc108cb8454312a0cfd41864fe6c/SCHEMA/%D0%9A%D0%BE%D0%BD%D1%86%D0%B5%D0%BF%D1%82%D1%83%D0%B0%D0%BB%D1%8C%D0%BD%D0%B0%D1%8F%20%D0%B4%D0%B8%D0%B0%D0%B3%D1%80%D0%B0%D0%BC%D0%BC%D0%B0.jpg)

## Реляционное моделирование

![Диаграмма реляционной модели данных](https://github.com/Sergey030520/ImportCsv/blob/5de852d2987df26a0815fee41a14cea63f46ebc9/SCHEMA/diagram_relational_model.jpg)

## Разработка БД
В папке [SCHEMA](https://github.com/Sergey030520/ImportCsv/blob/0ef0a4808769bc108cb8454312a0cfd41864fe6c/SCHEMA/schema.sql) лежит инструкция к развёртыванию рассматриваемой бд.

