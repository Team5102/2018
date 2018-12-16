package org.usfirst.frc.team5102.robot;

import java.text.DecimalFormat;

import org.usfirst.frc.team5102.robot.util.DriverStation_5102;
import org.usfirst.frc.team5102.robot.util.RobotMap;
import org.usfirst.frc.team5102.robot.util.Toggle;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.ControllerPower;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;

public class Grabber
{
	static Solenoid grabber, tilt;
	
	static Spark intakeMotor;
		
	AnalogInput workingPressureSensor;
	DecimalFormat df;
	
	static Toggle grabberToggle, tiltToggle;

	/**
	 *  TODO:
	 *  	* This constructor should probably be public.
	 *  	* 'df' should be moved to local method it's singly used in.
	 */
	Grabber()
	{
		grabber = new Solenoid(RobotMap.grabberSolenoid);
		tilt = new Solenoid(RobotMap.tiltSolenoid);
		
		intakeMotor = new Spark(RobotMap.intakeMotor);
		
		workingPressureSensor = new AnalogInput(RobotMap.workingPressureSensor);
		df = new DecimalFormat("###.##");
		
		grabberToggle = new Toggle();
		tiltToggle = new Toggle();
	}
	
	public void teleop()
	{
		DriverStation_5102.setAirPressure(getWorkingPSI());

		// This could replace the below --Charlie.
		grabber.set( (grabberToggle.toggle(DriverStation_5102.secondaryController.getButtonA())) );

		if(grabberToggle.toggle(DriverStation_5102.secondaryController.getButtonA()))
		{
			grabber.set(true);
		}
		else
		{
			grabber.set(false);
		}

//		// This could replace the below. --Charlie
//		tilt.set(Elevator.elevatorPot.get() > 0.15 );

		if(Elevator.elevatorPot.get() > 0.15)
		{
			tilt.set(true);
		}
		else
		{
			tilt.set(false);
		}
		
		if(DriverStation_5102.secondaryController.getLeftTriggerAxis() > .5)
		{
			intakeMotor.set(-0.7);
		}
		else if(DriverStation_5102.secondaryController.getRightTriggerAxis() > .5)
		{
			// Isn't this conditional redundant? --Charlie
			if(tilt.get())
			{
				intakeMotor.set(0.6);
			}
			else
			{
				intakeMotor.set(0.6);
			}
		}
		else
		{
			if(grabber.get())
			{
				intakeMotor.set(0);
			}
			else
			{
				intakeMotor.set(-0.2);
			}
		}
	}
	
	public double getWorkingPSI()
	{
		return Double.parseDouble((df.format(250*(workingPressureSensor.getVoltage()/ControllerPower.getVoltage5V())-25)));	//4.51
	}
	
	public static void setGrabberState(boolean state)
	{
		grabber.set(state);
	}
}
