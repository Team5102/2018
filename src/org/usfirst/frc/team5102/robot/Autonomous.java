package org.usfirst.frc.team5102.robot;

import org.usfirst.frc.team5102.robot.util.DriverStation_5102;
import org.usfirst.frc.team5102.robot.util.Vision;
import org.usfirst.frc.team5102.robot.util.DriverStation_5102.Side;
import org.usfirst.frc.team5102.robot.util.TurnPID.InputDevice;

public class Autonomous
{
	Robot robot;
	
	Autonomous(Robot robot)
	{
		this.robot = robot;
	}
	
	public void runAuto()
	{
		
		System.out.println("Auto selected: " + DriverStation_5102.getSelectedAuton());
		
		switch(DriverStation_5102.getSelectedAuton())
		{
    		case "No Auton":
    			break;
    		case "Test Auton":
    			testAuton();
    			break;
    		case "Drive Forward":
    			driveForward();
    			break;
    		case "Drive To Switch":
    			driveToSwitch();
    		case "Capture Switch":
    			captureSwitch();
		}
	}
	
	public void testAuton()
	{
		
	}
	
	public void driveForward()
	{
		new Thread()
		{
			public void run()
			{
				System.out.println("running driveForward");
				
				//grabAndLift();
				//waitFor(500);
				
				robot.grabber.setGrabberState(true);				
				Elevator.moveElevatorPID(0.15);
				
				Drive.drivePID.resetAngle();
				Drive.drivePID.driveAuton(150, 1, 0);
				
			}
		}.start();
	}
	
	public void driveToSwitch()
	{
		new Thread()
		{
			public void run()
			{
				System.out.println("running driveToSwitch");
				
				robot.grabber.grabber.set(true);				
				Elevator.moveElevatorPID(0.15);
				
				robot.drive.drivePID.resetAngle();
				robot.drive.drivePID.driveAuton(100, 1, 0);
				
			}
		}.start();
	}
	
	public void captureSwitch()
	{
		new Thread()
		{
			public void run()
			{
				System.out.println("running captureSwitch");
				
				robot.drive.drivePID.disable();
				robot.elevator.elevatorPID.disable();
				
				grabAndLift();
				
				robot.drive.drivePID.resetAngle();
								
				switch(DriverStation_5102.getFieldConfiguration())
				{
					case leftLeft:
						break;
					case leftRight:
						driveOver(90, 60);
						break;
					case centerLeft:
						driveOver(-90, 30);
						break;
					case centerRight:
						driveOver(90, 30);
						break;
					case rightLeft:
						driveOver(-90, 60);
					case rightRight:
						break;
				}
				
				waitFor(500);
				
				if(Vision.targetFound())
				{
					System.out.println("turning to target");
					robot.drive.drivePID.turnTo(0, InputDevice.camera);
					System.out.println("driving to target");
					robot.drive.drivePID.driveAuton(0, 1, 1);
					
					dischargeCube();
				}
			}
		}.start();
	}
	
	void waitFor(int ms)
	{
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public void grabAndLift()
	{
		robot.grabber.grabber.set(true);
		waitFor(1500);
		robot.grabber.grabber.set(false);
		Grabber.intakeMotor.set(-0.4);
		waitFor(1500);
		Grabber.intakeMotor.set(0);				
		Elevator.moveElevatorPID(0.15);
	}
	
	public void driveOver(double angle, int distance)
	{
		robot.drive.drivePID.turnTo(angle, InputDevice.gyro);
		waitFor(300);
		robot.drive.drivePID.driveAuton(distance, 1, 0);
		waitFor(300);
		robot.drive.drivePID.turnTo(0, InputDevice.gyro);
	}
	
	public void dischargeCube()
	{
		Grabber.intakeMotor.set(0.4);
		waitFor(1000);
		Grabber.intakeMotor.set(0);
	}
}
