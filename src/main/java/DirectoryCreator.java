import java.io.File;

public class DirectoryCreator {
    static void createDataDirectory(){
        /**
         * This function creates a folder where scrapped data will be saved and connects to the Category catalog
         * @see "data" directory created in your path
         */

        String path;
        File file = new File(".");
        path = file.getAbsolutePath();
        File fileData = new File("data");
        if (!fileData.exists()) {
            fileData.mkdir();
        }
    }
}
