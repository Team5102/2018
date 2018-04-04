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
