package projectrain.tools;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener{
	public static Assets instance = new Assets();
	private AssetManager assetManager;
	public Guy guy;
	
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
		
		guy = new Guy(atlas);
	}

	@Override
	public void error(AssetDescriptor asset, Throwable throwable) {
		
	}

	@Override
	public void dispose() {
		
	}
	
	public class Guy{
		public final AtlasRegion guy;
		public Guy(TextureAtlas atlas){
			guy = atlas.findRegion("fag");
		}
	}

}
