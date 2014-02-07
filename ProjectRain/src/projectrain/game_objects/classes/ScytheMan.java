package projectrain.game_objects.classes;

import projectrain.tools.Assets;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class ScytheMan extends ManipulatableObject {
	public ScytheMan(){
		
	}
	public ScytheMan(float x, float y, float width, float  height){
		init(x, y, width, height);
		
	}
	private void init(float x, float y, float width, float  height){
		aniRunning = Assets.instance.scytheMan.aniRunning;
		aniNormal = Assets.instance.scytheMan.aniNormal;
		
		position.set(x, y);
		acceleration.set(0, -1000);
		moveSpeed = new Vector2(300, 500);
		setAnimation(aniNormal);
		terminalVelocity.set(400, 600);
		dimension.set(width, height);
		bounds.set(0, 0, width, height);
		
	}
	
	@Override
	public void actOnInputKeyDown(int keycode){
		
		super.actOnInputKeyDown(keycode);
		
	}
	
	@Override
	public void update(float deltaTime){
		
		super.update(deltaTime);
		
	}
	
	@Override
	public void render(SpriteBatch batch) {
		image = null;
		image = animation.getKeyFrame(stateTime, true);
		super.render(batch);
	}

}
