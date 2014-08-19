package com.me.walljumper.game_objects.abilities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.walljumper.game_objects.AbstractGameObject;
import com.me.walljumper.tools.Assets;

public class Ability extends AbstractGameObject {

	protected float endTime;
	public boolean over;

	public Ability() {

	}

	public Ability(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	public Ability(float x, float y, float width, float height, boolean flipX,
			boolean flipY) {
		super(x, y, width, height, flipX, flipY);
	}

	public void setEndTime(float endTime) {
		this.endTime = endTime;
	}

	@Override
	public void update(float deltaTime) {
		endTime -= deltaTime;
		if (endTime < 0) {
			over = true;
		}
		super.update(deltaTime);
		
	}

	@Override
	public void render(SpriteBatch batch) {
		// get correct image and draw the current proportions
		image = null;
		image = animation.getKeyFrame(stateTime, looping);
		currentFrameDimension.set(image.getRegionWidth() / 10f,
				image.getRegionHeight() / 10f);
		// Draw
		if(onScreen)
		batch.draw(image, position.x, position.y, origin.x, origin.y,
				dimension.x, dimension.y, 1, 1,
				rotation);
		
	}
}
