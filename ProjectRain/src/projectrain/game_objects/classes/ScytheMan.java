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
		acceleration.set(0, -900);
		moveSpeed = new Vector2(300, 500);
		setAnimation(aniNormal);
		terminalVelocity.set(400, 600);
		dimension.set(width, height);
		bounds.set(0, 0, width, height);
		
	}
	@Override
	protected void ensureCorrectCollisionBounds() {
		bounds.y = position.y;
		if(viewDirection == VIEW_DIRECTION.left){
			bounds.width = dimension.x - 20;
			bounds.x = position.x + 20;
			
			
		}else{
			bounds.x = position.x + 20;
			bounds.width = dimension.x - 20;
			
		}
		
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
		super.render(batch);
	}

}
