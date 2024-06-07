import java.util.HashMap;
import java.util.Random;

public class Map {
	private final static int WIDTH = 70;
	private final static int HEIGHT = 24;
	private char[][] map;
	private HashMap<Integer, Coin> coins; // TODO 名前ややこしいの何とかしたい
	private int gx; // ゴールの座標
	private int gy;
	private boolean isPlaying;
	
	
	public Map() {
		map = new char[WIDTH][HEIGHT];
		coins = new HashMap<Integer, Coin>();
		gx = 0;
		gy = 0;
		isPlaying = true; // TODO タイトル画面を作ったら変更する
		// clear();
		makeMaze();
	}

	public void clear() {
		for(int i = 0; i < WIDTH; i++) {
			for(int j = 0; j < HEIGHT; j++) {
				map[i][j] = '_';
			}
		}
	}

	public void makeMaze() {
		// 迷路の壁生成
		for(int i = 0; i < WIDTH; i++) {
			for(int j = 0; j < HEIGHT; j++) {
				if(i == 0 || i == WIDTH-1 || j == 0 || j == HEIGHT-1) {
					map[i][j] = '#';
				} else {
					map[i][j] = ' ';
				}
			}
		}
		// コイン生成
		Random random = new Random(System.currentTimeMillis());
		for(int i = 0; i < 5; i++) {
			int x = random.nextInt(WIDTH-2);
			int y = random.nextInt(HEIGHT-2);
			coins.put((x+1)*WIDTH+y+1, new Coin(x+1, y+1, 'O'));
		}
		// ゴール生成
		// TODO 実装が汚いのでなんとかする
		int d = random.nextInt(WIDTH*2+HEIGHT*2);
		if(d < WIDTH) {
			gx = d;
			gy = 0;
		} else if(d < WIDTH*2) {
			gx = d-WIDTH;
			gy = HEIGHT-1;
		} else if(d < WIDTH*2 + HEIGHT) {
			gx = 0;
			gy = d-WIDTH*2;
		} else {
			gx = WIDTH-1;
			gy = d-WIDTH*2-HEIGHT;
		}
	}
	public void gameClear() {
		isPlaying = false;
		// TODO 今これ実装中 クリアの表示を作る 枠とか
	}

	public void paint(View view) {
		// 壁の配置
		for(int i = 0; i < WIDTH; i++) {
			for(int j = 0; j < HEIGHT; j++) {
				view.put(map[i][j], i, j);
			}
		}
		// コイン配置
		for(HashMap.Entry<Integer, Coin> i : coins.entrySet()) {
			if(i.getValue().getIsGot()) {
				i.getValue().paint(view);
			}
		}
		// ゴール配置
		if(remainingCoinsNum() == 0) {
			// TODO 通れるゴールを周囲3*3マスに拡大する
			map[gx][gy] = ' ';
		} else {
			view.put('G', gx, gy);	
		}
		
		// UI配置
		String s1 = "Coins: ";
		String s2 = remainingCoinsNum() + " / " + coins.size();
		view.drawString(s1, WIDTH+2, 4);
		view.drawString(s2, WIDTH+4, 5);
	}

	public int getWidth() {
		return WIDTH;
	}
	public int getHeight() {
		return HEIGHT;
	}
	public char getMap(int x, int y) {
		if(x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
			return map[x][y];
		} else return 'e'; 
	}
	public int remainingCoinsNum() {
		int sum = 0;
		for(HashMap.Entry<Integer, Coin> i : coins.entrySet()) {
			if(i.getValue().getIsGot()) {
				sum++;
			}
		}
		return sum;
	}
	public boolean isGoal(int x, int y) {
		return gx == x && gy == y;
	}
	public HashMap<Integer, Coin> getCoins() {
		return coins;
	}
}