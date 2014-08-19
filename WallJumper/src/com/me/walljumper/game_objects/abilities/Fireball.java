package com.me.walljumper.game_objects.abilities;

import com.badlogic.gdx.utils.Array;
import com.me.walljumper.DIRECTION;
import com.me.walljumper.game_objects.AbstractGameObject;
import com.me.walljumper.game_objects.classes.ManipulatableObject;
import com.me.walljumper.game_objects.classes.ManipulatableObject.VIEW_DIRECTION;
import com.me.walljumper.screens.World;
import com.me.walljumper.tools.Assets;

public class Fireball extends Ability {

	private static float moveSpeed = 15;

	public Fireball(ManipulatableObject parent, DIRECTION direction) {
		super(parent.position.x, parent.position.y + parent.dimension.y / 3,
				1f, 1f);

		// STRAIGHT DOWN
		if (direction == DIRECTION.DOWN) {
			rotation = 270;
			velocity.y = -moveSpeed;

			// STRAIGHT UP
		} else if (direction == DIRECTION.UP) {
			rotation = 90;
			velocity.y = moveSpeed;
			// RIGHT
		} else if (direction == DIRECTION.RIGHT) {
			rotation = 0;
			velocity.x = moveSpeed;

			// LEFT
		} else if (direction == DIRECTION.LEFT) {
			rotation = 180;
			velocity.x = -moveSpeed;
		}else if(direction == DIRECTION.UP_LEFT){
			rotation = 135;
			velocity.x = moveSpeed * (float)(Math.cos(Math.PI * rotation / 180));
			velocity.y = moveSpeed * (float) (Math.sin(Math.PI * rotation / 180));
			

		}else if(direction == DIRECTION.UP_RIGHT){
			rotation = 45;
			velocity.x = moveSpeed * (float)( Math.cos(Math.PI * rotation / 180));
			velocity.y = moveSpeed * (float) (Math.sin(Math.PI * rotation / 180));
			System.out.println("upright");


		}else if(direction == DIRECTION.DOWN_LEFT){
			rotation = 225;
			velocity.x = moveSpeed * (float)( Math.cos(Math.PI * rotation / 180));
			velocity.y = moveSpeed * (float) (Math.sin(Math.PI * rotation / 180));
			System.out.println("shoeunoeuneu");

		}else if(direction == DIRECTION.DOWN_RIGHT){
			rotation = 315;
			velocity.x = moveSpeed * (float)( Math.cos(Math.PI * rotation / 180));
			velocity.y =  moveSpeed * (float) (Math.sin(Math.PI * rotation / 180));

		//NO INPUT, DO FIREBALL IN CORRECT DIRECTION
		}else{
			if(parent.viewDirection == VIEW_DIRECTION.right){
				rotation = 0;
				velocity.x = moveSpeed;		
				
			}else{
				rotation = 180;
				velocity.x = -moveSpeed;
			}
		}
		System.out.println(velocity);

		/*
		 * if (direction.size == 2) { // STRAIGHT DOWN if (direction ==
		 * DIRECTION.DOWN) { rotation -= 45;
		 * 
		 * // STRAIGHT UP } }
		 */

		endTime = 10;
		setAnimation(Assets.instance.rogue.aniFireBall);
		terminalVelocity.set(15, 15);
	}

	public Fireball(Array<DIRECTION> direction) {

	}

	public Fireball(float x, float y, float width, float height) {
		super(x, y, width, height);

	}

	public Fireball(float x, float y, float width, float height, boolean flipX,
			boolean flipY) {
		super(x, y, width, height, flipX, flipY);

	}

	@Override
	public void update(float deltaTime) {
		for (AbstractGameObject enemy : World.controller.levelStage.enemyControlledObjects) {

			if (enemy.bounds.overlaps(bounds)) {
				setEndTime(0);
				break;
			}

		}
		for (AbstractGameObject backPlatform : World.controller.levelStage.backPlatforms) {

			if (backPlatform.bounds.overlaps(bounds)) {
				setEndTime(0);

				break;
			}

		}
		for (AbstractGameObject platform : World.controller.levelStage.platforms) {

			if (platform.bounds.overlaps(bounds)) {
				setEndTime(0);
				break;
			}

		}
		super.update(deltaTime);
		position.x += velocity.x * deltaTime;
		position.y += velocity.y * deltaTime;
		bounds.setPosition(position); 
	}
}
