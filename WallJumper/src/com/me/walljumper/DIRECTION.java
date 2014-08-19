package com.me.walljumper;

import com.me.walljumper.game_objects.classes.ManipulatableObject;
import com.me.walljumper.game_objects.classes.ManipulatableObject.VIEW_DIRECTION;

public class DIRECTION {

	public static final DIRECTION RIGHT = new DIRECTION();
	public static final DIRECTION LEFT = new DIRECTION();
	public static final DIRECTION UP = new DIRECTION();
	public static final DIRECTION DOWN = new DIRECTION();
	public static final DIRECTION UP_LEFT = new DIRECTION();
	public static final DIRECTION UP_RIGHT = new DIRECTION();
	public static final DIRECTION DOWN_LEFT = new DIRECTION();
	public static final DIRECTION DOWN_RIGHT = new DIRECTION();
	
	private DIRECTION(){
		
	}
	public static DIRECTION getDirection(ManipulatableObject caller, boolean up, boolean down, boolean right, boolean left){
		if(left && right || up && down)
			return caller.viewDirection == VIEW_DIRECTION.right ? RIGHT : LEFT;
		
		if(up){
			if(left)
				return UP_LEFT;
			if(right)
				return UP_RIGHT;
			else
				return UP;
			
		}else if(down){
			if(left)
				return DOWN_LEFT;
			if(right)
				return DOWN_RIGHT;
			else 
				return DOWN;
			
		}else if(right){
			if(up)
				return UP_RIGHT;
			if(down)
				return DOWN_RIGHT;
			else
				return RIGHT;
		}else if(left){
			if(up)
				return UP_LEFT;
			if(down)
				return DOWN_LEFT;
			else
				return LEFT;
		}
		
		
		return null;
	}

}
