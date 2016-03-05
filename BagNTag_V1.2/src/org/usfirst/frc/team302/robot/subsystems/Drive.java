
package org.usfirst.frc.team302.robot.subsystems;

import org.usfirst.frc.team302.robot.RobotMap;
import org.usfirst.frc.team302.robot.utilities.AngularMath;
import org.usfirst.frc.team302.robot.utilities.NavXStuffs;
import org.usfirst.frc.team302.robot.utilities.PIDController;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This subsystem defines the drive subsystem for the robot. It controls the talons/motors for the drive as well as processes the encoder values.
 * 
 * @version <b>version 1:</b> 1/16/2016 -- Derek Witcpalek -- original dummy class
 * @version <b>version 2:</b> 2/1/2016 -- Eric Smith and Klayton Smith -- Fill in the details
 * @version <b>version 3:</b> 2/21/2016 -- Derek Witcpalek -- Update with turn control for arcade drive
 * @version <b>version 4:</b> 2/21/2016 -- Klayton Smith -- Cross obstacle
 * @version <b>version 5:</b> 2/27/2016 -- Josh Baker -- lowBar + implemented navX wrapper
 * @version <b>version 6:</b> 3/3/2016  -- Derek Witcpalek -- Add Jeff's turn to angle and vision-based orientation
 * @version <b>version 7:</b> 3/3/2016 -- Joe Witcpalek Merge -- Derek's changes with the latest version
 * @version <b>version 8:</b> 3/3/2016 -- Joe Witcpalek Add Review comments
 */

/*
 * Review comments
 * 1) Remember naming conventions are m_lowerCase for the attributes
 * 2) enums are good to put each on a separate line with a comment next to 
 *    the value indicating what it means (e.g. ASCENDING, // rising up the incline on a defense
 * 3) Probably should keep the shift as an attribute, no need to repeatedly call the 
 *    subsystemfactory to get it
 * 4) Delete commented code or indicate what isn't working if it is something that should be 
 *    comming back in.
 * 5) More JavaDoc
 * 6) Should driveOverObstacle and lowBar be in here or should they be part of commands?
 * 7) double comparisons should have a tolerance
 * 8) is individualMotor being used (it is called by EncoderTest which isn't called.
 * 9) Seems like this class needs methods like:
 * 10) driveDistance( double targetDistance) that a command can call to drive a particular distance
 * 11) turnToAngle( double targetAngle ) that a command can call to rotate to a particular angle
 * 12) double getAngle() that returns the current angle 
 * 13) driveToState( obstacleDriveState targetState )
 * 14) enum should probably be more generic such as driveState and list all states:
 *      TELEOP_ARCADE,
 *      TELEOP_TANK,
 *      STOPPED,
 *      AUTON_DRIVE_DISTANCE,
 *      AUTON_ASCEND_DEFENSE,
 *      AUTON_CROSS_DEFENSE, // This one may need one for each defense
 *      AUTON_DESCEND_DEFENSE, 
 *      AUTON_LANDED_AFTER_DEFENSE,
 *      AUTON_BACKUP_TO_DEFENSE,
 *      AUTON_DEFENSE_DONE,
 *      AUTON_TURN_ANGLE
 * 15) Why is obstacleDriveState a public attribute?  Really should be private, I believe, and
 *     if someone needs to get/set its value create methods.
 */

public class Drive extends Subsystem
{

    private RobotDrive m_drive;
    private CANTalon m_leftMotor;
    private CANTalon m_rightMotor;
    private CANTalon m_leftMotorB;
    private CANTalon m_rightMotorB;
    private PIDController m_LPIDController;
    private PIDController m_RPIDController;
    private PIDController m_TurningController;
    private PIDController m_TargetingController;
    private AHRS m_navX;

    public enum obstacleDriveState
    {
        STOPPED, 
        APPROACHING, 
        ACENDING, 
        LANDED, 
        BACKUP, 
        DONE, 
        CROSS, 
        DECENDING
    }
    
    
    public enum autonAngles
   {
        FIRSTPOSITION, 
        SECONDPOSITION, 
        THIRDPOSITION, 
        FOURTHPOSITION, 
        FITHPOSITION, 
        TURN180
   }

    
    private final double[] AnglesToTurn = { 42, 21.5, -10, -15, -26, 180 };

    double startingHeading;
    double startingPitch;
    Timer timer;
    public obstacleDriveState obstacleState = obstacleDriveState.STOPPED;

