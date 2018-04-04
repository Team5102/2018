package org.usfirst.frc.team5102.robot.util;

import org.usfirst.frc.team5102.robot.Drive;
import org.usfirst.frc.team5102.robot.util.TurnPID.InputDevice;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class DrivePID implements PIDOutput, PIDSource
{
	public static PIDController drivePID;
	
	public static TurnPID turnPID;
	
	public static Counter driveCounter;
	
	public static int direction;
	
	//public static CustomTimer timeout;
	
	enum Mode
	{
		counter,
		vision
	}
	
	Mode mode;
	
	public DrivePID()
	{
		drivePID = new PIDController(0.014, 0.0000, 0.012, this, this);
		drivePID.setSetpoint(0);
		drivePID.setOutputRange(-0.6, 0.6);
		drivePID.setAbsoluteTolerance(5);
		
		turnPID = new TurnPID();
		
		driveCounter = new Counter(4);
		driveCounter.setSemiPeriodMode(false);
		driveCounter.reset();
		
		direction = 1;
		
		mode = Mode.counter;
		
	}
	
	public void enable()
	{
		System.out.println("PID enabled");
		driveCounter.reset();
		drivePID.enable();
		turnPID.turnPID.enable();
	}
	
	public void disable()
	{
		drivePID.disable();
		turnPID.turnPID.disable();
	}
	
	public void driveAuton(int targetTicks, int newDirection, int newMode)
	{
		direction = newDirection;
		turnPID.setInputDevice(InputDevice.gyro);
		
		if(newMode == 0)		//drive specified distance using counter
		{
			mode = Mode.counter;
			drivePID.setPID(0.014, 0.005, 0.012);
			drivePID.setOutputRange(-1.0, 1.0);
			drivePID.setAbsoluteTolerance(5);
			
			driveCounter.reset();
			drivePID.setSetpoint(targetTicks);
			
			int counter = 0;
			this.enable();
			while(!drivePID.onTarget())
			{
				if(driveCounter.get() == 0)
				{
					if(counter > 100000000)
					{
						System.out.println("Encoder disconnect safety triggered");
						break;
					}
					counter++;
				}
				
			}
			this.disable();
		}
		else if(newMode == 1)	//drive until vision target is certain size
		{
			mode = Mode.vision;
			drivePID.setPID(0.1, 0.0005, 0.012);
			drivePID.setOutputRange(-0.6, 0.6);
			drivePID.setSetpoint(8);
			drivePID.setAbsoluteTolerance(0.5);
			
			this.enable();
			while(!drivePID.onTarget())
			{
				if(!Vision.targetFound())	//if target is lost, cancel aim operation
				{
					break;
				}
			}
			this.disable();
		}
		Drive.robotDrive.arcadeDrive(0, 0);
		
	}
	
	public void turnTo(double angle, InputDevice inputDevice)
	{
		turnPID.currentInput = inputDevice;
		
		//turnPID.gyro.reset();
		
		turnPID.turnPID.setSetpoint(angle);
		
		System.out.println("turnPID enabled");
		turnPID.turnPID.enable();
		
		while(!turnPID.turnPID.onTarget())
		{
			//System.out.println("curve: " + turnPID.getCurve());
			Drive.robotDrive.arcadeDrive(0, -turnPID.getCurve());
		}
		Drive.robotDrive.arcadeDrive(0, 0);
		turnPID.turnPID.disable();
	}
	
	public void resetAngle()
	{
		turnPID.gyro.reset();
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
		if(mode == Mode.counter)
		{
			return driveCounter.get();
		}
		else if(mode == Mode.vision)
		{
			return Vision.getArea();
		}
		else
		{
			return 0;
		}
		
	}

	@Override
	public void pidWrite(double output)
	{
		//System.out.println("Gyro: " + turnPID.gyro.getAngle());
		
		Drive.robotDrive.arcadeDrive(-output*direction, 0);//turnPID.getCurve());
		
		System.out.println("current ticks: " + driveCounter.get() + ", gyro: " + Drive.drivePID.turnPID.gyro.getAngle() + ", angle: " + turnPID.getCurve() + ", pidwrite: " + output);
	}
}
