package com.informationconfig.spring.bootcamp.bootcamp_proyect.models;


public class Intern extends User {

    private String internUniversity;
    private String internSemester;
    private Task[] task;
    private LogBook[] logBook;

    public String getInternUniversity() {
        return internUniversity;
    }
    public void setInternUniversity(String internUniversity) {
        this.internUniversity = internUniversity;
    }
    public String getInternSemester() {
        return internSemester;
    }
    public void setInternSemester(String internSemester) {
        this.internSemester = internSemester;
    }
    public Task[] getTask() {
        return task;
    }
    public void setTask(Task[] task) {
        this.task = task;
    }
    public LogBook[] getLogBook() {
        return logBook;
    }
    public void setLogBook(LogBook[] logBook) {
        this.logBook = logBook;
    }

}
