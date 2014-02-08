package projectrain.tools;

import projectrain.game_objects.AbstractGameObject;
import projectrain.game_objects.classes.ManipulatableObject;
import projectrain.game_objects.terrain.Platform;
import projectrain.levels.LevelLoader;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class LevelStage {
	
	private Platform platform;
	public static Array<ManipulatableObject> controllableObjects = new Array<ManipulatableObject>();
	public static Array<AbstractGameObject> platforms = new Array<AbstractGameObject>();
	private LevelLoader levelLoader;
	
	
	public LevelStage(){
		levelLoader = new LevelLoader("levels/testLevel.png");
		System.out.println(controllableObjects.get(0).position);
		for(AbstractGameObject plat: LevelStage.platforms){
			System.out.println(plat.position);
		}
		
		
		
		
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
	
}
