package com.alesya07.lab1.model;

public class Student {
    private long id;
    private String name;
    private String surname;
    private String patronymic;
    private String email;
    private int enrollmentYear;

    public Student() {}

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public String getPatronymic() { return patronymic; }
    public void setPatronymic(String patronymic) { this.patronymic = patronymic; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getEnrollmentYear() { return enrollmentYear; }
    public void setEnrollmentYear(int enrollmentYear) { this.enrollmentYear = enrollmentYear; }


    public String getGroup() {
        int digits = enrollmentYear % 100;
        return String.format("ПИНз-1%d", digits);
    }

    public String getLogin() {
        int digits = enrollmentYear % 100;
        return String.format("student-pinz1%d-%d", digits, id);
    }
}
