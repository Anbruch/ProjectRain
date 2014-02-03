package com.me.projectrain;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2.Settings;

public class Main {
	
	private static boolean rebuildAtlas = true;
	public static void main(String[] args) {
		if(rebuildAtlas){
			Settings settings = new Settings();
			TexturePacker2.process(settings, "assets-raw/images", "../ProjectRain-desktop/assets/images", "ProjectRain.pack");
			
		}
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "ProjectRain";
		cfg.useGL20 = false;
		cfg.width = 1280;
		cfg.height = 720;
		
		new LwjglApplication(new ProjectRain(), cfg);
	}
}


