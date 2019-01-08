package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Climb {
    private DcMotor motor;
    private int topEncoder = 5000;
    private int bottomEncoder = 0;

    public Climb(DcMotor motor) {
        this.motor = motor;
    }

    public boolean setClimb(boolean direction) {
        if (direction == false) {
            if (motor.getCurrentPosition() < bottomEncoder) {
                motor.setPower(-1);
                return false;
            } else {
                motor.setPower(0);
                return true;
            }
        } else {
            if (motor.getCurrentPosition() > topEncoder) {
                motor.setPower(1);
                return false;
            } else {
                motor.setPower(0);
                return true;
            }
        }
    }
}
