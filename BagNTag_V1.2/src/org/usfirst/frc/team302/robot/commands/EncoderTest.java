
package org.usfirst.frc.team302.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team302.robot.subsystems.Drive;
import org.usfirst.frc.team302.robot.subsystems.SubsystemFactory;
import org.usfirst.frc.team302.robot.utilities.PIDController;
/**
 * This class is used to control driving with encoders.
 * It can control via velocity or position.
 * Position control accepts values in inches.
 * Velocity control accepts values in inches per second.
 * To use these methods, two parameters are needed: leftTarget and rightTarget
 * They are the desired values for velocity or position, depending on which is being used, in aforementioned units.
 */
//NOTE: ON ROBOT, WHEELS SPIN 1 TIME FOR EVERY 3 ROTATIONS OF THE ENCODER
public class EncoderTest extends Command {

        //these are the velocities of the left and right encoders
        double leftEncoderVelocity = 0;
        double rightEncoderVelocity = 0;

        //these are the positions of the left and right encoders
        double leftEncoderPosition = 0;
        double rightEncoderPosition = 0;

        //these are the current values for the talons
        double leftMotorValue = 0;
        double rightMotorValue = 0;

    //this will be the output from the pid calculatisons
    double leftOutput = 0;
    double rightOutput = 0;

    protected Drive m_drive;
    protected PIDController m_LPIDController;
    protected PIDController m_RPIDController;
    public EncoderTest() {
        // Use requires() here to declare subsystem dependencies
        m_drive = SubsystemFactory.getSubsystemFactory().getDrive();
        requires(m_drive);
        m_LPIDController = new PIDController(0.00003, 0, 0);
        m_RPIDController = new PIDController(0.00003, 0, 0);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        m_drive.resetEncoders();
        //turns on the brakes on the talons so it does not go too far in autonomous
        m_drive.setBrake(true);
    }


    /**
     * This class is used to drive the robot at a constant velocity using encoders and PID.
     * @param leftTargetVelocity
     *            value for the velocity of the left wheels, in inches per second
     * @param rightTargetVelocity
     *            value for the velocity of the right wheels, in inches per second
     */
     public void encoderVelocity(double leftTargetVelocity, double rightTargetVelocity){

       //gets the encoder values of the left and right sides
       leftEncoderVelocity = m_drive.getEncoderVelocity()[0];
       rightEncoderVelocity = -m_drive.getEncoderVelocity()[1];

       //calculate the outputs for all of the motors using the PID class
          leftOutput = (m_LPIDController.calculateOutput((leftEncoderVelocity), leftTargetVelocity));
          rightOutput = (m_RPIDController.calculateOutput((rightEncoderVelocity), rightTargetVelocity));

          //This takes the PID output and adds it to the current motor value

          leftMotorValue = (leftMotorValue + leftOutput);
          rightMotorValue = (rightMotorValue + rightOutput);

    //this uses the makeInRange function to keep the value between -1 and 1
      leftMotorValue = (makeInRange(leftMotorValue));
      rightMotorValue = (makeInRange(rightMotorValue));

      m_drive.individualMotor(-leftMotorValue, rightMotorValue);
   }

     /**
      * This class is used for testing.
      * It displays the velocities and positions of the left and right encoders on the smart dashboard.
      * Units are in inches for position, and inches per second for velocity
      */
   public void encoderData(){
       //gets encoder velocities from the drive class
       leftEncoderVelocity = m_drive.getEncoderVelocity()[0];
       rightEncoderVelocity = m_drive.getEncoderVelocity()[1];

       leftEncoderPosition = m_drive.getEncoderPosition()[0];
       rightEncoderPosition = -m_drive.getEncoderPosition()[1];

       SmartDashboard.putNumber("Left Encoder Velocity", leftEncoderVelocity);
       SmartDashboard.putNumber("Right Encoder Velocity", rightEncoderVelocity);

       SmartDashboard.putNumber("Left Encoder Position", leftEncoderPosition);
       SmartDashboard.putNumber("Right Encoder Position", rightEncoderPosition);
   }

   /**
    * This class is used to drive the robot to a certain distance using encoders and PID.
    * @param leftTargetPosition
    *            value for the position of the left wheels, in inches.
    * @param rightTargetPosition
    *            value for the position of the right wheels, in inches.
    */
   public void encoderPosition(double leftTargetPosition, double rightTargetPosition){
       //gets the encoder values of the left and right sides
       leftEncoderPosition = m_drive.getEncoderPosition()[0];
       rightEncoderPosition = -m_drive.getEncoderPosition()[1];

       //calculate the outputs for all of the motors using the PID class
          leftOutput = (m_LPIDController.calculateOutput((leftEncoderPosition), leftTargetPosition));
          rightOutput = (m_RPIDController.calculateOutput((rightEncoderPosition), rightTargetPosition));

     SmartDashboard.putNumber("Left Motor Value", leftOutput);
     SmartDashboard.putNumber("Right Motor Value", rightOutput);
     m_drive.individualMotor(-leftOutput, rightOutput);
}

    private double makeInRange(double output)
    {
        double corrected = 0;

        if (output > 1)
        {
            corrected = 1;
        } else if (output < -1)
        {
            corrected = -1;
        } else
        {
            corrected = output;
        }

        return corrected;
    }
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }



    protected void execute()
    {
        // TODO Auto-generated method stub

    }
}
