package Model;

import java.io.*;
import java.util.ArrayList;

public class Student {
    private static ArrayList<Student> studentArrayList = initArray();
    public Group group;
    public Faculty faculty;
    public Institute institute;
    private int studentID;
    private String login;
    private String name;
    private String surname;

    private Student(int studentID, String login, String name, String surname, int groupID, int facultyID, int instituteID) {
        this.studentID = studentID;
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.group = Group.get(groupID);
        this.faculty = Faculty.get(facultyID);
        this.institute = Institute.get(instituteID);
    }

    public Student() {

    }

    public Student(String login, String name, String surname, int group, int faculty, int institute) {
        int ID = 1;
        while (true) {
            boolean isFree = true;// if ID is not occupied
            for (Student student : studentArrayList) {
                if (ID == student.studentID) {
                    isFree = false;
                    break;
                }
            }
            if (isFree) {
                this.studentID = ID;// we use first free ID
                break;
            }
            ID++;
        }
        Student student = new Student(ID, login, name, surname, group, faculty, institute);
        studentArrayList.add(student);
        try {
            FileWriter writer = new FileWriter("students.txt", true);
            writer.write(studentID + " " + login + " " + name + " " + surname + " " + group + " " + faculty + " " + institute + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void delete(String login){
        User.delete(login);
        Result.delete(login);
        studentArrayList.remove(get(login));
        flush();
    }

    public static ArrayList<Student> getStudentArrayList() {
        return studentArrayList;
    }

    private static ArrayList<Student> initArray() {
        ArrayList<Student> result = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("students.txt"));
            String raw;
            while ((raw = reader.readLine()) != null) {
                String[] buffer = raw.split(" ");
                result.add(new Student(
                        Integer.parseInt(buffer[0]),
                        buffer[1],
                        buffer[2],
                        buffer[3],
                        Integer.parseInt(buffer[4]),
                        Integer.parseInt(buffer[5]),
                        Integer.parseInt(buffer[6])));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Student get(String login) {
        Student result = null;
        for (Student student :
                studentArrayList) {
            if (student.login.equals(login)) {
                result = student;
                break;
            }
        }
        return result;
    }

    public static void flush(){
        FileWriter writer;
        try {
            writer = new FileWriter("students.txt");
            for (Student student :
                    studentArrayList) {
                writer.write(student.studentID + " "
                        + student.login + " "
                        + student.name + " "
                        + student.surname + " "
                        + student.group.getID() + " "
                        + student.faculty.getID() + " "
                        + student.institute.getID() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Student get(int ID) {
        for (Student student :
                studentArrayList) {
            if (student.studentID == ID) {
                return student;
            }
        }
        return null;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGroup() {
        return group.getName();
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getFaculty() {
        return faculty.getName();
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public String getInstitute() {
        return institute.getName();
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
        Group sg = new Group(" b");
        sg.setID(46);
    }
}
