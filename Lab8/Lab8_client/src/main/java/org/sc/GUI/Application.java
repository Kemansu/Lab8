package org.sc.GUI;

import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.converter.DefaultStringConverter;
import org.sc.Request;
import org.sc.Server;
import org.sc.data.*;
import org.sc.exceptions.WrongInputException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;


public class Application extends javafx.application.Application {
    private Stage stage;
    private Image RedhouseImage;
    private Image GreenhouseImage;
    private String response = null;
    private Request request;
    private String language = "ru";
    private DateFormat dateformat = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("ru", "RU")); ;
    private Pane root;
    Server client;
    double lastMouseX;
    double lastMouseY;
    double offsetX = 0;
    double offsetY = 0;

    public void start(Stage stage) throws Exception {
        this.stage = stage;
        client = new Server();
        stage.setTitle("JavaFX");

        showRegLogScene();
    }
    public void Launch(String[] args){
        launch(args);
    }

    public void showRegLogScene(){
        Text sana = new Text("server are not available");
        Text uwtdiae = new Text("user with that data is already exist");
        Text piad = new Text("Please, input all data");
        Text wuoap = new Text("wrong username or a password");

        // Создание текстового поля и кнопки
        TextField textField_for_username = new TextField();
        textField_for_username.setMaxWidth(300);

        TextField textField_for_password = new TextField();
        textField_for_password.setMaxWidth(300);


        Text text_username = new Text("Username");
        Text text_password = new Text("Password");
        Text text_status = new Text();


        Button button_login = new Button("Login");
        Button button_register = new Button("Register");
        Button buttonRu = new Button("Русский");
        Button buttonLv = new Button("Latviešu");
        Button buttonDe = new Button("Deutsch");
        Button buttonEs = new Button("Español (Guatemala)");

        buttonRu.setOnAction(e -> {
            language = "ru";
            dateformat = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("ru", "RU"));
            LocalizationUtil.setLocale(new Locale("ru"));
            button_login.setText(LocalizationUtil.getString("log_in"));
            button_register.setText(LocalizationUtil.getString("Register"));
            text_username.setText(LocalizationUtil.getString("Username"));
            text_password.setText(LocalizationUtil.getString("Password"));
            sana.setText(LocalizationUtil.getString("server_are_not_available"));
            uwtdiae.setText(LocalizationUtil.getString("user_with_that_data_is_already_exist"));
            piad.setText(LocalizationUtil.getString("please_input_all_data"));
            wuoap.setText(LocalizationUtil.getString("wrong_username_or_a_password"));
        });

        buttonLv.setOnAction(e -> {
            language = "lv";
            dateformat = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("lv", "LV"));
            LocalizationUtil.setLocale(new Locale("lv"));
            button_login.setText(LocalizationUtil.getString("log_in"));
            button_register.setText(LocalizationUtil.getString("Register"));
            text_username.setText(LocalizationUtil.getString("Username"));
            text_password.setText(LocalizationUtil.getString("Password"));
            sana.setText(LocalizationUtil.getString("server_are_not_available"));
            uwtdiae.setText(LocalizationUtil.getString("user_with_that_data_is_already_exist"));
            piad.setText(LocalizationUtil.getString("please_input_all_data"));
            wuoap.setText(LocalizationUtil.getString("wrong_username_or_a_password"));
        });

        buttonDe.setOnAction(e -> {
            language = "de";
            dateformat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMAN);
            LocalizationUtil.setLocale(new Locale("de"));
            button_login.setText(LocalizationUtil.getString("log_in"));
            button_register.setText(LocalizationUtil.getString("Register"));
            text_username.setText(LocalizationUtil.getString("Username"));
            text_password.setText(LocalizationUtil.getString("Password"));
            sana.setText(LocalizationUtil.getString("server_are_not_available"));
            uwtdiae.setText(LocalizationUtil.getString("user_with_that_data_is_already_exist"));
            piad.setText(LocalizationUtil.getString("please_input_all_data"));
            wuoap.setText(LocalizationUtil.getString("wrong_username_or_a_password"));
        });

        buttonEs.setOnAction(e -> {
            language = "es";
            dateformat = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("es", "ES"));
            LocalizationUtil.setLocale(new Locale("es", "GT"));
            button_login.setText(LocalizationUtil.getString("log_in"));
            button_register.setText(LocalizationUtil.getString("Register"));
            text_username.setText(LocalizationUtil.getString("Username"));
            text_password.setText(LocalizationUtil.getString("Password"));
            sana.setText(LocalizationUtil.getString("server_are_not_available"));
            uwtdiae.setText(LocalizationUtil.getString("user_with_that_data_is_already_exist"));
            piad.setText(LocalizationUtil.getString("please_input_all_data"));
            wuoap.setText(LocalizationUtil.getString("wrong_username_or_a_password"));
        });


        // Добавление обработчика события для кнопки
        button_register.setOnAction(e -> {
            request = new Request("create", null, textField_for_password.getText(), textField_for_username.getText());
            try {
                if (request.getLogin().isEmpty() || request.getPassword().isEmpty()){
                    throw new WrongInputException();
                }
                String[] response = client.sendEcho(request).split(" ");
                if (response[0].equals("server")){
                    text_status.setText(sana.getText());
                } else if (response[0].equals("User created successfully")){
                    showMainScene(response[1]);
                } else {
                    text_status.setText(uwtdiae.getText());
                }
            } catch (IOException | WrongInputException ex) {
                text_status.setText(piad.getText());
            }
        });

        button_login.setOnAction(e -> {
            request = new Request("log_in", null, textField_for_password.getText(), textField_for_username.getText());

            try {
                String[] response = client.sendEcho(request).split(" ");
                if (response[0].equals("server")){
                  text_status.setText(sana.getText());
                } else if (response[0].equals("true")){
                    showMainScene(response[1]);
                } else{
                    text_status.setText(wuoap.getText());
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Создание контейнеров для надписей и полей ввода
        VBox usernameBox = new VBox(5); // Отступ между надписью и полем ввода
        usernameBox.setAlignment(Pos.CENTER);
        usernameBox.getChildren().addAll(text_username, textField_for_username);

        VBox passwordBox = new VBox(5);
        passwordBox.setAlignment(Pos.CENTER);
        passwordBox.getChildren().addAll(text_password, textField_for_password, text_status);

        VBox locBox = new VBox(5);
        locBox.setAlignment(Pos.BOTTOM_LEFT);
        locBox.getChildren().addAll(buttonRu, buttonLv, buttonDe, buttonEs);


        // Создание основного вертикального контейнера и добавление в него компонентов
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(
                usernameBox,
                passwordBox,
                button_register,
                button_login,
                locBox
        );


        // Настройка и отображение сцены
        Scene scene = new Scene(vbox, 600, 370);;
        stage.setTitle("Authorization");
        stage.setScene(scene);
        stage.show();

    }
    public String convertDateFormat(String date) {
        if (date.matches("^\\d{2}\\.\\d{2}\\.\\d{4}")){// Разбиваем строку на части по разделителю "-"
            date = date.replaceAll("\\.", "-");
            String[] parts = date.split("-");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid date format. Expected format: dd-MM-yyyy");
            }

            // Переставляем части в порядке "год-месяц-день"
            String day = parts[0];
            String month = parts[1];
            String year = parts[2];

            // Формируем новую строку
            return year + "-" + month + "-" + day;
        } else if (date.matches("^\\d{2}\\.\\d{2}\\.\\d{2}")) {
            date = date.replaceAll("\\.", "-");
            String[] parts = date.split("-");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid date format. Expected format: dd-MM-yyyy");
            }

            // Переставляем части в порядке "год-месяц-день"
            String day = parts[0];
            String month = parts[1];
            String year = "20" + parts[2];

            // Формируем новую строку
            return year + "-" + month + "-" + day;
        } else if (date.matches("^\\d{2}/\\d{1}/\\d{2}")) {
            date = date.replaceAll("/", "-");
            String[] parts = date.split("-");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid date format. Expected format: dd-MM-yyyy");
            }

            // Переставляем части в порядке "год-месяц-день"
            String month;
            String day = parts[0];
            if (parts[1].length() == 1){
                month = "0" + parts[1];
            }else {
                month = parts[1];
            }
            String year = "20" + parts[2];

            // Формируем новую строку
            return year + "-" + month + "-" + day;
        }
        return "";
    }

    public void showMainScene(String id) {
        // Создание контейнера AnchorPane
        AnchorPane anchorPane = new AnchorPane();

        // Создание кнопок
        Button button_commands = new Button("Commands");
        Button button_log_out = new Button("log out");
        Button button_map_open = new Button("map");
        Button button_refresh_table = new Button("refresh table");
        Button buttonRu = new Button("Русский");
        Button buttonLv = new Button("Latviešu");
        Button buttonDe = new Button("Deutsch");
        Button buttonEs = new Button("Español (Guatemala)");

        Text text_id = new Text("USER ID: " + id);

        // Задание привязок
        AnchorPane.setTopAnchor(button_log_out, 20.0);
        AnchorPane.setLeftAnchor(button_log_out, 20.0);

        AnchorPane.setTopAnchor(button_commands, 20.0);
        AnchorPane.setLeftAnchor(button_commands, 120.0);

        AnchorPane.setTopAnchor(button_map_open, 20.0);
        AnchorPane.setLeftAnchor(button_map_open, 220.0);

        AnchorPane.setTopAnchor(button_refresh_table, 20.0);
        AnchorPane.setLeftAnchor(button_refresh_table, 290.0);

        AnchorPane.setTopAnchor(buttonRu, 20.0);
        AnchorPane.setLeftAnchor(buttonRu, 450.0);

        AnchorPane.setTopAnchor(buttonEs, 20.0);
        AnchorPane.setLeftAnchor(buttonEs, 530.0);

        AnchorPane.setTopAnchor(buttonDe, 20.0);
        AnchorPane.setLeftAnchor(buttonDe, 670.0);

        AnchorPane.setTopAnchor(buttonLv, 20.0);
        AnchorPane.setLeftAnchor(buttonLv, 750.0);

        AnchorPane.setRightAnchor(text_id, 50.0);
        AnchorPane.setTopAnchor(text_id, 25.0);

        TableView<DataModel> tableView = new TableView<>();

        // Создание 14 колонок
        String[] columnNames = {
                "id", "owner_id", "name", "coordinate_X", "coordinate_Y", "area", "number_of_rooms",
                "kitchen_area", "view", "transport", "name_of_house", "year_of_house", "number_of_flats_on_floor", "creation_date"
        };

        for (int i = 1; i <= 14; i++) {
            TableColumn<DataModel, String> column = new TableColumn<>(columnNames[i - 1]);
            column.setCellValueFactory(new PropertyValueFactory<>("column" + i));
            column.setReorderable(false);
            column.setSortable(true);
            tableView.getColumns().add(column);

            if (!columnNames[i - 1].equals("id") && !columnNames[i - 1].equals("owner_id") && !columnNames[i - 1].equals("creation_date")) {
                column.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
                column.setOnEditCommit(event -> {
                    DataModel dataModel = event.getRowValue();
                    if (dataModel.getColumn2().equals(id)) { // Проверка owner_id
                        int colIndex = event.getTablePosition().getColumn();
                        switch (colIndex) {
                            case 2:
                                dataModel.setColumn3(event.getNewValue());
                                break;
                            case 3:
                                dataModel.setColumn4(event.getNewValue());
                                break;
                            case 4:
                                dataModel.setColumn5(event.getNewValue());
                                break;
                            case 5:
                                dataModel.setColumn6(event.getNewValue());
                                break;
                            case 6:
                                dataModel.setColumn7(event.getNewValue());
                                break;
                            case 7:
                                dataModel.setColumn8(event.getNewValue());
                                break;
                            case 8:
                                dataModel.setColumn9(event.getNewValue());
                                break;
                            case 9:
                                dataModel.setColumn10(event.getNewValue());
                                break;
                            case 10:
                                dataModel.setColumn11(event.getNewValue());
                                break;
                            case 11:
                                dataModel.setColumn12(event.getNewValue());
                                break;
                            case 12:
                                dataModel.setColumn13(event.getNewValue());
                                break;
                        }
                        Flat flat = new Flat(Long.parseLong(dataModel.getColumn1()));
                        Coordinates coordinates = new Coordinates(0L, 0L);
                        flat.setOwnerId(Long.parseLong(dataModel.getColumn2())); // owner_id
                        flat.setName(dataModel.getColumn3()); // name
                        coordinates.setX(Double.parseDouble(dataModel.getColumn4())); // coordinate_X
                        coordinates.setY(Long.parseLong(dataModel.getColumn5())); // coordinate_Y
                        flat.setCoordinates(coordinates);
                        flat.setArea(Long.parseLong(dataModel.getColumn6())); // area
                        flat.setNumberOfRooms(Integer.parseInt(dataModel.getColumn7())); // number_of_rooms
                        flat.setKitchenArea(Double.parseDouble(dataModel.getColumn8())); // kitchen_area
                        flat.setView(View.valueOf(dataModel.getColumn9())); // view
                        flat.setTransport(Transport.valueOf(dataModel.getColumn10())); // transport
                        House house = new House();
                        house.setName(dataModel.getColumn11()); // name_of_house
                        house.setYear(Long.parseLong(dataModel.getColumn12())); // year_of_house
                        house.setNumberOfFlatsOnFloor(Integer.parseInt(dataModel.getColumn13())); // number_of_flats_on_floor
                        flat.setHouse(house);
                        flat.setCreationDate(LocalDate.parse(dataModel.getColumn14())); // creation_date
                        try {
                            client.sendEcho(new Request("update " + dataModel.getColumn1(), Long.parseLong(id), flat));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
        }

        tableView.setEditable(true);

        // Добавление данных в таблицу
        ObservableList<DataModel> data = FXCollections.observableArrayList();

        String[] flats1;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String[] flats = client.sendEcho(new Request("show")).split("/");
            flats1 = flats;
            if (!flats[0].equals("No flats :(")) {
                for (int i = 0; i < flats.length; i++) {
                    String dateString = flats[i].split(" ")[13];
                    Date date = dateFormat.parse(dateString);
                    data.add(new DataModel(
                            flats[i].split(" ")[0],
                            flats[i].split(" ")[1],
                            flats[i].split(" ")[2],
                            flats[i].split(" ")[3],
                            flats[i].split(" ")[4],
                            flats[i].split(" ")[5],
                            flats[i].split(" ")[6],
                            flats[i].split(" ")[7],
                            flats[i].split(" ")[8],
                            flats[i].split(" ")[9],
                            flats[i].split(" ")[10],
                            flats[i].split(" ")[11],
                            flats[i].split(" ")[12],
                            convertDateFormat(dateformat.format(date))
                    ));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        tableView.setItems(data);

        // Создание выпадающего списка и текстового поля для фильтрации
        ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList(columnNames));
        comboBox.setValue(columnNames[0]); // Установить значение по умолчанию

        TextField filterField = new TextField();
        filterField.setPromptText("Введите значение для фильтрации");

        HBox filterBox = new HBox(comboBox, filterField);
        filterBox.setSpacing(10);
        AnchorPane.setTopAnchor(filterBox, 20.0);
        AnchorPane.setLeftAnchor(filterBox, 900.0);
        anchorPane.getChildren().add(filterBox);

        // Обновление таблицы при изменении фильтров
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            String selectedColumn = comboBox.getValue();
            tableView.setItems(filterData(data, selectedColumn, newValue));
        });

        // Добавление обработчика события для кнопки
        button_log_out.setOnAction(e -> showRegLogScene());
        button_map_open.setOnAction(e -> {
            try {
                showMapScene(id);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        button_commands.setOnAction(e -> showCommandsScene(id));
        button_refresh_table.setOnAction(e -> showMainScene(id));
        buttonRu.setOnAction(e -> {
            language = "ru";
            dateformat = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("ru", "RU"));
            LocalizationUtil.setLocale(new Locale("ru"));
            button_log_out.setText(LocalizationUtil.getString("log_out"));
            button_commands.setText(LocalizationUtil.getString("Commands"));
            button_map_open.setText(LocalizationUtil.getString("Map"));
            button_refresh_table.setText(LocalizationUtil.getString("Refresh_table"));
            text_id.setText(LocalizationUtil.getString("USER_ID") + ": " + id);
        });
        buttonDe.setOnAction(e -> {
            language = "de";
            dateformat = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("de", "DE"));
            LocalizationUtil.setLocale(new Locale("de"));
            button_log_out.setText(LocalizationUtil.getString("log_out"));
            button_commands.setText(LocalizationUtil.getString("Commands"));
            button_map_open.setText(LocalizationUtil.getString("Map"));
            button_refresh_table.setText(LocalizationUtil.getString("Refresh_table"));
            text_id.setText(LocalizationUtil.getString("USER_ID") + ": " + id);
        });
        buttonLv.setOnAction(e -> {
            language = "lv";
            dateformat = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("lv", "LV"));
            LocalizationUtil.setLocale(new Locale("lv"));
            button_log_out.setText(LocalizationUtil.getString("log_out"));
            button_commands.setText(LocalizationUtil.getString("Commands"));
            button_map_open.setText(LocalizationUtil.getString("Map"));
            button_refresh_table.setText(LocalizationUtil.getString("Refresh_table"));
            text_id.setText(LocalizationUtil.getString("USER_ID") + ": " + id);
        });
        buttonEs.setOnAction(e -> {
            language = "es";
            dateformat = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("es", "ES"));
            LocalizationUtil.setLocale(new Locale("es", "GT"));
            button_log_out.setText(LocalizationUtil.getString("log_out"));
            button_commands.setText(LocalizationUtil.getString("Commands"));
            button_map_open.setText(LocalizationUtil.getString("Map"));
            button_refresh_table.setText(LocalizationUtil.getString("Refresh_table"));
            text_id.setText(LocalizationUtil.getString("USER_ID") + ": " + id);
        });

        // Создание StackPane для центрирования таблицы
        StackPane stackPane = new StackPane(tableView);
        StackPane.setAlignment(tableView, Pos.CENTER);

        // Задание привязок для StackPane
        AnchorPane.setTopAnchor(stackPane, 60.0);
        AnchorPane.setLeftAnchor(stackPane, 20.0);
        AnchorPane.setRightAnchor(stackPane, 20.0);
        AnchorPane.setBottomAnchor(stackPane, 20.0);

        // Добавление элементов в контейнер
        anchorPane.getChildren().addAll(
                button_commands,
                button_log_out,
                text_id,
                button_map_open,
                button_refresh_table,
                buttonRu,
                buttonEs,
                buttonDe,
                buttonLv,
                stackPane
        );

        // Настройка и отображение сцены
        Scene scene = new Scene(anchorPane, 2000, 600);
        stage.setTitle("Application");
        stage.setScene(scene);
        stage.show();

        LocalizationUtil.setLocale(new Locale(language));
        button_log_out.setText(LocalizationUtil.getString("log_out"));
        button_commands.setText(LocalizationUtil.getString("Commands"));
        button_map_open.setText(LocalizationUtil.getString("Map"));
        button_refresh_table.setText(LocalizationUtil.getString("Refresh_table"));
        text_id.setText(LocalizationUtil.getString("USER_ID") + ": " + id);
    }

    // Метод для фильтрации данных
    private ObservableList<DataModel> filterData(ObservableList<DataModel> data, String selectedColumn, String filterValue) {
        return FXCollections.observableArrayList(data.stream()
                .filter(dataModel -> {
                    String value = getColumnValue(dataModel, selectedColumn);
                    return value.equals(filterValue);
                })
                .toList());
    }

    // Метод для получения значения колонки по имени
    private String getColumnValue(DataModel dataModel, String columnName) {
        return switch (columnName) {
            case "id" -> dataModel.getColumn1();
            case "owner_id" -> dataModel.getColumn2();
            case "name" -> dataModel.getColumn3();
            case "coordinate_X" -> dataModel.getColumn4();
            case "coordinate_Y" -> dataModel.getColumn5();
            case "area" -> dataModel.getColumn6();
            case "number_of_rooms" -> dataModel.getColumn7();
            case "kitchen_area" -> dataModel.getColumn8();
            case "view" -> dataModel.getColumn9();
            case "transport" -> dataModel.getColumn10();
            case "name_of_house" -> dataModel.getColumn11();
            case "year_of_house" -> dataModel.getColumn12();
            case "number_of_flats_on_floor" -> dataModel.getColumn13();
            case "creation_date" -> dataModel.getColumn14();
            default -> "";
        };
    }

    public void showCommandsScene(String id){
        // Создание контейнера AnchorPane
        AnchorPane anchorPane = new AnchorPane();

        // создание кнопок
        Button button_command_add = new Button("add");
        Button button_command_add_if_min = new Button("add_if_min");
        Button button_command_count_greater_than_house = new Button("count_greater_than_house");
        Button button_command_group_counting_by_kitchen_area = new Button("group_counting_by_kitchen_area");
        Button button_command_info = new Button("info");
        Button button_command_print_field_ascending_transport = new Button("print_field_ascending_transport");
        Button button_command_remove_by_id = new Button("remove_by_id");
        Button button_command_remove_greater = new Button("remove_greater");
        Button button_command_remove_lower = new Button("remove_lower");
        Button button_command_update = new Button("update");
        Button buttonRu = new Button("Русский");
        Button buttonLv = new Button("Latviešu");
        Button buttonDe = new Button("Deutsch");
        Button buttonEs = new Button("Español (Guatemala)");
        Button button_back_to_Application = new Button("Back");



        Text text_id = new Text("USER ID: " + id);

        // Задание привязок
        AnchorPane.setTopAnchor(button_command_add, 20.0);
        AnchorPane.setLeftAnchor(button_command_add, 50.0);

        AnchorPane.setTopAnchor(button_command_add_if_min, 20.0);
        AnchorPane.setLeftAnchor(button_command_add_if_min, 140.0);

        AnchorPane.setTopAnchor(button_command_count_greater_than_house, 20.0);
        AnchorPane.setLeftAnchor(button_command_count_greater_than_house, 310.0);

        AnchorPane.setTopAnchor(button_command_update, 20.0);
        AnchorPane.setLeftAnchor(button_command_update, 480.0);

        AnchorPane.setTopAnchor(button_command_group_counting_by_kitchen_area, 60.0);
        AnchorPane.setLeftAnchor(button_command_group_counting_by_kitchen_area, 50.0);

        AnchorPane.setTopAnchor(button_command_info, 60.0);
        AnchorPane.setLeftAnchor(button_command_info, 315.0);

        AnchorPane.setTopAnchor(button_command_print_field_ascending_transport, 60.0);
        AnchorPane.setLeftAnchor(button_command_print_field_ascending_transport, 420.0);

        AnchorPane.setTopAnchor(button_command_remove_by_id, 100.0);
        AnchorPane.setLeftAnchor(button_command_remove_by_id, 50.0);

        AnchorPane.setTopAnchor(button_command_remove_greater, 100.0);
        AnchorPane.setLeftAnchor(button_command_remove_greater, 180.0);

        AnchorPane.setTopAnchor(button_command_remove_lower, 100.0);
        AnchorPane.setLeftAnchor(button_command_remove_lower, 320.0);


        AnchorPane.setBottomAnchor(button_back_to_Application, 20.0);
        AnchorPane.setLeftAnchor(button_back_to_Application, 50.0);

        AnchorPane.setBottomAnchor(buttonRu, 20.0);
        AnchorPane.setLeftAnchor(buttonRu, 150.0);

        AnchorPane.setBottomAnchor(buttonDe, 20.0);
        AnchorPane.setLeftAnchor(buttonDe, 250.0);

        AnchorPane.setBottomAnchor(buttonEs, 20.0);
        AnchorPane.setLeftAnchor(buttonEs, 340.0);

        AnchorPane.setBottomAnchor(buttonLv, 20.0);
        AnchorPane.setLeftAnchor(buttonLv, 470.0);



        AnchorPane.setRightAnchor(text_id, 50.0);
        AnchorPane.setTopAnchor(text_id, 25.0);

        // Добавление обработчика события для кнопки
        button_back_to_Application.setOnAction(e -> showMainScene(id));
        button_command_add.setOnAction(e -> showFlatGenerator(id, "add"));
        button_command_add_if_min.setOnAction(e -> showFlatGenerator(id, "add_if_min"));
        button_command_count_greater_than_house.setOnAction(e -> showHouseGenerator(id, "count_greater_than_house"));
        button_command_group_counting_by_kitchen_area.setOnAction(e -> {
            request.setMessage("group_counting_by_kitchen_area");
            try {
                response = client.sendEcho(request);
                if (response.equals("no flats in collection :(")){
                    LocalizationUtil.setLocale(new Locale(language));
                    response = LocalizationUtil.getString("no_flats_in_collection");
                } else {
                    LocalizationUtil.setLocale(new Locale(language));
                    response = response.replaceAll("Kitchen area", LocalizationUtil.getString("kitchen_area"));
                    response = response.replaceAll("Amount", LocalizationUtil.getString("amount"));
                }
                showPopupWindow(response);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        button_command_info.setOnAction(e -> {
            request.setMessage("info");
            try {
                response = client.sendEcho(request);
                LocalizationUtil.setLocale(new Locale(language));
                response = response.replaceAll("Type", LocalizationUtil.getString("type"));
                response = response.replaceAll("Count of Flats", LocalizationUtil.getString("count_of_flats"));
                response = response.replaceAll("Init date", LocalizationUtil.getString("init_date"));
                showPopupWindow(response);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        button_command_print_field_ascending_transport.setOnAction(e -> {
            request.setMessage("print_field_ascending_transport");
            try {
                response = client.sendEcho(request);
                if (response.equals("no flats in collection :(")){
                    LocalizationUtil.setLocale(new Locale(language));
                    response = LocalizationUtil.getString("no_flats_in_collection");
                }
                showPopupWindow(response);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        button_command_remove_by_id.setOnAction(e -> showRemoveCommandWindow(Long.parseLong(id)));
        button_command_update.setOnAction(e -> showUpdateCommandWindow(id));
        button_command_remove_greater.setOnAction(e -> showFlatGenerator(id, "remove_greater"));
        button_command_remove_lower.setOnAction(e -> showFlatGenerator(id, "remove_lower"));
        buttonRu.setOnAction(e -> {
            language = "ru";
            dateformat = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("ru", "RU"));
            LocalizationUtil.setLocale(new Locale("ru"));
            button_command_add.setText(LocalizationUtil.getString("add"));
            button_command_add_if_min.setText(LocalizationUtil.getString("add_if_min"));
            button_command_count_greater_than_house.setText(LocalizationUtil.getString("count_greater_than_house"));
            button_command_group_counting_by_kitchen_area.setText(LocalizationUtil.getString("group_counting_by_kitchen_area"));
            button_command_print_field_ascending_transport.setText(LocalizationUtil.getString("print_field_ascending_transport"));
            button_command_remove_by_id.setText(LocalizationUtil.getString("remove_by_id"));
            button_command_remove_greater.setText(LocalizationUtil.getString("remove_greater"));
            button_command_remove_lower.setText(LocalizationUtil.getString("remove_lower"));
            button_command_update.setText(LocalizationUtil.getString("update"));
            button_command_info.setText(LocalizationUtil.getString("info"));
            button_back_to_Application.setText(LocalizationUtil.getString("Back"));
            text_id.setText(LocalizationUtil.getString("USER_ID") + ": " + id);
        });
        buttonEs.setOnAction(e -> {
            language = "es";
            dateformat = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("es", "ES"));
            LocalizationUtil.setLocale(new Locale("es", "GT"));
            button_command_add.setText(LocalizationUtil.getString("add"));
            button_command_add_if_min.setText(LocalizationUtil.getString("add_if_min"));
            button_command_count_greater_than_house.setText(LocalizationUtil.getString("count_greater_than_house"));
            button_command_group_counting_by_kitchen_area.setText(LocalizationUtil.getString("group_counting_by_kitchen_area"));
            button_command_print_field_ascending_transport.setText(LocalizationUtil.getString("print_field_ascending_transport"));
            button_command_remove_by_id.setText(LocalizationUtil.getString("remove_by_id"));
            button_command_remove_greater.setText(LocalizationUtil.getString("remove_greater"));
            button_command_remove_lower.setText(LocalizationUtil.getString("remove_lower"));
            button_command_update.setText(LocalizationUtil.getString("update"));
            button_command_info.setText(LocalizationUtil.getString("info"));
            button_back_to_Application.setText(LocalizationUtil.getString("Back"));
            text_id.setText(LocalizationUtil.getString("USER_ID") + ": " + id);
        });
        buttonDe.setOnAction(e -> {
            language = "de";
            dateformat = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("de", "DE"));
            LocalizationUtil.setLocale(new Locale("de"));
            button_command_add.setText(LocalizationUtil.getString("add"));
            button_command_add_if_min.setText(LocalizationUtil.getString("add_if_min"));
            button_command_count_greater_than_house.setText(LocalizationUtil.getString("count_greater_than_house"));
            button_command_group_counting_by_kitchen_area.setText(LocalizationUtil.getString("group_counting_by_kitchen_area"));
            button_command_print_field_ascending_transport.setText(LocalizationUtil.getString("print_field_ascending_transport"));
            button_command_remove_by_id.setText(LocalizationUtil.getString("remove_by_id"));
            button_command_remove_greater.setText(LocalizationUtil.getString("remove_greater"));
            button_command_remove_lower.setText(LocalizationUtil.getString("remove_lower"));
            button_command_update.setText(LocalizationUtil.getString("update"));
            button_command_info.setText(LocalizationUtil.getString("info"));
            button_back_to_Application.setText(LocalizationUtil.getString("Back"));
            text_id.setText(LocalizationUtil.getString("USER_ID") + ": " + id);
        });
        buttonLv.setOnAction(e -> {
            language = "lv";
            dateformat = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("lv", "LV"));
            LocalizationUtil.setLocale(new Locale("lv"));
            button_command_add.setText(LocalizationUtil.getString("add"));
            button_command_add_if_min.setText(LocalizationUtil.getString("add_if_min"));
            button_command_count_greater_than_house.setText(LocalizationUtil.getString("count_greater_than_house"));
            button_command_group_counting_by_kitchen_area.setText(LocalizationUtil.getString("group_counting_by_kitchen_area"));
            button_command_print_field_ascending_transport.setText(LocalizationUtil.getString("print_field_ascending_transport"));
            button_command_remove_by_id.setText(LocalizationUtil.getString("remove_by_id"));
            button_command_remove_greater.setText(LocalizationUtil.getString("remove_greater"));
            button_command_remove_lower.setText(LocalizationUtil.getString("remove_lower"));
            button_command_update.setText(LocalizationUtil.getString("update"));
            button_command_info.setText(LocalizationUtil.getString("info"));
            button_back_to_Application.setText(LocalizationUtil.getString("Back"));
            text_id.setText(LocalizationUtil.getString("USER_ID") + ": " + id);
        });


        // Добавление кнопок в контейнер
        anchorPane.getChildren().addAll(
                button_command_add,
                button_command_add_if_min,
                button_command_count_greater_than_house,
                button_command_group_counting_by_kitchen_area,
                button_command_info,
                button_command_print_field_ascending_transport,
                button_command_remove_by_id,
                button_command_remove_greater,
                button_command_remove_lower,
                button_command_update,
                button_back_to_Application,
                buttonRu,
                buttonEs,
                buttonDe,
                buttonLv,
                text_id
        );

        // Настройка и отображение сцены
        Scene scene = new Scene(anchorPane, 1000, 600);;
        stage.setTitle("Commands");
        stage.setScene(scene);
        stage.show();

        LocalizationUtil.setLocale(new Locale(language));
        button_command_add.setText(LocalizationUtil.getString("add"));
        button_command_add_if_min.setText(LocalizationUtil.getString("add_if_min"));
        button_command_count_greater_than_house.setText(LocalizationUtil.getString("count_greater_than_house"));
        button_command_group_counting_by_kitchen_area.setText(LocalizationUtil.getString("group_counting_by_kitchen_area"));
        button_command_print_field_ascending_transport.setText(LocalizationUtil.getString("print_field_ascending_transport"));
        button_command_remove_by_id.setText(LocalizationUtil.getString("remove_by_id"));
        button_command_remove_greater.setText(LocalizationUtil.getString("remove_greater"));
        button_command_remove_lower.setText(LocalizationUtil.getString("remove_lower"));
        button_command_update.setText(LocalizationUtil.getString("update"));
        button_command_info.setText(LocalizationUtil.getString("info"));
        button_back_to_Application.setText(LocalizationUtil.getString("Back"));
        text_id.setText(LocalizationUtil.getString("USER_ID") + ": " + id);



    }

    public void showFlatGenerator(String id, String command){

        Flat flat = new Flat(Long.valueOf(id));

        // Создание контейнера AnchorPane
        AnchorPane anchorPane = new AnchorPane();

        // Создание текстовых полей ввода, кнопок и надписей
        TextField textField_for_name = new TextField();
        textField_for_name.setMaxWidth(200);

        TextField textField_for_X = new TextField();
        textField_for_X.setMaxWidth(200);

        TextField textField_for_Y = new TextField();
        textField_for_Y.setMaxWidth(200);

        TextField textField_for_area = new TextField();
        textField_for_area.setMaxWidth(200);

        TextField textField_for_number_of_rooms = new TextField();
        textField_for_number_of_rooms.setMaxWidth(200);

        TextField textField_for_kitchen_Area = new TextField();
        textField_for_kitchen_Area.setMaxWidth(200);

        TextField textField_for_view = new TextField();
        textField_for_view.setMaxWidth(200);

        TextField textField_for_transport = new TextField();
        textField_for_transport.setMaxWidth(200);

        TextField textField_for_house_name = new TextField();
        textField_for_house_name.setMaxWidth(200);

        TextField textField_for_house_year = new TextField();
        textField_for_house_year.setMaxWidth(200);

        TextField textField_for_number_Of_Flats_On_Floor = new TextField();
        textField_for_number_Of_Flats_On_Floor.setMaxWidth(200);

        Text text_name = new Text("name");
        Text text_X = new Text("coordinate X");
        Text text_Y = new Text("coordinate Y");
        Text text_area = new Text("area");
        Text text_number_of_rooms = new Text("number of rooms");
        Text text_kitchen_Area = new Text("kitchen Area");
        Text text_view = new Text("view");
        Text text_transport = new Text("transport");
        Text text_name_of_house = new Text("name of house");
        Text text_year_of_house = new Text("year of house");
        Text text_number_Of_Flats_On_Floor = new Text("number Of Flats On Floor");
        Text text_for_exception = new Text();

        Button button_create = new Button("create flat");
        Button button_back = new Button("Back");

        // Создание VBox для центрирования элементов
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(
                text_name, textField_for_name,
                text_X, textField_for_X,
                text_Y, textField_for_Y,
                text_area, textField_for_area,
                text_number_of_rooms, textField_for_number_of_rooms,
                text_kitchen_Area, textField_for_kitchen_Area,
                text_view, textField_for_view,
                text_transport, textField_for_transport,
                text_name_of_house, textField_for_house_name,
                text_year_of_house, textField_for_house_year,
                text_number_Of_Flats_On_Floor, textField_for_number_Of_Flats_On_Floor,
                button_create, text_for_exception
        );

        // Добавление VBox в AnchorPane
        AnchorPane.setTopAnchor(vbox, 50.0);
        AnchorPane.setLeftAnchor(vbox, 50.0);
        AnchorPane.setRightAnchor(vbox, 50.0);
        AnchorPane.setBottomAnchor(vbox, 50.0);

        AnchorPane.setLeftAnchor(button_back, 50.0);
        AnchorPane.setBottomAnchor(button_back, 58.5);

        anchorPane.getChildren().addAll(vbox, button_back);

        // Добавление обработки кнопок
        button_create.setOnAction(e -> {
            try {
                flat.setName(textField_for_name.getText());
                flat.setCoordinates(new Coordinates(
                        Double.parseDouble(textField_for_X.getText()),
                        Long.parseLong(textField_for_Y.getText())
                ));
                flat.setArea(Long.parseLong(textField_for_area.getText()));
                flat.setNumberOfRooms(Long.parseLong(textField_for_number_of_rooms.getText()));
                flat.setKitchenArea(Double.valueOf(textField_for_kitchen_Area.getText()));
                flat.setView(View.valueOf(textField_for_view.getText()));
                flat.setTransport(Transport.valueOf(textField_for_transport.getText()));
                House house = new House();
                house.setName(textField_for_house_name.getText());
                house.setYear(Long.valueOf(textField_for_house_year.getText()));
                house.setNumberOfFlatsOnFloor(Integer.valueOf(textField_for_number_Of_Flats_On_Floor.getText()));
                flat.setHouse(house);

                response = client.sendEcho(new Request(command, Long.valueOf(id), flat));
                if (response.matches("\\d+")){
                    LocalizationUtil.setLocale(new Locale(language));
                    response = LocalizationUtil.getString("collection_successfully_changed!_flats_deleted_-_") + " - " + response;
                } else {
                    response = response.replaceAll(" ", "_");
                    LocalizationUtil.setLocale(new Locale(language));
                    response = LocalizationUtil.getString(response);
                }

                showPopupWindow(response);
                showCommandsScene(id);
            } catch (Exception h) {
                LocalizationUtil.setLocale(new Locale(language));
                text_for_exception.setText(LocalizationUtil.getString("wrong_input_data_check_fields_again"));
            }
        });

        button_back.setOnAction(e -> showCommandsScene(id));

        // Настройка и отображение сцены
        Scene scene = new Scene(anchorPane, 700, 800);
        stage.setTitle("Flat creator");
        stage.setScene(scene);
        stage.show();


        LocalizationUtil.setLocale(new Locale(language));
        text_name.setText(LocalizationUtil.getString("name"));
        text_X.setText(LocalizationUtil.getString("coordinate_X"));
        text_Y.setText(LocalizationUtil.getString("coordinate_Y"));
        text_area.setText(LocalizationUtil.getString("area"));
        text_number_of_rooms.setText(LocalizationUtil.getString("number_of_rooms"));
        text_kitchen_Area.setText(LocalizationUtil.getString("kitchen_Area"));
        text_view.setText(LocalizationUtil.getString("view"));
        text_transport.setText(LocalizationUtil.getString("transport"));
        text_name_of_house.setText(LocalizationUtil.getString("name_of_house"));
        text_year_of_house.setText(LocalizationUtil.getString("year_of_house"));
        text_number_Of_Flats_On_Floor.setText(LocalizationUtil.getString("number_Of_Flats_On_Floor"));
        button_back.setText(LocalizationUtil.getString("Back"));
        button_create.setText(LocalizationUtil.getString("create_flat"));

    }

    public void showHouseGenerator(String id, String command){
        House house = new House();

        // Создание контейнера AnchorPane
        AnchorPane anchorPane = new AnchorPane();

        // Создание текстовых полей ввода, кнопок и надписей
        TextField textField_for_house_name = new TextField();
        textField_for_house_name.setMaxWidth(200);

        TextField textField_for_house_year = new TextField();
        textField_for_house_year.setMaxWidth(200);

        TextField textField_for_number_Of_Flats_On_Floor = new TextField();
        textField_for_number_Of_Flats_On_Floor.setMaxWidth(200);


        Text text_name_of_house = new Text("name of house");
        Text text_year_of_house = new Text("year of house");
        Text text_number_Of_Flats_On_Floor = new Text("number Of Flats On Floor");
        Text text_for_exception = new Text();

        Button button_create = new Button("create house");
        Button button_back = new Button("Back");

        // Создание VBox для центрирования элементов
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(
                text_name_of_house, textField_for_house_name,
                text_year_of_house, textField_for_house_year,
                text_number_Of_Flats_On_Floor, textField_for_number_Of_Flats_On_Floor,
                button_create, text_for_exception
        );

        // Добавление VBox в AnchorPane
        AnchorPane.setTopAnchor(vbox, 50.0);
        AnchorPane.setLeftAnchor(vbox, 50.0);
        AnchorPane.setRightAnchor(vbox, 50.0);
        AnchorPane.setBottomAnchor(vbox, 50.0);

        AnchorPane.setLeftAnchor(button_back, 50.0);
        AnchorPane.setBottomAnchor(button_back, 58.5);

        anchorPane.getChildren().addAll(vbox, button_back);

        response = "Houses greater than your";
        // Добавление обработки кнопок
        button_create.setOnAction(e -> {
            try {
                house.setName(textField_for_house_name.getText());
                house.setYear(Long.valueOf(textField_for_house_year.getText()));
                house.setNumberOfFlatsOnFloor(Integer.valueOf(textField_for_number_Of_Flats_On_Floor.getText()));
                request.setHouse(house);
                request.setMessage(command);
                response += (" - " + client.sendEcho(request));
                showPopupWindow(response);
                showCommandsScene(id);
            } catch (Exception h) {
                LocalizationUtil.setLocale(new Locale(language));
                text_for_exception.setText(LocalizationUtil.getString("wrong_input_data_check_fields_again"));
            }
        });

        button_back.setOnAction(e -> showCommandsScene(id));

        // Настройка и отображение сцены
        Scene scene = new Scene(anchorPane, 700, 800);
        stage.setTitle("House creator");
        stage.setScene(scene);
        stage.show();

        LocalizationUtil.setLocale(new Locale(language));
        response = LocalizationUtil.getString("houses_greater_than_your");
        text_name_of_house.setText(LocalizationUtil.getString("name_of_house"));
        text_year_of_house.setText(LocalizationUtil.getString("year_of_house"));
        text_number_Of_Flats_On_Floor.setText(LocalizationUtil.getString("number_Of_Flats_On_Floor"));
        button_create.setText(LocalizationUtil.getString("create_house"));
        button_back.setText(LocalizationUtil.getString("Back"));


    }

    public void showPopupWindow(String result) {
        // Создание нового окна
        Stage popupStage = new Stage();
        popupStage.setTitle("Results");

        // Создание содержимого для нового окна
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);

        Text text = new Text(result);
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> popupStage.close());

        vbox.getChildren().addAll(text, closeButton);

        // Создание сцены и добавление содержимого в новое окно
        Scene scene = new Scene(vbox, 300, 300);
        popupStage.setScene(scene);

        // Установка модальности для нового окна
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initOwner(stage);

        LocalizationUtil.setLocale(new Locale(language));
        closeButton.setText(LocalizationUtil.getString("close"));


        // Отображение нового окна
        popupStage.showAndWait();
    }

    public void showRemoveCommandWindow(long id){
        // Создание нового окна
        Stage popupStage = new Stage();
        popupStage.setTitle("Remove by id");

        // Создание содержимого для нового окна
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);

        Text text = new Text();
        TextField textField_for_id = new TextField();
        textField_for_id.setMaxWidth(100);
        Button button_remove = new Button("remove");

        button_remove.setOnAction(e -> {
            try {
                request.setId(id);
                request.setMessage("remove_by_id " + textField_for_id.getText());
                if (request.getMessage().split(" ").length != 2 || !request.getMessage().split(" ")[1].matches("^\\d+")){
                    throw new WrongInputException();
                }
                response = client.sendEcho(request);
                response = response.replaceAll(" ", "_");
                LocalizationUtil.setLocale(new Locale(language));
                response = LocalizationUtil.getString(response);
                showPopupWindow(response);
            } catch (IOException | WrongInputException ex) {
                LocalizationUtil.setLocale(new Locale(language));
                showPopupWindow(LocalizationUtil.getString("wrong_input_data_check_fields_again"));
            }
        });

        vbox.getChildren().addAll(text, textField_for_id, button_remove);

        // Создание сцены и добавление содержимого в новое окно
        Scene scene = new Scene(vbox, 300, 200);
        popupStage.setScene(scene);

        // Установка модальности для нового окна
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initOwner(stage);

        LocalizationUtil.setLocale(new Locale(language));
        button_remove.setText(LocalizationUtil.getString("remove"));

        // Отображение нового окна
        popupStage.showAndWait();

    }

    public void showUpdateCommandWindow(String id){
        // Создание нового окна
        Stage popupStage = new Stage();
        popupStage.setTitle("Update");

        // Создание содержимого для нового окна
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);

        Text text = new Text();
        TextField textField_for_id = new TextField();
        textField_for_id.setMaxWidth(100);
        Button button_update = new Button("update");

        button_update.setOnAction(e -> {
            try {
                request.setMessage("update " + textField_for_id.getText());
                if (request.getMessage().split(" ").length != 2 || !request.getMessage().split(" ")[1].matches("^\\d+")){
                    throw new WrongInputException();
                }
                request.setId(Long.valueOf(id));
                response = client.sendEcho(request);
                if (response.equals("id is good")){
                    showFlatGenerator(id, request.getMessage());
                    popupStage.close();
                } else {
                    response = response.replaceAll(" ", "_");
                    LocalizationUtil.setLocale(new Locale(language));
                    showPopupWindow(LocalizationUtil.getString(response));
                }
            } catch (IOException | WrongInputException ex) {
                LocalizationUtil.setLocale(new Locale(language));
                showPopupWindow(LocalizationUtil.getString("wrong_input_data_check_fields_again"));
            }
        });

        vbox.getChildren().addAll(text, textField_for_id, button_update);

        // Создание сцены и добавление содержимого в новое окно
        Scene scene = new Scene(vbox, 300, 200);
        popupStage.setScene(scene);

        // Установка модальности для нового окна
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initOwner(stage);


        LocalizationUtil.setLocale(new Locale(language));
        button_update.setText(LocalizationUtil.getString("update"));


        // Отображение нового окна
        popupStage.showAndWait();

    }

    public void showMapScene(String id) throws IOException {
        String[] flats = client.sendEcho(new Request("show")).split("/");
        RedhouseImage = new Image("дом_красный.JPEG");
        GreenhouseImage = new Image("дом_зеленый.JPEG");

        Canvas canvas = new Canvas(2000, 2000);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root = new Pane();

        Button button_back = new Button("Back");
        button_back.setOnAction(e -> showMainScene(id));

        // Устанавливаем позицию кнопки
        button_back.setLayoutX(50);
        button_back.setLayoutY(800);

        root.getChildren().addAll(canvas, button_back);

        gc.clearRect(0, 0, 2000, 2000);

        canvas.setOnMouseClicked(event -> {
            double mouseX = event.getX();
            double mouseY = event.getY();

            // Корректируем координаты мыши с учетом смещения
            double adjustedMouseX = mouseX - offsetX;
            double adjustedMouseY = mouseY - offsetY;

            if (!flats[0].equals("No flats :(")) {
                for (String flat : flats) {
                    double flatX = Double.parseDouble(flat.split(" ")[3]);
                    double flatY = Double.parseDouble(flat.split(" ")[4]);
                    double flatArea = Double.parseDouble(flat.split(" ")[5]) * 0.6; // использования площади

                    boolean isInside = (adjustedMouseX >= flatX && adjustedMouseX <= flatX + flatArea &&
                            adjustedMouseY >= flatY && adjustedMouseY <= flatY + flatArea);

                    if (isInside) {
                        try {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = dateFormat.parse(flat.split(" ")[13]);
                        showFlatInfoWindow(
                                "id - " + flat.split(" ")[0] + "\n"
                                        + "owner_id - " + flat.split(" ")[1] + "\n"
                                        + "name - " + flat.split(" ")[2] + "\n"
                                        + "coordinate_X - " + flat.split(" ")[3] + "\n"
                                        + "coordinate_Y - " + flat.split(" ")[4] + "\n"
                                        + "area - " + flat.split(" ")[5] + "\n"
                                        + "number_of_rooms - " + flat.split(" ")[6] + "\n"
                                        + "kitchen_area - " + flat.split(" ")[7] + "\n"
                                        + "view - " + flat.split(" ")[8] + "\n"
                                        + "transport - " + flat.split(" ")[9] + "\n"
                                        + "name_of_house - " + flat.split(" ")[10] + "\n"
                                        + "year_of_house - " + flat.split(" ")[11] + "\n"
                                        + "number_of_flats_on_floor - " + flat.split(" ")[12] + "\n"
                                        + "creation_date - " + convertDateFormat(dateformat.format(date)) + "\n",
                                flat.split(" "),
                                Long.parseLong(id), gc
                        );
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        break;
                    }
                }
            }
        });



        // Инициализация карты
        drawMapAnm(gc, Long.parseLong(id));

        canvas.setOnMousePressed(event -> {
            lastMouseX = event.getSceneX();
            lastMouseY = event.getSceneY();
        });

        canvas.setOnMouseDragged(event -> {
            double deltaX = event.getSceneX() - lastMouseX;
            double deltaY = event.getSceneY() - lastMouseY;

            offsetX += deltaX;
            offsetY += deltaY;

            lastMouseX = event.getSceneX();
            lastMouseY = event.getSceneY();

            try {
                drawMap(gc, Long.parseLong(id));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });




        stage.setScene(new Scene(root, 1000, 1000));
        stage.setTitle("Map of flats");
        stage.show();

        LocalizationUtil.setLocale(new Locale(language));
        button_back.setText(LocalizationUtil.getString("Back"));

    }

    private void drawMap(GraphicsContext gc, long id) throws IOException {
        String[] flats = client.sendEcho(new Request("show")).split("/");
        gc.clearRect(0, 0, 2000, 2000);
        gc.save();
        gc.translate(offsetX, offsetY);

        // Здесь вы рисуете вашу карту. Для примера нарисуем сетку.
        for (int i = -2000; i < 2000; i += 100) {
            for (int j = -2000; j < 2000; j += 100) {
                gc.setStroke(Color.GRAY);
                gc.strokeRect(i, j, 100, 100);
            }
        }

        root.getChildren().removeIf(node -> node instanceof ImageView);

        for (String flat: flats) {
            if (!flat.equals("No flats :(") && Long.parseLong(flat.split(" ")[1]) == id){
                gc.drawImage(GreenhouseImage,
                        Double.parseDouble(flat.split(" ")[3]),
                        Double.parseDouble(flat.split(" ")[4]),
                        Double.parseDouble(flat.split(" ")[5]) * 0.6,
                        Double.parseDouble(flat.split(" ")[5]) * 0.6);
            } else if (!flat.equals("No flats :(")) {
                gc.drawImage(RedhouseImage,
                        Double.parseDouble(flat.split(" ")[3]),
                        Double.parseDouble(flat.split(" ")[4]),
                        Double.parseDouble(flat.split(" ")[5]) * 0.6,
                        Double.parseDouble(flat.split(" ")[5]) * 0.6);
            }

        }
        gc.restore();
    }

    private void drawMapAnm(GraphicsContext gc, long id) throws IOException {
        String[] flats = client.sendEcho(new Request("show")).split("/");
        gc.clearRect(0, 0, 2000, 2000);
        gc.save();
        gc.translate(offsetX, offsetY);

        // Здесь нарисуем сетку.
        for (int i = -2000; i < 2000; i += 100) {
            for (int j = -2000; j < 2000; j += 100) {
                gc.setStroke(Color.GRAY);
                gc.strokeRect(i, j, 100, 100);
            }
        }

        // Удаляем старые дома перед добавлением новых
        root.getChildren().removeIf(node -> node instanceof ImageView);

        for (String flat : flats) {
            if (!flat.equals("No flats :(")) {
                ImageView houseImageView = new ImageView();
                if (Long.parseLong(flat.split(" ")[1]) == id) {
                    houseImageView.setImage(GreenhouseImage);
                } else {
                    houseImageView.setImage(RedhouseImage);
                }

                houseImageView.setX(Double.parseDouble(flat.split(" ")[3]) + offsetX);
                houseImageView.setY(Double.parseDouble(flat.split(" ")[4]) + offsetY);
                houseImageView.setFitWidth(Double.parseDouble(flat.split(" ")[5]) * 0.6);
                houseImageView.setFitHeight(Double.parseDouble(flat.split(" ")[5]) * 0.6);

                // Добавляем анимацию появления
                animateNode(houseImageView, true);

                // Добавляем изображение в root
                root.getChildren().add(houseImageView);
            }
        }
        gc.restore();
    }

    private void drawMapAnm(GraphicsContext gc, long id, boolean b, long needed_id) throws IOException, InterruptedException {
        String[] flats = client.sendEcho(new Request("show")).split("/");
        gc.clearRect(0, 0, 2000, 2000);
        gc.save();
        gc.translate(offsetX, offsetY);

        // Здесь нарисуем сетку.
        for (int i = -2000; i < 2000; i += 100) {
            for (int j = -2000; j < 2000; j += 100) {
                gc.setStroke(Color.GRAY);
                gc.strokeRect(i, j, 100, 100);
            }
        }

        // Удаляем старые дома перед добавлением новых
        root.getChildren().removeIf(node -> node instanceof ImageView);

        for (String flat : flats) {
            if (!flat.equals("No flats :(")) {
                ImageView houseImageView = new ImageView();
                if (Long.parseLong(flat.split(" ")[1]) == id) {
                    houseImageView.setImage(GreenhouseImage);
                } else {
                    houseImageView.setImage(RedhouseImage);
                }

                houseImageView.setX(Double.parseDouble(flat.split(" ")[3]) + offsetX);
                houseImageView.setY(Double.parseDouble(flat.split(" ")[4]) + offsetY);
                houseImageView.setFitWidth(Double.parseDouble(flat.split(" ")[5]) * 0.6);
                houseImageView.setFitHeight(Double.parseDouble(flat.split(" ")[5]) * 0.6);

                // Добавляем анимацию появления
                if (Long.parseLong(flat.split(" ")[0]) == needed_id){
                    animateNode(houseImageView, b);
                } else {
                    animateNode(houseImageView, !b);
                }

                // Добавляем изображение в root
                root.getChildren().add(houseImageView);
            }
        }
        gc.restore();
    }

    private void animateNode(ImageView node, boolean fadeIn) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), node);
        if (fadeIn) {
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
        } else {
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.0);
        }
        fadeTransition.play();
    }



    public void showFlatInfoWindow(String result, String[] flat, long id, GraphicsContext gc) {
        // Создание нового окна
        Stage popupStage = new Stage();
        popupStage.setTitle("Info window");

        // Создание содержимого для нового окна
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);

        Text text = new Text(result);
        Text text_ans = new Text();
        String text_info = text.getText();

        Button closeButton = new Button("close");
        Button button_edit = new Button("edit");
        Button button_remove = new Button("remove");

        closeButton.setOnAction(e -> popupStage.close());
        button_edit.setOnAction(e -> {
            if (Long.parseLong(flat[1]) == id){
                showEditFlatWindow(flat);
            } else {
                LocalizationUtil.setLocale(new Locale(language));
                text_ans.setText(LocalizationUtil.getString("cant_edit_flat"));
            }
        });
        button_remove.setOnAction(e -> {
            if (Long.parseLong(flat[1]) == id){
                try {
                    popupStage.close();
                    drawMapAnm(gc, id, false, Long.parseLong(flat[0]));
                    request.setId((id));
                    request.setMessage("remove_by_id " + Long.parseLong(flat[0]));
                    client.sendEcho(request);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                LocalizationUtil.setLocale(new Locale(language));
                text_ans.setText(LocalizationUtil.getString("cant_remove_flat"));
            }
        });

        vbox.getChildren().addAll(text, text_ans, closeButton, button_edit, button_remove);

        // Создание сцены и добавление содержимого в новое окно
        Scene scene = new Scene(vbox, 300, 350);
        popupStage.setScene(scene);

        // Установка модальности для нового окна
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initOwner(stage);

        LocalizationUtil.setLocale(new Locale(language));
        closeButton.setText(LocalizationUtil.getString("close"));
        button_remove.setText(LocalizationUtil.getString("remove"));
        button_edit.setText(LocalizationUtil.getString("edit"));
        text_info = text_info.replaceFirst("id", LocalizationUtil.getString("id"));
        text_info = text_info.replaceFirst("owner_id", LocalizationUtil.getString("owner_id"));
        text_info = text_info.replaceFirst("name", LocalizationUtil.getString("name"));
        text_info = text_info.replaceFirst("coordinate_X", LocalizationUtil.getString("coordinate_X"));
        text_info = text_info.replaceFirst("coordinate_Y", LocalizationUtil.getString("coordinate_Y"));
        text_info = text_info.replaceFirst("area", LocalizationUtil.getString("area"));
        text_info = text_info.replaceFirst("number_of_rooms", LocalizationUtil.getString("number_of_rooms"));
        text_info = text_info.replaceFirst("kitchen_area", LocalizationUtil.getString("kitchen_area"));
        text_info = text_info.replaceFirst("view", LocalizationUtil.getString("view"));
        text_info = text_info.replaceFirst("transport", LocalizationUtil.getString("transport"));
        text_info = text_info.replaceFirst("name_of_house", LocalizationUtil.getString("name_of_house"));
        text_info = text_info.replaceFirst("year_of_house", LocalizationUtil.getString("year_of_house"));
        text_info = text_info.replaceFirst("number_of_flats_on_floor", LocalizationUtil.getString("number_Of_Flats_On_Floor"));
        text_info = text_info.replaceFirst("creation_date", LocalizationUtil.getString("creation_date"));
        text.setText(text_info);

        // Отображение нового окна
        popupStage.showAndWait();

    }

    public void showEditFlatWindow(String[] flats){

        Flat flat = new Flat(Long.parseLong(flats[0]));

        Stage popupStage = new Stage();
        popupStage.setTitle("Edit window");

        // Создание контейнера AnchorPane
        AnchorPane anchorPane = new AnchorPane();

        TextField textField_for_name = new TextField(flats[2]);
        textField_for_name.setMaxWidth(100);

        TextField textField_for_coordinate_X = new TextField(flats[3]);
        textField_for_coordinate_X.setMaxWidth(100);

        TextField textField_for_coordinate_Y = new TextField(flats[4]);
        textField_for_coordinate_Y.setMaxWidth(100);

        TextField textField_for_area = new TextField(flats[5]);
        textField_for_area.setMaxWidth(100);

        TextField textField_for_number_of_rooms = new TextField(flats[6]);
        textField_for_number_of_rooms.setMaxWidth(100);

        TextField textField_for_kitchen_area = new TextField(flats[7]);
        textField_for_kitchen_area.setMaxWidth(100);

        TextField textField_for_view = new TextField(flats[8]);
        textField_for_view.setMaxWidth(100);

        TextField textField_for_transport = new TextField(flats[9]);
        textField_for_transport.setMaxWidth(100);

        TextField textField_for_name_of_house = new TextField(flats[10]);
        textField_for_name_of_house.setMaxWidth(100);

        TextField textField_for_year_of_house = new TextField(flats[11]);
        textField_for_year_of_house.setMaxWidth(100);

        TextField textField_for_number_of_flats_on_floor = new TextField(flats[12]);
        textField_for_number_of_flats_on_floor.setMaxWidth(100);


        Text text_name = new Text("name");
        Text text_X = new Text("coordinate X");
        Text text_Y = new Text("coordinate Y");
        Text text_area = new Text("area");
        Text text_number_of_rooms = new Text("number of rooms");
        Text text_kitchen_Area = new Text("kitchen Area");
        Text text_view = new Text("view");
        Text text_transport = new Text("transport");
        Text text_name_of_house = new Text("name of house");
        Text text_year_of_house = new Text("year of house");
        Text text_number_Of_Flats_On_Floor = new Text("number Of Flats On Floor");
        Text text_for_exception = new Text();

        Button button_conf = new Button("Confirm");


        button_conf.setOnAction(e -> {
            try {
                flat.setName(textField_for_name.getText());
                flat.setCoordinates(new Coordinates(
                        Double.parseDouble(textField_for_coordinate_X.getText()),
                        Long.parseLong(textField_for_coordinate_Y.getText())
                ));
                flat.setArea(Long.parseLong(textField_for_area.getText()));
                flat.setNumberOfRooms(Long.parseLong(textField_for_number_of_rooms.getText()));
                flat.setKitchenArea(Double.valueOf(textField_for_kitchen_area.getText()));
                flat.setView(View.valueOf(textField_for_view.getText()));
                flat.setTransport(Transport.valueOf(textField_for_transport.getText()));
                House house = new House();
                house.setName(textField_for_name_of_house.getText());
                house.setYear(Long.valueOf(textField_for_year_of_house.getText()));
                house.setNumberOfFlatsOnFloor(Integer.valueOf(textField_for_number_of_flats_on_floor.getText()));
                flat.setHouse(house);
                response = client.sendEcho(new Request("update " + flats[0], Long.parseLong(flats[1]), flat));
                popupStage.close();
                try {
                    showMapScene(flats[1]);
                } catch (IOException h) {
                    throw new RuntimeException(h);
                }
                LocalizationUtil.setLocale(new Locale(language));
                response = LocalizationUtil.getString(response.replaceAll(" ", "_"));
                showEditResultPopupWindow(response);
            } catch (Exception h) {
                LocalizationUtil.setLocale(new Locale(language));
                text_for_exception.setText(LocalizationUtil.getString("wrong_input_data_check_fields_again"));
            }
        });

        // Создание VBox для центрирования элементов
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(
                text_name, textField_for_name,
                text_X, textField_for_coordinate_X,
                text_Y, textField_for_coordinate_Y,
                text_area, textField_for_area,
                text_number_of_rooms, textField_for_number_of_rooms,
                text_kitchen_Area, textField_for_kitchen_area,
                text_view, textField_for_view,
                text_transport, textField_for_transport,
                text_name_of_house, textField_for_name_of_house,
                text_year_of_house, textField_for_year_of_house,
                text_number_Of_Flats_On_Floor, textField_for_number_of_flats_on_floor,
                button_conf, text_for_exception
        );

        // Добавление VBox в AnchorPane
        AnchorPane.setTopAnchor(vbox, 50.0);
        AnchorPane.setLeftAnchor(vbox, 50.0);
        AnchorPane.setRightAnchor(vbox, 50.0);
        AnchorPane.setBottomAnchor(vbox, 50.0);


        anchorPane.getChildren().addAll(vbox);

        // Создание сцены и добавление содержимого в новое окно
        Scene scene = new Scene(anchorPane, 300, 750);
        popupStage.setScene(scene);

        // Установка модальности для нового окна
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initOwner(stage);


        LocalizationUtil.setLocale(new Locale(language));
        text_name.setText(LocalizationUtil.getString("name"));
        text_X.setText(LocalizationUtil.getString("coordinate_X"));
        text_Y.setText(LocalizationUtil.getString("coordinate_Y"));
        text_area.setText(LocalizationUtil.getString("area"));
        text_number_of_rooms.setText(LocalizationUtil.getString("number_of_rooms"));
        text_kitchen_Area.setText(LocalizationUtil.getString("kitchen_Area"));
        text_view.setText(LocalizationUtil.getString("view"));
        text_transport.setText(LocalizationUtil.getString("transport"));
        text_name_of_house.setText(LocalizationUtil.getString("name_of_house"));
        text_year_of_house.setText(LocalizationUtil.getString("year_of_house"));
        text_number_Of_Flats_On_Floor.setText(LocalizationUtil.getString("number_Of_Flats_On_Floor"));
        button_conf.setText(LocalizationUtil.getString("confirm"));


        // Отображение нового окна
        popupStage.showAndWait();
    }

    public void showEditResultPopupWindow(String result) {
        // Создание нового окна
        Stage popupStage = new Stage();
        popupStage.setTitle("Results");

        // Создание содержимого для нового окна
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);

        Text text = new Text(result);
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> popupStage.close());

        vbox.getChildren().addAll(text, closeButton);

        // Создание сцены и добавление содержимого в новое окно
        Scene scene = new Scene(vbox, 300, 300);
        popupStage.setScene(scene);

        // Установка модальности для нового окна
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initOwner(stage);

        LocalizationUtil.setLocale(new Locale(language));
        closeButton.setText(LocalizationUtil.getString("close"));



        // Отображение нового окна
        popupStage.showAndWait();
    }


}