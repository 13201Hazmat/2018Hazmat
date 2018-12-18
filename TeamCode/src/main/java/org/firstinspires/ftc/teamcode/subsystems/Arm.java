package org.firstinspires.ftc.teamcode.subsystems;

import android.hardware.camera2.CameraManager;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class Arm {
    public static final int REST = 0;
    public static final int BOTTOM = -100;
    public static final int MIDDLE = -400;
    public static final int TOP = -610;
    private DcMotor armMotor;
    private int upDog;
    private double power;
    private TouchSensor sensor;

    public Arm(DcMotor motor, TouchSensor sensor) {
        armMotor = motor;
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setPower(.5);
        armMotor.setTargetPosition(BOTTOM);
        this.sensor = sensor;
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public int armTest() {
        armMotor.setPower(.0);
        return armMotor.getCurrentPosition();
    }

    public void setArm(int height, double power) {
        upDog = height;
        this.power = power;
    }

    public void update() {
        if (sensor.isPressed()) {
            armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        armMotor.setPower(power);
        armMotor.setTargetPosition(upDog);
        if (Math.abs(armMotor.getCurrentPosition() - armMotor.getTargetPosition()) < 10) {
            armMotor.setPower(0);
        }
    }
}
