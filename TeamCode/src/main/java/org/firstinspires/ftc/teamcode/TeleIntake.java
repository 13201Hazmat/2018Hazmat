package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

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
    }


}
