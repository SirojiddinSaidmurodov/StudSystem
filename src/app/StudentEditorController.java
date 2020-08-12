package app;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class StudentEditorController implements Initializable {
    private Student student;
    private User user;
    private boolean okClicked = false;
    private Stage dialog;
    @FXML
    private Button ok;
    @FXML
    private Button cancel;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private ComboBox<Group> groupComboBox;
    private ObservableList<Group> groupObservableList = FXCollections.observableArrayList();
    @FXML
    private ComboBox<Faculty> facultyComboBox;
    private ObservableList<Faculty> facultyObservableList = FXCollections.observableArrayList();
    @FXML
    private ComboBox<Institute> instituteComboBox;
    private ObservableList<Institute> instituteObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        groupComboBox.setItems(groupObservableList);
        instituteComboBox.setItems(instituteObservableList);
        facultyComboBox.setItems(facultyObservableList);
        groupObservableList.addAll(Group.getGroupArrayList());
        instituteObservableList.addAll(Institute.getInstitutesArrayList());
        facultyObservableList.addAll(Faculty.getFacultyArrayList());

        //Cancelling of signing up
        cancel.setOnAction(click -> dialog.close());


        ok.setOnAction(click -> {
            if (isValid()) {
                okClicked = true;
                makeStudent();
                dialog.close();
            }

        });
    }

    public boolean isOKclicked() {
        return okClicked;
    }

    public void setStudent(Student student, User user, boolean isNew) {
        this.student = student;
        this.user = user;

        if (!isNew) {
            nameField.setText(student.getName());
            surnameField.setText(student.getSurname());
            loginField.setText(student.getLogin());
            passwordField.setText(user.getPassword());
            groupComboBox.setValue(student.group);
            facultyComboBox.setValue(student.faculty);
            instituteComboBox.setValue(student.institute);
        }
    }

    public void setStage(Stage stage) {
        this.dialog = stage;
    }

    public boolean isValid() {
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
        if (passwordField.getText() == null || passwordField.getText().length() == 0 || passwordField.getText().split(" ").length > 1
        || isNormalPassword(passwordField.getText())) {
            message += "Некорректный пароль\n";
        }
        if (instituteComboBox.getValue() == null) {
            message += "Не выбран институт\n";
        }
        if (facultyComboBox.getValue() == null) {
            message += "Не выбран факультет\n";
        }
        if (groupComboBox.getValue() == null) {
            message += "Не выбрана акдемическая группа\n";
        }
        if (message.length() == 0) {//no mistakes
            TextInputDialog check = new TextInputDialog();
            check.setTitle("Заершение регистрации");
            check.setContentText("Повторно введите пароль для завершения регистрации нового студента");
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

    private boolean isNormalPassword(String password) {
        int digit = 0;
        int letter = 0;
        for (int i = 0; i < password.length(); i++) {
            if(Character.isDigit(password.charAt(i))){
                digit++;
            }else {
                letter++;
            }
        }
        return digit > 4 && letter > 4;
    }

    private void makeStudent() {
        student.setFaculty(facultyComboBox.getValue());
        student.setGroup(groupComboBox.getValue());
        student.setInstitute(instituteComboBox.getValue());
        student.setLogin(loginField.getText());
        student.setName(nameField.getText());
        student.setSurname(surnameField.getText());
        user.setLogin(loginField.getText());
        user.setPassword(passwordField.getText());
        user.setUserType("student");
    }
}
