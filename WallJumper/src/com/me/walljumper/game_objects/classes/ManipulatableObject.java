package com.me.walljumper.game_objects.classes;

import Controllers.Xbox360;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.me.walljumper.Constants;
import com.me.walljumper.game_objects.AbstractGameObject;
import com.me.walljumper.game_objects.LandDust;
import com.me.walljumper.game_objects.abilities.Ability;
import com.me.walljumper.screens.World;
import com.me.walljumper.tools.LevelStage;

public class ManipulatableObject extends AbstractGameObject {

	protected float JUMP_TIME;
	public Vector2 moveSpeed;
	public Vector2 deltaPosition, bhOriginPos;
	protected float wallJumpSpeedX;
	public VIEW_DIRECTION viewDirection;
	protected STATE state;
	protected AbstractGameObject target, collidingPlatformX;
	public AbstractGameObject collidingPlatformY;
	protected boolean xCollision;
	
	private Vector2 leftJoyStick, rightJoyStick;

	private float deltax, deltay, rotSpeed;
	public float moveToSomethingOverTime;
	protected Animation zAttack;
	protected Animation aniLanding;
	public Animation aniJumping, aniWalling;
	private MeleeEnemyAI AI;
	public COMBAT combatState;
	private boolean wallJumped;
	private float startFallTime;
	public float time;
	public float bhRadius;
	
	private boolean controller;
	private boolean rising;
	private float jumpTime;

	public enum VIEW_DIRECTION {
		left, right;
	}

	public enum STATE {
		JUMPING, GROUNDED, WALLING;
	}

	public enum COMBAT {
		CASTING, DEFENDING, NEUTRAL;
	}

	public ManipulatableObject(boolean controller) {
		// Set the Default States
		super();
		this.controller = controller;
		leftJoyStick = new Vector2();
		rightJoyStick = new Vector2();
		viewDirection = VIEW_DIRECTION.right;
		state = STATE.JUMPING;
		jumpTime = 0;
		combatState = COMBAT.NEUTRAL;
		startFallTime = 0;
		bhOriginPos = new Vector2();

		wallJumped = false;

		currentFrameDimension = new Vector2();
	}

	public void actOnInputKeyDown(int keycode) {
		// Movement, same among all characters
		if (World.controller.blackHoled) {
			return;
		}

		// Movement, same among all characters
		switch (keycode) {

		// LEFT
		case Keys.LEFT:

			moveLeft();
			break;
		// RIGHT
		case Keys.RIGHT:

			moveRight();
			break;
		// DOWN
		case Keys.DOWN:

			moveDown();
			break;
		// UP
		case Keys.UP:

			moveUp();
			break;
		// ABILITIES
		// A BUTTON
		case Keys.A:
			abilityDown1();
			break;
		case Keys.S:
			abilityDown2();
			break;
		case Keys.D:
			abilityDown3();
			break;
		case Keys.F:
			abilityDown4();
			break;

		case Keys.SPACE:

			jump();
			break;
		}

	}// END OF METHOD

	public void actOnInputKeyUp(int keycode) {
		// Movement, same among all characters
		switch (keycode) {
		// LEFT
		case Keys.LEFT:

			stopMoveLeft();
			break;
		// RIGHT
		case Keys.RIGHT:

			stopMoveRight();
			break;
		// DOWN
		case Keys.DOWN:

			stopMoveDown();
			break;
		// UP
		case Keys.UP:

			stopMoveUp();
			break;

		case Keys.A:
			abilityUp1();

		}

	}// End of actOnInput methods

	public void jump() {

		// WALL JUMPING, CAN BE REMOVED
		if (state == STATE.WALLING && !wallJumped && collidingPlatformX != null) {
			velocity.y = moveSpeed.y;
			rising = true;
			jumpTime = 0;

			if (viewDirection == VIEW_DIRECTION.left) {
				velocity.x = wallJumpSpeedX;
				moveRight();
				
			} else {
				velocity.x = -wallJumpSpeedX;
				moveLeft();
				
			}
			//switchViewDirection();
			wallJumped = true;
		}

		// Jumping off ground
		if (state == STATE.GROUNDED) {
			if(!right && !left)
				acceleration.x = 0;
			velocity.y = moveSpeed.y;
			state = STATE.JUMPING;
			wallJumped = false;
			setAnimation(aniJumping);
			rising = true;
			jumpTime = 0;

		}
	}

