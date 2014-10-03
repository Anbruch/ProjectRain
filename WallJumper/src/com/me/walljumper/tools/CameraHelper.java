package com.me.walljumper.tools;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.walljumper.Constants;
import com.me.walljumper.game_objects.AbstractGameObject;
import com.me.walljumper.screens.World;

public class CameraHelper {

	private static final String TAG = CameraHelper.class.getName();
	private final float MAX_ZOOM_IN = .16f;
	private final float MAX_ZOOM_OUT = 2.5f;
	private Vector2 position;
	public float zoom;
	private Vector2 targetSpot;
	private AbstractGameObject target;
	private static final float rate = 2;
	Rectangle rect;
	private float zoomTarget;
	private boolean zooming;

	public CameraHelper() {
		position = new Vector2();
		zoom = Constants.defaultZoom;
		zoomTarget = zoom;
		targetSpot = new Vector2();
		rect = new Rectangle(Constants.LEFT_LIMIT, Constants.BOT_LIMIT, Constants.RIGHT_LIMIT,
				Constants.TOP_LIMIT );
	}

	public void update(float deltaTime) {

		// Zoom out/in as much as needed
		float deltaZoom = rate * deltaTime;
		if (zoom > zoomTarget) {
			setZoom(deltaZoom > zoom - zoomTarget ? zoomTarget : zoom - deltaZoom, false);
		} else if(zoom < zoomTarget) {
			setZoom(deltaZoom > zoomTarget - zoom ? zoomTarget : zoom + deltaZoom, false);
		}
		
		
		

	/*
		if(targetSpot != null || hasTarget()){
			float deltaX = !hasTarget() ? (targetSpot.x - position.x) / 10
					: (target.position.x - position.x) / 10, deltaY = !hasTarget() ? (targetSpot.y - position.y) / 10
					: (target.position.y - position.y) / 10;
			position.x += deltaX;
			position.y += deltaY;*/
			
			/*if(deltaX > 5 || deltaX < - 5 || deltaY > 5 || deltaY < - 5)
				World.controller.camOnTarget = false;
			else{
				if(World.controller != null && hasTarget())
					World.controller.camOnTarget = true;
			}
		
		
		rect.x += deltaX;
		rect.y += deltaY;
		}*/
	}
/*
	public void setTarget(AbstractGameObject target) {
		this.target = target;
		this.targetSpot = null;
	}*/

	public boolean onScreen(AbstractGameObject obj) {
		if(obj.bounds.contains(rect)){
			return true;
		}
		// Checks if camera bounds overlaps with
		if(obj.bounds.x > rect.x + rect.width ){
			obj.setPosition(rect.x - obj.dimension.x, obj.position.y);
		}else if(obj.bounds.x + obj.bounds.width < rect.x){
			obj.setPosition(rect.x + rect.width, obj.position.y);
			
		}else if(obj.bounds.y + obj.dimension.y < rect.y){
			obj.setPosition(obj.position.x, rect.y + rect.height);
		}else if(obj.bounds.y > rect.y + rect.height){
			obj.setPosition(obj.position.x, rect.y - obj.dimension.y);
		}

		return false;

	}

	public void setPosition(float x, float y) {
		this.position.set(x, y);
		rect.set(new Rectangle(x - Constants.viewportWidth / 2, y - Constants.viewportHeight / 2, Constants.viewportWidth, Constants.viewportHeight));
		targetSpot = new Vector2(position);
		/*rect.setPosition(x - Constants.viewportWidth / 2 - 5, y
				- Constants.viewportHeight / 2);*/
		

	}

	public Vector2 getPosition() {
		return position;
	}

	public void addZoom(float amount) {
		setZoom(zoom + amount, false);

	}
	
	public void setZoom(float zoom, boolean setZoomTarget) {

		this.zoom = MathUtils.clamp(zoom, MAX_ZOOM_IN, MAX_ZOOM_OUT);
		zoomTarget = setZoomTarget ? this.zoom : zoomTarget;

	}

	public float getZoom() {
		return zoom;
	}

	public AbstractGameObject getTarget() {
		return target;
	}

	/*public boolean hasTarget() {
		return target != null;
	}
*/
	/*public boolean hasTarget(AbstractGameObject target) {
		return hasTarget() && this.target.equals(target);
	}*/

	public void applyTo(OrthographicCamera camera) {
		camera.position.x = this.position.x;
		camera.position.y = this.position.y;
		camera.zoom = zoom;
		camera.update();

	}

	public void destroy() {
		target = null;
		targetSpot = null;
		position = null;
	}

	public void movePositionBy(Vector2 deltaPos) {
		moveTowardsPosition(deltaPos.add(position));
	}

	public void moveTowardsPosition(Vector2 targetSpot) {
		
		this.targetSpot = targetSpot;
	}

	public void zoomBy(float zoomBy) {
		transitionToZoom(zoom + zoomBy);
	}

	public void transitionToZoom(float zoomTo) {

		// timePassed * rate
		zoomTarget = zoomTo;
	}

	public boolean isWithin(float f) {
		
		if(position.dst(target.position) < f){
			return true;
		}
		return false;
	}
}
