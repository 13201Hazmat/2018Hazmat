package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.subsystems.Arm;

public class TeleArm {
    private Arm arm;
    private Gamepad controller;
    private int upDog;

    public TeleArm(Arm arm, Gamepad controller){
        this.arm = arm;
        this.controller = controller;
    }

    public void update(){
        double power=0;
        if (controller.a){
            upDog = Arm.BOTTOM;
            power=.25;
        }else if(controller.b){
            upDog = Arm.MIDDLE;
            power=.25;
        }else if(controller.y){
            upDog = Arm.TOP;
            power=1;
        }else if(controller.x){
            upDog = Arm.REST;
            power=.25;
        }
        arm.setArm(upDog,power);
        arm.update();
    }
}
