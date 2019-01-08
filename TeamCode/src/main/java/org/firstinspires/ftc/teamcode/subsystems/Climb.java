package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Climb {
    private DcMotor motor;
    private int topEncoder = 5000;
    private int bottomEncoder = 0;

    public Climb(DcMotor motor) {
        this.motor = motor;
    }

    public boolean setClimb(double power) {
            if (motor.getCurrentPosition() < bottomEncoder && motor.getCurrentPosition() > topEncoder) {
                motor.setPower(power);
                return false;
            } else {
                motor.setPower(0);
                return true;
            }
        }
}
