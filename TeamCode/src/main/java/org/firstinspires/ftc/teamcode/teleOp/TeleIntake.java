package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.subsystems.Intake;

public class TeleIntake {
    private Intake intake;
    private Gamepad controller;
    public TeleIntake(Intake in, Gamepad c) {
        intake = in;
        controller = c;
    }

    public void update() {
        boolean suck = controller.right_trigger > 0.5;
        if (suck) {
            intake.setIntakeSpeed(1);
        } else {
            intake.stopAtAngle(30.0);
        }
        if(controller.dpad_down){
            intake.setIntakePosition(false,true);
        }else if(controller.dpad_up){
            intake.setIntakePosition(true,false);
        }
    }


}
