package projectrain.tools;

import projectrain.game_objects.classes.ScytheMan;
import projectrain.game_objects.terrain.Platform;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class LevelStage {
	
	private Platform platform;
	private ScytheMan scytheMan;

	public LevelStage(){
		scytheMan = new ScytheMan(400, 500, 84, 64);
		platform = new Platform(30, 400, 712, 48);
		
	}
	public void render(SpriteBatch batch){
		scytheMan.render(batch);
		platform.render(batch);
		
		
	}
	public void update(float deltaTime){
		scytheMan.update(deltaTime);
		Rectangle r1, r2;
		r1 = new Rectangle(scytheMan.position.x, scytheMan.position.y, scytheMan.bounds.width, scytheMan.bounds.height);
		r2 = new Rectangle(platform.position.x, platform.position.y, platform.bounds.width, platform.bounds.height);
		if(r1.overlaps(r2)){
			
			scytheMan.position.y = platform.position.y + platform.bounds.height;
			scytheMan.acceleration.set(0, 0);
			scytheMan.velocity.set(0, 0);
		}
		
		
	}

}
