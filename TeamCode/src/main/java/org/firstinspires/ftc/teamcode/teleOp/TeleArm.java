package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.subsystems.Arm;

public class TeleArm {
    private Arm arm;
    private Gamepad controller;
    private int direction;

    public TeleArm(Arm arm, Gamepad controller) {
        this.arm = arm;
        this.controller = controller;
    }

    public void update() {
        double power = 0;
        if (controller.a) {
            direction = Arm.BOTTOM;
            power = .2;
        } else if (controller.b) {
            direction = Arm.MIDDLE;
            power = .2;
        } else if (controller.y) {
            direction = Arm.TOP;
            power = .5;
        } else if (controller.x) {
            direction = Arm.REST;
            power = .2;
        }
        arm.setArm(direction, power);
        arm.update();
    }
}
