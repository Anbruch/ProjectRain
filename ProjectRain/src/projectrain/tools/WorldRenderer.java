package projectrain.tools;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.me.projectrain.Constants;

public class WorldRenderer implements Disposable{
	private SpriteBatch batch;
	public static WorldRenderer renderer = new WorldRenderer();
	public OrthographicCamera camera;
	
	private WorldRenderer(){
	}
	public void init(){
		
		batch = new SpriteBatch();
		
		//Initialize main camera
		camera = new OrthographicCamera(Constants.viewportWidth, Constants.viewportHeight);
		camera.position.set(0, 0, 0);
		camera.setToOrtho(false);
		camera.update();
		
		
	}
	private void renderWorld(){
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		WorldController.controller.render(batch);
		batch.end();
		
	}
	public void render(){
		renderWorld();
		camera.update();
	}
	@Override
	public void dispose() {
		batch.dispose();
	}

}
