package org.usfirst.frc.team5102.robot;

import org.usfirst.frc.team5102.robot.util.DriverStation_5102;
import org.usfirst.frc.team5102.robot.util.DriverStation_5102.RobotMode;
import org.usfirst.frc.team5102.robot.util.DriverStation_5102.Side;
import org.usfirst.frc.team5102.robot.util.Vision;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * TODO:
 * 		* Document. Look into JavaDoc the guide.
 * 			<a href="https://www.oracle.com/technetwork/java/javase/tech/index-137868.html"></a>
 * 		* Consider making a class that encapsulates the subsystems like 'Drive'.
 */
public class Robot extends IterativeRobot
{
	DriverStation_5102 driverStation;
	
	Drive drive;
	Grabber grabber;
	Elevator elevator;
	Climber climber;
	
	Autonomous auton;
	
	@Override
	public void robotInit()
	{
		System.out.println("Initializing robot...");
		
		driverStation = new DriverStation_5102();
		drive = new Drive();
		grabber = new Grabber();
		elevator = new Elevator();
		climber = new Climber();
		
		auton = new Autonomous(this);
		
		//CameraServer.getInstance().startAutomaticCapture();
		
		NetworkTableInstance.getDefault()
	    .getEntry("/CameraPublisher/limelightStream/streams")
	    .setStringArray(new String[]{"mjpg:http://limelight.local:5800/?action=stream"});
		
		Vision.init();				//Initialize networktable for vision
		Vision.setLEDs(false);		//Turn off vision LEDs
	}

	@Override
	public void autonomousInit()
	{		
		Vision.setLEDs(true);		//Turn on vision LEDs
		
		driverStation.setMode(RobotMode.auton);

//		// This could replace the below --Charlie.
//		// Ternary is handy for things like assigning based off of an if/else.
//		String outputSwitch = (driverStation.getSwitch() == Side.left) ? "Left" : "Right";
//		String outputScale = (driverStation.getScale() == Side.left) ? "Left" : "Right";
//		System.out.println("Switch:   " + outputSwitch + "\nScale   " + outputScale);

		if(driverStation.getSwitch() == Side.left)
		{
			System.out.println("Switch:   Left");
		}
		else
		{
			System.out.println("Switch:   Right");
		}
		if(driverStation.getScale() == Side.left)
		{
			System.out.println("Scale:    Left");
		}
		else
		{
			System.out.println("Scale:    Right");
		}
		
		auton.runAuto();		//Start running autonomous procedure
	}

	@Override
	public void autonomousPeriodic()
	{
		driverStation.updateDS();
		
		DriverStation_5102.setAirPressure(grabber.getWorkingPSI());
	}

	/**
	 * TODO:
	 * 	* Consider moving things like setting 'setGrabberState' to a
	 * 	  method internal to the Grabber class. The subcomponents could implement
	 * 	  'teleopInit' from a common interface.
	 *  - It makes each component responsible for themselves, and it makes it more obvious
	 *    where changes are being made to the subsystem.
	 */
	@Override
	public void teleopInit()
	{
		Vision.setLEDs(false);
		
		grabber.setGrabberState(true);
		
		drive.drivePID.disable();
		
		driverStation.setMode(RobotMode.teleop);
	}

	@Override
	public void teleopPeriodic()
	{
		driverStation.updateDS();
		
		drive.teleop();
		grabber.teleop();
		elevator.teleop();
		climber.teleop();
	}

	@Override
	public void testPeriodic() {
	}
	
	@Override
	public void disabledInit()
	{		
		Vision.setLEDs(false);
		driverStation.setMode(RobotMode.disabled);
	}
	
	@Override
	public void disabledPeriodic()
	{
		driverStation.setConnected();
		driverStation.updateAlliance();
		
		DriverStation_5102.setAirPressure(grabber.getWorkingPSI());
		
		driverStation.updateDS();
	}
}