	// Switches the state of the view direction variable and
	// fixes bug where you teleport to other side of the platform because
	// of the method positionOnSidePlatform()
	private void switchViewDirection() {
		viewDirection = viewDirection == VIEW_DIRECTION.left ? VIEW_DIRECTION.right
				: VIEW_DIRECTION.left;
		/*right = false;
		left = false;*/

	}

	// Called when you hit a platform in the x-axis
	public void positionOnSidePlatform() {
		System.out.println(velocity);
		if (velocity.x < 0) {
			position.x = collidingPlatformX.position.x
					+ collidingPlatformX.bounds.width;
			moveLeft();
		} else if (velocity.x > 0) {
			position.x = collidingPlatformX.position.x - bounds.width;
			moveRight();
		}
	}

	public void abilityDown1() {
		// TODO Auto-generated method stub

	}

	public void abilityDown2() {
		// TODO Auto-generated method stub

	}

	public void abilityDown3() {
		// TODO Auto-generated method stub

	}

	public void abilityDown4() {
		// TODO Auto-generated method stub

	}

	public void abilityUp1() {

	}

	public void moveRight() {
		

		if (left) {
			stopMoveLeft();
		}
		right = true;

		viewDirection = VIEW_DIRECTION.right;
		
		
		if (combatState == COMBAT.CASTING)
			return;
		// Animates the run if grounded
		if (state == STATE.GROUNDED) {
			setAnimation(aniRunning);
		}

		acceleration.x = moveSpeed.x;

	}

	public void moveLeft() {

		// Set left to true so if we were holding right previous to this,
		// when we let go of right, it will move us left
		if (right) {
			stopMoveRight();

		}
		left = true;

		viewDirection = VIEW_DIRECTION.left;

		
		
		
		if (combatState == COMBAT.CASTING) {
			return;
		}

		// Sets up running animation if on ground
		if (state == STATE.GROUNDED) {
			setAnimation(aniRunning);
		}

		acceleration.x = -moveSpeed.x;

	}

	public void addAI(MeleeEnemyAI abstractEnemyAI) {
		this.AI = abstractEnemyAI;
	}

	public void stopMoveRight() {

		// Set velocity to 0, check if left might be pressed
		right = false;
		if (state != STATE.WALLING) {

			// Animates back to neutral
			if (state != STATE.JUMPING) {
				setAnimation(aniNormal);

			}
		}
		if (left) {
			moveLeft();
		}
		acceleration.x = velocity.x > 0 ? -moveSpeed.x : moveSpeed.x;

	}

	public void stopMoveLeft() {
		left = false;

		if (state != STATE.WALLING) {
			// Set velocity.x to 0, check if right might be pressed
			// Sets animation back to neutral
			if (state != STATE.JUMPING) {
				setAnimation(aniNormal);

			}
		}
		// Bug fix for if both buttons are down,
		// Left is released, then character should move right
		if (right) {
			moveRight();
		}
		acceleration.x = velocity.x > 0 ? -moveSpeed.x : moveSpeed.x;

	}

	public void stopMove() {

		acceleration.x = velocity.x > 0 ? -moveSpeed.x /3 : moveSpeed.x /3;

		// Set velocity.x to 0, check if right might be pressed
		if(state != STATE.JUMPING){
			acceleration.x = velocity.x > 0 ? -moveSpeed.x * 2 : moveSpeed.x * 2;

			left = false;
			right = false;
			// Sets animation back to neutral
			setAnimation(aniNormal);
		}

	}

	private void moveUp() {
		up = true;

	}

	private void moveDown() {
		down = true;
	}

	private void stopMoveUp() {
		up = false;
	}

	private void stopMoveDown() {
		down = false;
	}

	public void setTarget(AbstractGameObject target) {
		this.target = target;
	}

