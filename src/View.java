
public class View {
	private final static int WIDTH = 80;
	private final static int HEIGHT = 24;
	char[][] screen;
	private Model model;
	
	public View(Model model) {
		this.model = model;
		screen = new char[WIDTH][HEIGHT];
		clear();
	}
	public void clear() {
		for(int i = 0; i < 80; i++) {
			for(int j = 0; j < 24; j++) {
				screen[i][j] = ' ';
			}
		}
	}
	void put(char c, int x, int y) {
		if(0 <= x && x < WIDTH && 0 <= y && y < HEIGHT) {
			screen[x][y] = c;
		}
	}
	void drawString(String s, int x, int y) {
		for(int i = 0; i < s.length(); i++) {
			put(s.charAt(i), x+i, y);
		}
	}
	
	
	
	public void paint() {
		for(int i = 0; i < 24; i++) {
			for(int j = 0; j < 80; j++) {
				System.out.print(screen[j][i]);
			}
			System.out.println();
		}
	}
	
	public int getWidth() {
		return WIDTH;
	}
	
	public int getHeight() {
		return HEIGHT;
	}

}
