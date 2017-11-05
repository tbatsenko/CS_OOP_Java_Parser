import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.jsoup.select.Elements;

import java.io.IOException;


public class CategoryParser extends Parser{

    static void parseCategory(String url) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        // Create DATA directory
        DirectoryCreator.createDataDirectory();

        Elements nums = Parser.getElements("a.paginator-catalog-l-link", url);
        int num;
        num = Integer.parseInt(nums.last().text());

        for (int i=0; i < num; i++){
            String pg = url + String.format("page=%d", i+1);
            // parse page with id i+1
            CategoryPageParser.parseCategoryPage(pg);
        }


    }
}
