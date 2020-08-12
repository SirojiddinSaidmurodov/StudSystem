package app;

import Model.Exam;
import Model.Result;
import Model.Student;
import Model.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ResultEditorController implements Initializable {
    private Stage dialog;
    private Result result;
    private Student student;
    private boolean okClicked = false;
    private ObservableList<Exam> examObservableList = FXCollections.observableArrayList();
    private ObservableList<Subject> subjectObservableList = FXCollections.observableArrayList();

    @FXML
    private TextField markField;
    @FXML
    private ComboBox<Exam> examComboBox;
    @FXML
    private ComboBox<Subject> subjectComboBox;
    @FXML
    private Button ok;
    @FXML
    private Button cancel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        examComboBox.setItems(examObservableList);
        subjectComboBox.setItems(subjectObservableList);
        examObservableList.addAll(Exam.getExamArrayList());
        subjectObservableList.addAll(Subject.getSubjectArrayList());

        cancel.setOnAction(actionEvent -> dialog.close());

        ok.setOnAction(actionEvent -> {

            if (isValid()) {
                okClicked = true;
                makeResult();
                dialog.close();
            }
        });
    }

    private void makeResult() {
        result.setExam(examComboBox.getValue());
        result.setMark(Integer.parseInt(markField.getText()));
        result.setStudent(student);
        result.setSubject(subjectComboBox.getValue());
    }

    private boolean isValid() {
        String message = "";
        if ((markField.getText() == null) || (markField.getText().length() == 0) || (markField.getText().split(" ").length > 1) || (100 < Integer.parseInt(markField.getText())) || Integer.parseInt(markField.getText()) < 0) {
            message += "Некорректная оценка\n";
        }
        if (subjectComboBox.getValue() == null) {
            message += "Выберите предмет\n";
        }
        if (examComboBox.getValue() == null) {
            message += "Выберите сессию\n";
        }
        if (message.length() == 0) {
            return true;
        }else{
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

    public void setResult(Result result, Student student, boolean isNew) {
        this.result = result;
        this.student = student;
        if (!isNew) {
            examComboBox.setValue(result.getExam());
            subjectComboBox.setValue(result.getSubject());
            markField.setText(result.getMark() + "");
        }
    }

    public boolean isokclicked() {
        return okClicked;
    }
}
