package org.usfirst.frc.team302.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CommandFactory
{
    private static CommandFactory m_factory;

    public CommandFactory()
    {

    }

    public static CommandFactory getCommandFactory()
    {
        if (m_factory == null)
        {
            m_factory = new CommandFactory();
        }

        return m_factory;
    }

    /**
     * getCommand - Get and create the command based on the commandID
     * 
     * @param commandID
     *            = name of the command from the text file
     * @return command = the command that was selected
     */
    public Command getCommand(String commandID)
    {
        Command command = null;
        switch (commandID)
        {
        case "intakeboulder":
            command = new IntakeBoulder();
            break;
        case "shootboulder":
            command = new ShootBoulder();
            break;
        case "stopshooter":
            command = new StopShooter();
            break;
        case "cubedarcade":
            command = new CubedArcadeDrive();
            break;
        case "cubedtank":
            command = new CubedTankDrive();
            break;
        case "cineararcade":
            command = new LinearArcadeDrive();
            break;
        case "lineartank":
            command = new LinearTankDrive();
            break;
        case "chevaldefrisedown":
            command = new GoToChevalDeFriseDownPosition();
            break;
        case "chevaldefrisestart":
            command = new GoToChevalDeFriseStartPosition();
            break;
        case "climbend":
            command = new GoToClimbEndPosition();
            break;
        case "climbhook":
            command = new GoToClimbHookPosition();
            break;
        case "climb":
            command = new GoToClimbPosition();
            break;
        case "drawbridgeend":
            command = new GoToDrawbridgeEndPosition();
            break;
        case "drawbridgehook":
            command = new GoToDrawbridgeHookPosition();
            break;
        case "drawbridgestart":
            command = new GoToDrawbridgeEndPosition();
            break;
        case "extendedtopreparetoclimb":
            command = new GoToExtendedToPrepareToClimbPosition();
            break;
        case "portcullisend":
            command = new GoToPortcullisEndPosition();
            break;
        case "portcullisstart":
            command = new GoToPortcullisStartPosition();
            break;
        case "sallyportendhook":
            command = new GoToSallyPortEndHookPosition();
            break;
        case "sallyportstart":
            command = new GoToSallyPortStartPosition();
            break;
        case "startingposition":
            command = new GoToStartingPosition();
            break;
        case "shiftup":
            command = new ShiftUp();
            break;
        case "shiftdown":
            command = new ShiftDown();
            break;
        case "intakegroup":
            command = new IntakeGroup();
            break;
        case "aimfolded":
            command = new AimFolded();
            break;
        case "highgoalgroup":
            command = new HighGoalGroup();
            break;
        case "lowgoalgroup":
            command = new LowGoalGroup();
            break;
        case "aimneutral":
            command = new AimNeutral();
            break;
        case "dropbouldergroup":
            command = new DropBoulderGroup();
            break;
         case "manualaimshooter":
           command = new ManualAimShooter();
           break;
         case "punch":
        	 command = new Punch();
        	 break;
         case "punchreset":
        	 command = new PunchReset();
        	 break;
        default:
        	SmartDashboard.putString("BadCommandChoice", commandID);
        	command = null;
            break;
        }
        return command;
    }
}
