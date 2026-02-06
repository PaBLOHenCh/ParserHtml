
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.List;

import javax.sound.sampled.Line;

import java.util.ArrayList;


public class HtmlAnalyser {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Please, inform at least one URL");
            return;
        }

        String urlString = args[0];

        try {
            List<LineInfo> lines = downloadHtml(urlString);
            Result result = searchDeepestText(lines);

            result.print_result();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static List<LineInfo> downloadHtml(String urlString) throws Exception {

        URL url = new URL(urlString);

        List<LineInfo> lines = new ArrayList<>();
        String line;
        int lineNumber = 0;


        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            while ((line = reader.readLine()) != null) {
                line = line.strip();
                lineNumber++;
    
                if(!(line.isEmpty())) {
                    lines.add(new LineInfo(lineNumber, line));
                }
                
            }
    
            reader.close();
        } catch (Exception e) {
            throw new Exception("Invalid URL: " + urlString+ "\nOr error while processing file");
        }
        
        return lines;
    }

    private static Result searchDeepestText(List<LineInfo> htmlLines) throws Exception {

        Deque<String> stack = new ArrayDeque<>();

        String peek ="";

        int maxDeep = 0;
        int localMaxDeep = 0;
        String deepestText = "";

        for (LineInfo line : htmlLines) {
            String line_text = line.getLineText().strip();
            int lineLength = line_text.length();
            if(!(line_text.startsWith("</"))){
                //its text or opening tag

                if(line_text.startsWith("<")) {
                    //its opening tag
                    stack.push(line_text.substring(1, lineLength - 1));
                    localMaxDeep += 1;
                }
                else if(line_text == "" || line_text == "\n"){
                    continue;
                }
                else{
                    //its text
                    if(localMaxDeep > maxDeep){
                        deepestText = line_text;
                        maxDeep = localMaxDeep;
                    }
                }
            }
            else{
                //its closing tag
                String tag = line_text.substring(2, lineLength -1);

                peek = stack.peekFirst();

                if(peek == null){
                    throw new Exception("Error at line " + line.getLineNumber() + "." + "\nThere are many closing tags, " + line_text + " are not necessary");
                }

                if(peek.strip().equals(tag)){
                    stack.pop();
                    localMaxDeep -= 1;
                }
                else{
                    throw new Exception("Error at line " + line.getLineNumber() + "." + "\nYou cant close tag " + line_text
                    + " without close tag"+ " <"+peek+">" + "\nHTML malformed");
                }
            }

        }

        if(stack.size() != 0){
            throw new Exception("HTML malformed. There are unclosed tags");
        }

        return new Result(maxDeep, deepestText);
    }

}