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
						
						return true;
					}
				}
				// Iterate through platforms
				for (AbstractGameObject platform : LevelStage.platforms) {

					// If collision
					if (bounds.overlaps(platform.bounds)) {
						
						return true;
					}
				}
		return false;
	}
	public void destroy() {
		owner.position.set(position);
		setEndTime(0);
	}
}
