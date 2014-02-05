package projectrain.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class WorldController implements InputProcessor {
	public static WorldController controller = new WorldController();
	private CameraHelper cameraHelper;
	public LevelStage levelStage;
	
	private WorldController(){
		
	}
	public void init(){
		
		Gdx.input.setInputProcessor(this);
		levelStage = new LevelStage();
		
	}
	public void render(SpriteBatch batch){
		levelStage.render(batch);
	}
	public void update(float deltaTime){
		
		levelStage.update(deltaTime);
	}
	
	
	
	//InputProcessor functions
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
