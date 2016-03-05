package org.usfirst.frc.team302.robot.subsystems;

import org.usfirst.frc.team302.robot.subsystems.Arm.ControlStyle;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class will keep track of all of the robot's subsystems. Rather than accessing subsystems directly, you ask the subsystem factory for each of
 * the subsystems you need, and it will give what you ask for.
 * 
 * <p>
 * Here is an example of how to access a subsystem: <i>Drive drive = SubsystemFactory.getSubsystemFactory().getDrive();</i>
 * </p>
 * 
 * 
 * <p>
 * <b>Change Log:</b>
 * </p>
 * 
 * @version version 1: 1/16/2016 -- Derek Witcpalek -- original class
 * @version version 2: 1/17/2016 -- Derek Witcpalek -- added methods to get each specific subsystem, changed <i>getSubsystem</i> method to private,
 *          changed SubsystemName enum to private
 * @version version 3: 1/29/2016 -- Joe Witcpalek -- Refactor Package Name, remove excess subsystems removed getSubsystem method and enum. Added a
 *          sample subsystem.
 * @version version 4: 2/10/2016 -- Joe Witcpalek -- Add getAimShooter (missing from files provided), so everything compiles.
 * @version version 5: 2/13/2016 -- Eldon Maffey -- Add getPunchingServo
 * @version version 6: 2/15/2016 -- Joe Witcpalek -- merged in Adam/Zach's change for the shooter aiming subsystem
 * @version version 7: 2/17/2016 -- Eric Smith -- Add getShifter
 * @version version 8: 3/3/2016 -- Joe Witcpalek -- Add Review Comments
 * 
 * @author Derek Witcpalek
 */

/*
 * Review Comments
 * 1) Not all methods have javadoc
 */
public class SubsystemFactory
{

    // Subsystems
    private Drive m_drive; // Drive
    private Shooter m_shooter; // Shooter
    private AimShooter m_aimShooter;  // Aiming mechanism for shooter
    private Arm m_arm; // Arm
    private PunchingServo m_servo; // Servo
    private Shift m_shifter; // shifter

    // Factory
    private static SubsystemFactory m_factory = null;

    /**
     * Creator -- can not be called by you!!!
     */
    private SubsystemFactory()
    {
        m_drive = null;
        m_shooter = null;
        m_arm = null;
        m_aimShooter = null;
        m_servo = null;
        m_shifter = null;
    }

    /**
     * This is used to get the subsystem factory so that you can get the subsystem you need. You need to call this before you can call getSubsytem().
     * 
     * 
     * @return SubsystemFactory
     */
    public static SubsystemFactory getSubsystemFactory()
    {
        if (m_factory == null)
        {
            m_factory = new SubsystemFactory();
        }

        return m_factory;
    }

    /* ============================================================================== */
    /* THE FOLLOWING METHODS ARE USED BY OUTSIDE CLASSES TO GAIN ACCESS TO SUBSYSTEMS */
    /* ============================================================================== */

    /**
     * Call this method to get the drive subsystem object
     * 
     * @return Drive The robot drive system object
     */
    public Drive getDrive()
    {
        if (m_drive == null)
        {
            m_drive = new Drive();
        }
        return m_drive;
    }

    /**
     * Call this method to get the shooter subsystem object
     * 
     * @return Shooter The robot's shooter object
     */
    public Shooter getShooter()
    {
        if (m_shooter == null)
        {
            m_shooter = new Shooter();
        }
        return m_shooter;
    }

    /**
     * Call this method to get the aiming subsystem object
     * 
     * @return AimShooter The robot's shooter aiming object
     */
    public AimShooter getAimShooter()
    {
        if (m_aimShooter == null)
        {
            m_aimShooter = new AimShooter();
        }
        return m_aimShooter;
    }

    /**
     * Call this method to get the arm subsystem object
     * 
     * @return Arm The robot's arm object
     */
    public Arm getArm()
    {
        if (m_arm == null)
        {
            SmartDashboard.putNumber("p-constant", 0);
            SmartDashboard.putNumber("i-constant", 0);
            SmartDashboard.putNumber("d-constant", 0);

            m_arm = new Arm(ControlStyle.SET_POINT, SmartDashboard.getNumber("p-constant"), SmartDashboard.getNumber("i-constant"),
                    SmartDashboard.getNumber("d-constant"));
        }
        return m_arm;
    }

    /**
     * Call this method to get the punch servo subsystem object
     * 
     * @return Punch servo system object
     */
    public PunchingServo getPunchingServo()
    {
        if (m_servo == null)
        {
            m_servo = new PunchingServo();
        }
        return m_servo;
    }

    public Shift getShifter()
    {
        if (m_shifter == null)
        {
            m_shifter = new Shift();
        }
        return m_shifter;
    }
}
