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

        if (controller.a)  { //slow down
            direction = Arm.BOTTOM;
            power = .2;
        } else if (controller.b) { //slow up
            direction = Arm.MIDDLE;
            //direction = ARM.TOP
            power = .2;
        } else if (controller.y) { //fast up
            direction = Arm.TOP;
            power = .5;
            //power = 1;
        } else if (controller.x) { //fast down
            direction = Arm.REST;
            power = 0.2
            ;
            //direction = Arm.BOTTOM;
            //power = 1;
        }else if (controller.start){
            arm.resetArm();
            direction = Arm.REST;
            power = 0;
        }
        arm.setArm(direction, power);
        arm.update();
    }
}
