import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.File;
import java.lang.reflect.Array;
import java.util.List;


public class urlParser {
    public static void main(String[] args) throws Exception{

        String url = "https://rozetka.com.ua/ua/headphones/c80027/";
        parseCategory(url);

    }

    static void parseCategory(String url) throws IOException {
        String path;
        File file = new File(".");
        path = file.getAbsolutePath();
        File fileData = new File("data");
        if (!fileData.exists()) {
            System.out.print("No Folder");
            fileData.mkdir();
            System.out.print("Folder created");
        }

        Document doc = Jsoup.connect(url).get();
        Elements nums = doc.select("a.paginator-catalog-l-link");
        int num = Integer.parseInt(nums.last().text());

        for (int i=0; i < num; i++){
            String pg = url + String.format("page=%d", i+1);
            System.out.println(pg);
            parseCategoryPage(pg);
        }


    }

    static void parseCategoryPage(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements tiles = doc.select("div.g-i-tile-i-title");
        for (Element tile: tiles
                ) {
                String link = tile.select("a").attr("href") + "comments";
                parseReviews(link);
                }
    }

    static void parseReviews(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements nums = doc.select("a.paginator-catalog-l-link");
        if (nums.toArray().length > 0){
            int num = Integer.parseInt(nums.last().text());
        } else {
            int num = 0;
        }



        /*


        sentiments = []

        for i in range(num):
           pg = url + 'page={}/'.format(i + 1)
           sentiments += parse_reviews_page(pg)

        filename = 'data/' + url.split('/')[4] + '.csv'

        with open(filename,'w') as fl:
            wr = csv.writer(fl, dialect='excel')
            wr.writerows(sentiments)

        print(len(sentiments), ' reviews from ', url)
         */

    }

    static void parseReviewsPages(String url){

    }
}
