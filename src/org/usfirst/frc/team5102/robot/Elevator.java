package org.usfirst.frc.team5102.robot;

import org.usfirst.frc.team5102.robot.util.DriverStation_5102;
import org.usfirst.frc.team5102.robot.util.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Elevator implements PIDOutput, PIDSource
{
	static WPI_TalonSRX elevatorMotor1, elevatorMotor2;
	
	public static PIDController elevatorPID;
	public static AnalogPotentiometer elevatorPot;
	public static DigitalInput lowerLimit, upperLimit;
	
	PWM heightLEDs;
	
	public Elevator()
	{
		System.out.println("Initializing elevator...");
		
		elevatorMotor1 = new WPI_TalonSRX(RobotMap.elevatorMotor1);
		elevatorMotor2 = new WPI_TalonSRX(RobotMap.elevatorMotor2);
		elevatorMotor1.setInverted(true);
		elevatorMotor1.setSafetyEnabled(false);
		elevatorMotor2.setSafetyEnabled(false);
		
		elevatorMotor2.follow(elevatorMotor1);
		
		elevatorPot = new AnalogPotentiometer(RobotMap.elevatorPot);
		lowerLimit = new DigitalInput(RobotMap.elevatorLowerLimit);
		upperLimit = new DigitalInput(RobotMap.elevatorUpperLimit);
		
		elevatorPID = new PIDController(5.05, 0.01, 0.1, this, this);
		elevatorPID.setInputRange(0, 0.24);
		elevatorPID.setSetpoint(0);
		elevatorPID.setOutputRange(-0.6, 0.6);
		elevatorPID.setAbsoluteTolerance(0.05);
		
		LiveWindow.addActuator("Elevator", "Elevator PID", elevatorPID);
		
		//heightLEDs = new PWM(9);
		
	}
	
	public void teleop()
	{
		//heightLEDs.setSpeed(0);
		
		/*
		if(DriverStation_5102.secondaryController.getButtonA())
		{
			
			if(!elevatorPID.isEnabled())
			{
				System.out.println("PID enabled");
				
				elevatorPID.enable();
			}
			elevatorPID.setSetpoint((DriverStation_5102.launchpad1.getX()+1)*50);
		}
		*/
		//else
		{
			if(elevatorPID.isEnabled())
			{
				System.out.println("PID disabled");
				
				elevatorPID.disable();
			}
			
			moveElevator(((DriverStation_5102.launchpad1.getRawAxis(1)+1)/2)*(-DriverStation_5102.secondaryController.applyDeadband(DriverStation_5102.secondaryController.getLeftStickY())));
			
			
			//System.out.println(speed);
			
		}
	}
	
	public void moveElevator(double speed)
	{
		speed = speed/2;
		
		if(lowerLimit.get() || elevatorPot.get() <= 0.002)
		{
			if(speed > 0)
			{
				elevatorMotor1.set(speed);
			}
			else
			{
				elevatorMotor1.set(0);
			}
		}
		else if(upperLimit.get() || elevatorPot.get() >= 0.237)
		{
			if(speed < 0)
			{
				elevatorMotor1.set(speed);
			}
			else
			{
				elevatorMotor1.set(0.1);
			}
		}
		else if(elevatorPot.get() < 0.03 && speed < -0.2)
		{
			elevatorMotor1.set(-0.1);
			System.out.println("going slow - " + elevatorPot.get());
		}
		else if(elevatorPot.get() > 0.215 && speed > 0.2)
		{
			elevatorMotor1.set(0.3);
			System.out.println("going slow - " + elevatorPot.get());
		}
		else
		{
			elevatorMotor1.set(speed+0.1);
		}
	}
	
	public static void moveElevatorPID(double height)
	{
		elevatorPID.setSetpoint(height);
		elevatorPID.enable();
		while(!elevatorPID.onTarget())
		{
			Thread.yield();
			//System.out.println("Moving to target: " + height);
		}
		System.out.println("Target Found");
		elevatorPID.disable();
		hold();
	}
	
	public static void hold()
	{
		elevatorMotor1.set(0.1);
	}
	
	@Override
	public void pidWrite(double output)
	{
		System.out.println("pidwrite: " + output);
		
		if(lowerLimit.get())
		{
			System.out.println("lower limit triggered");
			
			if(output > 0)
			{
				elevatorMotor1.set(output);
			}
			else
			{
				elevatorMotor1.set(0);
			}
		}
		else if(upperLimit.get())
		{
			System.out.println("upper limit triggered");
			
			if(output < 0)
			{
				elevatorMotor1.set(output);
			}
			else
			{
				elevatorMotor1.set(0);
			}
		}
		else
		{
			elevatorMotor1.set(output);
			
			//System.out.println("pidwrite: " + output);
		}
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		return PIDSourceType.kDisplacement;
	}

	@Override
	public double pidGet()
	{
		double height = elevatorPot.get();
		//height = ((height-0.007)/.24)*100;
		
		return height;
	}
}
