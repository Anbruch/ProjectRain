package projectrain.tools;

import projectrain.game_objects.classes.ManipulatableObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class World {
	public static World controller = new World();
	public CameraHelper cameraHelper;
	public LevelStage levelStage;
	private ManipulatableObject player;
	
	private World(){
		
	}
	public void init(){
		
		cameraHelper = new CameraHelper();//Essentially makes the camera follow player
		levelStage = new LevelStage();
		
		//have a player variable here
		player = levelStage.getPlayer();
		cameraHelper.setTarget(player);	
		
	}
	
	public void render(SpriteBatch batch){
		levelStage.render(batch);
	}
	public void update(float deltaTime){
		
		levelStage.update(deltaTime);
		cameraHelper.update(deltaTime);
	}
	
	
}
