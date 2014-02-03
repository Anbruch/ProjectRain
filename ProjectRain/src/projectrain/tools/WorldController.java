package projectrain.tools;

import projectrain.game_objects.Guy;

public class WorldController {
	public static WorldController controller = new WorldController();
	private CameraHelper cameraHelper;
	public Guy guy;
	
	private WorldController(){
		
	}
	public void init(){
		guy = new Guy();
	}
	
	public void update(float deltaTime){
		LevelStage.levelStage.update(deltaTime);
		guy.update(deltaTime);
	}

}
