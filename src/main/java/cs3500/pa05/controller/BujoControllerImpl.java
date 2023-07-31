package cs3500.pa05.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa05.JavaFxApp;
import cs3500.pa05.model.DayName;
import cs3500.pa05.model.Event;
import cs3500.pa05.model.Period;
import cs3500.pa05.model.Spread;
import cs3500.pa05.model.Task;
import cs3500.pa05.model.Theme;
import cs3500.pa05.model.Time;
import cs3500.pa05.model.Week;
import cs3500.pa05.model.WeekItem;
import cs3500.pa05.model.jsons.JsonSpread;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Represents a concrete implementation of a bujo controller.
 */
@SuppressWarnings("unused")
public class BujoControllerImpl implements BujoController, ActionResponder {
  /**
   * Fields to keep track of model.
   */
  private Spread spread;
  private final Week week;

  /**
   * FXML fields to represent various aspects of the GUI.
   */
  private final Stage stage;
  @FXML
  private Scene scene;
  @FXML
  private HBox buttonBar;
  @FXML
  private Button openFileButton;
  @FXML
  private Button saveFileButton;
  @FXML
  private Button newEventButton;
  @FXML
  private Button newTaskButton;
  @FXML
  private Button newWeekButton;
  @FXML
  private Button changeThemeButton;
  @FXML
  private Button settingsButton;
  @FXML
  private TextField name;
  @FXML
  private TextArea quotesNotes;
  @FXML
  private VBox sun;
  @FXML
  private VBox mon;
  @FXML
  private VBox tues;
  @FXML
  private VBox wed;
  @FXML
  private VBox thurs;
  @FXML
  private VBox fri;
  @FXML
  private VBox sat;
  @FXML
  private AnchorPane anchorPane;
  @FXML
  private Label totalEvents;
  @FXML
  private Label totalTasks;
  @FXML
  private Label taskPercentage;

  /**
   * Fields to keep track of task status
   */
  private int monIncompleteCounter;
  private int monTotalCounter;
  @FXML
  private Label monCompleted;

  private int tuesIncompleteCounter;
  private int tuesTotalCounter;
  @FXML
  private Label tuesCompleted;

  private int wedIncompleteCounter;
  private int wedTotalCounter;
  @FXML
  private Label wedCompleted;

  private int thursIncompleteCounter;
  private int thursTotalCounter;
  @FXML
  private Label thursCompleted;

  private int friIncompleteCounter;
  private int friTotalCounter;
  @FXML
  private Label friCompleted;

  private int satIncompleteCounter;
  private int satTotalCounter;
  @FXML
  private Label satCompleted;

  private int sunIncompleteCounter;
  private int sunTotalCounter;
  @FXML
  private Label sunCompleted;

  /**
   * Fields to keep track of GUI progress bars
   */
  @FXML
  private ProgressBar monProgress;
  @FXML
  private ProgressBar tuesProgress;
  @FXML
  private ProgressBar wedProgress;
  @FXML
  private ProgressBar thursProgress;
  @FXML
  private ProgressBar friProgress;
  @FXML
  private ProgressBar satProgress;
  @FXML
  private ProgressBar sunProgress;

