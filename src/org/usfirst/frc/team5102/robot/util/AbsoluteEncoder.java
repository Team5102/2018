package org.usfirst.frc.team5102.robot.util;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Notifier;

public class AbsoluteEncoder
{
	int rotations, rawValue, previousValue, zeroOffset, rangeMin, rangeMax, encPort;
	
	Joystick launchPad;
	
	AbsoluteEncoder(Joystick launchpad, int port)
	{
		launchPad = launchpad;
		
		encPort = port;
		
		rotations = 0;
		zeroOffset = getRawValue();
		
		setRange(-1000, 1000);
				
		new Thread()
		{
			public void run()
			{
				while(true)
				{
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {}
					
					int value = getRawValue();
					
					rawValue = value;
					
					if(previousValue > 250 && rawValue < 120)
					{						
						rotations++;
					}
					else if(previousValue < 120 && rawValue > 250)
					{						
						rotations--;
					}
					
					if(((value + (rotations*360))-zeroOffset) > rangeMax)
					{
						setValueDegrees(rangeMax);
					}
					else if(((value + (rotations*360))-zeroOffset) < rangeMin)
					{
						setValueDegrees(rangeMin);
					}
										
					previousValue = rawValue;
				}
				
			}
		}.start();
	}
	
	public int getRawValue()
	{
		return (int)((launchPad.getRawAxis(encPort)+1)*180);
	}
	
	public double getValue()
	{
		return (rawValue + (rotations*360))-zeroOffset;
	}
	
	public int getRotations()
	{
		return rotations;
	}
	
	public void setRange(int min, int max)
	{
		rangeMin = min;
		rangeMax = max;
	}
	
	public void reset()
	{
		rotations = 0;
		zeroOffset = rawValue;
	}
	
	public int getOffset()
	{
		return zeroOffset;
	}
	
	public void setValueDegrees(int value)
	{
		int offset_new = getRawValue();

		value = value-offset_new;
		
		int rotation_new = value/360;
		int extraDegrees = -(value-(360*rotation_new));
				
		//System.out.println("SETVALUE: rotations " + rotation_new + " offset: " + extraDegrees + "  value:  " + value);
		
		rotations = rotation_new;
		zeroOffset = extraDegrees;
	}
	
	public void setValuePercent(int percent)
	{
		setValueDegrees(toDegrees(percent));
	}
	
	public int getPercent()
	{
		double totalRange = rangeMax-rangeMin;
		double value = (getValue()-rangeMin);
		double percent = (value/totalRange)*100;
		
		return (int)Math.round(percent);
	}
	
	public int getScaledPercent(int scaleFactor)
	{
		return (getPercent()*scaleFactor)/100;
	}
	
	public int toDegrees(double percent)
	{
		double totalRange = rangeMax-rangeMin;
		double degrees = ((percent/100)*totalRange)-rangeMin;
		
		return (int) degrees;
	}
}
