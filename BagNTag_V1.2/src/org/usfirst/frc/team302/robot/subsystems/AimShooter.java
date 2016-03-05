package org.usfirst.frc.team302.robot.subsystems;

import org.usfirst.frc.team302.robot.RobotMap;
import org.usfirst.frc.team302.robot.commands.ManualAimShooter;
import org.usfirst.frc.team302.robot.utilities.HallEffectSensor;
import org.usfirst.frc.team302.robot.utilities.IAngleSensor;
import org.usfirst.frc.team302.robot.utilities.PIDController;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is for the AimShooter subsystem.
 * 
 * <br>
 * <b>Change Log:</b>
 * <br>
 * version 1: 2/6/2016 -- Zach Zweber -- initial subsystem creation 
 * <br>
 * version 2: 3/3/2016 -- Joe Witcpalek -- Add Review comments 
 * <br>
 * 
 * @author Adam Armstrong, Zach Zweber
 * @version version 2: 3/3/2016 
 */

/* Review comments:
 * 
 * See the general comments
 * 
 * 1) More detail on the class JavaDoc:
 *    - What is the AimShooter subsystem supposed to do?
 *    - What motors/sensors is it controlling
 *    - Are there any assumptions or pre-requisites for this class
 * 
 * 2) Enum:
 *    - put each item on a separate line with a comment as to what it means along side it
 *      (e.g. INTAKE, // Move the shooter to the angle to pick up a boulder from the ground)
 *      
 * 3) Don't take the default visibility for the class attributes and use the standard naming convention
 * 
 * 4) Add JavaDoc for each method
 *    - what is it supposed to do
 *    - how does it do it
 *    - any assumptions/pre-requisites
 *    
 * 5) Can any of the unimplemented methods be removed or are the abstract from the Subsystem
 *    class?
 *    
 * 6) Delete commented code (or if it is coming back explain what isn't working)
 * 
 * 7) setMotorSpeed is poorly named (doesn't actually set any speed and its input is a target
 *    which isn't a speed rather an angle).  It is calculating a target speed based on distance 
 *    from the target angle.  I'd even contend that the method should just take in a target state
 *    and it could find the current angle and the desired angle internally.
 *    
 * 8) manualMoveMotor could be named better.  It is really should be something like manualAim().
 * 
 * 9) Should there be a defaultCommand like ManualAimShooter or are we assigning that command
 *    to a button?
 */


public class AimShooter extends Subsystem
{
	
	private static final double blah = 0.0;
	
    public enum AimState
    {
        INTAKE, NEUTRAL, LOWGOAL, HIGHGOAL, FOLDED
    }

    CANTalon m_talon;
    IAngleSensor m_sensor;
    AnalogInput m_input;
    PIDController m_PID;
    double m_pConstant; //proportional constant for potentiometer

    public AimShooter()
    {
        m_talon = new CANTalon(RobotMap.SHOOTER_ELEVATION_MOTOR); // Sets motor
        m_talon.enableBrakeMode(true);
        m_talon.setFeedbackDevice(FeedbackDevice.QuadEncoder); // Sets feedback
        m_sensor = new HallEffectSensor(RobotMap.SHOOTER_ELEVATION_SENSOR); // Sets encoder counts to degrees
        m_PID = new PIDController(.0004, 0, 0, 0, 1);
        m_pConstant = RobotMap.PROPORATIONAL_CONSTANT;
    }

    public void initDefaultCommand()
    {
        //setDefaultCommand(new ManualAimShooter());
    }
    
    public double getSensorValue()
    {
    	return m_sensor.getAngle(blah);
    }

    public void stopMotors()
    {
        m_talon.set(0.0);
    }

    /**
     * It decides the speed of the motor
     * 
     * @param target
     *            angel
     * @return Double for motor speed
     */
//    public double setMotorSpeed(double target)
//    {
//        double actual = m_sensor.getAngle(m_talon.getEncPosition()); //actual angle
//        double actual2 = ((m_sensorP.getVoltage())*m_pConstant)-90;
//        SmartDashboard.putNumber("shooterAngle", actual);
//        double d = -target + actual; // distance from target
//        double d1 = RobotMap.DISTANCE_TO_SLOW_DOWN; // distance from targey in which it will start slowing down
//        double cruiseSpeed = RobotMap.CRUISE_SPEED; // max speed
//        double finalSpeed = RobotMap.FINAL_SPEED; // lowest possible speed
//        double dynamicSpeed = (d / d1) * cruiseSpeed; // used in if statement to determine speed
//        double speed = 1; // Output speed. It won't change if if statement fails.
//
//        if (d < RobotMap.TOLERANCE && d > -RobotMap.TOLERANCE) // tests if the shooter is within the tolerance
//        {
//            speed = 0;
//        }
//        else if (d > d1) // tests distance is greate than d1
//        {
//            speed = cruiseSpeed;
//        }
//        else if (d < -d1) // tests if distance is greater than -d1
//        {
//            speed = -cruiseSpeed;
//        }
//        else if (d <= d1 && d > 2) // tests if distance is less d1
//        {
//            if (dynamicSpeed > finalSpeed)
//                speed = dynamicSpeed;
//            else
//                speed = finalSpeed;
//        }
//        else if (d >= -d1 && d < -2) // tests if distance is greater than -d1
//        {
//            if (dynamicSpeed < -finalSpeed)
//                speed = dynamicSpeed;
//            else
//                speed = -finalSpeed;
//        }
//        else
//        {
//            speed = 0;
//        }
//        return speed;
//    }
    
