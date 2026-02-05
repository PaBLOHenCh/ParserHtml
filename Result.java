public class Result {
    private int maxDepth;
    private String deepestText;

    public Result(int maxDepth, String deepestText) {
        this.maxDepth = maxDepth;
        this.deepestText = deepestText;
    }
    public void print_result(){
        System.out.println("Maxima profundidade: " + this.maxDepth);
        System.out.println("Texto mais profundo: " + this.deepestText);
    }
}