    private final double m_MaxTurnRateLowGear = 359.85;
    private final double m_MaxTurnRateHighGear = 359.85;// TODO check this rate

    private double turnTarget = 0;
    private boolean turnTargetSet = false;
    
    
    /**
     * Initialize the motors for the the robot drive.
     */
    public Drive()
    {
        m_leftMotor = new CANTalon(RobotMap.DRIVE_MOTOR_LEFT_MAIN);
        m_rightMotor = new CANTalon(RobotMap.DRIVE_MOTOR_RIGHT_MAIN);
        m_leftMotorB = new CANTalon(RobotMap.DRIVE_MOTOR_LEFT_SLAVE);
        m_rightMotorB = new CANTalon(RobotMap.DRIVE_MOTOR_RIGHT_SLAVE);
        m_leftMotor.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
        m_rightMotor.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
        m_leftMotor.setInverted(false);
        m_leftMotorB.setInverted(false);
        m_rightMotor.setInverted(false);
        m_rightMotorB.setInverted(false);
        m_drive = new RobotDrive(m_leftMotor, m_leftMotorB, m_rightMotor, m_rightMotorB);

        m_TurningController = new PIDController(0.2, 0, 0);
        m_TargetingController = new PIDController(0.000001, 0.001, 0);

        m_navX = NavXStuffs.getNavX();
    }

    /**
     * Updates the default command on this subsystem
     * 
     * @param command
     *            the command to run by default
     */
    public void updateDefaultCommand(Command command)
    {
        setDefaultCommand(command);
    }

    /**
     * 
     */
    public void initDefaultCommand()
    {
    }

    /**
     * Drive the robot with one value being the forward/backward motion and the other being the rotation value. This controls the robot like an video
     * game.
     * 
     * @param moveValue
     *            value for moving the robot forward
     * @param rotateValue
     *            value for rotating the robot
     */
    public void arcadeDrive(double moveValue, double rotateValue)
    {
        double output;
        if (Math.abs(rotateValue) < 0.1)
        {
            output = 0;
        }
        else
        {
            if (SubsystemFactory.getSubsystemFactory().getShifter().isLowGear())
            {
                output = rotateValue + m_TurningController.calculateOutput(m_navX.getRate(), rotateValue * m_MaxTurnRateLowGear / 20);
            }
            else if (SubsystemFactory.getSubsystemFactory().getShifter().isHighGear())
            {
                output = rotateValue + m_TurningController.calculateOutput(m_navX.getRate(), rotateValue * m_MaxTurnRateHighGear / 20);
            }
            else
            {
                output = rotateValue;
            }
        }

        m_drive.arcadeDrive(moveValue, makeInRange(output));
    }

    /**
     * Drive the robot by providing the forward/backward value for each side of the robot. Turning is achieved by providing different values to each
     * side.
     * 
     * @param leftValue
     *            speed of the left side of the robot
     * @param rightValue
     *            speed of the right side of the robot
     */
    public void tankDrive(double leftValue, double rightValue)
    {
        m_drive.tankDrive(leftValue, rightValue);
    }

