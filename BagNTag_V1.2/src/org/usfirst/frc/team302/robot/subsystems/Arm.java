package org.usfirst.frc.team302.robot.subsystems;

import org.usfirst.frc.team302.robot.RobotMap;
import org.usfirst.frc.team302.robot.utilities.PIDController;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class allows control of the arm
 * 
 * <h1>Change Log:</h1>
 * 
 *version 1: 2/2/2016 -- Jeff Tefend
 *          <p>
 *          Initial class creation, motor and sensors with ability to set states
 *          </p>
 * 
 * version 2: 2/2/2016 -- Derek Witcpalek
 *          <p>
 *          Added methods to check whether the states have been reached and to stop the motors
 *          Also added documentation
 *          </p>
 * 
 * version 3: 2/9/2016 -- Derek Witcpalek
 *          <p>
 *          Added documentation and classes to return angles based on sensor input
 *          </p>
 * 
 * version 4: 2/10/2016 -- Derek Witcpalek
 *          <p>
 *          Added adjustments for the bend in the arms
 *          </p>
 * 
 * version 5: 2/13/2016 -- Derek Witcpalek
 *          <p>
 *          Tested arm code: the arm was reaching setpoints, but there were issues with it reaching
 *          outside of the frame perimeter, and with the angles read by the potentiometer changing
 *          from test to test. The issue with the potentiometers still requires more testing, and the
 *          frame perimeter issue also needs a solution. I see two options: coordinating the outputs
 *          between the two joints or adding more setpoints that are essentially "transition" setpoints
 *          that the arm would move to between target positions.
 *          </p>
 * 
 * version 6: 2/14/2016 -- Derek Witcpalek
 *          <p>
 *          Added in coordination of control between the two joints of the arm so that the arm will
 *          be less likely to reach outside of the frame. Additionally, there is now the option to
 *          control the arm either manually or with setpoints. When controlling the arm manually,
 *          there is now code to make sure that it doesn't go outside of the extension limit. These
 *          new things still need to be tested.
 *          </p>
 * 
 * version 7: 2/16/2016 -- Derek Witcpalek
 *          <p>
 *          Removed deprecated methods
 *          </p>
 * 
 * version 8: 2/17/2016 -- Derek Witcpalek
 *          <p>
 *          Renamed things better -- motor names are now shoulder and elbow motor and the
 *          PID controllers also follow this naming model
 *          </p>
 * version 9: 3/3/2016 -- Joe Witcpalek Add Review comments
 * 
 * @author Jeff Tefend
 * @version version 9: 3/3/2016
 *
 */



/* Review comments:
 * 
 * See the general comments
 * 
 * 1) More detail on the class JavaDoc:
 *    - What is this supposed to do?
 *    - What motors/sensors is it controlling
 *    - Are there any assumptions or pre-requisites for this class
 * 
 * 2) Enum:
 *    - put each item on a separate line with a comment as to what it means along side it
 *      (e.g. MANUAL, // Move the joints manually )
 *      
 * 3) Don't take the default visibility for the class attributes and use the standard naming convention
 * 
 * 4) Add more detail in the JavaDoc for each method
 *    - what is it supposed to do
 *    - how does it do it
 *    - any assumptions/pre-requisites
 *    
 * 5) Can any of the unimplemented methods be removed or are the abstract from the Subsystem
 *    class?
 *    
 * 6) Delete commented code (or if it is coming back explain what isn't working)
 * 
 * 7) Should there be a defaultCommand
 * 
 * 8) Choose descriptive variable names (e.g. p, i and d are pretty common but using names
 *    like proportionalConstant, integralConstant and derivativeConstant would be clearer)
 *    
 * 9) Are all of the constructor used?  Do we need to support all of them?
 * 
 * 10) isAtTargetState why are we checking the shoulder joint twice?
 */

public class Arm extends Subsystem
{

    // full down position:
    // first joint ~~ 20 degrees
    // second joint ~~ 0 degrees

    // private final double m_CountsPerRevolution = 1440; //100

	// TODO - put constants into RobotMap
    private final double m_lowerArmZeroAngle = 6.6;
    private final double m_upperArmZeroAngle = -2;

    private final double m_MaxManualExtension = 13;

    private ControlStyle m_ControlStyle;

    // motors
    private CANTalon m_ShoulderJoint;
    private CANTalon m_ElbowJoint;

    // Add PID Controller
    private PIDController m_ShoulderController;
    private PIDController m_ElbowController;

