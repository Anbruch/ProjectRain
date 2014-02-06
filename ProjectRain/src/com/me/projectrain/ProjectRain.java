package com.me.projectrain;

import projectrain.tools.Assets;
import projectrain.tools.InputManager;
import projectrain.tools.World;
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
		InputManager.inputManager.init();
		World.controller.init();
		WorldRenderer.renderer.init();
		paused = false;
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void render() {		
		if(!paused){
			World.controller.update(Gdx.graphics.getDeltaTime());
		}
		/*
		Midnight Blue 2F2F4F
		Violet 4F2F4F*/
		Gdx.gl.glClearColor(25, 25, 112, 0); //Default background color
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
