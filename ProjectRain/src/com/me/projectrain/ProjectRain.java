package com.me.projectrain;

import projectrain.tools.Assets;
import projectrain.tools.WorldController;
import projectrain.tools.WorldRenderer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL10;

public class ProjectRain implements ApplicationListener {
	boolean paused;//Game being paused handled in main
	
	//Variables for the world
	
	
	
	
	@Override
	public void create() {	
		
		Assets.instance.init(new AssetManager());//Make the Spritesheet to be cut from later
		WorldController.controller.init();
		WorldRenderer.renderer.init();
		paused = false;
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void render() {		
		if(!paused){
			WorldController.controller.update(Gdx.graphics.getDeltaTime());
		}
		
		
		Gdx.gl.glClearColor(1, 1, 1, 0); //Default background color
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		WorldRenderer.renderer.render();

		
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
