package projectrain.tools;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener{
	public static Assets instance = new Assets();
	private AssetManager assetManager;
	public ScytheMan scytheMan;
	public Platform platform;
	
	private Assets(){
		
	}
	public void init(){
		
	}
	public void init(AssetManager assetManager){
		//Load Spritesheet to be cut from texture packer2
		this.assetManager = assetManager;
		assetManager.setErrorListener(this);
		assetManager.load("images/ProjectRain.pack", TextureAtlas.class);
		assetManager.finishLoading();
		
		TextureAtlas atlas = assetManager.get("images/ProjectRain.pack");
		platform = new Platform(atlas);
		scytheMan = new ScytheMan(atlas);
	}

	@Override
	public void error(AssetDescriptor asset, Throwable throwable) {
		
	}

	@Override
	public void dispose() {
		
	}
	public class Platform{
		public final AtlasRegion platform;
		public Platform(TextureAtlas atlas){
			platform = atlas.findRegion("platform");
		}
	}
	
	public class ScytheMan{
		public final Array<AtlasRegion> scytheNormal;
		public final Array<AtlasRegion> scytheRunning;
		public final Animation aniRunning;
		public final Animation aniNormal;
		
		public ScytheMan(TextureAtlas atlas){
			
			scytheRunning = atlas.findRegions("Scythe_running");
			scytheNormal = atlas.findRegions("Scythe_normal");
			scytheNormal.add(atlas.findRegion("Scythe_normal"));
			
			aniRunning = new Animation(1 / 10.0f, scytheRunning, Animation.LOOP_PINGPONG);
			aniNormal = new Animation(1 / 10.0f, scytheNormal, Animation.NORMAL);

		}
	}

}
