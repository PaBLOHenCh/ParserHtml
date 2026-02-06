public class LineInfo {
    private int lineNumber;
    private String lineText;

    public LineInfo(int lineNumber, String lineText) {
        this.lineNumber = lineNumber;
        this.lineText = lineText;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getLineText() {
        return lineText;
    }
}
