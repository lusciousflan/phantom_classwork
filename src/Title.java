public class Title {
    private int width;
    private int height;
    public Title(int w, int h) {
        width = w;
        height = h;
    }
    public void paint(View view) {
        String s = "Phantom";
        for(int i = 0; i < s.length(); i++) {
            view.put(s.charAt(i), width/2-s.length()/2+i, 5);
        }
    }
}
