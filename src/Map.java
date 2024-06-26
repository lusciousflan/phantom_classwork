import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Map { // TODO 名前ややこしいの何とかしたい
	private final static int WIDTH = 70;
	private final static int HEIGHT = 24;
	private final static int DELAY = 1000; // msec
	private char[][] map;
	private HashMap<Integer, Coin> coins;
	private HashMap<Integer, Enemy> enemies; 
	private int gx; // ゴールの座標
	private int gy;
	private boolean isPlaying; // プレイ中かどうか
	private boolean isTimerCounting; // タイマーが動いているかどうか
	private int time; // 残り時間
	private int timeCounter; // 1秒たったかのカウントをする変数
	private Result result;
	
	
	public Map() {
		map = new char[WIDTH][HEIGHT];
		coins = new HashMap<Integer, Coin>();
		enemies = new HashMap<Integer, Enemy>();
		gx = 0;
		gy = 0;
		isPlaying = true;
		isTimerCounting = false;
		time = 256; // TODO テストが終わったら適切な値に戻す
		timeCounter = 0;
		result = new Result();
		makeMaze();
	}

	public void update() { // Mapで毎フレーム実行するもの
		if(isPlaying && timeCounter % 10 == 0) {
			moveEnemy();
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
		// ランダムに迷路の仕切りを作る
		// 未証明だが侵入できない区画はできないはず
		Random random = new Random(System.currentTimeMillis());
		for(int i = 0; i < 10; i++) {
			int x, y;
			boolean f = false;
			// 開始座標
			do {
				f = false;
				x = random.nextInt(WIDTH-4)+2;
				y = random.nextInt(HEIGHT-4)+2;
				for(int j1 = -1; j1 <= 1; j1++) {
					for(int j2 = -1; j2 <= 1; j2++) {
						if(map[x+j1][y+j2] == '#') {
							f = true;
						}
					}
				}
			} while(f);
			int len = random.nextInt(10) + 10;
			int dir = random.nextInt(4);
			int dx = dir % 2 * (dir-2);
            int dy = (dir+1) % 2 * (dir-1);
			for(int j = 0; j < len; j++) {
				map[x+dx*j][y+dy*j] = '#';
				if(isInMap(x+dx*(j+2), y+dy*(j+2)) && map[x+dx*(j+2)][y+dy*(j+2)] == '#') {
					break;
				}
			}
		}

		// コイン生成
		for(int i = 0; i < 5; i++) {
			int x, y;
			do {
				x = random.nextInt(WIDTH-2);
				y = random.nextInt(HEIGHT-2);	
			} while(map[x+1][y+1] == '#');
			coins.put((x+1)*WIDTH+y+1, new Coin(x+1, y+1, 'O'));
		}
		// 敵生成
		for(int i = 0; i < 5; i++) {
			int x, y;
			do {
				x = random.nextInt(WIDTH-2);
				y = random.nextInt(HEIGHT-2);	
			} while(map[x+1][y+1] == '#');
			enemies.put(i, new Enemy(x+1, y+1, '^', '<', '>', 'v'));
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
	public void gameFinish() {
		isPlaying = false;
		isTimerCounting = false;
		for(int i = 10; i < WIDTH-10; i++) {
			for(int j = 5; j < HEIGHT-5; j++) {
				if((i == 10 || i == WIDTH-11) && (j == 5 || j == HEIGHT-6)) {
					map[i][j] = '+';
				} else if(i == 10 || i == WIDTH-11) {
					map[i][j] = '|';
				} else if(j == 5 || j == HEIGHT-6) {
					map[i][j] = '-';
				} else {
					map[i][j] = ' ';
				}
			}
		}
		String s = "Press Enter or Space.";
		for(int i = 0; i < s.length(); i++) {
			map[WIDTH/2-s.length()/2+i][HEIGHT-8] = s.charAt(i);
		}
	}
	public void gameClear() {
		gameFinish();
		String s = "CLEAR";
		for(int i = 0; i < s.length(); i++) {
			map[WIDTH/2-s.length()/2+i][7] = s.charAt(i);
		}
		s = "Time : " + String.format("%02d", time/60) + ":" + String.format("%02d", time%60);
		for(int i = 0; i < s.length(); i++) {
			map[WIDTH/2-s.length()/2+i][9] = s.charAt(i);
		}
		result.writeScore(time);
		ArrayList<Integer> score = result.getScore();
		for(int j = 0; j < Math.min(3, score.size()); j++) {
			s = (j+1) + " "  + String.format("%02d", score.get(j)/60) + ":" + String.format("%02d", score.get(j)%60);
			for(int i = 0; i < s.length(); i++) {
				map[WIDTH/2-s.length()/2+i][12+j] = s.charAt(i);
			}	
		}
	}
	public void gameOver() {
		gameFinish();
		String s = "Game Over";
		for(int i = 0; i < s.length(); i++) {
			map[WIDTH/2-s.length()/2+i][8] = s.charAt(i);
		}
	}

	public void paint(View view) {
		// 壁の配置
		for(int i = 0; i < WIDTH; i++) {
			for(int j = 0; j < HEIGHT; j++) {
				view.put(map[i][j], i, j);
			}
		}
		// コイン配置
		if(isPlaying) {
			for(HashMap.Entry<Integer, Coin> i : coins.entrySet()) {
				if(i.getValue().getIsGot()) {
					i.getValue().paint(view);
				}
			}
		}
		// 敵配置
		if(isPlaying) {
			for(HashMap.Entry<Integer, Enemy> i : enemies.entrySet()) {
				i.getValue().paint(view);
			}
		}
		// ゴール配置
		if(remainingCoinsNum() == 0) {
			map[gx][gy] = ' ';
		} else {
			view.put('G', gx, gy);	
		}
		
		// UI配置
		// Coin
		String s1 = "Coins: ";
		String s2 = remainingCoinsNum() + " / " + coins.size();
		view.drawString(s1, WIDTH+2, 4);
		view.drawString(s2, WIDTH+4, 5);
		// Timer
		s1 = "Timer: ";
		s2 = String.format("%02d", time/60) + ":" + String.format("%02d", time%60);
		view.drawString(s1, WIDTH+2, 7);
		view.drawString(s2, WIDTH+4, 8);
	}

	public boolean isInMap(int x, int y) {
		return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT;
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
		} else return 'e'; // エラーの意
	}
	public boolean getIsPlaying() {
		return isPlaying;
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
	public void coinCheck(int x, int y) {
		for(HashMap.Entry<Integer, Coin> i : coins.entrySet()) {
			if(i.getValue().getIsGot() && x*WIDTH+y == i.getKey()) {
				i.getValue().getCoin();
				if(coins.size()-remainingCoinsNum() == 1) {
					isTimerCounting = true;
				}
			}
		}
	}
	public void enemyCheck(int x, int y) {
		for(HashMap.Entry<Integer, Enemy> i : enemies.entrySet()) {
			if(i.getValue().isCatch(this, x, y)) {
				gameOver();
			}
		}
	}
	public void moveEnemy() {
		Random random = new Random(System.currentTimeMillis());
		for(HashMap.Entry<Integer, Enemy> i : enemies.entrySet()) {
			i.getValue().move(this, random);
		}
	}

	public void timerProcess(int delay) {
		timeCounter++;
		if(timeCounter >= DELAY/delay) { // 1秒たった
			timeCounter = 0;
			if(isTimerCounting) {
				time--;
			}
			if(time < 0) {
				gameOver();
			}
		}
	}
}
