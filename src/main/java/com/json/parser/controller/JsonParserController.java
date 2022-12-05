package com.json.parser.controller;

import com.json.parser.service.JsonParserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

@Component
public class JsonParserController {
    ObservableList<String> fileTypes = FXCollections.observableArrayList("CSV","PDF","XLSX");
    @FXML
    private Button submit;
    @FXML
    private TextArea jsonTextArea;
    @FXML
    private ChoiceBox<String> fileTypeChoice;

    private final JsonParserService jsonParserService;

    public JsonParserController(JsonParserService jsonParserService) {
        this.jsonParserService = jsonParserService;
    }

    @FXML
    private void initialize() {
        fileTypeChoice.setValue("CSV");
        fileTypeChoice.setItems(fileTypes);
    }

    @FXML
    protected void onSubmit() throws IOException {
        String csvData = jsonParserService.parse(jsonTextArea.getText(), fileTypeChoice.getValue());
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(submit.getScene().getWindow());
        if(Objects.nonNull(file)) {
            saveCsvToFile(csvData, file);
        }
    }

    private void saveCsvToFile(String content, File file) {
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
