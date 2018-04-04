package org.usfirst.frc.team5102.robot.util;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SPI;

public class TurnPID implements PIDOutput, PIDSource
{
	public static AHRS gyro;
	PIDController turnPID;
	
	public static double curve;
	
	public enum InputDevice
	{
		gyro,
		camera
	}
	
	InputDevice currentInput;
	
	public TurnPID()
	{
		turnPID = new PIDController(0.004, 0.01, 0.07, this, this);
		turnPID.setSetpoint(0);
		turnPID.setOutputRange(-0.6, 0.6);
		turnPID.setInputRange(-360, 360);
		turnPID.setAbsoluteTolerance(1);
		
		currentInput = InputDevice.gyro;
		
		gyro = new AHRS(SPI.Port.kMXP);
		
		curve = 0;
	}
	
	public double getCurve()
	{
		return curve;
	}
	
	public void setInputDevice(InputDevice device)
	{
		currentInput = device;
	}

	@Override
	public void pidWrite(double output)
	{
		curve = output;
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
		System.out.println("target: " + gyro.getAngle());
		
		if(currentInput == InputDevice.gyro)
		{
			//turnPID.setPID(0.05, 0.005, 0.3);
			//turnPID.setOutputRange(-0.6, 0.6);
			turnPID.setP(0.004);
			return gyro.getAngle();
		}
		else if(currentInput == InputDevice.camera)
		{
			//turnPID.setPID(0.05, 0.01, 0.3);
			//turnPID.setOutputRange(-0.5, 0.5);
			turnPID.setP(0.001);
			return -Vision.getTargetX();
		}
		return 0;
	}

}
