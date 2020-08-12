package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Institute {
    private static ArrayList<Institute> institutesArrayList = initArray();
    private String name;

    public static ArrayList<Institute> getInstitutesArrayList() {
        return institutesArrayList;
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

    private int ID;

    public Institute(String name) {
        this.name = name;
        int ID = 0;
        while (true) {
            boolean isFree = true;// if ID is not occupied
            for (Institute institute : institutesArrayList) {
                if (ID == institute.ID) {
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
        institutesArrayList.add(this);
        try {
            FileWriter writer = new FileWriter("institutes.txt", true);
            writer.write(name + "%%" + ID + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Exception Institute constructor");
            e.printStackTrace();
        }
    }

    private Institute(String name, int ID) {
        this.name = name;
        this.ID = ID;
    }

    public static void flush() {
        try {
            FileWriter writer = new FileWriter("institutes.txt", false);
            for (Institute institute :
                    institutesArrayList) {
                writer.write(institute.name + "%%" + institute.ID + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Exception while flushing institutes");
            e.printStackTrace();
        }

    }

    private static ArrayList<Institute> initArray() {
        ArrayList<Institute> result = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("institutes.txt"));
            String raw;
            while ((raw = reader.readLine()) != null) {
                String[] buffer = raw.split("%%");
                result.add(new Institute(buffer[0], Integer.parseInt(buffer[1])));
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Institute get(int ID) {
        for (Institute institute : institutesArrayList) {
            if (institute.ID == ID) {
                return institute;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
