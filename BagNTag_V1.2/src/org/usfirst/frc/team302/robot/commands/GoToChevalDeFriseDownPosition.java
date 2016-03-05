package org.usfirst.frc.team302.robot.commands;

import org.usfirst.frc.team302.robot.subsystems.ArmSetpoints;
import org.usfirst.frc.team302.robot.subsystems.SubsystemFactory;

import edu.wpi.first.wpilibj.command.Command;

public class GoToChevalDeFriseDownPosition extends Command
{

    public GoToChevalDeFriseDownPosition()
    {
        requires(SubsystemFactory.getSubsystemFactory().getArm());
    }

    protected void initialize()
    {

    }

    protected void execute()
    {
        SubsystemFactory.getSubsystemFactory().getArm().setState(ArmSetpoints.ArmState.CHEVAL_DE_FRISE_DOWN);
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
