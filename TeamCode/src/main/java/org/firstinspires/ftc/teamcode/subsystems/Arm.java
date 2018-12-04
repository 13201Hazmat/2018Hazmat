package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Arm {
    private final int BOTTOM = 0;
    private final int MIDDLE = 0;
    private final int TOP = 0;
    private DcMotor armMotor;
    private boolean upDog;

    public Arm(DcMotor motor) {
        armMotor = motor;
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setPower(.5);
        armMotor.setTargetPosition(BOTTOM);
    }

    public void setArm(boolean direction) {
        upDog = direction;
    }

    public void update() {
        if (upDog) {
            armMotor.setTargetPosition(TOP);
        } else {
            armMotor.setTargetPosition(BOTTOM);
        }
    }

}
