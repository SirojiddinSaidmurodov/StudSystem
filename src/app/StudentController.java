package app;

import Model.Exam;
import Model.Result;
import Model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentController implements Initializable {
    private ObservableList<Result> resultObservableList = FXCollections.observableArrayList();
    private ObservableList<Exam> examObservableList = FXCollections.observableArrayList();
    private ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    private Student student;
    @FXML
    private MenuItem openGroupStats;
    @FXML
    private PieChart chart;
    @FXML
    private TableView<Result> resultTableView;
    @FXML
    private TableColumn<Result, Integer> markColumn;
    @FXML
    private TableColumn<Result, String> subjectColumn;
    @FXML
    private TableColumn<Result, String> examColumn;
    @FXML
    private ComboBox<Exam> examChooseCombo;
    @FXML
    private BorderPane studentMarks;
    @FXML
    private MenuItem About;
    @FXML
    private MenuItem exit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        markColumn.setCellValueFactory(new PropertyValueFactory<>("Mark"));//Searches getter in class Result
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("Subject"));
        examColumn.setCellValueFactory(new PropertyValueFactory<>("Exam"));
        resultTableView.setItems(resultObservableList);
        chart.setData(pieChartData);
        examChooseCombo.setItems(examObservableList);
        examObservableList.add(new Exam("Все", "сессии", 0));
        examObservableList.addAll(Exam.getExamArrayList());

        examChooseCombo.getSelectionModel().select(0);

        openGroupStats.setOnAction(actionEvent -> {
            Parent root;
            Stage primaryStage = new Stage();
            try {
                root = FXMLLoader.load(getClass().getResource("GroupStat.fxml"));
                primaryStage.setTitle("Статистика по группам");
                primaryStage.setScene(new Scene(root, 600, 400));
                primaryStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });


        examChooseCombo.getSelectionModel().selectedItemProperty().addListener(((observableValue, exam, t1) -> {

            resultObservableList.clear();
            if (t1.getYear().equals("Все")) {
                resultObservableList.addAll(Result.get(student.getLogin()));
            } else {
                resultObservableList.addAll(Result.get(student.getLogin(), t1.getID()));

            }
            setChart();
        }));


        exit.setOnAction(actionEvent -> {
            Stage primaryStage = (Stage) examChooseCombo.getScene().getWindow();
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


        About.setOnAction(click -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("О программе");
            alert.setHeaderText("Система учёта успеваемости студентов");
            alert.setContentText("Автор: Саидмуродов Сирожиддин, 09-852, ИВМиИТ, КФУ");
            alert.showAndWait();
        });
    }

    public void setStudent(Student student) {
        this.student = student;
        resultObservableList.addAll(Result.get(student.getLogin()));
        setChart();
    }

    public void setChart() {//86,76,56
        pieChartData.clear();
        int excellent = 0;
        int good = 0;
        int notBad = 0;
        int bad = 0;
        for (Result result : resultObservableList) {
            if (result.getMark() >= 86) {
                excellent++;
            } else if (result.getMark() >= 76 && result.getMark() < 86) {
                good++;
            } else if (result.getMark() >= 56 && result.getMark() < 76) {
                notBad++;
            } else {
                bad++;
            }
        }
        pieChartData.addAll(
                new PieChart.Data("Отлично - " + excellent, excellent),
                new PieChart.Data("Хорошо - " + good, good),
                new PieChart.Data("Удовлетворительно - " + notBad, notBad),
                new PieChart.Data("Неудовлетворительно - " + bad, bad));

    }
}