	public void moveX(float deltaTime) {
		
		handleControllerJoyStick();
		// Used to check if grounded or not
		Vector2 curPosition = new Vector2(position);
		
		
		if(!left && !right && state != STATE.JUMPING){
			
			if(velocity.x < .5f && velocity.x > -.5f){
				acceleration.x = 0;
				velocity.x = 0;
				setAnimation(aniNormal);
			}
			
		}else{

		}

		// change in X axis this frame
		deltax = velocity.x * deltaTime;

		// overriden in subclass of ManipulatableObject
		ensureCorrectCollisionBounds();

		if (AI != null) {
			wallJumped = false;
		}
		// If you wall jump and come off the ledge, you can walljump again
		if (wallJumped && deltax != 0 && collidingPlatformX == null) {
			wallJumped = false;
		}

		// Collision Check this object once for x only
		if (!collision(deltax, 0)) {
			// System.out.println(deltax);
			position.x += deltax;
			if (deltax != 0)
				collidingPlatformX = null;

			// Jumping
			if (deltay != 0) {
				if (state != STATE.JUMPING)
					setAnimation(aniJumping);
				state = STATE.JUMPING;
			}
			xCollision = false;

			// Hit a wall while walking
			// Everything below hit a wall in the x-axis
		} else if (deltay == 0) {
			state = STATE.GROUNDED;
			setAnimation(aniNormal);
			xCollision = true;

			// If you're on the wall sliding down
		} else if (state != STATE.WALLING) {

			// Walling
			state = STATE.WALLING;
			positionOnSidePlatform();
			setAnimation(aniWalling);
			xCollision = true;

		} else {
			xCollision = true;
		}

		// so you run when you land from jump
		if (animation != aniRunning && state == STATE.GROUNDED
				&& position.x != curPosition.x)
			setAnimation(aniRunning);

		// or if you just land, you don't want to be in jumping animation
		else if (state == STATE.GROUNDED && position.x == curPosition.x
				&& animation == aniRunning) {
			
			setAnimation(aniNormal);
		}
	}

	private void handleControllerJoyStick() {
		if(!controller)
			return;
		if(leftJoyStick.y > .35f){
			moveDown();
		}else if(leftJoyStick.y < -.35f){
			moveUp();
		}else{
			up = false;
			down = false;
		}
		
		if(leftJoyStick.x > .35f){
			
			if(right || state == STATE.WALLING)
				return;
			moveRight();
		}else if(leftJoyStick.x < -.35f){
			if(left || state == STATE.WALLING)
				return;
			moveLeft();
		}else{
			if(state == STATE.GROUNDED)
			stopMove();
		}
	}

	public void moveY(float deltaTime) {
		if(state != STATE.JUMPING)
			rising = false;
			
		// change in y this frame
		deltay = velocity.y * deltaTime;
		jumpTime += deltaTime;
		// overriden by whatever subclass of manipulatable object
		ensureCorrectCollisionBounds();

		// If you didn't collide in y axis,
		// add deltaY to the position.y
		if (!collision(0, deltay)) {
			position.y += deltay;
			if(rising && jumpTime < JUMP_TIME){
				velocity.y = moveSpeed.y;
				System.out.println(jumpTime);
			}
			// if you did collide with something,
			// in the Y AXIS ONLY, set velocity to 0
		} else {
			velocity.y = 0;
			if (position.y > collidingPlatformY.position.y) {
				deltay = 0;
			}
		}

		// If you're in the air, set state to jumping
		if (deltay != 0) {
			collidingPlatformY = null;

			// else you've either hit the top of the top of the platform
			// or one of the other 3 sides.
		} else {
			// If you hit the top of the platform,
			// set state to grounded and velocity to 0,
			// and position to the top of the platform
			if (collidingPlatformY != null
					&& position.y > collidingPlatformY.position.y
							+ collidingPlatformY.bounds.height) {
				if ((left || right) && xCollision == false)
					setAnimation(aniRunning);
				else {
					setAnimation(aniNormal);
					
				}
				state = STATE.GROUNDED;
				LevelStage.uncollidableObjects.add(new LandDust(position.x,
						collidingPlatformY.position.y
								+ collidingPlatformY.bounds.height + .1f, 0, 0,
						false, false) {
					@Override
					public void animationComplete() {
						LevelStage.uncollidableObjects.removeValue(this, true);
					}
				});
				velocity.y = 0;
				position.y = collidingPlatformY.position.y
						+ collidingPlatformY.bounds.height;
			}

		}

	}

	@Override
	public void update(float deltaTime) {

		/*
		 * if (World.controller.blackHoled) { return; }
		 */

		super.update(deltaTime);
		
		moveX(deltaTime);
		moveY(deltaTime);

		// Just to clarify where the rectangle ended
		bounds.setPosition(position);

	}

