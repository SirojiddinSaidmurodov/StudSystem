package app;

import Model.Exam;
import Model.Group;
import Model.Result;
import Model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GroupStatController implements Initializable {
    private ObservableList<Exam> examObservableList = FXCollections.observableArrayList();
    private ObservableList<Group> groupObservableList = FXCollections.observableArrayList();
    private ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();
    private ObservableList<Result> resultObservableList = FXCollections.observableArrayList();
    private ArrayList<Student> studentArrayList = new ArrayList<>();

    @FXML
    private ComboBox<Exam> examComboBox;

    @FXML
    private ComboBox<Group> groupComboBox;

    @FXML
    private PieChart pieChart;

    @FXML
    private Button show;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        examComboBox.setItems(examObservableList);
        examObservableList.add(new Exam("Все", "сессии", 0));
        examObservableList.addAll(Exam.getExamArrayList());
        groupComboBox.setItems(groupObservableList);
        groupObservableList.addAll(Group.getGroupArrayList());
        studentArrayList.addAll(Student.getStudentArrayList());
        pieChart.setData(chartData);
        resultObservableList.addAll(Result.getResultArrayList());
        setChart();
        show.setOnAction(actionEvent -> {
            if (examComboBox.getValue() != null || groupComboBox.getValue() != null) {
                if (examComboBox.getValue().getYear().equals("Все")) {
                    resultObservableList.clear();
                    studentArrayList.clear();
                    Group group = groupComboBox.getValue();
                    for (Result result : Result.getResultArrayList()) {
                        if (result.getStudent().getGroup().equals(group.toString())) {
                            resultObservableList.add(result);
                            if (!studentArrayList.contains(result.getStudent())) {
                                studentArrayList.add(result.getStudent());
                            }
                        }
                    }
                }
                setChart();
            }
        });


    }

    void setChart() {
        chartData.clear();
        int excellent = 0;
        int good = 0;
        int notBad = 0;
        int bad = 0;
        for (Student student : studentArrayList) {
            int mark = 0;
            int subs = 0;
            for (Result res : resultObservableList) {
                if (res.getStudent() == student) {
                    mark += res.getMark();
                    subs++;
                }
            }
            double avg = (double) mark / subs;
            if (avg >= 86) {
                excellent++;
            } else {
                bad++;
            }
        }
        chartData.addAll(new PieChart.Data("Отличники - " + excellent, excellent),
                new PieChart.Data("Хорошисты", good),
                new PieChart.Data("Среднечки", notBad),
                new PieChart.Data("Двоечники - " + bad, bad));
    }
}
