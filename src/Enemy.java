public class Enemy {
    private int x;
    private int y;
    private char icon_u;
    private char icon_l;
    private char icon_r;
    private char icon_d;
    
    
    public Enemy(int x, int y, char icon_u, char icon_l, char icon_r, char icon_d) {
        this.x = x;
        this.y = y;
        this.icon_u = icon_u;
        this.icon_l = icon_l;
        this.icon_r = icon_r;
        this.icon_d = icon_d;
        
    }
    public void paint(View view) {
        view.put(icon, x, y);
    }
}
