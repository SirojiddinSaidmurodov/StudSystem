package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Result {
    private static ArrayList<Result> resultArrayList = initArray();
    private Subject subject;
    private Exam exam;
    private Student student;
    private int mark;
    private int resultID;

    public Result(int mark, int subjectID, int examID, int studentID) {
        this.mark = mark;
        subject = Subject.get(subjectID);
        exam = Exam.get(examID);
        student = Student.get(studentID);
        int ID = 0;
        while (true) {
            boolean isFree = true;// if ID is not occupied
            for (Result result : resultArrayList) {
                if (ID == result.resultID) {
                    isFree = false;
                    break;
                }
            }
            if (isFree) {
                this.resultID = ID;// we use first free ID
                break;
            }
            ID++;
        }
        resultArrayList.add(this);
        try {
            FileWriter writer = new FileWriter("results.txt", true);
            writer.write(mark + " " + subjectID + " " + examID + " " + studentID + " " + ID + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Exception result constructor");
            e.printStackTrace();
        }
    }

    private Result(int mark, Subject subject, Exam exam, Student student, int resultID) {
        this.mark = mark;
        this.subject = subject;
        this.exam = exam;
        this.student = student;
        this.resultID = resultID;
    }

    public Result() {

    }

    public static ArrayList<Result> getResultArrayList() {
        return resultArrayList;
    }

    public static void delete(String login) {
        resultArrayList.removeAll(get(login));
        flush();
    }

    public static void delete(Result result) {
        resultArrayList.remove(result);
        flush();
    }

    public static void flush() {
        try {
            FileWriter writer = new FileWriter("results.txt", false);
            for (Result result :
                    resultArrayList) {
                writer.write(result.mark + " " +
                        result.subject.getID() + " " +
                        result.exam.getID() + " " +
                        result.student.getStudentID() + " " +
                        result.resultID + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Exception while flushing faculties");
            e.printStackTrace();
        }
    }

    private static ArrayList<Result> initArray() {
        ArrayList<Result> result = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("results.txt"));
            String raw;
            while ((raw = reader.readLine()) != null) {
                String[] buffer = raw.split(" ");
                result.add(new Result(
                        Integer.parseInt(buffer[0]),
                        Subject.get(Integer.parseInt(buffer[1])),
                        Exam.get(Integer.parseInt(buffer[2])),
                        Student.get(Integer.parseInt(buffer[3])),
                        Integer.parseInt(buffer[4])));
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Result get(int ID) {
        for (Result result : resultArrayList) {
            if (result.resultID == ID) {
                return result;
            }
        }
        return null;
    }

    public static ArrayList<Result> get(String login) {
        ArrayList<Result> buffer = new ArrayList<>();
        for (Result result : resultArrayList) {
            if (result.student.getLogin().equals(login)) {
                buffer.add(result);
            }
        }
        return buffer;
    }

    public static ArrayList<Result> get(String studentLogin, int examID) {
        ArrayList<Result> buffer = new ArrayList<>();
        for (Result result : resultArrayList) {
            if (result.student.getLogin().equals(studentLogin) && result.exam.getID() == examID) {
                buffer.add(result);
            }
        }
        return buffer;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getResultID() {
        return resultID;
    }

    public void setResultID(int resultID) {
        this.resultID = resultID;
    }

}