    public double setMotorSpeed(double target)
    {
    	//actual angle
        double actual = m_sensor.getAngle(blah);
        SmartDashboard.putNumber("shooterAngle", actual);
        double d = -target + actual; // distance from target
        double d1 = RobotMap.DISTANCE_TO_SLOW_DOWN; // distance from targey in which it will start slowing down
        double cruiseSpeed = RobotMap.CRUISE_SPEED; // max speed
        double finalSpeed = RobotMap.FINAL_SPEED; // lowest possible speed
        double dynamicSpeed = (d / d1) * cruiseSpeed; // used in if statement to determine speed
        double speed = 1; // Output speed. It won't change if if statement fails.

        if (d < RobotMap.TOLERANCE && d > -RobotMap.TOLERANCE) // tests if the shooter is within the tolerance
        {
            speed = 0;
        }
        else if (d > d1) // tests distance is greate than d1
        {
            speed = cruiseSpeed;
        }
        else if (d < -d1) // tests if distance is greater than -d1
        {
            speed = -cruiseSpeed;
        }
        else if (d <= d1 && d > 2) // tests if distance is less d1
        {
            if (dynamicSpeed > finalSpeed)
                speed = dynamicSpeed;
            else
                speed = finalSpeed;
        }
        else if (d >= -d1 && d < -2) // tests if distance is greater than -d1
        {
            if (dynamicSpeed < -finalSpeed)
                speed = dynamicSpeed;
            else
                speed = -finalSpeed;
        }
        else
        {
            speed = 0;
        }
        return speed;
    }

    /**
     * @param State
     *            of the shooter
     * 
     */
    public void setState(AimState State)
    {

        switch (State)
        {
        case INTAKE:
            m_talon.set(this.setMotorSpeed(RobotMap.INTAKE_ANGLE));// Set shooter to Intake
            SmartDashboard.putNumber("AngleOutpun", m_PID.calculateOutput(m_sensor.getAngle(blah), RobotMap.INTAKE_ANGLE));
            
            break;

        case NEUTRAL:
            m_talon.set(this.setMotorSpeed(RobotMap.NEUTRAL_ANGLE));// Set shooter to Neutral
            SmartDashboard.putNumber("AngleOutpun", m_PID.calculateOutput(m_sensor.getAngle(blah), RobotMap.NEUTRAL_ANGLE));
            break;

        case LOWGOAL:
            m_talon.set(this.setMotorSpeed(RobotMap.LOW_GOAL_ANGLE));// Set shooter Low Goal Position
            SmartDashboard.putNumber("AngleOutpun", m_PID.calculateOutput(m_sensor.getAngle(blah), RobotMap.LOW_GOAL_ANGLE));
            break;

        case HIGHGOAL:
            m_talon.set(this.setMotorSpeed(RobotMap.HIGH_GOAL_ANGLE));// Set shooter to High Goal Position
            SmartDashboard.putNumber("AngleOutpun", m_PID.calculateOutput(m_sensor.getAngle(blah), RobotMap.HIGH_GOAL_ANGLE));
            break;

        case FOLDED:
            m_talon.set(this.setMotorSpeed(RobotMap.FOLDED_ANGLE));// Set shooter to Folder
            SmartDashboard.putNumber("EncoderAngleOutpun", m_PID.calculateOutput(m_sensor.getAngle(blah), RobotMap.FOLDED_ANGLE));
            break;
        // -m_PID.calculateOutput(m_sensor.getAngle(m_talon.getEncPosition()),185

        default:
            break;
        }

    }

    
    /**
     * manualMoveMotor -- manually move the shooter after a button press
     * 
     * @param POV -- ??
     */
    public void manualMoveMotor(int POV)
    {
    	if(POV == 0)
    	{
    		m_talon.set(0.25);
    	}
    	else if(POV == 180)
    	{
    		m_talon.set(-0.25);
    	}
    	else if(POV == -1)
    	{
    		m_talon.set(0.0);
    	}
    }
    
}
