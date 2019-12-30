package com.netcracker.model;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Greeting {

    private String firstName = "Сергей";
    private String lastName = "Кузин";
    private String patronymic = "Иванович";
    private int age = 25;
    private double salary = 32000;
    private String email = "kuzins4099ergey4313@yandex.ru";
    private String job = "Шоколадница";

    public Greeting() {
    }

    public Greeting(String firstName, String lastName,
                    String patronymic, int age, double salary, String email, String job) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.age = age;
        this.salary = salary;
        this.email = email;
        this.job = job;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Greeting greeting = (Greeting) o;

        if (age != greeting.age) return false;
        if (Double.compare(greeting.salary, salary) != 0) return false;
        if (!firstName.equals(greeting.firstName)) return false;
        if (!lastName.equals(greeting.lastName)) return false;
        if (!patronymic.equals(greeting.patronymic)) return false;
        if (!email.equals(greeting.email)) return false;
        return job.equals(greeting.job);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + patronymic.hashCode();
        result = 31 * result + age;
        temp = Double.doubleToLongBits(salary);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + email.hashCode();
        result = 31 * result + job.hashCode();
        return result;
    }

    public void fromExcel(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        Workbook wb = new XSSFWorkbook(fis);

        lastName = wb.getSheetAt(0).getRow(0).getCell(0).getStringCellValue();
        firstName = wb.getSheetAt(0).getRow(1).getCell(0).getStringCellValue();
        patronymic = wb.getSheetAt(0).getRow(2).getCell(0).getStringCellValue();
        age = (int) wb.getSheetAt(0).getRow(3).getCell(0).getNumericCellValue();
        salary = wb.getSheetAt(0).getRow(4).getCell(0).getNumericCellValue();
        email = wb.getSheetAt(0).getRow(5).getCell(0).getStringCellValue();
        job = wb.getSheetAt(0).getRow(6).getCell(0).getStringCellValue();

        fis.close();
    }

    public void toExcel() throws IOException {
        File file = new File("my.xlsx");
        FileInputStream fis = new FileInputStream(file);
        Workbook wb = new XSSFWorkbook(fis);
        Sheet sheet0 = wb.getSheetAt(0);
        int size = 7; //всего 7 строк
        Row[] rows = new Row[size];    //7 строк всего
        Row row = sheet0.getRow(0);
        int max = 0;
        for (Cell cell : row) {
            max = cell.getColumnIndex();
        }
        max++;  //вычислили последний столбец

        Cell[] cells = new Cell[size];
        for (int i = 0; i < size; i++) {
            rows[i] = sheet0.getRow(i);
            cells[i] = rows[i].createCell(max);
        }

        cells[0].setCellValue(lastName);
        cells[1].setCellValue(firstName);
        cells[2].setCellValue(patronymic);
        cells[3].setCellValue(age);
        cells[4].setCellValue(salary);
        cells[5].setCellValue(email);
        cells[6].setCellValue(job);

        FileOutputStream fos = new FileOutputStream(file);
        wb.write(fos);
        fis.close();
    }

    //поиск в файле по фамилии и имени
    public Greeting searchByName() throws IOException {
        Greeting greet = new Greeting("0", "0",
                "0", 0,0,"0", "0");
        File file = new File("my.xlsx");
        FileInputStream fis = new FileInputStream(file);
        Workbook wb = new XSSFWorkbook(fis);
        Sheet sheet0 = wb.getSheetAt(0);
        Row row0 = sheet0.getRow(0);
        Row row1 = sheet0.getRow(1);
        String tempLast = lastName;
        String tempFirst = firstName;
        
        int cell1 =-1, cell2=-2;
        for (Cell cell : row0) {
            if(tempLast.equals(cell.getStringCellValue())) {
                cell1 = cell.getColumnIndex();
                break;
            }
        }
        for (Cell cell : row1) {
            if(tempFirst.equals(cell.getStringCellValue())) {
                cell2 = cell.getColumnIndex();
                break;
            }
        }
        if(cell1==cell2) {
            greet.setLastName(lastName);
            greet.setFirstName(firstName);
            greet.setPatronymic(sheet0.getRow(2).getCell(cell1).getStringCellValue());
            greet.setAge((int) sheet0.getRow(3).getCell(cell1).getNumericCellValue());
            greet.setSalary(sheet0.getRow(4).getCell(cell1).getNumericCellValue());
            greet.setEmail(sheet0.getRow(5).getCell(cell1).getStringCellValue());
            greet.setJob(sheet0.getRow(6).getCell(cell1).getStringCellValue());
        }
        fis.close();
        return greet;
    }
}
