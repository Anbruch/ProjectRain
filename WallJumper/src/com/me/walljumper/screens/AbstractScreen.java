package com.me.walljumper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.me.walljumper.DirectedGame;
import com.me.walljumper.ProfileLoader;
import com.me.walljumper.WallJumper;
import com.me.walljumper.screens.screentransitions.ScreenTransition;
import com.me.walljumper.screens.screentransitions.ScreenTransitionSlice;
import com.me.walljumper.tools.AudioManager;

public abstract class AbstractScreen implements Screen{
	protected DirectedGame game;
	
	public AbstractScreen(DirectedGame game){
		this.game = game;
	}
	@Override
	public void render(float delta) {
		
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		WallJumper.paused = true;
	}

	@Override
	public void resume() {
		WallJumper.paused = false;
	}

	@Override
	public void dispose() {
		
	}
	
	public boolean handleTouchInputDown(int screenX, int screenY, int pointer, int button){
		return false;
	}

	public boolean handleKeyInput(int keycode) {
		return false;
	}
	public void changeScreen(AbstractScreen screen) {
		((DirectedGame) Gdx.app.getApplicationListener()).setScreen(screen);

	}

	public abstract InputProcessor getInputProcessor();
	public void backToLevelMenu(){
		
		ProfileLoader.profileLoader.saveProfile();
		AudioManager.instance.stopMusic();
		ScreenTransition transition = ScreenTransitionSlice.init(.6f, ScreenTransitionSlice.UP_DOWN, 10,
				Interpolation.pow2Out);
		game.setScreen(new LevelMenu(game), transition);
	}
	public void nextLevel() {
		
	}
	public void restartLevel(){

		World.controller.destroy();
		World.controller.init();
	}
	public void backToMainMenu() {
		AudioManager.instance.stopMusic();
		ScreenTransition transition = ScreenTransitionSlice.init(.6f, ScreenTransitionSlice.UP_DOWN, 10,
				Interpolation.pow2Out);
		game.setScreen(new MainMenu(game), transition);
	}
	public void backToHomeMenu() {
		
	}
	public void handleTouchInputUp(int screenX, int screenY, int pointer,
			int button) {
		
	}
	public void handleKeyUp(int keycode) {
		// TODO Auto-generated method stub
		
	}
	public abstract void controllerButtonDown(int controllerNumber, int buttonIndex);
	
	
	public void joyStick(int controllerNumber, Vector2 leftJoyStick, Vector2 rightJoyStick) {
		
	}
	public void controllerButtonUp(int controllerNumber, int buttonIndex) {
		
	}

}
