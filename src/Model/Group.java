package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Group {
    private static ArrayList<Group> groupArrayList = initArray();
    private int ID;
    private String name;

    public Group(String name) {
        this.name = name;
        int ID = 0;
        while (true) {
            boolean isFree = true;// if ID is not occupied
            for (Group group : groupArrayList) {
                if (ID == group.ID) {
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
        groupArrayList.add(this);
        try {
            FileWriter writer = new FileWriter("groups.txt", true);
            writer.write(name + "%%" + this.ID + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Exception group constructor");
            e.printStackTrace();
        }
    }

    private Group(String name, int ID) {
        this.name = name;
        this.ID = ID;
    }

    public static ArrayList<Group> getGroupArrayList() {
        return groupArrayList;
    }

    public static void flush() {
        try {
            FileWriter writer = new FileWriter("groups.txt", false);
            for (Group group :
                    groupArrayList) {
                writer.write(group.name + "%%" + group.ID + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Exception while flushing groups");
            e.printStackTrace();
        }

    }

    private static ArrayList<Group> initArray() {
        ArrayList<Group> result = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("groups.txt"));
            String raw;
            while ((raw = reader.readLine()) != null) {
                String[] buffer = raw.split("%%");
                result.add(new Group(buffer[0], Integer.parseInt(buffer[1])));
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Group get(int ID) {
        for (Group group : groupArrayList) {
            if (group.ID == ID) {
                return group;
            }
        }
        return null;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
