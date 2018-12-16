package org.usfirst.frc.team5102.robot.util;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Vision
{
static NetworkTable target;
	
	public static void init()
	{
		target = NetworkTable.getTable("limelight");
	}
	
	public static double getTargetX()
	{
		return target.getDouble("tx", 0);
	}
	
	public static double getTargetY()
	{
		return target.getDouble("ty", 0);
	}
	
	public static double getArea()
	{
		return target.getDouble("ta", 0);
	}
	
	public static boolean targetFound()
	{
		// This could replace the below. --Charlie
//		return (target.getDouble("tv", 0) == 1);

		if(target.getDouble("tv", 0) == 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static void setLEDs(boolean state)
	{
//		// This could replace the below. --Charlie.
//		target.putDouble("ledMode", (state) ? 0 : 1);

		if(state)
		{
			target.putDouble("ledMode", 0);
		}
		else
		{
			target.putDouble("ledMode", 1);
		}
	}
}
