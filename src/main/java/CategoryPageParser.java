import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class CategoryPageParser {
    static void parseCategoryPage(String url) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        /*
        This function connects to the page of catalog with given URL and calls function to parseReviews
        */

        Elements tiles = Parser.getElements("div.g-i-tile-i-title", url);

        for (Element tile: tiles) {
            String link = tile.select("a").attr("href") + "comments";
            ReviewsPageParser.parseReviews(link);
        }
    }
}
