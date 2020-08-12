package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Subject {
    private static ArrayList<Subject> subjectArrayList = initArray();
    private String name;
    private int ID;

    public Subject(String name) {
        this.name = name;
        int ID = 0;
        while (true) {
            boolean isFree = true;// if ID is not occupied
            for (Subject subject : subjectArrayList) {
                if (ID == subject.ID) {
                    isFree = false;
                    break;
                }
            }
            if (isFree) {
                this.ID = ID;// we use first free ID
                break;
            }
            ID++;
        }
        subjectArrayList.add(this);
        try {
            FileWriter writer = new FileWriter("subjects.txt", true);
            writer.write(name + "%%" + ID + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Exception in subject constructor");
            e.printStackTrace();
        }
    }

    private Subject(String name, int ID) {
        this.name = name;
        this.ID = ID;
    }

    public static void flush() {
        try {
            FileWriter writer = new FileWriter("subjects.txt", false);
            for (Subject subject :
                    subjectArrayList) {
                writer.write(subject.name + "%%" + subject.ID + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Exception while flushing faculties");
            e.printStackTrace();
        }

    }

    private static ArrayList<Subject> initArray() {
        ArrayList<Subject> result = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("subjects.txt"));
            String raw;
            while ((raw = reader.readLine()) != null) {
                String[] buffer = raw.split("%%");
                result.add(new Subject(buffer[0], Integer.parseInt(buffer[1])));
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ArrayList<Subject> getSubjectArrayList() {
        return subjectArrayList;
    }

    public static Subject get(int ID) {
        for (Subject subject : subjectArrayList) {
            if (subject.ID == ID) {
                return subject;
            }
        }
        return null;
    }
    public static Subject get(String name) {
        for (Subject subject : subjectArrayList) {
            if (subject.name.equals(name)) {
                return subject;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return name;
    }
}
