package com.me.walljumper.game_objects.classes;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.me.walljumper.Constants;
import com.me.walljumper.DIRECTION;
import com.me.walljumper.game_objects.abilities.Fireball;
import com.me.walljumper.game_objects.abilities.Teleport;
import com.me.walljumper.game_objects.classes.ManipulatableObject.COMBAT;
import com.me.walljumper.game_objects.classes.ManipulatableObject.STATE;
import com.me.walljumper.game_objects.classes.ManipulatableObject.VIEW_DIRECTION;
import com.me.walljumper.screens.World;
import com.me.walljumper.tools.Assets;

public class Mage extends ManipulatableObject {






	private boolean teleportAiming;
	private Teleport teleport;
	private float y;

	

	public Mage(boolean controller){
		super(controller);
	}

	
	public Mage(float x, float y, float width, float  height, int scale, boolean controller){
		super(controller);
		init(x, y, width, height, scale);
		
	}
	private void init(float x, float y, float width, float  height, int scale){
		this.scale = scale;
		aniRunning = Assets.instance.rogue.aniRunning;
		aniNormal = Assets.instance.rogue.aniNormal;
		aniJumping = Assets.instance.rogue.aniJumping;
		aniWalling = Assets.instance.rogue.aniWalling;
		aniLanding = Assets.instance.particle.landAnimation;
		
		position.set(x, y);
		acceleration.set(0, Constants.gravity);
		moveSpeed = new Vector2(60f, 15f);
		JUMP_TIME = .3f;
		wallJumpSpeedX = 7;
		state = STATE.GROUNDED;
		setAnimation(aniNormal);
		terminalVelocity.set(15, 18);
		dimension.set(width * scale, height * scale);
		bounds.set(position.x, position.y, dimension.x, dimension.y - .2f);
	}
	
	//FireBall
	@Override
	public void abilityUp1() {

		DIRECTION dir = DIRECTION.getDirection(this, up, down, right, left);
		World.controller.activeAbilities.add(new Fireball(this, dir));
		combatState = COMBAT.NEUTRAL;
		checkCombatState();

	}
	@Override
	public void abilityDown1() {
		combatState = COMBAT.CASTING;
		checkCombatState();
	}
	
	
	@Override
	protected void ensureCorrectCollisionBounds() {
		
		bounds.y = position.y;
		bounds.x = position.x;
		
	
	}

	
	@Override
	public void render(SpriteBatch batch) {
		// get correct image and draw the current proportions
		image = null;
		image = animation.getKeyFrame(stateTime, looping);
		currentFrameDimension.set((image.getRegionWidth() / 100.0f) * scale,
				 (image.getRegionHeight()/100.0f) * scale);
		
		//Draw him flipped when he's running into walll
		if(state == STATE.GROUNDED && animation.getPlayMode() == Animation.NORMAL){
			// Draw
			batch.draw(image.getTexture(), position.x, position.y, 0, 0,
					currentFrameDimension.x, currentFrameDimension.y, 1, 1,
					rotation, image.getRegionX(), image.getRegionY(),
					image.getRegionWidth(), image.getRegionHeight(),
					viewDirection == VIEW_DIRECTION.right, false);
			return;
		}
		
		// Draw him .2 pixels to the right because he looks off the wall
		//when he's sliding down and he's on the left side of the wall

		if(state == STATE.WALLING && viewDirection == VIEW_DIRECTION.right)
			batch.draw(image.getTexture(), position.x + .2f, position.y, 0, 0,
					currentFrameDimension.x, currentFrameDimension.y, 1, 1,
					rotation, image.getRegionX(), image.getRegionY(),
					image.getRegionWidth(), image.getRegionHeight(),
					viewDirection == VIEW_DIRECTION.left, false);
		else{
			batch.draw(image.getTexture(), position.x, position.y, 0, 0,
					currentFrameDimension.x, currentFrameDimension.y, 1, 1,
					rotation, image.getRegionX(), image.getRegionY(),
					image.getRegionWidth(), image.getRegionHeight(),
					viewDirection == VIEW_DIRECTION.left, false);
		}

	}
}
