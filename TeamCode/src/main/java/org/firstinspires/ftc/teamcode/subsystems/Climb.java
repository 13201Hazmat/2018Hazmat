package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Climb {
    private DcMotor motor;
    private boolean direction = false;

    public Climb(DcMotor motor) {
        this.motor = motor;
    }
    public void setClimb() {
        if (direction == false) {
            motor.setPower(-0.5);
        }
        if (direction == true) {
            motor.setPower(0.5);
        }
    }
}
