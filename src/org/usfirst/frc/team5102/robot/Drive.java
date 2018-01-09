package org.usfirst.frc.team5102.robot;

import org.usfirst.frc.team5102.robot.util.DriverStation;
import org.usfirst.frc.team5102.robot.util.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Drive implements PIDOutput, PIDSource
{
	public WPI_TalonSRX leftDriveMotor1,leftDriveMotor2,rightDriveMotor1,rightDriveMotor2;
	
	public static RobotDrive robotDrive;
		
	static double pidSpeed;
	
	static AHRS gyro;
	
	public static PIDController drivePID;
		
	//public static TouchlessEncoder driveEncoder;
		
	Drive() 
		{		
		leftDriveMotor1 = new WPI_TalonSRX(RobotMap.leftDriveMotor1);
		leftDriveMotor2 = new WPI_TalonSRX(RobotMap.leftDriveMotor2);
		rightDriveMotor1 = new WPI_TalonSRX(RobotMap.rightDriveMotor1);
		rightDriveMotor2 = new WPI_TalonSRX(RobotMap.rightDriveMotor2);
		
		leftDriveMotor2.follow(leftDriveMotor1);
		rightDriveMotor2.follow(rightDriveMotor1);
		
		robotDrive = new RobotDrive(leftDriveMotor1,rightDriveMotor1);
		
		pidSpeed = .5;
		
		gyro = new AHRS(SPI.Port.kMXP);
		
		//driveEncoder = new TouchlessEncoder(0);
		//driveEncoder.reset();
		
		drivePID = new PIDController(0.05, 0.005, 0.3, this, this);
		drivePID.setSetpoint(0);
		drivePID.setOutputRange(-0.5, 0.5);
		drivePID.setAbsoluteTolerance(1);
		
		LiveWindow.addActuator("Drive", "Drive PID", drivePID);
		
	}
	
	/*
	public static void turnTo(double angle)
	{
		Drive.drivePID.setPID(0.05, 0.02, 0.2);
		
		Drive.drivePID.setOutputRange(-.4, .4);
		
		Drive.setPIDSpeed(0);
		Drive.drivePID.setSetpoint(angle);
		
		while(!drivePID.onTarget()){}
		//Drive.drivePID.enable();
	}
	*/
	
	public void teleop()
	{
		
		robotDrive.arcadeDrive(-DriverStation.driveController.getLeftStickY(),-DriverStation.driveController.getRightStickX());
		
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PIDSourceType getPIDSourceType()
	{
		return PIDSourceType.kDisplacement;
	}

	@Override
	public double pidGet()
	{
		return Drive.gyro.getAngle();
	}

	@Override
	public void pidWrite(double output)
	{
		System.out.println("pidWrite - " + output + " Gyro: " + gyro.getAngle());
		
		robotDrive.arcadeDrive(pidSpeed,-output);
	}
	
	public static void setPIDSpeed(double speed)
	{
		pidSpeed = speed;
	}
	
	public static double getPIDSpeed()
	{
		return pidSpeed;
	}
}
