package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.subsystems.Arm;

public class TeleArm {
    private Arm arm;
    private boolean armUp;
    private Gamepad controller;

    public TeleArm(Arm arm, Gamepad c){
        this.arm = arm;
        armUp = false;
        controller = c;
    }

    public void update(){
        arm.setArm(armUp);
        if (controller.b){
            armUp = !armUp;
        }
        arm.update();
    }
}
