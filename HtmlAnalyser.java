
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.List;
import java.util.ArrayList;


public class HtmlAnalyser {

    public static void main(String[] args) {

        if (args.length != 1) {
            /* System.out.println("Please, inform at least one URL");
            return; */
        }

        String urlString = "https://raw.githubusercontent.com/PaBLOHenCh/ParserHtml/refs/heads/main/html_tests/html_01.html";

        try {
            List<String> lines = downloadHtml(urlString);
            Result result = searchDeepestText(lines);

            result.print_result();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static List<String> downloadHtml(String urlString) throws Exception {

        URL url = new URL(urlString);

        List<String> lines = new ArrayList<>();
        String line;


        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            while ((line = reader.readLine()) != null) {
                line = line.strip();
    
                if(!(line.isEmpty())) {
                    lines.add(line);
                    lines.add("\n");
                }
                
            }
    
            reader.close();
        } catch (Exception e) {
            throw new Exception("Invalid URL: " + urlString+ "\nOr error while processing file");
        }
        
        return lines;
    }

    private static Result searchDeepestText(List<String> htmlLines) throws Exception {

        Deque<String> stack = new ArrayDeque<>();

        String peek ="";

        int maxDepth = 0;
        int tempMaxDepth = 0;
        String deepestText = "";

        for (String line : htmlLines) {
            int lineLength = line.length();
            if(!(line.startsWith("</"))){
                //its text or opening tag

                if(line.startsWith("<")) {
                    //its opening tag
                    stack.push(line.substring(1, lineLength - 1));
                    tempMaxDepth += 1;
                }
                else if(line == "" || line == "\n"){
                    continue;
                }
                else{
                    //its text
                    if(tempMaxDepth > maxDepth){
                        deepestText = line;
                    }
                }
            }
            else{
                //its closing tag
                String tag = line.substring(2, lineLength -1);

                peek = stack.peekFirst();


                if(peek.strip().equals(tag)){
                    stack.pop();
                    tempMaxDepth -= 1;
                }
                else{
                    throw new Exception("Tag " + line + " without corresponding opening tag HTML malformed");
                }
            }

            if(tempMaxDepth > maxDepth){
                maxDepth = tempMaxDepth ;
                
                
            }
        }

        if(stack.size() != 0){
            throw new Exception("HTML malformed. There are unclosed tags");
        }

        return new Result(maxDepth +1, deepestText);
    }

}