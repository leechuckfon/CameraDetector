package be.kdg.simulator.generators;
import be.kdg.simulator.config.MessageScheduler;
import be.kdg.simulator.model.CameraMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cglib.core.Local;
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
            LocalDateTime ldt = LocalDateTime.now();
            while (!line.equals("")) {
                String[] splittedLine = line.split(",");
                if (splittedLine[0].equals("")) {
                    splittedLine[0]="-9999";
                }
                try {
                    ldt = ldt.plusNanos(Long.parseLong(splittedLine[2])*100000);
                    cmL.add(new CameraMessage(Integer.parseInt(splittedLine[0]), splittedLine[1], ldt  , Integer.parseInt(splittedLine[2])));
                } catch (NumberFormatException e) {
                    LOGGER.error("Er is een string waardat er een nummer verwacht wordt");
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
