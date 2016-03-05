package org.usfirst.frc.team302.robot.commands;

import org.usfirst.frc.team302.robot.subsystems.ArmSetpoints;
import org.usfirst.frc.team302.robot.subsystems.SubsystemFactory;

import edu.wpi.first.wpilibj.command.Command;

public class GoToSallyPortEndHookPosition extends Command
{

    public GoToSallyPortEndHookPosition()
    {
        requires(SubsystemFactory.getSubsystemFactory().getArm());
    }

    protected void initialize()
    {
        // possibly stop arm???
    }

    protected void execute()
    {
        SubsystemFactory.getSubsystemFactory().getArm().setState(ArmSetpoints.ArmState.SALLY_PORT_END_HOOK);
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
