package app;

import Model.Professor;
import Model.Student;
import Model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInController implements Initializable {
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cancelButton.setCancelButton(true);
        //clearing the inputs when cancel
        cancelButton.setOnAction(click -> {
            loginField.setText("");
            passwordField.setText("");
        });

        okButton.setDefaultButton(true);
        //logging in
        okButton.setOnAction(click -> {
            Stage stage = (Stage) okButton.getScene().getWindow();
            switch (User.userType(loginField.getText(), passwordField.getText())) {
                case ("admin"):
                    try {
                        stage.setTitle("Администратор");
                        stage.setMinWidth(600);
                        stage.setMinHeight(400);
                        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("Admin.fxml")), 600, 400));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case ("professor"):
                    try {
                        stage.setTitle("Преподаватель: " + Professor.get(loginField.getText()).getSurname() + " "+
                                Professor.get(loginField.getText()).getName());
                        stage.setMinWidth(800);
                        stage.setMinHeight(500);
                        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("Professor.fxml")), 800, 500));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case ("student"):
                    try {
                        stage.setTitle("Студент: " +
                                Student.get(loginField.getText()).getName() + " " +
                                Student.get(loginField.getText()).getSurname());
                        stage.setMinWidth(600);
                        stage.setMinHeight(400);
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(StudentController.class.getResource("Student.fxml"));
                        Parent root = loader.load();
                        StudentController controller = loader.getController();
                        controller.setStudent(Student.get(loginField.getText()));
                        stage.setScene(new Scene(root, 700, 500));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Пользователь не найден");
                    alert.setTitle("Ошибка!");
                    alert.setContentText("Пользователь с такими данными не существует. Проверьте правильность введённых данных или свяжитесь с администратором");
                    alert.showAndWait();
                    break;
            }
        });
    }
}