  /**
   * Fields to represent common button types.
   */
  private final ButtonType createButton = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
  private final ButtonType cancelButton =
      new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
  private final ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);

  /**
   * Useful fields to assist with displaying information in GUI.
   */
  private final List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
  private final List<DayName> days =
      Arrays.asList(
          DayName.MONDAY,
          DayName.TUESDAY,
          DayName.WEDNESDAY,
          DayName.THURSDAY,
          DayName.FRIDAY,
          DayName.SATURDAY,
          DayName.SUNDAY);

  /**
   * Constructor for a bujo controller.
   *
   * @param spread the Spread of this controller - JavaFX UI that the user will interact with
   * @param stage  the stage that the GUI will be displayed upon
   */
  public BujoControllerImpl(Spread spread, Stage stage) {
    this.spread = spread;
    this.stage = stage;
    this.week = this.spread.getWeek();
  }

  /**
   * Runs a bullet journal application by handling interactivity
   * and delegating to model methods to update bujo data.
   */
  @Override
  public void run() {
    openFileButton.setOnAction(e -> respond(Action.OPEN_FILE));
    saveFileButton.setOnAction(e -> respond(Action.SAVE_FILE));
    newEventButton.setOnAction(e -> respond(Action.EVENT));
    newTaskButton.setOnAction(e -> respond(Action.TASK));
    newWeekButton.setOnAction(e -> respond(Action.WEEK));
    changeThemeButton.setOnAction(e -> respond(Action.THEME));
    settingsButton.setOnAction(e -> respond(Action.SETTINGS));
    name.textProperty()
        .addListener((observable, oldValue, newValue) -> spread.changeName(newValue));
    quotesNotes.setWrapText(true);
    quotesNotes.textProperty()
        .addListener((observable, oldValue, newValue) -> spread.changeQuotesNotes(newValue));

    stage.getScene().setOnKeyPressed(event -> {
      KeyCode keyCode = event.getCode();
      if (event.isShortcutDown()) {
        if (keyCode == KeyCode.E) {
          respondNewEvent();
        } else if (keyCode == KeyCode.T) {
          respondNewTask();
        } else if (keyCode == KeyCode.S) {
          respondSaveFile();
        } else if (keyCode == KeyCode.O) {
          respondOpenFile();
        } else if (keyCode == KeyCode.W) {
          respondNewWeek();
        } else if (keyCode == KeyCode.B) {
          respondChangeTheme();
        } else if (keyCode == KeyCode.X) {
          respondSettings();
        }
      }
    });
  }

  /**
   * Responds to the given action by delegating to a helper method.
   *
   * @param a the action that must be responded to
   */
  @Override
  public void respond(Action a) {
    if (a.equals(Action.OPEN_FILE)) {
      respondOpenFile();
    } else if (a.equals(Action.SAVE_FILE)) {
      respondSaveFile();
    } else if (a.equals(Action.EVENT)) {
      respondNewEvent();
    } else if (a.equals(Action.TASK)) {
      respondNewTask();
    } else if (a.equals(Action.WEEK)) {
      respondNewWeek();
    } else if (a.equals(Action.SETTINGS)) {
      respondSettings();
    } else if (a.equals(Action.THEME)) {
      respondChangeTheme();
    } else {
      throw new IllegalArgumentException("No such Action found");
    }
  }

  /**
   * Allows a user to open and load a .bujo file from their computer.
   */
  private void respondOpenFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open File");
    fileChooser.getExtensionFilters().add(
        new FileChooser.ExtensionFilter("BUJO files", "*.bujo"));
    File openedFile = fileChooser.showOpenDialog(new Stage());

    if (openedFile != null) {
      this.loadFileJson(openedFile);
      name.setText(spread.getName());
      quotesNotes.setText(spread.getQuotesNotes());
      updateDisplay(spread.getThemeAsInt());
    }
  }

  /**
   * Given a file, will load the file
   *
   * @param f the file to be loaded
   */
  private void loadFileJson(File f) {
    ObjectMapper objectMapper = new ObjectMapper();
    String jsonString = readFileToString(f);
    try {
      System.out.println(jsonString);
      JsonSpread jsonSpread = objectMapper.readValue(jsonString, JsonSpread.class);
      System.out.println(jsonSpread);
      this.spread = Spread.decode(jsonSpread);
      updateUi();
    } catch (JsonProcessingException e) {
      System.out.println("Error deserializing the JSON");
    }
  }

  /**
   * Given a file, will convert the contents of that file into a JSON String
   *
   * @param f the file to be read
   * @return the entirety of the file into a string
   */
  private static String readFileToString(File f) {
    StringBuilder content = new StringBuilder();
    try {
      Scanner scan = new Scanner(f);
      while (scan.hasNextLine()) {
        String line = scan.nextLine();
        content.append(line + "\n");
      }
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File cannot be converted into a string");
    }
    return content.toString();
  }

  /**
   * Loads the previous events into the current spread. Helper for loadFileJson.
   */
  private void updateUi() {
    Week week = this.spread.getWeek();
    clearContainers();

    populateWeekdayContainer(mon, week.getEventsForDay(DayName.MONDAY),
        week.getTasksForDay(DayName.MONDAY));
    System.out.println(week.getEventsForDay(DayName.FRIDAY));
    populateWeekdayContainer(tues, week.getEventsForDay(DayName.TUESDAY),
        week.getTasksForDay(DayName.TUESDAY));
    populateWeekdayContainer(wed, week.getEventsForDay(DayName.WEDNESDAY),
        week.getTasksForDay(DayName.WEDNESDAY));
    populateWeekdayContainer(thurs, week.getEventsForDay(DayName.THURSDAY),
        week.getTasksForDay(DayName.THURSDAY));
    populateWeekdayContainer(fri, week.getEventsForDay(DayName.FRIDAY),
        week.getTasksForDay(DayName.FRIDAY));
    populateWeekdayContainer(sat, week.getEventsForDay(DayName.SATURDAY),
        week.getTasksForDay(DayName.SATURDAY));
    populateWeekdayContainer(sun, week.getEventsForDay(DayName.SUNDAY),
        week.getTasksForDay(DayName.SUNDAY));

    updateLabelTotalTasks();
    updateLabelTotalEvents();
    updateLabelTaskPercentage();
  }

  /**
   * Clear all containers to reset the GUI. Helper for updateUI.
   */
  private void clearContainers() {
    mon.getChildren().clear();
    tues.getChildren().clear();
    wed.getChildren().clear();
    thurs.getChildren().clear();
    fri.getChildren().clear();
    sat.getChildren().clear();
    sun.getChildren().clear();
    totalEvents.setText("0");
    totalTasks.setText("0");
  }

  /**
   * Populates the given weekday container with the given events and tasks.
   * Helper for updateUI.
   *
   * @param container the container to be populated
   * @param events    the events to populate the given container with
   * @param tasks     the tasks to populate the given container with
   */
  private void populateWeekdayContainer(VBox container, List<Event> events, List<Task> tasks) {
    for (Event event : events) {
      VBox newVbox = new VBox();
      newVbox.setStyle("-fx-border-color: black; -fx-border-width: 1px;");

      Label descriptionLabel = new Label(event.getDescription());
      descriptionLabel.setWrapText(true);
      String description = event.getDescription();
      if (description.contains("http://") || description.contains("https://")) {
        descriptionLabel.setGraphic(createHyperlinkText(description));
      } else {
        descriptionLabel.setText(description);
      }

      newVbox.getChildren().addAll(
          new Label(event.getName()),
          descriptionLabel,
          new Label(event.getStart()),
          new Label(event.getDuration() + " hours")
      );
      container.getChildren().add(newVbox);
      System.out.println("Event added.");
    }

    for (Task task : tasks) {
      VBox newVbox = createTaskVbox(task);
      container.getChildren().add(newVbox);
      System.out.println("Task added.");
    }
  }

  /**
   * Allows a user to save a spread to a new .bujo file on their computer.
   */
  private void respondSaveFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save File");
    fileChooser.getExtensionFilters().add(
        new FileChooser.ExtensionFilter("BUJO files", "*.bujo"));

    File file = fileChooser.showSaveDialog(new Stage());

    if (file != null) {
      saveTextToFile(file);
    }
  }

  /**
   * Saves the encoded spread to the given file.
   *
   * @param file the file to save the encoded spread to.
   */
  private void saveTextToFile(File file) {
    try {
      FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
      fileWriter.write(spread.encode());
      fileWriter.close();
      System.out.println(spread.encode());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Fields to aid in event creation.
   */
  private ComboBox<DayName> dayComboBox;
  private TextField eventNameField;
  private TextField descriptionField;
  private ComboBox<Integer> hourComboBox;
  private ComboBox<Period> periodComboBox;
  private ComboBox<Integer> durationBox;

  /**
   * Allows the user to create a new event.
   */
  private void respondNewEvent() {
    Dialog<Event> eventDialog = new Dialog<>();
    eventDialog.setTitle("Create a New Event");
    VBox eventInputBox = createEventInputBox();
    eventDialog.getDialogPane().setContent(eventInputBox);
    eventDialog.getDialogPane().getButtonTypes().addAll(createButton, cancelButton);
    eventUserInput(eventDialog);
    handleShowEvent(eventDialog);
    updateLabelTotalEvents();
  }

  /**
   * Creates an input box with necessary input fields for a user to create a new event.
   *
   * @return a VBox representing a new event.
   */
  private VBox createEventInputBox() {
    dayComboBox = new ComboBox<>();
    eventNameField = new TextField();
    hourComboBox = new ComboBox<>();
    periodComboBox = new ComboBox<>();
    durationBox = new ComboBox<>();
    descriptionField = new TextField();
    HBox timeBox = new HBox();

    dayComboBox.getItems().addAll(days);
    hourComboBox.getItems().addAll(list);
    durationBox.getItems().addAll(list);
    periodComboBox.getItems().addAll(Period.AM, Period.PM);
    timeBox.getChildren().addAll(hourComboBox, periodComboBox);
    VBox eventInputBox = new VBox(20);
    eventInputBox.getChildren().addAll(
        new Label("Day:"), dayComboBox,
        new Label("Event Name:"), eventNameField,
        new Label("Event description:"), descriptionField,
        new Label("Start Time:"), timeBox,
        new Label("Duration (Hours):"), durationBox
    );

    return eventInputBox;
  }

  /**
   * Sets up interactivity of event creation dialog by connecting a lambda
   * to the create button that takes user input and returns a new event.
   *
   * @param d an event creation dialog that needs to be connected to this controller.
   */
  private void eventUserInput(Dialog<Event> d) {
    d.setResultConverter(dialogButton -> {
      if (dialogButton.equals(createButton)) {
        try {
          DayName dayName = dayComboBox.getValue();
          String name = eventNameField.getText();
          String descriptionEvent = descriptionField.getText();
          int timeHour = hourComboBox.getValue();
          Period periodHour = periodComboBox.getValue();
          int durationMin = durationBox.getValue();
          Time startTime = new Time(timeHour, periodHour);

          if (dayName == null || name.isEmpty() || timeHour == 0 || periodHour == null
              || durationMin == 0) {
            showIncompleteEventAlert();
          } else {
            if (descriptionEvent.isEmpty()) {
              return new Event(dayName, name, startTime, durationMin);
            } else {
              return new Event(dayName, name, descriptionEvent, startTime, durationMin);
            }
          }
        } catch (NullPointerException e) {
          showIncompleteEventAlert();
        }
      }
      return null;
    });
  }

  /**
   * Sets up interactivity by connecting the handleEventDay lambda with the waiting dialog.
   *
   * @param e the dialog to connect the lambda with.
   */
  private void handleShowEvent(Dialog<Event> e) {
    Optional<Event> theResultant = e.showAndWait();
    theResultant.ifPresent(event -> {
      if (event != null) {
        VBox newVbox = createEventVbox(event);
        try {
          addWeekItemToDay(event.getDayName(), newVbox, event);
        } catch (IllegalStateException i) {
          showMaxEventsAlert();
        }
      } else {
        e.close();
      }
    });
  }

  /**
   * Creates a VBox to visually display the given event.
   *
   * @param event the event to display
   * @return a VBox displaying the information of the given event
   */
  private VBox createEventVbox(Event event) {
    VBox eventVbox = new VBox();
    eventVbox.setStyle("-fx-border-color: black; -fx-border-width: 1px;");

    Label eventNameLabel = new Label(event.getName());
    Label eventTimeLabel = new Label(event.getStart());
    Label eventDurationLabel = new Label("Duration: " + event.getDuration() + " hours");
    Label eventDescriptionLabel = new Label(event.getDescription());
    eventDescriptionLabel.setWrapText(true);

    eventVbox.getChildren()
        .addAll(eventNameLabel, eventDescriptionLabel, eventTimeLabel, eventDurationLabel);

    return eventVbox;
  }

  /**
   * Displays an alert warning the user that the event is incomplete.
   */
  private void showIncompleteEventAlert() {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Incomplete Event");
    alert.setHeaderText(null);
    alert.setContentText("Please fill in all the required fields.");
    alert.showAndWait();
  }

  /**
   * Displays an alert warning the user that the max number of events have already been reached.
   */
  private void showMaxEventsAlert() {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Max Events Reached");
    alert.setHeaderText(null);
    alert.setContentText("You have reached the maximum number of events for the day.");
    alert.showAndWait();
  }

  /**
   * Allows a user to create a new task by opening a Dialogue.
   */
  private void respondNewTask() {
    Dialog<Task> taskDialog = new Dialog<>();
    taskDialog.setTitle("Create a New Task");

    VBox taskInputBox = new VBox(20);
    ComboBox<DayName> day = new ComboBox<>();
    TextField taskNameField = new TextField();
    ComboBox<Boolean> completedBox = new ComboBox<>();
    TextField descriptionField = new TextField();

    day.getItems().addAll(days);
    completedBox.getItems().addAll(true, false);
    taskInputBox.getChildren().addAll(
        new Label("Day:"), day,
        new Label("Task Name:"), taskNameField,
        new Label("Task description:"), descriptionField
    );
    taskDialog.getDialogPane().setContent(taskInputBox);
    taskDialog.getDialogPane().getButtonTypes().addAll(createButton, cancelButton);
    taskUserInput(taskDialog, day, taskNameField, descriptionField);
    handleShowTask(taskDialog);
    updateLabelTotalTasks();
    updateLabelTaskPercentage();
  }

  /**
   * Sets up interactivity of task creation dialog by connecting a lambda
   * to the create button that takes user input and returns a new event.
   *
   * @param d           the task creation dialog
   * @param day         the day selection component of the dialog
   * @param taskName    the task name field of the dialog
   * @param description the description field of the dialog
   */
  private void taskUserInput(Dialog<Task> d,
                             ComboBox<DayName> day,
                             TextField taskName,
                             TextField description) {

    d.setResultConverter(dialogButton -> {
      try {
        if (dialogButton.equals(createButton)) {
          String name = taskName.getText();
          String descriptionTask = description.getText();
          boolean completed = false;
          addIncompleteCounter(day.getValue());
          updateCompletedLabel(day.getValue(), false);
          if (name.isEmpty()) {
            this.taskAlert();
          } else if (descriptionTask.isEmpty()) {
            return new Task(day.getValue(), name, completed);
          } else {
            return new Task(day.getValue(), name, descriptionTask, completed);
          }
        }
      } catch (NullPointerException e) {
        this.taskAlert();
      }
      return null;
    });
  }

  /**
   * Notifies the user if they create without a name provided for the task
   */
  private void taskAlert() {
    Alert eventAlert = new Alert(Alert.AlertType.ERROR);
    eventAlert.setTitle("Incomplete Task");
    eventAlert.setHeaderText("Please completely fill out the task");
    eventAlert.setContentText("Please fill in all the fields to create the task.");
    eventAlert.showAndWait();
  }

  /**
   * Updates the task status tracker for a given day when a new task has been created.
   *
   * @param dayName the day for which the task status needs to be updated
   */
  private void addIncompleteCounter(DayName dayName) {
    Label[] completedElements = {monCompleted, tuesCompleted, wedCompleted, thursCompleted,
        friCompleted, satCompleted, sunCompleted};
    ProgressBar[] progressElements = {monProgress, tuesProgress, wedProgress, thursProgress,
        friProgress, satProgress, sunProgress};
    DayName[] dayNames = {DayName.MONDAY, DayName.TUESDAY, DayName.WEDNESDAY, DayName.THURSDAY,
        DayName.FRIDAY, DayName.SATURDAY, DayName.SUNDAY};

    int index = dayName.ordinal();
    if (index < completedElements.length) {
      Label completed = completedElements[index];
      ProgressBar progress = progressElements[index];

      int incompleteCounter = spread.getWeek().getIncompleteTasksForDay(dayNames[index]).size();
      int totalCounter = spread.getWeek().getTasksForDay(dayNames[index]).size();
      completed.setText(String.valueOf(incompleteCounter));
      progress.setProgress(
          ((double) totalCounter - (double) incompleteCounter) / (double) totalCounter);
    }
  }

  /**
   * Sets up interactivity by connecting the taskUserInput lambda with the waiting dialog.
   *
   * @param e the dialog to connect the lambda with
   */
  private void handleShowTask(Dialog<Task> e) {
    Optional<Task> theResultant = e.showAndWait();
    theResultant.ifPresent(task -> {
      if (task != null) {
        this.handleTaskDay(task);
      } else {
        e.close();
      }
    });
  }

  /**
   * Adds a new task to the correct day of the GUI, catching incomplete or maximum errors.
   *
   * @param task the task to be added to the GUI on the correct day
   */
  private void handleTaskDay(Task task) {
    VBox newVbox = createTaskVbox(task);

    try {
      addWeekItemToDay(task.getDayName(), newVbox, task);
    } catch (NullPointerException e) {
      showIncompleteTaskAlert();
    } catch (IllegalStateException e) {
      showMaxTasksAlert();
    }
  }

  /**
   * Creates a visual representation of the given task.
   *
   * @param task the task to be added
   * @return the visual representation of the given task
   */
  private VBox createTaskVbox(Task task) {
    VBox newVbox = new VBox();

    newVbox.setStyle("-fx-border-color: black; -fx-border-width: 1px;");

    Label nameLabel = new Label(task.getName());
    nameLabel.setWrapText(true);

    Label descriptionLabel = createDescriptionLabel(task.getDescription());

    CheckBox completed = createCompletedCheckBox();
    completed.selectedProperty().addListener((observable, oldValue, newValue) -> {
      task.setCompleted();
      updateLabelTaskPercentage();
      updateCompletedLabel(task.getDayName(), oldValue);
    });

    newVbox.getChildren().addAll(nameLabel, descriptionLabel, completed);

    return newVbox;
  }

  /**
   * Creates and returns a label representing the given description
   * with HTTP and HTTPS links correctly parsed.
   *
   * @param description the description to parse into a label
   * @return a label representing the given description with links parsed
   */
  private Label createDescriptionLabel(String description) {
    Label descriptionLabel = new Label();
    descriptionLabel.setWrapText(true);

    if (description.contains("http://") || description.contains("https://")) {
      descriptionLabel.setGraphic(createHyperlinkText(description));
    } else {
      descriptionLabel.setText(description);
    }

    return descriptionLabel;
  }

  /**
   * Creates and returns a checkbox that represents the completion status of a task,
   * initialized to false (unchecked).
   *
   * @return an unchecked checkbox
   */
  private CheckBox createCompletedCheckBox() {
    CheckBox completed = new CheckBox("Completed: ");
    BooleanProperty b = completed.selectedProperty();
    completed.setText("Completed: " + b.get());

    return completed;
  }

  /**
   * Adds the given WeekItem to the given day by delegating to the model to update
   * day information and adding the given VBox to the correct GUI component.
   *
   * @param day      the day to add the given WeekItem to
   * @param newVbox  the GUI component that needs to be added to the given day
   * @param weekItem a model representation of the information to be added to the given day
   */
  private void addWeekItemToDay(DayName day, VBox newVbox, WeekItem weekItem) {
    switch (day) {
      case MONDAY:
        week.addWeekItemToDay(DayName.MONDAY, weekItem);
        mon.getChildren().add(newVbox);
        break;
      case TUESDAY:
        week.addWeekItemToDay(DayName.TUESDAY, weekItem);
        tues.getChildren().add(newVbox);
        break;
      case WEDNESDAY:
        week.addWeekItemToDay(DayName.WEDNESDAY, weekItem);
        wed.getChildren().add(newVbox);
        break;
      case THURSDAY:
        week.addWeekItemToDay(DayName.THURSDAY, weekItem);
        thurs.getChildren().add(newVbox);
        break;
      case FRIDAY:
        week.addWeekItemToDay(DayName.FRIDAY, weekItem);
        fri.getChildren().add(newVbox);
        break;
      case SATURDAY:
        week.addWeekItemToDay(DayName.SATURDAY, weekItem);
        sat.getChildren().add(newVbox);
        break;
      case SUNDAY:
        week.addWeekItemToDay(DayName.SUNDAY, weekItem);
        sun.getChildren().add(newVbox);
        break;
      default:
        throw new IllegalArgumentException("No day match found for the corresponding event");
    }
  }

  /**
   * Displays an alert warning the user that the Task is incomplete.
   */
  private void showIncompleteTaskAlert() {
    Alert eventAlert = new Alert(Alert.AlertType.ERROR);
    eventAlert.setTitle("Incomplete Task");
    eventAlert.setHeaderText("Please completely fill out the task");
    eventAlert.setContentText("Please fill in all the fields to create the task.");
    eventAlert.showAndWait();
  }

  /**
   * Displays an alert warning the user that the max number of tasks has already been reached.
   */
  private void showMaxTasksAlert() {
    Alert eventAlert = new Alert(Alert.AlertType.ERROR);
    eventAlert.setTitle("Max Tasks");
    eventAlert.setHeaderText("You have reached the max tasks.");
    eventAlert.setContentText(
        "You cannot add any more tasks to this day unless you change the settings.");
    eventAlert.showAndWait();
  }

  // COME BACK TO THIS
  private void updateCompletedLabel(DayName day, Boolean oldValue) {
    if (day.equals(DayName.MONDAY)) {
      updateLabelAndProgress(DayName.MONDAY, monIncompleteCounter, monCompleted, monProgress,
          monTotalCounter, oldValue);
    } else if (day.equals(DayName.TUESDAY)) {
      updateLabelAndProgress(DayName.TUESDAY, tuesIncompleteCounter, tuesCompleted, tuesProgress,
          tuesTotalCounter, oldValue);
    } else if (day.equals(DayName.WEDNESDAY)) {
      updateLabelAndProgress(DayName.WEDNESDAY, wedIncompleteCounter, wedCompleted, wedProgress,
          wedTotalCounter, oldValue);
    } else if (day.equals(DayName.THURSDAY)) {
      updateLabelAndProgress(DayName.THURSDAY, thursIncompleteCounter, thursCompleted,
          thursProgress, thursTotalCounter, oldValue);
    } else if (day.equals(DayName.FRIDAY)) {
      updateLabelAndProgress(DayName.FRIDAY, friIncompleteCounter, friCompleted, friProgress,
          friTotalCounter, oldValue);
    } else if (day.equals(DayName.SATURDAY)) {
      updateLabelAndProgress(DayName.SATURDAY, satIncompleteCounter, satCompleted, satProgress,
          satTotalCounter, oldValue);
    } else if (day.equals(DayName.SUNDAY)) {
      updateLabelAndProgress(DayName.SUNDAY, sunIncompleteCounter, sunCompleted, sunProgress,
          sunTotalCounter, oldValue);
    }
  }

  @SuppressWarnings("unused")
  private void updateLabelAndProgress(DayName dayName, int incompleteCounter, Label completed,
                                      ProgressBar progress, int totalCounter, Boolean oldValue) {
    totalCounter = spread.getWeek().getTasksForDay(dayName).size();
    incompleteCounter = spread.getWeek().getIncompleteTasksForDay(dayName).size();


    completed.setText(String.valueOf(incompleteCounter));
    progress.setProgress(
        ((double) totalCounter - (double) incompleteCounter) / (double) totalCounter);
  }

  /**
   * Creates accurately hyperlinked text from the HTTPS and HTTP links in the given text.
   *
   * @param text the text that needs to be parsed for hyperlinks
   * @return a Node of correctly parsed and hyperlinked text
   */
  private Node createHyperlinkText(String text) {
    TextFlow textFlow = new TextFlow();

    int startIndex = 0;
    int endIndex;

    while (startIndex < text.length()) {
      if (text.indexOf("http://", startIndex) == startIndex
          || text.indexOf("https://", startIndex) == startIndex) {
        endIndex = text.indexOf(" ", startIndex);
        if (endIndex == -1) {
          endIndex = text.length();
        }
        String link = text.substring(startIndex, endIndex);
        Hyperlink hyperlink = new Hyperlink(link);
        hyperlink.setOnAction(e -> openWebpage(link));
        textFlow.getChildren().add(hyperlink);
        startIndex += link.length();
      } else {
        endIndex = text.indexOf("http://", startIndex);
        if (endIndex == -1) {
          endIndex = text.length();
        }
        String plainText = text.substring(startIndex, endIndex);
        Text plainTextNode = new Text(plainText);
        textFlow.getChildren().add(plainTextNode);
        startIndex += plainText.length();
      }
    }
    return textFlow;
  }

  /**
   * Opens the webpage at the given URL.
   *
   * @param url the URL to open
   */
  public static void openWebpage(String url) {
    try {
      java.awt.Desktop.getDesktop().browse(new URI(url));
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Updates the GUI to display the total tasks in this week.
   */
  private void updateLabelTotalTasks() {
    int totalTasksCount = spread.getWeek().getTotalTasks();
    totalTasks.setText(String.valueOf(totalTasksCount));
  }

  /**
   * Updates the GUI to display the total events in this week.
   */
  private void updateLabelTotalEvents() {
    int totalEventsCount = spread.getWeek().getTotalEvents();
    totalEvents.setText(String.valueOf(totalEventsCount));
  }

  /**
   * Updates the GUI to display the percent of completed tasks in this week.
   */
  private void updateLabelTaskPercentage() {
    double taskPercentageValue = spread.getWeek().getTaskPercentage();
    taskPercentage.setText(String.format("%.2f%%", taskPercentageValue));
  }

  /**
   * Allows a user to start a new week. Opens a new window for the new week - essentially restarts
   * the program.
   */
  private void respondNewWeek() {
    JavaFxApp newApp = new JavaFxApp();
    try {
      newApp.start(new Stage());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Fields to assist with theme organization and display.
   */
  private final List<String> bgColor = Arrays.asList(
      "-fx-background-color: white;", // white - minimalist
      "-fx-background-color: #D2B48C;", // tan - earthy
      "-fx-background-color: #FF4500;" // orange red - sunset
  );
  private final List<String> dayColor = Arrays.asList(
      "-fx-background-color: #D3D3D3;", // light gray - minimalist
      "-fx-background-color: #808000;", // olive - earthy
      "-fx-background-color: #DA70D6" // orchid - sunset
  );
  private final List<String> stylesheets = Arrays.asList(
      "minimalist.css",
      "earthy.css",
      "sunset.css"
  );

  /**
   * Allows the user to change the spread theme to the next pre-set theme
   * and updates the display to show it.
   */
  private void respondChangeTheme() {
    int current = spread.getThemeAsInt();
    Theme newTheme;

    if (current == 0) {
      newTheme = Theme.EARTHY;
    } else if (current == 1) {
      newTheme = Theme.SUNSET;
    } else {
      newTheme = Theme.MINIMALIST;
    }

    spread.changeTheme(newTheme);
    updateDisplay(spread.getThemeAsInt());
  }

  /**
   * changes the display of the spread to the given theme index
   *
   * @param themeIdx the index of theme that this spread should display
   */
  public void updateDisplay(int themeIdx) {
    String bg = bgColor.get(themeIdx);
    String style = stylesheets.get(themeIdx);

    scene.getStylesheets().clear();
    scene.getStylesheets().add(style);

    name.setStyle(bg);
    buttonBar.setStyle(bg);
    anchorPane.setStyle(bg);

    String day = dayColor.get(themeIdx);
    sun.setStyle(day);
    mon.setStyle(day);
    tues.setStyle(day);
    wed.setStyle(day);
    thurs.setStyle(day);
    fri.setStyle(day);
    sat.setStyle(day);
    quotesNotes.setStyle(day);
  }

  /**
   * Allows a user to access the settings via a Dialogue.
   */
  private void respondSettings() {
    openSettingsWindow();
  }

  /**
   * Creates and displays a settings window with functionality.
   */
  private void openSettingsWindow() {
    Dialog<ButtonType> settingsDialog = new Dialog<>();
    settingsDialog.setTitle("Settings");

    GridPane gridPane = new GridPane();
    gridPane.setHgap(10);
    gridPane.setVgap(10);
    gridPane.setPadding(new Insets(20));

    ComboBox<Integer> maxTasksComboBox = new ComboBox<>();
    maxTasksComboBox.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8);
    maxTasksComboBox.setValue(3);

    ComboBox<Integer> maxEventsComboBox = new ComboBox<>();
    maxEventsComboBox.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8);
    maxEventsComboBox.setValue(3);

    Label colorPickerLabel = new Label("Background Color:");
    ColorPicker colorPicker = makeColorPicker();

    Label maxTasksLabel = new Label("Max Tasks:");
    gridPane.add(maxTasksLabel, 0, 0);
    gridPane.add(maxTasksComboBox, 1, 0);
    Label maxEventsLabel = new Label("Max Events:");
    gridPane.add(maxEventsLabel, 0, 1);
    gridPane.add(maxEventsComboBox, 1, 1);
    gridPane.add(colorPickerLabel, 0, 2);
    gridPane.add(colorPicker, 1, 2);

    settingsDialog.getDialogPane().getButtonTypes().addAll(saveButton, cancelButton);
    settingsDialog.getDialogPane().setContent(gridPane);

    settingsDialog.setResultConverter(buttonType -> {
      if (buttonType == saveButton) {
        int maxTasks = maxTasksComboBox.getValue();
        int maxEvents = maxEventsComboBox.getValue();
        spread.getWeek().changeMaxTasks(maxTasks);
        spread.getWeek().changeMaxEvents(maxEvents);
      }
      return null;
    });
    settingsDialog.showAndWait();
  }

  /**
   * Creates and returns a color picker that updates the GUI.
   *
   * @return a ColorPicker that updates the background color of the GUI.
   */
  private ColorPicker makeColorPicker() {
    ColorPicker colorPicker = new ColorPicker();
    colorPicker.setOnAction(event -> {
      Color c = colorPicker.getValue();
      String newColor = "-fx-background-color: " + String.format("#%02X%02X%02X",
          (int) (c.getRed() * 255),
          (int) (c.getGreen() * 255),
          (int) (c.getBlue() * 255)) + ";";
      anchorPane.setStyle(newColor);
      name.setStyle(newColor);
      buttonBar.setStyle(newColor);
    });

    return colorPicker;
  }
}
