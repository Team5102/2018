package org.usfirst.frc.team5102.robot.util;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriverStation_5102
{
	
	public static Joystick launchpad1, launchpad2; 
	public static Xbox driveController, secondaryController;
	
	boolean dataMode = false;
	
	static double airPressure;
	
	AbsoluteEncoder enc;
	
	public enum RobotMode
	{
		auton,
		teleop,
		disabled
	}
	
	public enum InfoStripMode
	{
		airPressure,
		info
	}
	
	public enum Side
	{
		left,
		right,
		neither
	}
	
	public enum FieldConfig
	{
		leftLeft,
		leftRight,
		centerLeft,
		centerRight,
		rightLeft,
		rightRight
	}
	
	boolean modeOverride;
	
	static SendableChooser<String> chooser = new SendableChooser<>();
	static SendableChooser<String> positionChooser = new SendableChooser<>();
		
	public DriverStation_5102()
	{
		driveController = new Xbox(0);
		secondaryController = new Xbox(1);
		
		launchpad1 = new Joystick(2);
		launchpad2 = new Joystick(3);
		
		modeOverride = false;
		
		airPressure = 0;
		
		enc = new AbsoluteEncoder(launchpad1, 2);
		enc.setRange(0, 720);
		
		chooser.addDefault("No Auton", "No Auton");
		chooser.addObject("Drive Forward", "Drive Forward");
		chooser.addObject("Drive To Switch", "Drive To Switch");
		chooser.addObject("Test Auton", "Test Auton");
		chooser.addObject("Capture Switch", "Capture Switch");
		SmartDashboard.putData("Auto Mode", chooser);
		positionChooser.addObject("Left", "Left");
		positionChooser.addDefault("Center", "Center");
		positionChooser.addObject("Right", "Right");
		SmartDashboard.putData("Starting Position", positionChooser);
		
		setConnected();
	}
	
	public void updateDS()
	{
		setInfoStrip(0, airPressure, InfoStripMode.airPressure);
		setInfoStrip(1, enc.getScaledPercent(15), InfoStripMode.info);
		
		if(driveController.getButtonA())
		{
			enc.setValuePercent(75);
		}
	}
	
	public void setInfoStrip(int meter, double number, InfoStripMode mode)
	{
		if(mode == InfoStripMode.airPressure)
		{
			
			if(number > 54)
	    	{
	    		number = 10;
	    	}
	    	else if(number > 48)
	    	{
	    		number = 9;
	    	}
	    	else if(number > 42)
	    	{
	    		number = 8;
	    	}
	    	else if(number > 36)
	    	{
	    		number = 7;
	    	}
	    	else if(number > 30)
	    	{
	    		number = 6;
	    	}
	    	else if(number > 24)
	    	{
	    		number = 5;
	    	}
	    	else if(number > 18)
	    	{
	    		number = 4;
	    	}
	    	else if(number > 12)
	    	{
	    		number = 3;
	    	}
	    	else if(number > 6)
	    	{
	    		number = 2;
	    	}
	    	else
	    	{
	    		number = 1;
	    	}
		}
	
		
		if(number == 0)
    	{
    		setCommPins(meter,false,false,false,false);
    	}
    	else if(number == 1)
    	{
    		setCommPins(meter,false,false,false,true);
    	}
    	else if(number == 2)
    	{
    		setCommPins(meter,false,false,true,true);
    	}
    	else if(number == 3)
    	{
    		setCommPins(meter,false,true,true,true);
    	}
    	else if(number == 4)
    	{
    		setCommPins(meter,true,true,true,true);
    	}
    	else if(number == 5)
    	{
    		setCommPins(meter,true,false,false,false);
    	}
    	else if(number == 6)
    	{
    		setCommPins(meter,true,true,false,false);
    	}
    	else if(number == 7)
    	{
    		setCommPins(meter,true,true,true,false);
    	}
    	else if(number == 8)
    	{
    		setCommPins(meter,false,false,true,false);
    	}
    	else if(number == 9)
    	{
    		setCommPins(meter,false,true,true,false);
    	}
    	else if(number == 10)
    	{
    		setCommPins(meter,false,true,false,false);
    	}
    	else if(number == 11)
    	{
    		setCommPins(meter,false,true,false,true);
    	}
    	else if(number == 12)
    	{
    		setCommPins(meter,true,false,true,false);
    	}
    	else if(number == 13)
    	{
    		setCommPins(meter,true,false,false,true);
    	}
    	else if(number == 14)
    	{
    		setCommPins(meter,true,true,false,true);
    	}
    	else if(number == 15)
    	{
    		setCommPins(meter,true,false,true,true);
    	}
	}
	
	public void setMode(RobotMode mode)
	{
		switch(mode)
		{
			case disabled:
				launchpad1.setOutput(2, false);
				break;
			case auton:
				launchpad1.setOutput(2, true);
				break;
			case teleop:
				launchpad1.setOutput(2, true);
				break;
		}
	}
	
	public static Side getSwitch() throws StringIndexOutOfBoundsException
	{
		String data = DriverStation.getInstance().getGameSpecificMessage();
		
		if(data.length() == 0)
		{
			return Side.neither;
		}
		
		if(data.charAt(0) == 'L')
		{
			return Side.left;
		}
		else
		{
			return Side.right;
		}
	}
	
	public Side getScale() throws StringIndexOutOfBoundsException
	{
		String data = DriverStation.getInstance().getGameSpecificMessage();
		
		if(data.length() == 0)
		{
			return Side.neither;
		}
		
		if(data.charAt(1) == 'L')
		{
			return Side.left;
		}
		else
		{
			return Side.right;
		}
	}
	
	public Alliance getAlliance()
	{
		return DriverStation.getInstance().getAlliance();
	}
	
	public void updateAlliance()
	{
		if(getAlliance() == Alliance.Red)
		{
			launchpad1.setOutput(3, false);
		}
		else
		{
			launchpad1.setOutput(3, true);
		}
	}
	
	public static void setAirPressure(double pressure)
	{
		airPressure = pressure;
	}
	
	public void setConnected()
	{
		launchpad1.setOutput(1, true);
	}
	
	public void setCommPins(int mode, boolean pin1, boolean pin2, boolean pin3, boolean pin4)
	{		
		if(mode == 0)
		{
			launchpad1.setOutput(7, pin1);
			launchpad1.setOutput(8, pin2);
			launchpad1.setOutput(9, pin3);
			launchpad1.setOutput(10, pin4);
		}
		else
		{
			launchpad2.setOutput(1, pin1);
			launchpad2.setOutput(2, pin2);
			launchpad2.setOutput(3, pin3);
			launchpad2.setOutput(4, pin4);
		}
	}
	
	public static String getSelectedAuton()
	{
		return chooser.getSelected();
	}
	
	public static FieldConfig getFieldConfiguration()
	{
		String position = positionChooser.getSelected();
		
		switch(position)
		{
			case "Center":
				if(getSwitch() == Side.left)
				{
					return FieldConfig.centerLeft;
				}
				else if(getSwitch() == Side.right)
				{
					return FieldConfig.centerRight;
				}
				break;
			case "Left":
				if(getSwitch() == Side.left)
				{
					return FieldConfig.leftLeft;
				}
				else if(getSwitch() == Side.right)
				{
					return FieldConfig.leftRight;
				}
				break;
			case "Right":
				if(getSwitch() == Side.left)
				{
					return FieldConfig.rightLeft;
				}
				else if(getSwitch() == Side.right)
				{
					return FieldConfig.rightRight;
				}
				break;
		}
		return FieldConfig.centerLeft;
	}
}
