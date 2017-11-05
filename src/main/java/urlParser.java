public class urlParser {
    protected static String url = "https://rozetka.com.ua/ua/headphones/c80027/";

    public static void main(String[] args) throws Exception{
        if(args.length != 0){
            url = args[0];
        }
        // Parse Category
        CategoryParser.parseCategory(url);
    }
}
