package org.usfirst.frc.team302.robot;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SPI.Port;

/**
 * This class stores and returns constants relating to the motors and sensors
 *
 * @version version 1: 1/16/2016 -- Zach Zweber Initial class creation, motor and sensor constants with getter methods
 * @version version 2: 1/17/2016 -- Derek Witcpalek Minimal clean up
 * @version version 3: 1/29/2016 -- Joe Witcpalek Refactor Package Name, make public static final and remove accessors Rename to RobotMap
 * @version version 4: 2/8/2016 -- Joe Witcpalek Add documentation/IDs for how the robot will be wired
 * @version version 5: 2/18/2016 -- Joe Witcpalek Updated documentation/IDs reacting to electrical wiring changes
 * @version version 6: 2/21/2016 -- Josh Baker Added Constants for the pressure sensor
 * @version version 7: 2/20/2016 -- Zach Zweber add new constants to the shooter
 * @version version 8: 2/23/2016 -- Joe Witcpalek Merge changes
 * 
 * @author Zach Zweber
 *
 */
public class RobotMap
{
	//==========================================================================================
	// CAN Devices
	//==========================================================================================
    public static final int DRIVE_MOTOR_LEFT_MAIN = 1; // left drive motor - Master
    public static final int DRIVE_MOTOR_LEFT_SLAVE = 2; // left drive motor - Slave
    public static final int DRIVE_MOTOR_RIGHT_MAIN = 3; // right drive motor - Master
    public static final int DRIVE_MOTOR_RIGHT_SLAVE = 4; // right drive motor - Slave
    public static final int ARM_SHOULDER_MOTOR = 5; // CAN - 5
    public static final int ARM_ELBOW_MOTOR = 6; // CAN - 6
    public static final int SHOOTER_ELEVATION_MOTOR = 8; // CAN - 8
    public static final int SHOOTER_MOTOR_LEFT = 7; // CAN - 7
    public static final int SHOOTER_MOTOR_RIGHT = 9; // CAN - 9
    
	//==========================================================================================
	// Analog Input Devices
	//==========================================================================================   
    public static final int ARM_SHOULDER_ANGLE_SENSOR = 4; // Analog - 0
    public static final int ARM_ELBOW_ANGLE_SENSOR = 1; // Analog - 1
    public static final int SHOOTER_ELEVATION_SENSOR = 0; // Expansion Board 0 = 4
    public static final int AIR_PRESSURE_SENSOR = 5; // Expansion Board 1 = 5
    //==========================================================================================
    // NavX thing
    //==========================================================================================
    public static final Port NAVX_PORT = SPI.Port.kMXP;
    
	//==========================================================================================
	// Digital Input Devices
	//==========================================================================================   
    public static final int SHOOTER_INTAKE_SENSOR = 0; // Digital IO - 0
   
	//==========================================================================================
	// PWM Devices
	//==========================================================================================   
    public static final int SHOOTER_PUNCHER_SERVO = 9; // PWM - 9
    
	//==========================================================================================
	// Pneumatic Control Module Devices
	//==========================================================================================   
    public static final int TRANSMISSION_SHIFT_SOLENOID = 0; // Pneumatic Control Module channel for solenoid

	
    // =========================================================================================
    // Drive Subsystem
    // -----------------------------------------------------------------------------------------
    // Two Talon SRX motor controllers drive the motors on each side of the robot (four motors
    // total). Each side will have a single gearbox, so the Talons should be set up with a
    // Master-Slave relationship between them. Encoders are wired into the Talons to provide
    // feedback.
    //
    // See CAN devices for Talon IDs
    // =========================================================================================
    public static final double MINIMAL_SPEED = 0.1;
    public static final float CLIMBING_ANGLE = 60;
    public static final float LEVEL_ANGLE = 0;
    
    // =========================================================================================
    // Shift (transmission) Subsystem
    // -----------------------------------------------------------------------------------------
    // Pneumatic solenoid controls shifting (see Pneumatic Control Module Devices for IDs)
    // =========================================================================================
    //Pressure Sensor Value thingies
    public static final double VOLTAGE_MAX = 4.5;
    public static final double VOLTAGE_MIN = 0.5;
    public static final double SUPPLY_VOLTAGE = 5.0;
    public static final double PRESSURE_MIN = 60;
    public static final int PRESSURE_SENSOR = 2;
    //Shift State thingy
    public static final boolean HIGH_GEAR = true;
    public static final boolean LOW_GEAR = false;


    // =========================================================================================
    // Arm Subsystem
    // -----------------------------------------------------------------------------------------
    // There are two arms with elbox and shoulder joints that will use Talon SRX motor controllers
    // to drive a sproket/chain drive at each joint. There will be Analog Hall Sensors to
    // help determine the angle at each joint.
    //
    // See CAN devices for Talon IDs, Analog Input Devices for the sensor IDs
    // =========================================================================================


    // =========================================================================================
    // Shooter Subsystem
    // -----------------------------------------------------------------------------------------
    // Talon SRX motor controllers will drive the shooter/intake motors. They will have
    // encoders directly wired into the Talon SRXs to provide speed feedback.
    //
    // A servo will move the boulder into the shooter wheels after they are up to speed.
    //
    // A digital banner sensor will detect when the boulder has taken in, so that the motors
    // can stop.
    //
    // See CAN devices for Talon IDs, Digital Input for the intake sensor ID and PWM for the Servo ID
    // =========================================================================================
    public static final double INTAKE_BOULDER_SPEED = 1.0; // velocity
    public static final double SHOOT_BOULDER_SPEED = 1.0; // velocity
    public static final double PUNCH_ANGLE = 90.0;
    public static final double PUNCH_RESET_ANGLE = 0.0;

    // =========================================================================================
    // Shooter Aiming Subsystem
    // -----------------------------------------------------------------------------------------
    // A single Talon SRX motor controller drives a motor to control the angle. An analog
    // Hall Sensor will provide angle feedback.
    //
    // See CAN devices for Talon IDs, Analog Input Devices for the elevation sensor ID
    // =========================================================================================
    public static final double INTAKE_ANGLE = 0;
    public static final double NEUTRAL_ANGLE = 5;
    public static final double LOW_GOAL_ANGLE = 23;
    public static final double HIGH_GOAL_ANGLE = 41;
    public static final double FOLDED_ANGLE = 185;
    public static final double DISTANCE_TO_SLOW_DOWN = 20;//The distance from target angle in which the shooter will start to slow.
    public static final double CRUISE_SPEED = .1; //Fastest aiming motor speed
    public static final double FINAL_SPEED = .05; //Slowest aiming motor speed
    public static final double TOLERANCE = 3; //Distance from target angle that the motors will stop
    public static final double PROPORATIONAL_CONSTANT = 39.1304348; //Proportional constant for potentiometers!

    // =========================================================================================
    // Joysticks
    // =========================================================================================
    public static final int DRIVER_CONTROLLER = 0;
    public static final int COPILOT_CONTROLLER = 1;
    

}