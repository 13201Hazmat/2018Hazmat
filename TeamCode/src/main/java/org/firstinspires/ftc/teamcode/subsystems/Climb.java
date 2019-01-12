package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Climb {
    private DcMotor motor;
    private int topEncoder = -25000;
    private int bottomEncoder = -100;

    public Climb(DcMotor motor) {
        this.motor = motor;
        this.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public boolean setClimb(double power) {
        if (power < 0 && motor.getCurrentPosition() >= topEncoder) {
            motor.setPower(power);
            return false;
        } else if (power > 0 && motor.getCurrentPosition() <= bottomEncoder) {
            motor.setPower(power);
            return false;
        } else {
            motor.setPower(0);
            return true;
        }
    }

    public void setPower(double power){
        motor.setPower(power);
    }
}
