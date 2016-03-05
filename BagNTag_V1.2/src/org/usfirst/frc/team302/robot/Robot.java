
package org.usfirst.frc.team302.robot;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.usfirst.frc.team302.robot.commands.AutonActions;
import org.usfirst.frc.team302.robot.subsystems.SubsystemFactory;
// Import java classes
import org.usfirst.frc.team302.robot.utilities.AngularMath;
import org.usfirst.frc.team302.robot.utilities.NavXStuffs;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to each mode, as
 * described in the IterativeRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the manifest file in the resource directory.
 */
public class Robot extends IterativeRobot
{
    double rotateToAngleRate;
    double target;

    // things to put Auton choices on Dashboard
    final String defaultAuto = "Default";
    final String customAuto = "My Auto";
    String autoSelected;
    SendableChooser m_autonChooser;

    SendableChooser m_driveMode;
    final String m_arcadeMode = "Arcade Mode";
    final String m_tankMode = "Tank Mode";

    SendableChooser m_driveSensitivity;
    final String m_linearSens = "Linear";
    final String m_cubedSens = "Cubed";

    // things to put Interface choices on Dashboard
    final String defaultInterface = "Interface";
    final String adamInterface = "adamInterface";
    final String ashtynInterface = "ashtynInterface";
    final String derekInterface = "derekInterface";
    final String eldonInterface = "eldonInterface";
    final String ericInterface = "ericInterface";
    final String jeffInterface = "jeffInterface";
    final String joshInterface = "joshInterface";
    final String klaytonInterface = "klaytonInterface";
    final String zachInterface = "zachInterface";
    final String hooverInterface = "hooverInterface";
    final String witsInterface = "witsInterface";

    static double greatestAngle = 0;

    SelectAutonMode sam;
    AutonActions aa;

    String oiSelected;

    SendableChooser m_oiChooser;
    OI m_oi;

    /**
     * This function is run when the robot is first started up and should be used for any initialization code.
     */
    public void robotInit()
    {
        m_oi = OI.getOI();
        sam = new SelectAutonMode();
        aa = new AutonActions(sam);

        // putting auton choices on dashboard
        m_autonChooser = new SendableChooser();
        m_autonChooser.addDefault("Default Auto", defaultAuto);
        m_autonChooser.addObject("My Auto", customAuto);
        SmartDashboard.putData("Auto choices", m_autonChooser);

        // putting drive mode choices on dashboard
        m_driveMode = new SendableChooser();
        m_driveMode.addDefault("Drive Mode", m_arcadeMode);
        m_driveMode.addObject("tank", m_tankMode);
        SmartDashboard.putData("Drive Mode Chooser", m_driveMode);

        // putting drive sensitivity choices on dashboard
        m_driveSensitivity = new SendableChooser();
        m_driveSensitivity.addDefault("Drive Mode", m_linearSens);
        m_driveSensitivity.addObject("tank", m_cubedSens);
        SmartDashboard.putData("Drive Sensitivity Chooser", m_driveSensitivity);

        // putting interface choices on dashboard
        m_oiChooser = new SendableChooser();
        m_oiChooser.addDefault("Default Interface", defaultInterface);
        m_oiChooser.addObject("Adam Interface", adamInterface);
        m_oiChooser.addObject("Ashtyn Interface", ashtynInterface);
        m_oiChooser.addObject("Derek Interface", derekInterface);
        m_oiChooser.addObject("Eldon Interface", eldonInterface);
        m_oiChooser.addObject("Eric Interface", ericInterface);
        m_oiChooser.addObject("Jeff Interface", jeffInterface);
        m_oiChooser.addObject("Josh Interface", joshInterface);
        m_oiChooser.addObject("Klayton Interface", klaytonInterface);
        m_oiChooser.addObject("Zach Interface", zachInterface);
        m_oiChooser.addObject("Mr Hoover Interface", hooverInterface);
        m_oiChooser.addObject("Mr W Interface", witsInterface);
        SmartDashboard.putData("Interfaces", m_oiChooser);

        sam.getPosition();
        sam.getDefenseAction();
        sam.getShootAction();
        sam.getMoveAction();
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select between different autonomous modes using
     * the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
     * remove all of the chooser code and uncomment the getString line to get the auto name from the text box below the
     * Gyro
     *
     * You can add additional auto modes by adding additional comparisons to the switch structure below with additional
     * strings. If using the SendableChooser make sure to add them to the chooser code above as well.
     */
    public void autonomousInit()
    {
        autoSelected = (String) m_autonChooser.getSelected();
        // autoSelected = SmartDashboard.getString("Auto Selector",
        // defaultAuto);
        System.out.println("Auto selected: " + autoSelected);

        /*
         * turn = (int) SmartDashboard.getNumber("Turn number"); target = AngularMath.add(ahrs.getAngle(),
         * AnglesToTurn[turn-1]); SmartDashboard.putNumber("Target", target);
         */
        if (NavXStuffs.getNavX() != null)
        {
        	NavXStuffs.getNavX().reset();
        	NavXStuffs.getNavX().zeroYaw();
            while (NavXStuffs.getNavX().isCalibrating())
            {
                Timer.delay(0.05);
            }
            target = AngularMath.subtract(NavXStuffs.getNavX().getAngle(), 180);
            SmartDashboard.putNumber("Target", target);
        }

    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic()
    {

    }

    /**
     * Initialize teleop period
     */
    public void teleopInit()
    {
        SmartDashboard.putNumber("Potentiometer conversion factor", 1);
        SmartDashboard.putNumber("Potentiometer conversion factor2", 1);

        // using the selection
        oiSelected = (String) m_oiChooser.getSelected();
        m_oi.readFile(oiSelected);
        if (NavXStuffs.getNavX() != null)
        {
        	NavXStuffs.getNavX().reset();
        }
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic()
    {
        if (NavXStuffs.getNavX().isCalibrating())
        {
            SmartDashboard.putBoolean("Resetting", true);
        }
        else
        {
            SmartDashboard.putBoolean("Resetting", false);
        }

        Scheduler.getInstance().run();
        
        SmartDashboard.putNumber("Pitch", NavXStuffs.getNavX().getPitch());
        SmartDashboard.putNumber("Heading", NavXStuffs.getNavX().getYaw());
        SmartDashboard.putNumber("DisplacementX", NavXStuffs.getNavX().getDisplacementX());
        SmartDashboard.putNumber("DisplacementY", NavXStuffs.getNavX().getDisplacementY());
        SmartDashboard.putNumber("DisplacementZ", NavXStuffs.getNavX().getDisplacementZ());

    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic()
    {

    }

    public static void writing(String line, boolean newLine)
    {
        try
        {

            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("/home/admin/" + "Path.csv", true)));

            SmartDashboard.putString("debug", "Opened file");

            char[] charLine = line.toCharArray();
            for (int i = 0; i < (charLine.length - 1); i++)
            {
                out.append(charLine[i]);
            }
            SmartDashboard.putString("debug", "Apended file");
            if (newLine)
                out.append("\r\n");
            else
                out.append(", ");
            out.close();
            SmartDashboard.putString("debug", "New line");
            SmartDashboard.putString("File writing status", "Writing to file...");
        }
        catch (IOException e)
        {
            SmartDashboard.putString("File writing status", "Problem writing to the file " + e.getMessage());
            System.err.println("Problem writing to the file statsTest.txt");
        }
    }

}
