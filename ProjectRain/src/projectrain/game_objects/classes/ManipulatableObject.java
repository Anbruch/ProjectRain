package projectrain.game_objects.classes;

import projectrain.game_objects.AbstractGameObject;
import projectrain.game_objects.weapons.Weapon;
import projectrain.tools.LevelStage;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class ManipulatableObject extends AbstractGameObject {

	protected Vector2 moveSpeed;
	protected VIEW_DIRECTION viewDirection;
	protected STATE state;
	private AbstractGameObject target, collidingPlatform;
	private boolean right, left;
	public Weapon weapon;
	protected Animation aniJumping;

	public enum VIEW_DIRECTION {
		left, right
	}

	public enum STATE {
		JUMPING, GROUNDED
	}

	public ManipulatableObject() {

		viewDirection = VIEW_DIRECTION.right;
		state = STATE.JUMPING;

		currentFrameDimension = new Vector2();

	}

	public void actOnInputKeyDown(int keycode) {
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
		case Keys.SPACE:

			jump();
			break;
		}
	}// END OF METHOD

	private void jump() {
		if (state == STATE.GROUNDED) {
			velocity.y = moveSpeed.y;
			state = STATE.JUMPING;

			setAnimation(aniJumping);
		}
	}

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
		}
	}// End of actOnInput methods

	private void moveRight() {

		right = true;
		if (left) {
			if (state != STATE.JUMPING)
				setAnimation(aniNormal);
			velocity.x = 0;
			return;
		}
		// Animates the run if grounded
		if (state == STATE.GROUNDED) {
			setAnimation(aniRunning);
		}
		viewDirection = VIEW_DIRECTION.right;
		velocity.x = moveSpeed.x;

	}

	private void moveLeft() {
		// Set left to true so if we were holding right previous to this,
		// when we let go of right, it will move us left
		left = true;
		if (right) {
			velocity.x = 0;
			if (state != STATE.JUMPING)
				setAnimation(aniNormal);

			return;
		}

		// Sets up running animation if on ground
		if (state == STATE.GROUNDED) {
			setAnimation(aniRunning);
		}
		viewDirection = VIEW_DIRECTION.left;
		velocity.x = -moveSpeed.x;

	}

	private void stopMoveRight() {

		// Set velocity to 0, check if left might be pressed
		velocity.x = 0;
		right = false;

		// Animates back to neutral
		if (state != STATE.JUMPING)
			setAnimation(aniNormal);

		if (left) {
			moveLeft();
		}
	}

	private void stopMoveLeft() {

		// Set velocity.x to 0, check if right might be pressed
		velocity.x = 0;
		left = false;
		// Sets animation back to neutral
		if (state != STATE.JUMPING)
			setAnimation(aniNormal);

		// Bug fix for if both buttons are down,
		// Left is released, then character should move right
		if (right) {
			moveRight();
		}
	}

	private void stopMove() {

		// Set velocity.x to 0, check if right might be pressed
		velocity.x = 0;
		left = false;
		right = false;
		// Sets animation back to neutral
		setAnimation(aniNormal);

		

	}

	private void moveUp() {

	}

	private void moveDown() {

	}

	private void stopMoveUp() {

	}

	private void stopMoveDown() {

	}

	public void setTarget(AbstractGameObject target) {
		this.target = target;
	}

	public void moveX(float deltaTime){
		// Used to check if grounded or not
		Vector2 curPosition = new Vector2(position);
		
		//change in X axis this frame
		float deltaX = velocity.x * deltaTime;
		
		//overriden in subclass of ManipulatableObject
		ensureCorrectCollisionBounds();

		// Collision Check this object once for x, once for y
		if (!collision(deltaX, 0)) {
			position.x += deltaX;
		}

		
		// so you run when you land from jump
		if (animation != aniRunning && state == STATE.GROUNDED
				&& position.x != curPosition.x)
			setAnimation(aniRunning);
		
		//or if you just land, you don't want to be in jumping animation
		else if (state == STATE.GROUNDED && position.x == curPosition.x
				&& animation == aniRunning) {
			setAnimation(aniNormal);
		}
	}
	public void moveY(float deltaTime){
		//change in y this frame
		float deltaY = velocity.y * deltaTime;
		
		//overriden by whatever subclass of manipulatable object
		ensureCorrectCollisionBounds();

		//If you didn't collide in y axis,
		//add deltaY to the position.y
		if (!collision(0, deltaY)) {
			position.y += deltaY;
			
		//if you did collide with something,
		//in the Y AXIS ONLY, set velocity to 0
		}else{
			velocity.y = 0;	
			deltaY = 0;
		}
		
		//If you're in the air, set state to jumping
		if(deltaY != 0){
			if (state != STATE.JUMPING) {
				setAnimation(aniJumping);
			}
			
			state = STATE.JUMPING;
			collidingPlatform = null;
			
		//else you've either hit the top of the top of the platform
		//or one of the other 3 sides.
		} else {
			//If you hit the top of the platform,
			//set state to grounded and velocity to 0, 
			//and position to the top of the platform
			if (position.y > collidingPlatform.position.y
					+ collidingPlatform.bounds.height) {
				setAnimation(aniNormal);
				state = STATE.GROUNDED;
				velocity.y = 0;
				position.y = collidingPlatform.position.y
						+ collidingPlatform.bounds.height;
			}

		}

	}
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		moveX(deltaTime);
		moveY(deltaTime);
		

		// Just to clarify where the rectangle ended
		bounds.setPosition(position);
		

	}

	
	protected void ensureCorrectCollisionBounds() {

	}

	private boolean collision(float deltax, float deltay) {

		// Set bounds to where this object will be after adding
		// the velocity of this frame to check and see if we are
		// going to collide with anything
		bounds.setPosition(bounds.x + deltax, position.y + deltay);

		// Iterate through platforms
		for (AbstractGameObject platform : LevelStage.platforms) {

			// If collision
			if (bounds.overlaps(platform.bounds)) {
				collidingPlatform = platform;
				return true;
			}
		}
		return false;

	}

	@Override
	public void render(SpriteBatch batch) {

		// get correct image and draw the current proportions
		image = null;
		image = animation.getKeyFrame(stateTime, true);
		currentFrameDimension.set(image.getRegionWidth(),
				image.getRegionHeight());
		// Draw
		batch.draw(image.getTexture(), position.x, position.y, 0, 0,
				currentFrameDimension.x, currentFrameDimension.y, 1, 1,
				rotation, image.getRegionX(), image.getRegionY(),
				image.getRegionWidth(), image.getRegionHeight(),
				viewDirection == VIEW_DIRECTION.left, false);

	}

}