    /*
     * public void controllerOrientedDrive(double xValue, double yValue, double angleTolerance)
     * { // drive based off of the position of the driver
     * double angle = (navx.getYaw() + 180);
     * double goingAngle;
     * double pAngle;
     * double magnitude = Math.sqrt(Math.pow(Math.abs(xValue), 2) + Math.pow(Math.abs(xValue), 2)); // find the magnitude of
     * // wantingtogodirectionthing vector
     * // (sqrt(|x|^2+|y|^2)
     * int quadrant = 0;
     * boolean switched = false;
     * 
     * if ((xValue > 0) && (yValue > 0))
     * quadrant = 1;
     * if ((xValue < 0) && (yValue > 0))
     * quadrant = 2;
     * if ((xValue < 0) && (yValue < 0))
     * quadrant = 3;
     * if ((xValue > 0) && (yValue < 0))
     * quadrant = 4;
     * 
     * pAngle = ((Math.atan(yValue / xValue)) * 180 / 3.14159265);
     * if ((pAngle < 0) && quadrant != 2)
     * {
     * pAngle += 360;
     * switched = true;
     * }
     * 
     * if (!switched)
     * goingAngle = (pAngle + ((quadrant - 1) * 90));
     * else
     * goingAngle = pAngle;
     * 
     * if (quadrant == 2)
     * goingAngle = goingAngle + 90;
     * 
     * if ((xValue + yValue) < 0.2)
     * {
     * if (Math.abs(angle - goingAngle) > angleTolerance)
     * {
     * if (angle > goingAngle)
     * individualMotor(-magnitude, magnitude);
     * if (angle < goingAngle)
     * individualMotor(magnitude, -magnitude);
     * }
     * else
     * {
     * individualMotor(magnitude, magnitude);
     * }
     * 
     * }
     * }
     */

    
    public void driveOverObstacle(double speed)
    {

        SmartDashboard.putNumber("Heading", m_navX.getYaw());

        SmartDashboard.putNumber("Pitch", m_navX.getPitch());

        SmartDashboard.putNumber("Timer", timer.get());

        if (obstacleState == obstacleDriveState.STOPPED)
        {
            // navX.reset();
            startingHeading = m_navX.getYaw();
            startingPitch = m_navX.getPitch();
            timer.reset();
            timer.stop();
            obstacleState = obstacleDriveState.LANDED;
        }

        SmartDashboard.putNumber("Starting Heading", startingHeading);
        SmartDashboard.putNumber("Starting Pitch", startingPitch);

        if (obstacleState == obstacleDriveState.APPROACHING)
        {
            timer.start();
            individualMotor(-speed, speed);
            obstacleState = obstacleDriveState.ACENDING;
        }

        if (obstacleState == obstacleDriveState.ACENDING)
        {
            individualMotor(-speed, speed);
            if (timer.get() > 1.9)
                obstacleState = obstacleDriveState.BACKUP;
        }
        // 2

        if (obstacleState == obstacleDriveState.LANDED)
        {
            if (m_navX.getYaw() > startingHeading)
                individualMotor(-speed / 4, speed / 4);
            if (m_navX.getYaw() < startingHeading)
                individualMotor(speed / 4, speed / 4);
            // if(navX.getYaw() == startingHeading)obstacleState = obstacleDriveState.DONE;
        }

        if (obstacleState == obstacleDriveState.BACKUP)
        {
            individualMotor(speed / 3, -speed / 3);
            if (m_navX.getPitch() - startingPitch < -0.1)
                obstacleState = obstacleDriveState.DONE;
        }

    }
    
    
    /**
     * lowBar -- auton code that theoretically goes under the low bar
     * 
     * @param speed - speed to run motors
     */
    public void lowBar(double speed)
    {
    	float pitch = m_navX.getPitch();
    	
    	switch(obstacleState)
    	{
    	case APPROACHING:
    		while(pitch == RobotMap.LEVEL_ANGLE)
    		{
    			individualMotor(speed, -speed);
    			if(pitch != RobotMap.LEVEL_ANGLE)
    			{
    				obstacleState = obstacleDriveState.ACENDING;
    			}
    		}
    		break;
    	case ACENDING:
    		while(pitch == RobotMap.CLIMBING_ANGLE)
    		{
    			individualMotor(speed, -speed);
    			if(pitch != RobotMap.CLIMBING_ANGLE)
    			{
    				obstacleState = obstacleDriveState.CROSS;
    			}
    		}
    		break;
    	case CROSS:
    		while(pitch == RobotMap.LEVEL_ANGLE)
    		{
    			individualMotor(speed, -speed);
    			if(pitch != -RobotMap.LEVEL_ANGLE)
    			{
    				obstacleState = obstacleDriveState.DECENDING;
    			}
    		}
    		break;
    	case DECENDING:
    		while(pitch == -RobotMap.CLIMBING_ANGLE)
    		{
    			individualMotor(speed, -speed);
    			if(pitch != -RobotMap.CLIMBING_ANGLE)
    			{
    				obstacleState = obstacleDriveState.BACKUP;
    			}
    		}
    		break;
    	case BACKUP:
    		while(pitch == RobotMap.LEVEL_ANGLE)
    		{
    			individualMotor(speed, - speed);
    			if(pitch != RobotMap.LEVEL_ANGLE)
    			{
    				 obstacleState = obstacleDriveState.STOPPED;
    			}
    		}
    		break;
    	case DONE:
    		obstacleState = obstacleDriveState.STOPPED;
    		break;
    	case STOPPED:
    		individualMotor(speed, -speed);
    		break;
		default:
			break;
    	}
    }

    /**
     * this method can be used to test motors individually
     * 
     * @param left
     *            output for left side
     * @param right
     *            output for right side
     */
    public void individualMotor(double left, double right)
    {
        m_leftMotor.set(left);
        m_leftMotorB.set(left);
        m_rightMotor.set(right);
        m_rightMotorB.set(right);
    }
    
    
    