    // Sensors (math classes)
    // IAngleSensor m_LeftSideLowerJointSensor;
    // IAngleSensor m_LeftSideUpperJointSensor;
    // IAngleSensor m_RightSideLowerJointSensor;
    // IAngleSensor m_RightSideUpperJointSensor;

    // sensors (reference to physical objects)
    AnalogPotentiometer m_ShoulderSensor;
    AnalogPotentiometer m_ElbowSensor;

    // initialize the state that the arm is trying to reach
    private ArmSetpoints.ArmState m_currentState = ArmSetpoints.ArmState.STARTING_POSITION;

    /**
     * These are the types of control for the arm.
     * You can either control the arm by driving it
     * to setpoints, or by driving it with joystick
     * or other driver input.
     */
    public enum ControlStyle
    {
        MANUAL, 
        SET_POINT
    }

    /**
     * Creator
     * 
     * @param style
     *            this is a control style: manual or setpoint-controlled
     * @param pidConstant
     *            this is an array of the pid constants for the arm controllers.
     *            It is in the form: {p-constant, i-constant, d-constant}
     */
    public Arm(ControlStyle style, double[] pidConstant)
    {
        this(style, pidConstant[0], pidConstant[1], pidConstant[2]);
    }

    /**
     * Creator
     * 
     * @param style
     *            this is a control style: manual or setpoint-controlled
     */
    public Arm(ControlStyle style)
    {
        this(style, 0, 0, 0);
    }

    /**
     * Creator
     * 
     * @param style
     *            this is a control style: manual or setpoint-controlled
     * @param p
     *            proportional constant
     * @param i
     *            integral constant
     * @param d
     *            derivative constant
     */
    public Arm(ControlStyle style, double p, double i, double d)
    {

        m_ControlStyle = style;

        m_ShoulderJoint = new CANTalon(RobotMap.ARM_SHOULDER_MOTOR);
        m_ShoulderJoint.setInverted(false);

        m_ElbowJoint = new CANTalon(RobotMap.ARM_ELBOW_MOTOR);
        m_ElbowJoint.setInverted(true);

        // Add PID Controller
        m_ShoulderController = new PIDController(p, i, d);
        m_ElbowController = new PIDController(p, i, d);

        m_ShoulderSensor = new AnalogPotentiometer(RobotMap.ARM_SHOULDER_ANGLE_SENSOR, 210, 0); // channel, full range, offset
        m_ElbowSensor = new AnalogPotentiometer(RobotMap.ARM_ELBOW_ANGLE_SENSOR, 320, 0); // channel, full range, offset
    }

    /**
     * Changes the arm's control style to the input style.
     * This can be used to accommodate different preferences.
     * 
     * @param style
     *            This is the method you want to use to control the arm
     */
    public void setControlStyle(ControlStyle style)
    {
        m_ControlStyle = style;
    }

    /**
     * This can be used when testing to change the pid constants
     * 
     * @param p
     *            proportional
     * @param i
     *            integral
     * @param d
     *            derivative
     */
    public void setPIDConstants(double p, double i, double d)
    {
        m_ShoulderController.setPIDConstants(p, i, d);
        m_ElbowController.setPIDConstants(p, i, d);
    }

    /**
     * Use this to tell where the arm is trying to get to
     * 
     * @return ArmState The state that the arm is currently trying to reach
     */
    public ArmSetpoints.ArmState getArmStateTarget()
    {
        return m_currentState;

    }

    /**
     * Set the target state for the arm
     * 
     * @param Target
     *            this is the state that you want the arm to go to
     */
    public void setState(ArmSetpoints.ArmState Target)
    {

        if (m_ControlStyle == ControlStyle.SET_POINT)
        {
            // initialize the scale factors
            // these scales are used to make the outputs for each joint proportional to the distance they
            // have to travel. If one joint has to travel 3 times as far as the other joint, theoretically, this
            // will output
            double[] scales = { 1, 1 }; // this is {first joint, second joint}

            // update the target state of the arm
            m_currentState = Target;

            // Calculate preliminary outputs
            double output = m_ShoulderController.calculateOutput((m_ShoulderSensor.get()) - m_lowerArmZeroAngle, ArmSetpoints.getPosition(Target)[2])                   / 2;

            double secondOutput = m_ElbowController.calculateOutput((m_ElbowSensor.get()) - m_upperArmZeroAngle, ArmSetpoints.getPosition(Target)[3]);

            // Get the scales to coordinate the motion of the arms joints
            // scales = CoordinatedOutput.coordinateOutputs(m_FirstJointArmController.getError(),
            // m_SecondJointArmController.getError());

            // adjust the outputs to reflect the scale factor
            output *= scales[0];
            secondOutput *= scales[1];

            m_ShoulderJoint.set(output);
            SmartDashboard.putNumber("first joint output", output);

            m_ElbowJoint.set(secondOutput);
            SmartDashboard.putNumber("second joint output", secondOutput);
        }
    }

