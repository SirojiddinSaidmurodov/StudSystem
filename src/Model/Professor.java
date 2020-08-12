package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Professor {
    private static ArrayList<Professor> professorArrayList = initArray();
    private String login;
    private String name;
    private String surname;

    public Professor(String login, String name, String surname) {
        this.login = login;
        this.name = name;
        this.surname = surname;
    }

    public Professor() {

    }

    public static void addProfessor(String login, String name, String surname) {
        Professor professor = new Professor(login, name, surname);
        professorArrayList.add(professor);
        try {
            FileWriter writer = new FileWriter("professors.txt", true);
            writer.write(login + " " + name + " " + surname + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static ArrayList<Professor> initArray() {
        ArrayList<Professor> result = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("professors.txt"));
            String raw;
            while ((raw = reader.readLine()) != null) {
                String[] buffer = raw.split(" ");
                result.add(new Professor(buffer[0], buffer[1], buffer[2]));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void flush() {
        FileWriter writer;
        try {
            writer = new FileWriter("professors.txt");
            for (Professor professor :
                    professorArrayList) {
                writer.write(professor.login + " " + professor.name + " " + professor.surname + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Professor> getProfessorArrayList() {
        return professorArrayList;
    }

    public static void delete(String login) {
        professorArrayList.remove(get(login));
        flush();
    }

    public static Professor get(String login) {
        Professor result = null;
        for (Professor professor :
                professorArrayList) {
            if (professor.getLogin().equals(login)) {
                result = professor;
                break;
            }
        }
        return result;
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
}
