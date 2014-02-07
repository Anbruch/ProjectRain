package projectrain.game_objects.classes;

import projectrain.game_objects.AbstractGameObject;
import projectrain.game_objects.terrain.Platform;
import projectrain.tools.LevelStage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class ManipulatableObject extends AbstractGameObject{
	
	protected Vector2 moveSpeed; 
	private VIEW_DIRECTION viewDirection;
	private STATE state;
	private AbstractGameObject target;
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
		setAnimation(aniNormal);
		
		if(left){
			moveLeft();
		}
	}

	private void stopMoveLeft() {
		
		//Set velocity.x to 0, check if right might be pressed
		velocity.x = 0;
		left = false;
		setAnimation(aniNormal);
		
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
		Vector2 curPosition = new Vector2(position);

		//this frames change in position
		//Used to check for collision
		float deltax, deltay;
		deltax = velocity.x * deltaTime;
		deltay = velocity.y * deltaTime;
		
		//slightly recursive, one call leads to two separate calls
		move(deltax, deltay);
		
		//set for collision
		if(position.y == curPosition.y){
			state = STATE.GROUNDED;
		}
		
		//so you run when you land from jump
		if(animation != aniRunning && state == STATE.GROUNDED && position.x != curPosition.x)
			setAnimation(aniRunning);
	}
	private void move(float x, float y){
		//If it's the first call, break into components
		if(x != 0 && y != 0){
			move(x, 0);
			move(0, y);
			return;
		}
		
		//Collision Check this object once for x, once for y
		if(!collision(x, y)){
			position.x += x;
			position.y += y;
		}
		
		
		//Just to clarify where the rectangle ended
		bounds.setPosition(position);
		
	}
	private boolean collision(float x, float y){
		
		//Set bounds to where this object will be after adding
		//the velocity of this frame to check and see if we are
		//going to collide with anything
		bounds.setPosition(position.x + x, position.y + y);
		
		//Iterate through platforms
		for(AbstractGameObject platform: LevelStage.platforms){
			
			//If collision
			if(bounds.overlaps(platform.bounds)){
				position.y = platform.position.y + platform.bounds.height;
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
