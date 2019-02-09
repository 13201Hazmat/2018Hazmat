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
        if (controller.dpad_down) {
            intake.setIntakePosition(true);
        } else if (controller.dpad_up) {
            intake.setIntakePosition(false);
        }
        if (controller.a || controller.right_trigger > .5) {
            if (controller.a) {
                intake.setIntakeSpeed(.5);
            } else {
                intake.setIntakeSpeed(1);
            }
        } else if (controller.start || controller.left_trigger > .5) {
            if (controller.start) {
                intake.setIntakeSpeed(-.5);
            } else {
                intake.setIntakeSpeed(-1);
            }
        } else {
            intake.setIntakeSpeed(0);
            time = 0;
        }
        if (controller.right_trigger > .5) {
            intake.setIntakeSpeed(0.5);
        }
    }


}
