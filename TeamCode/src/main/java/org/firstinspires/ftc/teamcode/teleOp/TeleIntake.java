package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.subsystems.Intake;

public class TeleIntake {
    private Intake intake;
    private Gamepad controller;
    private long time;

    public TeleIntake(Intake in, Gamepad c) {
        intake = in;
        controller = c;
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
        if (controller.b ){
            time = System.currentTimeMillis();

        } else if (controller.a){
            long time = System.currentTimeMillis();
            while(System.currentTimeMillis()-time<300){
                intake.setIntakeSpeed(-1);
            }

        }
        if(time!=0&&System.currentTimeMillis()-time<300) {
            intake.setIntakeSpeed(1);
        }else {
            intake.setIntakeSpeed(0);
            time=0;
        }
    }


}
