import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Parser{
        public static Elements getElements(String css_classes, String url) throws IOException {
                /**
                 * Returns html elements
                 * This function connects to url and gets elements with a given class.
                 * @param css_classes - String representing classes of Elements that should be selected
                 * @param url - String represening url where data is located
                 * @return html elements from a given url and gets elements with a given class
                */
                Document doc = Jsoup.connect(url).get();
                Elements elements = doc.select(css_classes);

                return elements;
                }
}
