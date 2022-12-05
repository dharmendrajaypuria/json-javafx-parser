package com.json.parser;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StageInitializer implements ApplicationListener<JsonParserApplication.StageReadyEvent> {
    @Value("classpath:/json-parser-view.fxml")
    private Resource viewResource;
    private final String applicationTitle;
    private final ApplicationContext applicationContext;
    
    public StageInitializer(@Value("${spring.application.ui.title}") String applicationTitle, ApplicationContext applicationContext) {
        this.applicationTitle = applicationTitle;
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(JsonParserApplication.StageReadyEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(viewResource.getURL());
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Stage stage = event.getStage();
            stage.setScene(new Scene(fxmlLoader.load(), 800, 600));
            stage.setTitle(applicationTitle);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
