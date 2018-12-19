package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.subsystems.Climb;

public class TeleClimb {
    private Climb climb;
    private Gamepad controller;
    private boolean direction;

    public TeleClimb(Climb climber, Gamepad c) {
        climb = climber;
        controller = c;
    }

    public void update() {
        if (controller.left_bumper) {
            direction = true;
        }
        climb.setClimb(direction);
    }
}
