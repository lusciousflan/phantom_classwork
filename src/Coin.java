public class Coin {
    private int x;
    private int y;
    private boolean isGot;
    private char icon;

    public Coin(int x, int y, char icon) {
        this.x = x;
        this.y = y;
        this.icon = icon;
        isGot = true;
    }

    public void getCoin() { // TODO getCoinという名前はよくない気がしてきた
        isGot = false;
    }
    public boolean getIsGot() {
        return isGot;
    }

	public void paint(View view) {
		if(isGot) {
			view.put(icon, x, y);	
		}
	}
}