    public void individualMotor(double left, double right, double leftB, double rightB)
	{
		m_leftMotor.set(left);
		m_rightMotor.set(right);
		m_leftMotorB.set(leftB);
		m_rightMotorB.set(rightB);
	}

    /**
     * Returns an array of encoder position values, converted to inches.
     * [0]=left
     * [1] = right
     * 
     * @return	 double  - encoder position in inches
     */
    public double[] getEncoderPosition()
    {
        // creates an array of encoder position values
        // 0 = left front
        // 1 = right motor
        double[] encoderPositionArray = new double[2];
        encoderPositionArray[0] = ((m_leftMotor.getEncPosition() / 3072) * (8 * Math.PI));
        encoderPositionArray[1] = ((m_rightMotor.getEncPosition() / 3072) * (8 * Math.PI));
        return encoderPositionArray;
    }

    /**
     * Returns an array of encoder velocity values, converted to inches per seconds.
     * [0]=left
     * [1] = right
     * 
     * @return	 double  - encoder velocity in inches per second
     */
    public double[] getEncoderVelocity()
    {
        // creates an array of encoder velocity values
        // 0 = left front
        // 1 = right motor
        double[] encoderVelocityArray = new double[2];
        encoderVelocityArray[0] = ((m_leftMotor.getEncPosition() / 307.2) * (8 * Math.PI));
        encoderVelocityArray[1] = ((m_rightMotor.getEncPosition() / 307.2) * (8 * Math.PI));
        return encoderVelocityArray;
    }

    public void resetEncoders()
    {

        m_leftMotor.setEncPosition(0);
        m_rightMotor.setEncPosition(0);

    }

    public void setBrake(boolean brake)
    {
        m_leftMotor.enableBrakeMode(brake);
        m_leftMotorB.enableBrakeMode(brake);
        m_rightMotor.enableBrakeMode(brake);
        m_rightMotorB.enableBrakeMode(brake);
    }

    private double makeInRange(double target)
    {
        double output = target;

        if (output > 1)
        {
            output = 1;
        }
        else if (output < -1)
        {
            output = -1;
        }

        return output;
    }
    
    
    /**
     * Use this to drive to face a target
     * 
     * @param position			where the target is on the camera
     * @param targetPosition	where the target should be on the camera
     */
    public void faceTarget(int position, int targetPosition)
    {
    	double output = m_TargetingController.calculateOutput(position, targetPosition);
    	individualMotor(-output, -output);
    }
    
    
    /**
     * Use this to figure out whether the robot is facing towards the target
     * 
     * @param target	the x-position that the target should be at
     * @return			is the target in the correct position?
     */
    public boolean isFacingTarget(int target)
    {
    	return (Math.abs(Camera.getCamera("cam0").getX() - target) < 5);
    }
    
    
    public void driveToAngle(autonAngles Positions)
	{
		m_TurningController.setPIDConstants(0.005, 0.0001, -.02);

		double output;

		if (!turnTargetSet)
		{
			turnTarget = AngularMath.add(m_navX.getAngle(), AnglesToTurn[Positions.ordinal()]);
			turnTargetSet = true;
		}
		SmartDashboard.putBoolean("targetset", turnTargetSet);
		SmartDashboard.putNumber("TurningAngle", turnTarget);

		output = m_TurningController.calculateOutput(0,
				AngularMath.getBestRotationDescriptor(m_navX.getAngle(), turnTarget)[0]
						* AngularMath.getBestRotationDescriptor(m_navX.getAngle(), turnTarget)[1]);

		SmartDashboard.putNumber("Error", m_TurningController.getError());
		individualMotor(-output, -output, -output, -output);
		SmartDashboard.putNumber("Output", output);
		SmartDashboard.putNumber("Angle", m_navX.getAngle());
		// P.005, I .0001, D -.02

	}

	public boolean IsAtTargetAngle()
	{
		boolean atTarget = (AngularMath.getBestRotationDescriptor(m_navX.getAngle(),
				AngularMath.add(turnTarget, 1))[0] < 1
				&& AngularMath.getBestRotationDescriptor(m_navX.getAngle(),
						AngularMath.subtract(turnTarget, 1))[0] < 1);
	
		if (atTarget)
		{
			turnTargetSet = false;
		}
		return atTarget;
	}
	
	
	
	public void resetTarget()
	{
		turnTargetSet = false;
	}
    
}
