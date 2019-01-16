package org.firstinspires.ftc.teamcode.auto.commands;

import org.firstinspires.ftc.teamcode.auto.ICommand;
import org.firstinspires.ftc.teamcode.subsystems.Arm;

public class ArmCommand implements ICommand {
    private Arm arm;
    private boolean start;


    public ArmCommand(Arm arm, boolean start){
        this.arm = arm;
        this.start =  start;
    }

    @Override
    public boolean runCommand() {
        if (start) {
            arm.setArm(-60, .5);
        } else {
            arm.setArm(0, .5);
        }
        return true;
    }
}