	// Return false jump to World.contrlr.
	public boolean continueToHole(float deltaTime) {
		if (moveToSomethingOverTime > 0) {

			rotation += deltaTime * rotSpeed;
			position.x = bhOriginPos.x + deltaPosition.x
					* ((time - moveToSomethingOverTime) / time);
			position.y = bhOriginPos.y + deltaPosition.y
					* ((time - moveToSomethingOverTime) / time);
			moveToSomethingOverTime -= deltaTime;
			scale /= 1.03f;
			super.update(deltaTime);
			return true;
		}

		return false;

	}

	// This method takes the MO (manipulatable object) and
	// Sets it's animation back to what it should be after an attack
	// animation is finished
	protected void checkCombatState() {
	/*	ensureMoving();
		if (combatState == COMBAT.CASTING || combatState == COMBAT.DEFENDING) {
*/
			// if MO attack animation is finished

		//}

	}

	private void ensureMoving() {
		// Make him move left or right
		// seemlessly from an attack\
		if (left) {
			moveLeft();
		} else if (right) {
			moveRight();
		}

		// Go from attack animation to jumping
		if (state == STATE.JUMPING) {
			setAnimation(aniJumping);
		}
	}

	// To be overridden in subclasses
	protected void ensureCorrectCollisionBounds() {

	}

	private boolean collision(float deltaX, float deltaY) {

		// Set bounds to where this object will be after adding
		// the velocity of this frame to check and see if we are
		// going to collide with anything
		bounds.setPosition(position.x + deltaX, position.y + deltaY);

		// Iterate through platforms
		for (AbstractGameObject platform : LevelStage.backPlatforms) {

			// If collision
			if (bounds.overlaps(platform.bounds)) {
				if (deltaX != 0) {
					collidingPlatformX = platform;

					deltax = 0;
				}
				if (deltaY != 0) {
					collidingPlatformY = platform;
				}
				return true;
			}
		}
		// Iterate through platforms
		for (AbstractGameObject platform : LevelStage.platforms) {

			// If collision
			if (bounds.overlaps(platform.bounds)) {
				if (deltaX != 0) {
					collidingPlatformX = platform;
					deltax = 0;
				}
				if (deltaY != 0) {
					collidingPlatformY = platform;
				}
				return true;
			}
		}

		// Collide with (spikes, portals)
		for (AbstractGameObject interactable : LevelStage.interactables) {
			if (bounds.overlaps(interactable.bounds)) {
				interactable.interact(this);
			}
		}
		return false;

	}

	@Override
	public void render(SpriteBatch batch) {

		// get correct image and draw the current proportions
		image = null;
		image = animation.getKeyFrame(stateTime, looping);
		currentFrameDimension.set(image.getRegionWidth(),
				image.getRegionHeight());

		// Draw
		batch.draw(image.getTexture(), position.x, position.y, origin.x,
				origin.y, currentFrameDimension.x, currentFrameDimension.y, 1,
				1, rotation, image.getRegionX(), image.getRegionY(),
				image.getRegionWidth(), image.getRegionHeight(),
				viewDirection == VIEW_DIRECTION.left, false);

	}

	public void finishTeleport() {

	}

	public void buttonDown(int buttonIndex) {
		switch (buttonIndex) {

		case Xbox360.BUTTON_A:
			jump();
			break;
		
		case Xbox360.BUTTON_B:
			abilityUp1();
			break;

		}
	}
	public void buttonUp(int buttonIndex) {
		switch (buttonIndex) {

		case Xbox360.BUTTON_A:
			rising = false;
			break;
		
		case Xbox360.BUTTON_B:
			abilityUp1();
			break;

		}
	}

	public void joyStick(Vector2 leftJoyStick, Vector2 rightJoyStick) {
		if(Math.abs(leftJoyStick.x - this.leftJoyStick.x) < .15f
				&& Math.abs(leftJoyStick.y - this.leftJoyStick.y) <.15f){
			return; 
		}
		
		this.leftJoyStick = leftJoyStick;
		this.rightJoyStick = rightJoyStick;
		
		/*if(leftJoyStick.x < -.5f){
			moveLeft();
		}else if(leftJoyStick.x > .5f){
			moveRight();
		}else{
			stopMove();
		}*/
	}

}
