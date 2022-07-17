package utilities;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;



/**
 * FileHandler is a helper class for reading/writing/deleting files.
 */
public abstract class FileHandler {


    /**
     * writes <i>String</i> to <i>file</i>
     * @param file String
     * @param input String
     */
    public static void write(String file, String input) {

        try {
            Files.write(Path.of(file), input.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * appends <i>String</i> to <i>file</i>
     * @param file String
     * @param input String
     * @param append boolean
     */
    public static void write(String file, String input, boolean append) {
        Path filePath = Path.of(file);

        if(append && Files.exists(filePath)) {
            try {
                Files.write(filePath, input.getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{write(file, input);}

    }

    /**
     * reads <i>String</i> from <i>file</i>
     * @param file String
     * @return String
     */
    public static String read(String file) {

        String content = "";
        try {
            Path path = Path.of(file);
            if(Files.exists(path)) {
                content = new String(Files.readAllBytes(path));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;

    }

    /**
     * deletes <i>file</i>
     * @param file String
     */
    public static void delete(String file) {
        Path filePath = Path.of(file);

        if (Files.exists(filePath)) {
            try {
                Files.delete(filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }


}

