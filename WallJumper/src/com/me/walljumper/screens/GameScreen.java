package com.me.walljumper.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.me.walljumper.DirectedGame;
import com.me.walljumper.ProfileLoader;
import com.me.walljumper.WallJumper;
import com.me.walljumper.game_objects.classes.ManipulatableObject;
import com.me.walljumper.screens.screentransitions.ScreenTransition;
import com.me.walljumper.screens.screentransitions.ScreenTransitionFade;
import com.me.walljumper.screens.screentransitions.ScreenTransitionSlice;
import com.me.walljumper.tools.AudioManager;
import com.me.walljumper.tools.InputManager;

public class GameScreen extends AbstractScreen {

	public GameScreen(DirectedGame game) {
		super(game);
	}

	@Override
	public void render(float delta) {
		World.controller.render(delta);

	}

	@Override
	public void resize(int width, int height) {
		World.controller.resize(width, height);
	}

	@Override
	public void show() {
		World.controller = new World(game, this);
		World.controller.show();
		WallJumper.currentScreen = this;

	}

	@Override
	public void hide() {
		World.controller.hide();

	}

	@Override
	public void joyStick(int controllerNumber, Vector2 leftJoyStick,
			Vector2 rightJoyStick) {
		World.controller
				.joyStick(controllerNumber, leftJoyStick, rightJoyStick);
	}

	@Override
	public void pause() {
		super.pause();
		World.controller.pause();
	}

	@Override
	public void resume() {
		World.controller.resume();
	}

	@Override
	public void dispose() {
		World.controller.dispose();
	}

	public boolean handleTouchInputDown(int screenX, int screenY, int pointer,
			int button) {

		return World.controller.handleTouchInput(screenX, screenY, pointer,
				button);

	}

	public boolean handleKeyInput(int keycode) {

		return World.controller.handleKeyInput(keycode);
	}

	@Override
	public void handleKeyUp(int keycode) {
		// Send key up input to controlled objects
		for (ManipulatableObject target : InputManager.inputManager.controllableObjects) {
			target.actOnInputKeyUp(keycode);
		}
	}

	// CHANGE LEVEL METHODS
	public void nextScreen() {
		ScreenTransition transition = ScreenTransitionSlice.init(.6f,
				ScreenTransitionSlice.UP_DOWN, 10, Interpolation.pow2Out);
		game.setScreen(new GameScreen(game), transition);
	}

	// Set spawnpoint to null, destroy and init world controller and go to next
	// level
	public void nextLevel() {

		WallJumper.level++;
		World.controller.setSpawnPoint(null, false);
		World.controller.destroy();
		World.controller.init();
	}

	public void changeScreen(AbstractScreen screen) {
		((Game) Gdx.app.getApplicationListener()).setScreen(screen);
	}

	@Override
	public InputProcessor getInputProcessor() {
		return InputManager.inputManager;
	}

	@Override
	public void controllerButtonDown(int controllerNumber, int buttonIndex) {
		World.controller.controllerButtonDown(controllerNumber, buttonIndex);
	}

	@Override
	public void controllerButtonUp(int controllerNumber, int buttonIndex) {
		World.controller.controllerButtonUp(controllerNumber, buttonIndex);
		
	}
}
