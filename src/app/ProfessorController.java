package app;

import Model.Result;
import Model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfessorController implements Initializable {
    private ObservableList<Student> studentObservableList = FXCollections.observableArrayList();
    private ObservableList<Result> resultObservableList = FXCollections.observableArrayList();

    @FXML
    private TableView<Student> studentsTable;

    @FXML
    private TableColumn<Student, String> studentsNameColumn;

    @FXML
    private TableColumn<Student, String> studentsSurnameColumn;

    @FXML
    private TableColumn<Student, String> studentsGroupColumn;

    @FXML
    private TableColumn<Student, String> studentsFacultyColumn;

    @FXML
    private TableColumn<Student, String> studentsInstituteColumn;

    @FXML
    private TableView<Result> resultTableView;

    @FXML
    private TableColumn<Result, String> examColumn;

    @FXML
    private TableColumn<Result, String> subjectColumn;

    @FXML
    private TableColumn<Result, Integer> markColumn;

    @FXML
    private Button addResult;

    @FXML
    private Button editResult;

    @FXML
    private Button deleteResult;

    @FXML
    private MenuItem About;

    @FXML
    private MenuItem exit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        studentsNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        studentsSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("Surname"));
        studentsGroupColumn.setCellValueFactory(new PropertyValueFactory<>("Group"));
        studentsFacultyColumn.setCellValueFactory(new PropertyValueFactory<>("Faculty"));
        studentsInstituteColumn.setCellValueFactory(new PropertyValueFactory<>("Institute"));
        studentsTable.setItems(studentObservableList);
        studentObservableList.addAll(Student.getStudentArrayList());


        resultTableView.setItems(resultObservableList);
        examColumn.setCellValueFactory(new PropertyValueFactory<>("Exam"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("Subject"));
        markColumn.setCellValueFactory(new PropertyValueFactory<>("Mark"));


        studentsTable.getSelectionModel().selectedItemProperty().addListener(
                ((observableValue, result, newStudent) -> showStudentResults(newStudent)));

        About.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("О программе");
            alert.setHeaderText("Система учёта успеваемости студентов");
            alert.setContentText("Автор: Саидмуродов Сирожиддин, 09-852, ИВМиИТ, КФУ");
            alert.showAndWait();
        });
        exit.setOnAction(actionEvent -> {
            Stage primaryStage = (Stage) studentsTable.getScene().getWindow();
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                primaryStage.setTitle("Вход в систему");
                primaryStage.setScene(new Scene(root, 600, 400));
                primaryStage.setMinHeight(300);
                primaryStage.setMinWidth(500);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        addResult.setOnAction(actionEvent -> {
            Student selected = studentsTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                Result result = new Result();
                try {
                    if (showResultEditor(result, selected, true)) {
                        new Result(result.getMark(), result.getSubject().getID(), result.getExam().getID(), selected.getStudentID());
                        resultObservableList.clear();
                        resultObservableList.addAll(Result.get(selected.getLogin()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ошибка!");
                alert.setHeaderText("Не выбран студент!");
                alert.setContentText("Выберите студента в таблице");
                alert.showAndWait();
            }
        });

        deleteResult.setOnAction(actionEvent -> {
            Student selected = studentsTable.getSelectionModel().getSelectedItem();
            Result selectedResult = resultTableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                if (selectedResult != null) {
                    Result.delete(selectedResult);
                    resultObservableList.clear();
                    resultObservableList.addAll(Result.get(selected.getLogin()));
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Ошибка!");
                    alert.setHeaderText("Не выбрана оценка!");
                    alert.setContentText("Выберите оценку в таблице");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ошибка!");
                alert.setHeaderText("Не выбран студент!");
                alert.setContentText("Выберите студента в таблице");
                alert.showAndWait();
            }

        });

        editResult.setOnAction(actionEvent -> {
            Student selected = studentsTable.getSelectionModel().getSelectedItem();
            Result selectedResult = resultTableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                if (selectedResult != null) {
                    try {
                        if (showResultEditor(selectedResult, selected, false)) {
                            resultObservableList.set(resultTableView.getSelectionModel().getSelectedIndex(), selectedResult);
                            Result.flush();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Ошибка!");
                    alert.setHeaderText("Не выбрана оценка!");
                    alert.setContentText("Выберите оценку в таблице");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ошибка!");
                alert.setHeaderText("Не выбран студент!");
                alert.setContentText("Выберите студента в таблице");
                alert.showAndWait();
            }
        });

    }

    public void showStudentResults(Student student) {
        if (student != null) {
            resultObservableList.clear();
            resultObservableList.addAll(Result.get(student.getLogin()));
        } else {
            resultObservableList.clear();
        }
    }

    public boolean showResultEditor(Result result, Student student, boolean isNew) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(StudentEditorController.class.getResource("ResultEditor.fxml"));
        Parent page = loader.load();
        Stage dialog = new Stage();

        dialog.setTitle(" ");
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(null);
        Scene scene = new Scene(page);
        dialog.setScene(scene);

        ResultEditorController controller = loader.getController();
        controller.setStage(dialog);
        controller.setResult(result, student, isNew);
        dialog.showAndWait();
        return controller.isokclicked();
    }
}
