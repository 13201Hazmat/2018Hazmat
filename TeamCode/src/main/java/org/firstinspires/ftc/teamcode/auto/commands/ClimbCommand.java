package org.firstinspires.ftc.teamcode.auto.commands;

import org.firstinspires.ftc.teamcode.auto.ICommand;
import org.firstinspires.ftc.teamcode.subsystems.Climb;

public class ClimbCommand implements ICommand {
    private Climb climb;
    private boolean direction;

    public ClimbCommand(Climb climber, boolean direction) {
        climb = climber;
        this.direction = direction;
    }

    @Override
    public boolean runCommand() {
        if (direction) {
            return climb.setClimb(1);
        } else {
            return climb.setClimb(-1);
        }
    }
}
