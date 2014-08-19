package com.me.walljumper.game_objects.abilities;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.walljumper.Constants;
import com.me.walljumper.game_objects.AbstractGameObject;
import com.me.walljumper.game_objects.LandDust;
import com.me.walljumper.game_objects.classes.ManipulatableObject;
import com.me.walljumper.game_objects.classes.ManipulatableObject.STATE;
import com.me.walljumper.screens.World;
import com.me.walljumper.tools.LevelStage;

public class Teleport extends Ability {

	
	private ManipulatableObject owner;
	private float deltax;
	private float deltay;
	private static float moveSpeed = 7;
	public Teleport(ManipulatableObject owner) {
		
		//Copy the owner as shadow
		position.set(owner.position);
		bounds = new Rectangle(owner.bounds);
		scale = owner.scale;
		setAnimation(owner.animation);
		owner.velocity.set(0, 0);
		velocity.set(owner.moveSpeed);
		terminalVelocity.set(owner.terminalVelocity); 
		owner.acceleration.set(0, 0);
		acceleration.set(0,0);
		this.owner = owner;
		
		dimension = new Vector2(owner.dimension);
	}

	@Override
	public void update(float deltaTime) {
		moveX(deltaTime);
		moveY(deltaTime);
		bounds.setPosition(position);
		super.update(deltaTime);
	}
	
	
	@Override
	public void actOnInputKeyUp(int keycode) {
		super.actOnInputKeyUp(keycode);
		if(keycode ==  Keys.SPACE){
			owner.finishTeleport();
			return;
		}
	}
	
	public void moveX(float deltaTime) {
		// Used to check if grounded or not
		if(left)
			velocity.x = -moveSpeed;
		if(right)
			velocity.x = moveSpeed;
		
		if((!left && !right) || (left && right))
			velocity.x = 0;
		
		// change in X axis this frame
		deltax = velocity.x * deltaTime;


		// Collision Check this object once for x only
		if (!collision(deltax, 0)) {
			position.x += deltax;
			

		} else if (deltay == 0) {

			
		} 

	}

	private boolean collision(float deltax, float deltay) {
		// Set bounds to where this object will be after adding
				// the velocity of this frame to check and see if we are
				// going to collide with anything
				bounds.setPosition(position.x + deltax, position.y + deltay);

				// Iterate through platforms
				for (AbstractGameObject platform : LevelStage.backPlatforms) {

					// If collision
					if (bounds.overlaps(platform.bounds)) {
						if (deltax != 0) {
							deltax = 0;
						}
						if (deltay != 0) {

						}
						return true;
					}
				}
				// Iterate through platforms
				for (AbstractGameObject platform : LevelStage.platforms) {

					// If collision
					if (bounds.overlaps(platform.bounds)) {
						if (deltax != 0) {
							deltax = 0;
						}
						if (deltay != 0) {

						}
						return true;
					}
				}

		
		return false;
	}

	public void moveY(float deltaTime) {
		// change in y this frame
		if(up)
			velocity.y = moveSpeed;
		if(down)
			velocity.y = -moveSpeed;
		if((!up && !down) || (up && down))
			velocity.y = 0;

		deltay = velocity.y * deltaTime;
	


		// If you didn't collide in y axis,
		// add deltaY to the position.y
		if (!collision(0, deltay)) {
			position.y += deltay;

			// if you did collide with something,
			// in the Y AXIS ONLY, set velocity to 0
		} else {

		
		


		}

	}

	public void teleAim(float deltaTime, boolean left, boolean right, boolean up, boolean down, float moveSpeed) {
		
		
		

		bounds.setPosition(position);

			
	}
	public void destroy() {
		owner.collidingPlatformY = null;
		owner.position.set(position);
		setEndTime(0);
	}
}
