package com.informationconfig.spring.bootcamp.bootcamp_proyect.models;


public class intern extends user {

    private String internUniversity;
    private String internSemester;
    private Task task[];
    private LogBook logBook[];


    public void setInternPassword(String internPassword) {
        this.internPassword = internPassword;
    }
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


}
