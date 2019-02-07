package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.subsystems.Climb;

public class TeleClimb {
    private Climb climb;
    private Gamepad controller;
    private boolean direction;
    private double power;

    public TeleClimb(Climb climber, Gamepad c) {
        climb = climber;
        controller = c;
        direction = false;
        power = 0;
    }

    public void update() {
        if (controller.left_trigger > .5) {
            climb.setPower(-1);
            power = 0;
        } else if (controller.right_trigger > .5) {
            climb.setPower(1);
            power = 0;
        } else {
            if (controller.left_bumper) {
                power = 1;
            } else if (controller.right_bumper) {
                power = -1;
            } else if (controller.back) {
                power = 0;
            }
            climb.setClimb(power);
        }
    }
}
