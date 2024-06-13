import java.util.Random;

public class Enemy {
    private int x;
    private int y;
    private char icon;
    private char icon_u;
    private char icon_l;
    private char icon_r;
    private char icon_d;
    private int dx;
    private int dy;
    private int distance; // 何マス動くか
    
    
    public Enemy(int x, int y, char icon_u, char icon_l, char icon_r, char icon_d) {
        this.x = x;
        this.y = y;
        this.icon_u = icon_u;
        this.icon_l = icon_l;
        this.icon_r = icon_r;
        this.icon_d = icon_d;
        icon = icon_u;
        dx = 1;
        dy = 0;
        distance = 0;
        
    }

    public void move(Map map, Random random) {
        while(distance == 0 || map.getMap(x+dx, y+dy) == '#') { // 動き終わったor動けなかったら向きと距離をリセット
            int direction = random.nextInt(4);
            // 0:0,-1  1:-1,0  2:0,1  3:1,0 になるはず
            dx = direction % 2 * (direction-2);
            dy = (direction+1) % 2 * (direction-1);
            distance = random.nextInt(20);
        }
        x += dx;
        y += dy;
        distance--;
    }
    public void paint(View view) {
        view.put(icon, x, y);
    }
}
