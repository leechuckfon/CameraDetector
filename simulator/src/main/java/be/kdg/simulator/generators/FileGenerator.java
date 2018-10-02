package be.kdg.simulator.generators;
import be.kdg.simulator.config.MessageScheduler;
import be.kdg.simulator.model.CameraMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Component
@ConditionalOnProperty(name = "generator.type", havingValue = "file")
public class FileGenerator implements MessageGenerator{
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageScheduler.class);

    private String location;
    private File readableFile;
    private List<CameraMessage> cmL;

    @Autowired
    public FileGenerator(@Value("${file_location}") String readableFile) {
        this.readableFile = new File(readableFile);
        cmL = new ArrayList<>();
        fillArray();
    }

    private void fillArray(){
        try (BufferedReader br = new BufferedReader(new FileReader(readableFile))) {
            String line = br.readLine();
            while (!line.equals("")) {
                String[] splittedLine = line.split(",");
                if (splittedLine[0].equals("")) {
                    splittedLine[0]="-9999";
                }
                try {
                    cmL.add(new CameraMessage(Integer.parseInt(splittedLine[0]), splittedLine[1], LocalDateTime.now(), Integer.parseInt(splittedLine[2])));
                } catch (NumberFormatException e) {
                    throw(e);
                } finally {
                    line = br.readLine();
                    continue;
                }

            }
        }
        catch (IOException | NumberFormatException e ){
            if (e.getClass() == IOException.class) {
                LOGGER.error("Exception opgetreden tijdens het lezen van file.");
            }
            if (e.getClass() == NumberFormatException.class) {
            }
        }
    }


    @Override
    public CameraMessage generate() {

        //readbuffer gebruiken
            if (cmL.size() !=0) {
                CameraMessage r = cmL.get(0);
                cmL.remove(0);
                return r;
            }
            System.exit(0);
            return null;
    }
}
