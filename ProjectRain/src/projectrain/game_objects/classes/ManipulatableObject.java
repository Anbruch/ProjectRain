package projectrain.game_objects.classes;

import projectrain.game_objects.AbstractGameObject;
import projectrain.tools.LevelStage;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class ManipulatableObject extends AbstractGameObject{
	
	protected Vector2 moveSpeed; 
	protected VIEW_DIRECTION viewDirection;
	protected STATE state;
	private AbstractGameObject target, collidingPlatform;
	private boolean right, left;
	
	public enum VIEW_DIRECTION{
		left, right
	}
	public enum STATE{
		JUMPING, GROUNDED
	}
	public ManipulatableObject(){
		
		viewDirection = VIEW_DIRECTION.right;
		state = STATE.JUMPING;
		
	}
	
	public void actOnInputKeyDown(int keycode){
		//Movement, same among all characters
		switch(keycode){
		//LEFT
		case Keys.LEFT:
			
			moveLeft();
			break;
		//RIGHT
		case Keys.RIGHT:
			
			moveRight();
			break;
		//DOWN
		case Keys.DOWN:
			
			moveDown();
			break;
		//UP
		case Keys.UP:
			
			moveUp();
			break;
		case Keys.SPACE:
			
			jump();
			break;
		}
	}//END OF METHOD
	private void jump() {
		if(state == STATE.GROUNDED){
			velocity.y = moveSpeed.y;
			state = STATE.JUMPING;
		}
	}

	public void actOnInputKeyUp(int keycode) {
		//Movement, same among all characters
		switch(keycode){
		//LEFT
		case Keys.LEFT:
			
			stopMoveLeft();
			break;
		//RIGHT
		case Keys.RIGHT:
			
			stopMoveRight();
			break;
		//DOWN
		case Keys.DOWN:
			
			stopMoveDown();
			break;
		//UP
		case Keys.UP:
			
			stopMoveUp();
			break;
		}
	}//End of actOnInput methods
	
	private void moveRight() {
		
		right = true;
		if(left){
			setAnimation(aniNormal);
			velocity.x = 0;
			return;
		}
		//Animates the run if grounded
		if(state == STATE.GROUNDED){
			setAnimation(aniRunning);
		}
		viewDirection = VIEW_DIRECTION.right;
		velocity.x = moveSpeed.x;
		
	}

	private void moveLeft() {
		//Set left to true so if we were holding right previous to this,
		//when we let go of right, it will move us left
		left = true;
		if(right){
			velocity.x = 0;
			setAnimation(aniNormal);

			return;
		}
		
		//Sets up running animation if on ground
		if(state == STATE.GROUNDED){
			setAnimation(aniRunning);
		}
		viewDirection = VIEW_DIRECTION.left;
		velocity.x = -moveSpeed.x;
		

	}
	private void stopMoveRight() {
		
		//Set velocity to 0, check if left might be pressed
		velocity.x = 0;
		right = false;	
		
		//Animates back to neutral
		setAnimation(aniNormal);
		
		if(left){
			moveLeft();
		}
	}

	private void stopMoveLeft() {
		
		//Set velocity.x to 0, check if right might be pressed
		velocity.x = 0;
		left = false;
		//Sets animation back to neutral
		setAnimation(aniNormal);
		
		//Bug fix for if both buttons are down,
		//Left is released, then character should move right
		if(right){
			moveRight();
		}
	}

	private void moveUp() {
		
	}

	private void moveDown() {
		
	}
	private void stopMoveUp() {
		
	}

	private void stopMoveDown() {
		
	}
	public void setTarget(AbstractGameObject target){
		this.target = target;
	}
	
	@Override 
	public void update(float deltaTime){
		super.update(deltaTime);
		
		//Used to check if grounded or not
		Vector2 curPosition = new Vector2(position);

		//this frames change in position
		//Used to check for collision
		float deltax, deltay;
		deltax = velocity.x * deltaTime;
		deltay = velocity.y * deltaTime;
		
		//slightly recursive, one call leads to two separate calls
		if(deltax == 0){
			if(!move(deltax, deltay)){
				if(position.y > collidingPlatform.position.y + collidingPlatform.bounds.height){
					state = STATE.GROUNDED;
					velocity.y = 0;
					position.y = collidingPlatform.position.y + collidingPlatform.bounds.height;
			
				}else{
					velocity.y = 0;
					
				}
			}
		}else move(deltax, deltay);
		
		/*//set for collision
		if(position.y == curPosition.y){
			velocity.y = 0;
			state = STATE.GROUNDED;
			
		}*/

		//so you run when you land from jump
		if(animation != aniRunning && state == STATE.GROUNDED && position.x != curPosition.x)
			setAnimation(aniRunning);
	}
	private boolean move(float deltax, float deltay){
		//If it's the first call, break into components
		if(deltax != 0 && deltay != 0){
			move(deltax, 0);

			if(move(0, deltay)){
				state = STATE.JUMPING;
				collidingPlatform = null;
			}else{
				if(position.y > collidingPlatform.position.y + collidingPlatform.bounds.height){
					state = STATE.GROUNDED;
					velocity.y = 0;
					position.y = collidingPlatform.position.y + collidingPlatform.bounds.height;
			
				}else{
					velocity.y = 0;
				}
			}
			return false;
			
		}
		
		ensureCorrectCollisionBounds();

		//Collision Check this object once for x, once for y
		if(!collision(deltax, deltay)){
			position.x += deltax;
			if(deltay != 0){
				position.y += deltay;
				return true;
			}

		}
		
		//Just to clarify where the rectangle ended
		bounds.setPosition(position);
		return false;
		
	}
	protected void ensureCorrectCollisionBounds() {
		
	}

	private boolean collision(float deltax, float deltay){
		
		//Set bounds to where this object will be after adding
		//the velocity of this frame to check and see if we are
		//going to collide with anything
		bounds.setPosition(bounds.x + deltax, position.y + deltay);
		
		//Iterate through platforms
		for(AbstractGameObject platform: LevelStage.platforms){
			
			//If collision
			if(bounds.overlaps(platform.bounds)){
				collidingPlatform = platform;
				return true;
			}
		}
		return false;
		
	}
	@Override
	public void render(SpriteBatch batch) {
		
		batch.draw(image.getTexture(), position.x, position.y, 0, 0, dimension.x, dimension.y, 1, 1, rotation, image.getRegionX(), image.getRegionY(), image.getRegionWidth(), image.getRegionHeight(),
				viewDirection == VIEW_DIRECTION.left, false);

	}
}