    protected void initDefaultCommand()
    {
        // TODO Auto-generated method stub

    }

    /**
     * Sends an output of zero to all of the arm motors
     */
    public void stopArm()
    {
        m_ShoulderJoint.set(0);
        m_ElbowJoint.set(0);
    }

    /**
     * This will tell you if the arm is within a specific tolerance of its target.
     * 
     * If it is within that tolerance value, the method returns true
     * 
     * @param tolerance
     *            ???
     * @return isAtState is the arm within your tolerance threshold of the target state
     */
    public boolean isAtTargetState(double tolerance)
    {
        return (Math.abs(m_ShoulderController.getError()) < tolerance && Math.abs(m_ShoulderController.getError()) < tolerance) ? true : false;
    }

    /**
     * Get the average of the two first joint sensors
     * 
     * @return average angle
     */
    public double getFirstJointPosition()
    {
        // this is the angle between the chassis (horizontal) and the line that goes straight between the two pivots on the arm
        return (m_ShoulderSensor.get() - m_lowerArmZeroAngle);
    }

    /**
     * Get the average of the two second joint sensors
     * 
     * @return average angle
     */
    public double getSecondJointPosition()
    {
        // this is the angle between the line that goes straight between the pivots of the arm,
        // and the line that goes between the second pivot of the arm and the center of
        // the hooking mechanism
        return (m_ElbowSensor.get() - m_upperArmZeroAngle);
    }

    /**
     * Sets the motors to a specific output when you are using manual control.
     * This method will not let you exceed a specific maximum extension when
     * controlling the robot's arm manually.
     * 
     * @param lowerJoint
     *            ??
     * @param upperJoint
     *            ??
     */
    public void set(double lowerJoint, double upperJoint)
    {
        // double extension = ArmMath.getExtension(getFirstJointPosition(), getSecondJointPosition());
        //
        // if(m_ControlStyle == ControlStyle.MANUAL)
        // {
        // if(output1 > 0)
        // {
        // if(extension >= m_MaxManualExtension)
        // {
        // m_LeftSideLowerJoint.set(0);
        // //m_RightSideLowerJoint.set(0);
        // SmartDashboard.putNumber("Arm output 1", 0);
        // }
        // else
        // {
        // m_LeftSideLowerJoint.set(output1);
        // //m_RightSideLowerJoint.set(output1);
        // SmartDashboard.putNumber("Arm output 1", output1);
        // }
        //
        // }
        // else if(output1 < 0)
        // {
        // m_LeftSideLowerJoint.set(output1);
        // //m_RightSideLowerJoint.set(output);
        // SmartDashboard.putNumber("Arm output 1", output1);
        // }
        //
        // }
        // if(output2 > 0)
        // {
        // if(extension >= m_MaxManualExtension)
        // {
        // if(getSecondJointPosition()-getFirstJointPosition() < 0)
        // {
        // m_LeftSideUpperJoint.set(0);
        // m_RightSideUpperJoint.set(0);
        // SmartDashboard.putNumber("Arm output 2", 0);
        // }
        // else if(getSecondJointPosition()-getFirstJointPosition() >= 0)
        // {
        // m_LeftSideUpperJoint.set(output2);
        // m_RightSideUpperJoint.set(output2);
        // SmartDashboard.putNumber("Arm output 2", output2);
        // }
        // }
        //
        // }
        // else if(output2 < 0)
        // {
        // if(extension >= m_MaxManualExtension)
        // {
        // if(getSecondJointPosition()-getFirstJointPosition() > 0)
        // {
        // m_LeftSideUpperJoint.set(0);
        // m_RightSideUpperJoint.set(0);
        // SmartDashboard.putNumber("Arm output 2", 0);
        // }
        // else if(getSecondJointPosition()-getFirstJointPosition() <= 0)
        // {
        // m_LeftSideUpperJoint.set(output2);
        // m_RightSideUpperJoint.set(output2);
        // SmartDashboard.putNumber("Arm output 2", output2);
        // }
        // }
        // }
        // SmartDashboard.putNumber("Arm input 1", output1);
        // SmartDashboard.putNumber("Arm input 2", output2);
        if (m_ControlStyle == ControlStyle.MANUAL)
        {
            m_ShoulderJoint.set(lowerJoint);
            m_ElbowJoint.set(upperJoint);
        }
    }
}
