package org.usfirst.frc.team5102.robot.util;

public interface RobotMap
{
	//====================MOTORS====================
	//Drive
	public static final int rightDriveMotor1 = 1;
	public static final int rightDriveMotor2 = 2;
	public static final int rightDriveMotor3 = 3;
	
	public static final int leftDriveMotor1 = 4;
	public static final int leftDriveMotor2 = 5;
	public static final int leftDriveMotor3 = 6;
	
	//Lift
	public static final int liftMotor1 = 7;
	public static final int liftMotor2 = 8;
	
	//Elevator
	public static final int elevatorMotor1 = 9;
	public static final int elevatorMotor2 = 10;
	
	//Intake
	public static final int intakeMotor = 0;
	
	//====================Pneumatics====================
	
	public static final int grabberSolenoid = 0;
	public static final int tiltSolenoid = 1;
	
	//====================I/O====================
	//Pneumatics
	public static final int workingPressureSensor = 0;
	
	//Elevator
	public static final int elevatorPot = 1;
	public static final int elevatorLowerLimit = 0;
	public static final int elevatorUpperLimit = 1;
	
}
