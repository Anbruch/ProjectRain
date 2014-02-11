package projectrain.game_objects.terrain;

import projectrain.game_objects.AbstractGameObject;
import projectrain.tools.Assets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Platform extends AbstractGameObject{
	private TextureRegion image;
	
	public Platform(){
	}
	
	public Platform(float x, float y, float width, float height){
		image = Assets.instance.platform.platMap.getValueAt((int)(Math.random() * 4));

		init(x, y, width, height);
	}
	
	public Platform(String platType, float x, float y, float width, float height){
		image = Assets.instance.platform.platMap.get(platType);

		init(x, y, width, height);
	}
	
	private void init(float x, float y, float width, float height){
		
		//set basic vectors of position, dimension and bounds for collision
		position.set(x, y);
		dimension.set(width, height);
		bounds.set(position.x, position.y, dimension.x, dimension.y - 3);
	}
	
	@Override
	public void render(SpriteBatch batch) {
		
		batch.draw(image.getTexture(), position.x, position.y, dimension.x,
				dimension.y, image.getRegionX(), image.getRegionY(),
				image.getRegionWidth(), image.getRegionHeight(), false, false);
	}

}
