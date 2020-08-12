package app;

import Model.Professor;
import Model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProfessorEditorController implements Initializable {
    private Professor professor;
    private User user;
    private boolean okClicked = false;
    private Stage dialog;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button ok;
    @FXML
    private Button cancel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cancel.setOnAction(actionEvent -> dialog.close());

        ok.setOnAction(actionEvent -> {
            if (isValid()) {
                okClicked = true;
                makeProfessor();
                dialog.close();
            }
        });
    }

    private void makeProfessor() {
        professor.setLogin(loginField.getText());
        professor.setName(nameField.getText());
        professor.setSurname(surnameField.getText());
        user.setUserType("professor");
        user.setPassword(passwordField.getText());
        user.setLogin(loginField.getText());
    }

    private boolean isValid() {
        String message = "";
        if (nameField.getText() == null || nameField.getText().length() == 0 || nameField.getText().split(" ").length > 1) {
            message += "Некорректное имя\n";
        }
        if (surnameField.getText() == null || surnameField.getText().length() == 0 || surnameField.getText().split(" ").length > 1) {
            message += "Некорректная фамилия\n";
        }
        if (loginField.getText() == null || loginField.getText().length() == 0 || loginField.getText().split(" ").length > 1) {
            message += "Некорректный логин\n";
        }
        if (passwordField.getText() == null || passwordField.getText().length() == 0 || passwordField.getText().split(" ").length > 1) {
            message += "Некорректный пароль\n";
        }
        if (message.length() == 0) {//no mistakes
            TextInputDialog check = new TextInputDialog();
            check.setTitle("Завершение регистрации");
            check.setContentText("Повторно введите пароль для завершения регистрации нового преподавателя");
            check.setHeaderText("Введите пароль");
            Optional<String> result = check.showAndWait();
            if (result.isPresent()) {
                return result.get().equals(passwordField.getText());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка!");
            alert.setHeaderText("Неправильный ввод");
            alert.setContentText(message);

            alert.showAndWait();
        }
        return false;
    }

    public void setStage(Stage stage) {
        this.dialog = stage;
    }

    public void setProfessor(Professor professor, User user, boolean isNew) {
        this.professor = professor;
        this.user = user;

        if (!isNew) {
            nameField.setText(professor.getName());
            surnameField.setText(professor.getSurname());
            loginField.setText(professor.getLogin());
            passwordField.setText(user.getPassword());
        }
    }

    public boolean isOkclicked() {
        return okClicked;
    }

}
