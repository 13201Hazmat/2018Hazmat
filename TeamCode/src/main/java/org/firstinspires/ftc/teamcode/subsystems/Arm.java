package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class Arm {
    public static final int REST = 0;
    public static final int BOTTOM = -100;
    public static final int MIDDLE = -300;
    public static final int TOP = -550;
    private DcMotor armMotor;
    private int setPosition;
    private double power;
    private TouchSensor sensor;

    public Arm(DcMotor motor, TouchSensor sensor) {
        armMotor = motor;
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setTargetPosition(REST);
        this.sensor = sensor;
        resetArm();
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public int armTest() {
        armMotor.setPower(.0);
        return armMotor.getCurrentPosition();
    }

    public void setArm(int height, double power) {
        setPosition = height;
        this.power = power;
    }

    public void resetArm(){
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        while(!sensor.isPressed()){
        armMotor.setPower(.1);
    }
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
}

    public void update() {
        /*if (sensor.isPressed()) {
            armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }*/
        armMotor.setPower(power);
        armMotor.setTargetPosition(setPosition);
        if (Math.abs(armMotor.getCurrentPosition() - armMotor.getTargetPosition()) < 10) {
            armMotor.setPower(0);
        }
    }
}
