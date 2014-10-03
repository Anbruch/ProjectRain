package Controllers;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector2;
import com.me.walljumper.WallJumper;

public class XboxListener extends ControllerAdapter {
	private int controllerNumber;// This should corrolate to what index it sends
									// input too
	private Vector2 leftJoyStick;
	private Vector2 rightJoyStick;
	private Controller controller;
	public XboxListener(int number) {
		this.controllerNumber = number;
		controller = Controllers.getControllers().get(this.controllerNumber);
		leftJoyStick = new Vector2();
		rightJoyStick = new Vector2();

	}

	@Override
	public boolean povMoved(Controller controller, int povIndex,
			PovDirection value) {

		System.out.println("povIndex " + povIndex + ". direction value "
				+ value);
		return true;
	}

	@Override
	public boolean axisMoved(Controller controller, int axisIndex, float value) {
		
		return true;
	}

	@Override
	public boolean buttonDown(Controller controller, int buttonIndex) {
		WallJumper.currentScreen.controllerButtonDown(controllerNumber,
				buttonIndex);

		return true;
	}
	
	@Override
	public boolean buttonUp(Controller controller, int buttonIndex) {
		WallJumper.currentScreen.controllerButtonUp(controllerNumber,
				buttonIndex);

		return true;
	}

	public void update() {
		
		leftJoyStick.x = controller.getAxis(Xbox360.AXIS_LEFT_X);
		leftJoyStick.y = controller.getAxis(Xbox360.AXIS_LEFT_Y);
		rightJoyStick.x = controller.getAxis(Xbox360.AXIS_RIGHT_X);
		rightJoyStick.y = controller.getAxis(Xbox360.AXIS_RIGHT_Y);
		
		WallJumper.currentScreen.joyStick(controllerNumber, leftJoyStick, rightJoyStick);
		
	}

}
