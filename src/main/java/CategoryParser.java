import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;


public class CategoryParser {
    static void parseCategory(String url) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        /*
        This function creates a folder where scrapped data will be saved and connects to the Category catalog
         */
        String path;
        File file = new File(".");
        path = file.getAbsolutePath();
        File fileData = new File("data");
        if (!fileData.exists()) {
            fileData.mkdir();
        }

        Document doc = Jsoup.connect(url).get();
        Elements nums = doc.select("a.paginator-catalog-l-link");
        int num;
        num = Integer.parseInt(nums.last().text());

        for (int i=0; i < num; i++){
            String pg = url + String.format("page=%d", i+1);
            // parse page with id i+1
            PageParser.parseCategoryPage(pg);
        }


    }
}
