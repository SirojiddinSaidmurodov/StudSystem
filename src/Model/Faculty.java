package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Faculty {
    private static ArrayList<Faculty> facultyArrayList = initArray();
    private String name;
    private int ID;

    public Faculty(String facultyName) {
        this.name = facultyName;
        int ID = 0;
        while (true) {
            boolean isFree = true;// if ID is not occupied
            for (Faculty faculty : facultyArrayList) {
                if (ID == faculty.ID) {
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
        facultyArrayList.add(this);
        try {
            FileWriter writer = new FileWriter("faculties.txt", true);
            writer.write(facultyName + "%%" + ID + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Exception faculty constructor");
            e.printStackTrace();
        }
    }

    private Faculty(String groupName, int groupID) {
        this.name = groupName;
        this.ID = groupID;
    }

    public static void flush() {
        try {
            FileWriter writer = new FileWriter("faculties.txt", false);
            for (Faculty faculty :
                    facultyArrayList) {
                writer.write(faculty.name + "%%" + faculty.ID + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Exception while flushing faculties");
            e.printStackTrace();
        }

    }

    public static ArrayList<Faculty> getFacultyArrayList() {
        return facultyArrayList;
    }

    private static ArrayList<Faculty> initArray() {
        ArrayList<Faculty> result = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("faculties.txt"));
            String raw;
            while ((raw = reader.readLine()) != null) {
                String[] buffer = raw.split("%%");
                result.add(new Faculty(buffer[0], Integer.parseInt(buffer[1])));
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Faculty get(int facultyID) {
        for (Faculty faculty : facultyArrayList) {
            if (faculty.ID == facultyID) {
                return faculty;
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
