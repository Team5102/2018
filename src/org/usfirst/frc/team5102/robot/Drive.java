package org.usfirst.frc.team5102.robot;

import org.usfirst.frc.team5102.robot.util.DrivePID;
import org.usfirst.frc.team5102.robot.util.DriverStation_5102;
import org.usfirst.frc.team5102.robot.util.RobotMap;
import org.usfirst.frc.team5102.robot.util.TouchlessEncoder;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Drive implements PIDOutput, PIDSource
{
	public WPI_TalonSRX leftDriveMotor1,leftDriveMotor2,leftDriveMotor3,rightDriveMotor1,rightDriveMotor2,rightDriveMotor3;
	
	public static RobotDrive robotDrive;
		
	static double pidSpeed;
	
	//static AHRS gyro;
	
	//public static PIDController drivePID;
	
	public static DrivePID drivePID;
		
	public static TouchlessEncoder driveEncoder;
	
	//public static Counter driveCounter;
		
	Drive() 
		{		
		leftDriveMotor1 = new WPI_TalonSRX(RobotMap.leftDriveMotor1);
		leftDriveMotor2 = new WPI_TalonSRX(RobotMap.leftDriveMotor2);
		leftDriveMotor3 = new WPI_TalonSRX(RobotMap.leftDriveMotor3);
		rightDriveMotor1 = new WPI_TalonSRX(RobotMap.rightDriveMotor1);
		rightDriveMotor2 = new WPI_TalonSRX(RobotMap.rightDriveMotor2);
		rightDriveMotor3 = new WPI_TalonSRX(RobotMap.rightDriveMotor3);
		
		leftDriveMotor2.follow(leftDriveMotor1);
		leftDriveMotor3.follow(leftDriveMotor1);
		rightDriveMotor2.follow(rightDriveMotor1);
		rightDriveMotor3.follow(rightDriveMotor1);
		
		robotDrive = new RobotDrive(leftDriveMotor1,rightDriveMotor1);
		
		pidSpeed = .5;
		
		//gyro = new AHRS(SPI.Port.kMXP);
		
		//driveEncoder = new TouchlessEncoder(0);
		//driveEncoder.reset();
		
		//driveCounter = new Counter(4);
		//driveCounter.setExternalDirectionMode();
		//driveCounter.setSemiPeriodMode(false);
		//driveCounter.reset();
		
		drivePID = new DrivePID();
		
		//drivePID = new PIDController(0.05, 0.005, 0.3, this, this);
		//drivePID.setSetpoint(0);
		//drivePID.setOutputRange(-0.5, 0.5);
		//drivePID.setAbsoluteTolerance(1);
		
		//LiveWindow.addActuator("Drive", "Drive PID", drivePID);
		
	}
	
	public static void driveAuton(int targetTicks, int direction)
	{
		//drivePID.driveAuton(targetTicks, direction);
		
		//Drive.gyro.reset();
		
		//drivePID.setPID(0.05, 0.005, 0.3);
		//drivePID.setOutputRange(-0.5, 0.5);
		/*
		if(direction > 0)
		{
			Drive.setPIDSpeed(-.40);
		}
		else if(direction < 0)
		{
			Drive.setPIDSpeed(.40);
		}
		
		//Drive.drivePID.enable();
		Drive.driveEncoder.reset();
		
		while(driveEncoder.getTicks() < targetTicks)
		{			
			if(targetTicks - driveEncoder.getTicks() < 100-msPerTick)
			{
				msPerTick = 70;
			}
			
			if(driveEncoder.getTimePerTick() < msPerTick)
			{
				if(pidSpeed < 0)
				{
					pidSpeed += 0.001;
				}
				else
				{
					pidSpeed -= 0.001;
				}
				
			}
			else if(driveEncoder.getTimePerTick() > msPerTick)
			{
				if(pidSpeed < 0)
				{
					pidSpeed -= 0.001;
				}
				else
				{
					pidSpeed += 0.001;
				}
			}
			
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			driveEncoder.update();
		}
		*/
	}
	
	
	public static void turnTo(double angle)
	{
		//Drive.drivePID.setPID(0.05, 0.02, 0.2);
		
		//Drive.drivePID.setOutputRange(-.4, .4);
		
		//Drive.setPIDSpeed(0);
		//Drive.drivePID.setSetpoint(angle);
		
		//while(!drivePID.onTarget()){}
		//Drive.drivePID.enable();
	}
	
	
	@SuppressWarnings("deprecation")
	public void teleop()
	{
		
		robotDrive.arcadeDrive(
			DriverStation_5102.driveController.getLeftStickY()*((((DriverStation_5102.launchpad1.getRawAxis(0)+1)/4)+.5)),
			-DriverStation_5102.driveController.getRightStickX()*((((DriverStation_5102.launchpad1.getRawAxis(0)+1)/4)+.5))
			);
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
		return 0;//Drive.gyro.getAngle();
	}

	@Override
	public void pidWrite(double output)
	{
		//System.out.println("pidWrite - " + output + " Gyro: " + gyro.getAngle());
		
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
