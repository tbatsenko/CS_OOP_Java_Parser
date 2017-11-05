import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ReviewsPageParser {
    static void parseReviews(String url) throws IOException, CsvDataTypeMismatchException,
            CsvRequiredFieldEmptyException {
        /**
         * This function gets reviews of the page with given URL, calls function parseReviewsPage to get reviews and writes
         * them to .csv file for each product.
        */

        Elements nums = Parser.getElements("a.paginator-catalog-l-link", url);
        int num = 0;
        if (nums.toArray().length > 0) {
            num = Integer.parseInt(nums.last().text());
        }

        List<List<List<String>>> sentiments = new ArrayList<List<List<String>>>();

        for (int i=0; i < num; i++){
            String pg = url + String.format("/page=%d", i+1);
            sentiments.add(ReviewsParser.parseReviewsPage(pg));
        }

        String filename = "data/" + url.split("/")[4] + ".csv";


        Writer writer = new FileWriter(filename);
        StatefulBeanToCsv fileToCsv = new StatefulBeanToCsvBuilder(writer).build();

        int reviewsCounter = 0;

        for (List sentiment: sentiments
                ) {
            for (Object comment : sentiment) {
                reviewsCounter += 1;

                writer.write(comment.toString().replace("[", "").replace("]", "") + "\n");
            }
        }

        writer.close();

        System.out.println(reviewsCounter + " reviews from " + url);

    }
}