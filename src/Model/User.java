package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class User {
    private static ArrayList<User> userArrayList = initArray();
    private String login;
    private String password;
    private String userType;

    public User(String login, String password, String userType) {//for creating object in RAM
        this.login = login;
        this.password = password;
        this.userType = userType;
    }

    public User() {

    }


    public static String userType(String login, String password) {
        for (User user :
                userArrayList) {
            if (login.equals(user.login) && password.equals(user.password)) {
                return user.userType;
            }
        }
        return "unknown";
    }


    public static ArrayList<User> getUserArrayList() {
        return userArrayList;
    }

    private static ArrayList<User> initArray() {
        ArrayList<User> result = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("users.txt"));
            String raw;
            while ((raw = reader.readLine()) != null) {
                String[] buffer = raw.split(" ");
                result.add(new User(
                        buffer[0],
                        buffer[1],
                        buffer[2]));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void flush() {
        try {
            FileWriter writer = new FileWriter("users.txt", false);
            for (User user : userArrayList) {
                writer.write(user.login + " " +
                        user.password + " " +
                        user.userType + " " + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Users flushing");
            e.printStackTrace();
        }
    }

    public static void addUser(String login, String password, String userType) {
        User user = new User(login, password, userType);
        userArrayList.add(user);
        try {
            FileWriter writer = new FileWriter("users.txt", true);
            writer.write(user.login + " " +
                    user.password + " " +
                    user.userType + " " + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static User get(String login) {
        for (User user : userArrayList) {
            if (user.login.equals(login)) {
                return user;
            }
        }
        return null;
    }
    public static void delete(String login){
        userArrayList.remove(get(login));
        flush();
    }
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
