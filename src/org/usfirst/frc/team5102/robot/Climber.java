package org.usfirst.frc.team5102.robot;

import org.usfirst.frc.team5102.robot.util.DriverStation_5102;
import org.usfirst.frc.team5102.robot.util.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Climber
{
	WPI_TalonSRX climberMotor1, climberMotor2;
	
	public Climber()
	{
		climberMotor1 = new WPI_TalonSRX(RobotMap.liftMotor1);
		climberMotor2 = new WPI_TalonSRX(RobotMap.liftMotor2);
		climberMotor2.follow(climberMotor1);
	}
	
	public void teleop()
	{
		climberMotor1.set(-DriverStation_5102.secondaryController.applyDeadband(DriverStation_5102.secondaryController.getRightStickY()));
	}
}
