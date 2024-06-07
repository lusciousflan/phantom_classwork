import java.util.HashMap;

public class Player {
	private int x;
	private int y;
	private char icon;
	
	public Player(int x, int y, char icon) {
		this.x = x;
		this.y = y;
		this.icon = icon;
	}

	public void move(int dx, int dy, Map map) {
		if(x+dx >= 0 && x+dx < map.getWidth() && y+dy >= 0 && y+dy < map.getHeight()) {
			if(map.getMap(x+dx, y+dy) != '#') {
				x += dx;
				y += dy;
			}
			// コイン獲得処理
			for(HashMap.Entry<Integer, Coin> i : map.getCoins().entrySet()) {
				if(i.getKey() == x*map.getWidth()+y) {
					i.getValue().getCoin();
				}
			}
			// ゴール処理
			if(map.isGoal(x, y)) {
				map.gameClear();
			}
		}
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	public void paint(View view) {
		view.put(icon, x, y);
	}
}
