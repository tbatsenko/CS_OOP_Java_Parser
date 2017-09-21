import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;


public class urlParser {
    public static void main(String[] args) throws Exception{

        String url = "https://rozetka.com.ua/ua/headphones/c80027/";
        parseCategory(url);

    }

    static void parseCategory(String url) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
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
            System.out.println(pg);
            parseCategoryPage(pg);
        }


    }

    static void parseCategoryPage(String url) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Document doc = Jsoup.connect(url).get();
        Elements tiles = doc.select("div.g-i-tile-i-title");
        for (Element tile: tiles) {
                String link = tile.select("a").attr("href") + "comments";
                parseReviews(link);
                }
    }

    static void parseReviews(String url) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Document doc = Jsoup.connect(url).get();
        Elements nums = doc.select("a.paginator-catalog-l-link");
        int num = 0;
        if (nums.toArray().length > 0) {
            num = Integer.parseInt(nums.last().text());
        }

        List<List<List<String>>> sentiments = new ArrayList<List<List<String>>>();

        for (int i=0; i < num; i++){
            String pg = url + String.format("/page=%d", i+1);
            sentiments.add(parseReviewsPage(pg));
        }
        //System.out.println(sentiments);
        String filename = "data/" + url.split("/")[4] + ".csv";


        Writer writer = new FileWriter(filename);
        StatefulBeanToCsv fileToCsv = new StatefulBeanToCsvBuilder(writer).build();

        int reviewsCounter = 0;

        for (List sentiment: sentiments
             ) {
            for (Object comment : sentiment) {
                reviewsCounter += 1;
                //System.out.println(comment.toString().replace("[", "").replace("]", ""));
                writer.write(comment.toString().replace("[", "").replace("]", "") + "\n");
            }
        }

        writer.close();

        System.out.println(reviewsCounter + " reviews from " + url);

    }

    static List<List<String>> parseReviewsPage(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements reviews = doc.select("article.pp-review-i");
        List<List<String>> sentiments = new ArrayList<List<String>>();


        for (Element review : reviews) {

            Elements star = review.select("span.g-rating-stars-i");

            if (star.size() > 0) {
                Elements text = review.select("div.pp-review-text");


                Elements texts = text.select("div.pp-review-text-i");
                String content = star.attr("content");
                String txt = texts.text();
                List<String> itemsToAdd = new ArrayList<String>();
                itemsToAdd.add(content);
                itemsToAdd.add(txt);

                sentiments.add(itemsToAdd);


            }
        }
        return sentiments;
    }
}
