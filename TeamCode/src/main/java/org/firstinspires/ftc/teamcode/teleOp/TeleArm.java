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
        double power = .2;

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
            direction = Arm.TOP;
            power = .2;
        }else if (controller.start){
            arm.resetArm();
            direction = 0;
            power = 0;
        }
        arm.setArm(direction, power);
        arm.update();
    }
}
