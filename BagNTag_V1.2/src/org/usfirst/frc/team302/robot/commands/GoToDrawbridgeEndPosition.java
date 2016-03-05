package org.usfirst.frc.team302.robot.commands;

import org.usfirst.frc.team302.robot.subsystems.ArmSetpoints;
import org.usfirst.frc.team302.robot.subsystems.SubsystemFactory;

import edu.wpi.first.wpilibj.command.Command;

public class GoToDrawbridgeEndPosition extends Command
{

    public GoToDrawbridgeEndPosition()
    {
        requires(SubsystemFactory.getSubsystemFactory().getArm());
    }

    protected void initialize()
    {
    }

    protected void execute()
    {
        SubsystemFactory.getSubsystemFactory().getArm().setState(ArmSetpoints.ArmState.DRAWBRIDGE_END);
    }

    protected boolean isFinished()
    {
        return SubsystemFactory.getSubsystemFactory().getArm().isAtTargetState(1);
    }

    protected void end()
    {
        SubsystemFactory.getSubsystemFactory().getArm().stopArm();
    }

    protected void interrupted()
    {
        end();
    }

}