package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Arm {
    public static final int REST = -14;
    public static final int BOTTOM = -100;
    public static final int MIDDLE = -400;
    public static final int TOP = -610;
    private DcMotor armMotor;
    private int upDog;
    private double power;

    public Arm(DcMotor motor) {
        armMotor = motor;
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setPower(.5);
        armMotor.setTargetPosition(BOTTOM);
    }

    public int armTest(){
        armMotor.setPower(.0);
        return armMotor.getCurrentPosition();
    }

    public void setArm(int height, double power) {
        upDog = height;
        this.power = power;
    }

    public void update() {
        armMotor.setPower(power);
        armMotor.setTargetPosition(upDog);
    }

}
