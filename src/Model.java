import java.io.IOException;

public class Model {
	private View view;
	private Controller controller;
	private Player player;
	private Map map;
	private Title title;
	private boolean isStarted;

	public Model() {
		view = new View(this);
		controller = new Controller(this);
		player = new Player(5, 5, '@');
		map = new Map();
		title = new Title(view.getWidth(), view.getHeight());
		isStarted = false;
	}
	public void gameStart() {
		player = new Player(5, 5, '@');
		map = new Map();
	}
	public synchronized void process(String event) throws InterruptedException {
		if (event.equals("TIME_ELAPSED")) {
			// 時間経過時の処理
			
			// 描画の更新
			// まず画面をきれいにする
			view.clear();

			if(isStarted) { // ゲーム中
				map.timerProcess(controller.getDelay());
				map.update();
				player.update(map);
				
				// 処理
				map.paint(view);
				if(map.getIsPlaying()) {
					player.paint(view);
				}
			} else { // タイトル画面
				title.paint(view);
			}
		
			// 描画
			view.paint();
			
		} else {
			// タイプ時の処理
			if(isStarted) {
				if(map.getIsPlaying()) {
					if(event.equals("w") || event.equals("UP")) {
						player.move(0, -1, map);
					} else if(event.equals("a") || event.equals("LEFT")) {
						player.move(-1, 0, map);
					} else if(event.equals("s") || event.equals("DOWN")) {
						player.move(0, 1, map);
					} else if(event.equals("d") || event.equals("RIGHT")) {
						player.move(1, 0, map);
					}
				} else {
					if(event.equals("") || event.equals(" ")) {
						isStarted = false;
					}
				}
			} else {
				if(event.equals("") || event.equals(" ")) {
					isStarted = true;
					gameStart();
				}
			}
			
		}
	}

	public void run() throws IOException, InterruptedException {
		controller.run();
	}
	public static void main(String[] args) throws IOException, InterruptedException {
		Model model = new Model();
		model.run();
	}
}