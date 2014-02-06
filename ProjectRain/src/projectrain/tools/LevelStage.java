package projectrain.tools;

import projectrain.game_objects.AbstractGameObject;
import projectrain.game_objects.classes.ManipulatableObject;
import projectrain.game_objects.classes.ScytheMan;
import projectrain.game_objects.terrain.Platform;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class LevelStage {
	
	private Platform platform;
	private ScytheMan scytheMan;
	public static Array<ManipulatableObject> controllableObjects = new Array<ManipulatableObject>();
	public static Array<AbstractGameObject> platforms = new Array<AbstractGameObject>();
	
	public LevelStage(){
		scytheMan = new ScytheMan(400, 500, 84, 64);
		
		controllableObjects.add(scytheMan);
		for(int i = 0; i < 10; i++){
			platforms.add(new Platform(-3000 + i * 712, 400, 712, 48));

		}
		
		//add to input's array to send input to player controlled objects
		InputManager.inputManager.addObject(scytheMan);

	}
	public void render(SpriteBatch batch){
		
		//render all of the terrain
		for(AbstractGameObject platform: LevelStage.platforms){
			platform.render(batch);
		}
		
		//Render all of the manipulatable objects
		for(ManipulatableObject object: LevelStage.controllableObjects){
			object.render(batch);
		}
		
		
		
	}
	public void update(float deltaTime){
		
		//Iterate and update all enemies, players, controllable objects
		for(ManipulatableObject object: LevelStage.controllableObjects){
			object.update(deltaTime);
		}
		
		
	}
	public ManipulatableObject getPlayer() {

		return scytheMan;
	}

}
