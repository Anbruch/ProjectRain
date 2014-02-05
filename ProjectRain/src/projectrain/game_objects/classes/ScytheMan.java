package projectrain.game_objects.classes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import projectrain.game_objects.AbstractGameObject;
import projectrain.tools.Assets;

public class ScytheMan extends AbstractGameObject {
	private TextureRegion image;
	public ScytheMan(){
		
	}
	public ScytheMan(float x, float y, float width, float  height){
		init(x, y, width, height);
		
	}
	private void init(float x, float y, float width, float  height){
		image = Assets.instance.scytheMan.scytheMan;
		
		position.set(x, y);
		acceleration.set(0, -150f);
		terminalVelocity.set(400, 400);
		dimension.set(width, height);
		bounds.set(0, 0, width, height);
	}
	
	@Override
	public void update(float deltaTime){
		super.update(deltaTime);
		
	}
	
	@Override
	public void render(SpriteBatch batch) {
		batch.draw(image.getTexture(), position.x, position.y, 0, 0, bounds.width, bounds.height, 1, 1, rotation, image.getRegionX(), image.getRegionY(), image.getRegionWidth(), image.getRegionHeight(), false, false);
	}

}
