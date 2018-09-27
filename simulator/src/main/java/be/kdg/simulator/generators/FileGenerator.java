package be.kdg.simulator.generators;

import be.kdg.simulator.model.CameraMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Scanner;

@Component
@ConditionalOnProperty(name = "generator.type", havingValue = "file")
public class FileGenerator implements MessageGenerator{
    @Value("${file.location}")
    private String location;


    private File readableFile = new File("D:\\IdeaProjects\\cameraDetector\\simulator\\src\\main\\resources\\readFile\\FindMe.txt");
    private Scanner sc;

    {
        try {
            sc = new Scanner(readableFile);
        } catch (FileNotFoundException e) {
            e.getMessage();
        }
    }


    @Override
    public CameraMessage generate() {

        if (sc.hasNext()) {
                String[] splittedLine = sc.nextLine().split(",");
                return new CameraMessage(Integer.valueOf(splittedLine[0]),splittedLine[1],LocalDateTime.now(),Integer.valueOf(splittedLine[2]));
            }
            return null;
    }
}
