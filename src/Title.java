public class Title {
    private int width;
    private int height;
    private boolean isHowTo;
    public Title(int w, int h) {
        width = w;
        height = h;
        isHowTo = false;
    }
    public void switchHowTo() {
        isHowTo = !isHowTo;
    }
    public void resetHowTo() {
        isHowTo = false;
    }
    private void drawStringCenter(View view, String s, int y) {
        for(int i = 0; i < s.length(); i++) {
            view.put(s.charAt(i), width/2-s.length()/2+i, y);
        }
    }
    public void paint(View view) {
        if(isHowTo) {
            drawStringCenter(view, "[How to play]", 3);
            drawStringCenter(view, "move : WASD or allow key", 5);
            drawStringCenter(view, "[Rule]", 10);
            drawStringCenter(view, "You are the Phantom theif!", 12);
            drawStringCenter(view, "You must steal the treasure and escape from the hunters.", 14);
            drawStringCenter(view, "Get all the treasures and escape!", 15);
            drawStringCenter(view, "[Game Start] : Press Enter", height-3);
        } else {
            drawStringCenter(view, "Phantom", 5);
            drawStringCenter(view, "[How to Play] : Press Space", height-5);
            drawStringCenter(view, "[Game Start] : Press Enter", height-3);
        }
    }
}
