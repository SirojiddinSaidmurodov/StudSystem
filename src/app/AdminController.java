package app;

import Model.*;
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

public class AdminController implements Initializable {
    private ObservableList<Student> studentObservableList = FXCollections.observableArrayList();
    @FXML
    private MenuItem exit;
    @FXML
    private MenuItem About;
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
    private Button addStudent;
    @FXML
    private Button editStudent;
    @FXML
    private Button deleteStudent;

    private ObservableList<Professor> professorObservableList = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Professor, String> professorNameColumn;
    @FXML
    private TableColumn<Professor, String> professorSurnameColumn;
    @FXML
    private Button addProfessor;
    @FXML
    private Button editProfessor;
    @FXML
    private Button deleteProfessor;
    @FXML
    private TableView<Professor> professorTableView;

    private ObservableList<Institute> instituteObservableList = FXCollections.observableArrayList();
    @FXML
    private TableView<Institute> instituteTableView;
    @FXML
    private TableColumn<Institute, String> instituteTableColumn;
    @FXML
    private Button addInstitute;


    private ObservableList<Faculty> facultyObservableList = FXCollections.observableArrayList();
    @FXML
    private TableView<Faculty> facultyTableView;
    @FXML
    private TableColumn<Faculty, String> facultyTableColumn;
    @FXML
    private Button addFaculty;

    private ObservableList<Group> groupObservableList = FXCollections.observableArrayList();
    @FXML
    private TableView<Group> groupTableView;
    @FXML
    private TableColumn<Group, String> groupTableColumn;
    @FXML
    private Button addGroup;

    private ObservableList<Exam> examObservableList = FXCollections.observableArrayList();
    @FXML
    private TableView<Exam> examTableView;
    @FXML
    private TableColumn<Exam, String> examStringTableColumn;
    @FXML
    private Button addExam;

    private ObservableList<Subject> subjectObservableList = FXCollections.observableArrayList();
    @FXML
    private TableView<Subject> subjectTableView;
    @FXML
    private TableColumn<Subject, String> subjectStringTableColumn;
    @FXML
    private Button addSubject;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //init students page
        studentsNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        studentsSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("Surname"));
        studentsGroupColumn.setCellValueFactory(new PropertyValueFactory<>("Group"));
        studentsFacultyColumn.setCellValueFactory(new PropertyValueFactory<>("Faculty"));
        studentsInstituteColumn.setCellValueFactory(new PropertyValueFactory<>("Institute"));
        studentsTable.setItems(studentObservableList);
        studentObservableList.addAll(Student.getStudentArrayList());

        //init professors page
        professorNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        professorSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("Surname"));
        professorTableView.setItems(professorObservableList);
        professorObservableList.addAll(Professor.getProfessorArrayList());

        //init group page
        groupTableColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        groupTableView.setItems(groupObservableList);
        groupObservableList.addAll(Group.getGroupArrayList());

        //init faculty page
        facultyTableColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        facultyTableView.setItems(facultyObservableList);
        facultyObservableList.addAll(Faculty.getFacultyArrayList());

        //init inst page
        instituteTableColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        instituteTableView.setItems(instituteObservableList);
        instituteObservableList.addAll(Institute.getInstitutesArrayList());

        exit.setOnAction(actionEvent -> {
            Stage primaryStage = (Stage) addStudent.getScene().getWindow();
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
        About.setOnAction(actionEvent -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("О программе");
            alert.setHeaderText("Система учёта успеваемости студентов");
            alert.setContentText("Автор: Саидмуродов Сирожиддин, 09-852, ИВМиИТ, КФУ");
            alert.showAndWait();
        });
        //Adding student
        addStudent.setOnAction(click -> {
            Student student = new Student();
            User user = new User();
            try {
                if (showStudentEditor(student, user, true)) {
                    new Student(student.getLogin(),
                            student.getName(),
                            student.getSurname(),
                            student.group.getID(),
                            student.faculty.getID(),
                            student.institute.getID());
                    User.addUser(user.getLogin(), user.getPassword(), "student");
                    studentObservableList.clear();
                    studentObservableList.addAll(Student.getStudentArrayList());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //editing existing student
        editStudent.setOnAction(click -> {
            Student selected = studentsTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                try {
                    User oldUser = User.get(selected.getLogin());
                    if (showStudentEditor(selected, oldUser, false)) {
                        studentObservableList.set(studentsTable.getSelectionModel().getSelectedIndex(), selected);
                        Student.flush();
                        User.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ошибка!");
                alert.setHeaderText("Не выбран студент для изменения!");
                alert.setContentText("Выберите студента в таблице");
                alert.showAndWait();
            }
        });

        //Deleting student
        deleteStudent.setOnAction(actionEvent -> {
            Student selected = studentsTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                studentObservableList.remove(selected);
                Student.delete(selected.getLogin());
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ошибка!");
                alert.setHeaderText("Не выбран студент для удаления!");
                alert.setContentText("Выберите студента в таблице");
                alert.showAndWait();
            }
        });

        //adding professor
        addProfessor.setOnAction(event -> {
            Professor professor = new Professor();
            User user = new User();
            try {
                if (showProfessorEditor(professor, user, true)) {
                    Professor.addProfessor(professor.getLogin(), professor.getName(), professor.getSurname());
                    User.addUser(user.getLogin(), user.getPassword(), "professor");
                    professorObservableList.clear();
                    professorObservableList.addAll(Professor.getProfessorArrayList());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //editing professor
        editProfessor.setOnAction(event -> {
            Professor selected = professorTableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                try {
                    User oldUser = User.get(selected.getLogin());
                    if (showProfessorEditor(selected, oldUser, false)) {
                        professorObservableList.set(professorTableView.getSelectionModel().getSelectedIndex(), selected);
                        Professor.flush();
                        User.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ошибка!");
                alert.setHeaderText("Не выбран преподаватель для изменения!");
                alert.setContentText("Выберите преподавателя в таблице");
                alert.showAndWait();
            }
        });

        //deleting
        deleteProfessor.setOnAction(actionEvent -> {
            Professor selected = professorTableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                professorObservableList.remove(selected);
                Professor.delete(selected.getLogin());
                User.delete(selected.getLogin());
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Ошибка!");
                alert.setHeaderText("Не выбран преподаватель для удаления!");
                alert.setContentText("Выберите преподавателя в таблице");
                alert.showAndWait();
            }
        });
    }


    boolean showStudentEditor(Student student, User user, boolean isNew) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(StudentEditorController.class.getResource("StudentEditor.fxml"));
        Parent page = loader.load();
        Stage dialog = new Stage();

        dialog.setTitle("Редактор");
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(null);
        Scene scene = new Scene(page);
        dialog.setScene(scene);

        StudentEditorController controller = loader.getController();
        controller.setStage(dialog);
        controller.setStudent(student, user, isNew);
        dialog.showAndWait();
        return controller.isOKclicked();
    }

    boolean showProfessorEditor(Professor professor, User user, boolean isNew) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ProfessorEditorController.class.getResource("ProfessorEditor.fxml"));
        Parent page = loader.load();
        Stage dialog = new Stage();

        dialog.setTitle("Редактор");
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(null);
        Scene scene = new Scene(page);
        dialog.setScene(scene);

        ProfessorEditorController controller = loader.getController();
        controller.setStage(dialog);
        controller.setProfessor(professor, user, isNew);
        dialog.showAndWait();
        return controller.isOkclicked();

    }
}
