package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class Arm {
    public static final int REST = 0;
    public static final int BOTTOM = -185;
    public static final int MIDDLE = -300;
    public static final int TOP = -600;
    private DcMotor armMotor;
    private int setPosition;
    private double power;
    private TouchSensor sensor;
    private boolean resetting;

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

    public void resetArm() {
        resetting = true;
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void update() {
        if (resetting) {
            armMotor.setPower(.1);
            if (sensor.isPressed()) {
                resetting = false;
                armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
        } else {
            armMotor.setPower(power);
            armMotor.setTargetPosition(setPosition);
            if (Math.abs(armMotor.getCurrentPosition() - armMotor.getTargetPosition()) < 10) {
                armMotor.setPower(0);
            }
        }
    }
}
