import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Parser {
    public Parser(){}
    public Parser(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
    }
}
