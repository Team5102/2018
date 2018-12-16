package org.usfirst.frc.team5102.robot.util;

public class Toggle
{
	boolean pressed, running;
	
	public Toggle()
	{
		pressed = false;
		running = false;
	}
	
	public boolean toggle(boolean buttonPressed)
	{

//		// The below should as a replacement. -- Charlie
//		pressed = (buttonPressed && !pressed);
//		if(pressed)
//			running = !running;
//		return running;

		if(buttonPressed)
		{
			if(!pressed)
			{
				running = !running;
				
				pressed = true;
			}
		}
		else
		{
			pressed = false;
		}
		
		return running;
	}
	
	public void set(boolean state)
	{
		running = state;
	}
}
