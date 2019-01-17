package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.subsystems.Intake;

public class TeleIntake {
    private Intake intake;
    private Gamepad controller;
    private long time;
    private int count;

    public TeleIntake(Intake in, Gamepad c) {
        intake = in;
        controller = c;
        time = 0;
        count = 0;
    }

    public void update() {
        boolean suck = controller.right_trigger > 0.5;
        boolean spit = controller.left_trigger > 0.5;
        if (suck) {
            intake.setIntakeSpeed(1);
        } else {
            intake.stopAtAngle(30.0);
        }
        if (spit) {
            intake.setIntakeSpeed(-1);
        } else {
            intake.stopAtAngle(30.0);
        }
        if (controller.dpad_down) {
            intake.setIntakePosition(true);
        } else if (controller.dpad_up) {
            intake.setIntakePosition(false);
        }



        if (controller.a) {
            intake.setIntakeSpeed(1);
        } else if (controller.start) {
            intake.setIntakeSpeed(-1);
        } else {
            intake.setIntakeSpeed(0);
            time = 0;
        }
    }


}
