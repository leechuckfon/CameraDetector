package be.kdg.processor;

import be.kdg.processor.serviceUser.BoeteAnalyser;
import be.kdg.processor.serviceUser.CameraAnalyser;
import be.kdg.processor.serviceUser.LicensePlateAnalyser;
import be.kdg.sa.services.LicensePlateServiceProxy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class ProcessorApplication {


    public static void main(String[] args) {
        CameraAnalyser ca = new CameraAnalyser();
        try {
            ca.AskInfo(4);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SpringApplication.run(ProcessorApplication.class, args);

    }
}
