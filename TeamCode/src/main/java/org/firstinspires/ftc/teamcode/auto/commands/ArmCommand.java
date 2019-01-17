package org.firstinspires.ftc.teamcode.auto.commands;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.auto.ICommand;
import org.firstinspires.ftc.teamcode.subsystems.Arm;

public class ArmCommand implements ICommand {
    private DcMotor armMotor;
    private boolean start;


    public ArmCommand(DcMotor armMotor, boolean start){
        this.armMotor = armMotor;
        this.start =  start;
    }

    @Override
    public boolean runCommand() {
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setTargetPosition(-60);
        armMotor.setPower(.5);
        return true;
    }
}
