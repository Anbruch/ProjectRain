package projectrain.levels;

import projectrain.game_objects.AbstractGameObject;
import projectrain.game_objects.classes.ScytheMan;
import projectrain.game_objects.terrain.Platform;
import projectrain.tools.InputManager;
import projectrain.tools.LevelStage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;

public class LevelLoader {
	
	private int color;
	
	
	public enum BLOCK_TYPE{
		EMPTY(0, 0, 0), PLATFORM(0, 255, 0), PLAYER_SPAWNPOINT(255, 255, 255);
		private int color;
		private BLOCK_TYPE(int r, int g, int b){
			
			color = r << 24 | g << 16 | b << 8 | 0xff;
			
		}
		public boolean sameColor(int color){
			return this.color == color;
		}
		public int getColor(){
			return color;
		}
	}
	

	public LevelLoader(String fileName){
		init(fileName);
	}

	private void init(String fileName) {
		
		//Load image file that represents level data
		Pixmap pixmap = new Pixmap(Gdx.files.internal(fileName));
		//scan pixels from top-left to bottom right
		int lastPixel = -1;
		for(int pixelY = 0; pixelY < pixmap.getHeight(); pixelY++){
			
			for(int pixelX = 0; pixelX < pixmap.getWidth(); pixelX++){
				
				AbstractGameObject obj = null;
				float offsetHeight = 0;
				//height grows bottom to top
				float baseHeight = pixmap.getHeight() - pixelY;
				float heightIncreaseFactor = .25f;
				
				//Get color of current pixel as 32-bit RGBA value
				int currentPixel = pixmap.getPixel(pixelX, pixelY);
				
				//IF BLACK SPACE, NULL SPACE
				if(BLOCK_TYPE.EMPTY.sameColor(currentPixel)){
					//Do nothing
					
				//IF PLATFORM
				}else if(BLOCK_TYPE.PLATFORM.sameColor(currentPixel)){
					LevelStage.platforms.add(new Platform(pixelX * 356, pixmap.getHeight() - pixelY * 100, 356, 24));
					System.out.println(LevelStage.platforms.size);

				//IF PLAYER SPAWNPOINT
				}else if(BLOCK_TYPE.PLAYER_SPAWNPOINT.sameColor(currentPixel)){
					
					//Spawn player
					ScytheMan scytheMan = new ScytheMan(pixelX * 356, pixmap.getHeight() - pixelY * 100, 63, 48);
					
					//Track him in these arrays
					LevelStage.controllableObjects.add(scytheMan);
					InputManager.inputManager.addObject(scytheMan);
			
				}
				
				
			}
		}
	}
	
}
