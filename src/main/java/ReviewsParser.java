import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReviewsParser {
    static List<List<String>> parseReviewsPage(String url) throws IOException {
         /*
        This function connects to the page of catalog with given URL and get's all reviews with not null star and returns
        a List of Lists of <String> [[star, review], [...], ...]
        */
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
