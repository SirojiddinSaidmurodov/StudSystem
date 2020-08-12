package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Exam {
    private static ArrayList<Exam> examArrayList = initArray();
    private String year;
    private String season;
    private int ID;

    public Exam(String year, String season) {
        this.year = year;
        this.season = season;
        int ID = 1;
        while (true) {
            boolean isFree = true;// if ID is not occupied
            for (Exam exam : examArrayList) {
                if (ID == exam.ID) {
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
        examArrayList.add(this);
        try {
            FileWriter writer = new FileWriter("exams.txt", true);
            writer.write(year + " " + season + " " + ID + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Exception Exam constructor");
            e.printStackTrace();
        }
    }

    public Exam(String year, String season, int ID) {
        this.year = year;
        this.ID = ID;
        this.season = season;
    }

    public static ArrayList<Exam> getExamArrayList() {
        return examArrayList;
    }

    public static void flush() {
        try {
            FileWriter writer = new FileWriter("exams.txt", false);
            for (Exam exam :
                    examArrayList) {
                writer.write(exam.year + " " + exam.season + " " + exam.ID + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Exception while flushing exams");
            e.printStackTrace();
        }

    }

    private static ArrayList<Exam> initArray() {
        ArrayList<Exam> result = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("exams.txt"));
            String raw;
            while ((raw = reader.readLine()) != null) {
                String[] buffer = raw.split(" ");
                result.add(new Exam(buffer[0], buffer[1], Integer.parseInt(buffer[2])));
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Exam get(int facultyID) {
        for (Exam exam : examArrayList) {
            if (exam.ID == facultyID) {
                return exam;
            }
        }
        return null;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return year + ' ' + season;
    }
}
