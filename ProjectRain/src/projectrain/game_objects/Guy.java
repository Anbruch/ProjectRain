package projectrain.game_objects;

import projectrain.tools.Assets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Guy {
	
	TextureRegion image;
	
	public Guy(){
		image = Assets.instance.guy.guy;
		System.out.println(image);
		
	}
	public void render(SpriteBatch batch){
		batch.draw(image.getTexture(), 1, 5, 0, 0, 400, 400, 1, 1, 0, image.getRegionX(), image.getRegionY(), image.getRegionWidth(), image.getRegionHeight(), false, false);
	}
	public void update(float deltaTime){
		
	}
	
}