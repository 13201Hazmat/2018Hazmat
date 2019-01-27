package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class Intake {
    private DcMotor intakeMotor;
    private Servo intakeServo;
    private Servo intakeServo2;

    public Intake(DcMotor motor, Servo servo, Servo servo2) {
        intakeMotor = motor;
        intakeServo = servo;
        intakeServo2 = servo2;
    }

    public void setIntakePosition(double left,double right){
        intakeServo.setPosition(left);
        intakeServo2.setPosition(right);
    }
    public void setIntakePosition(boolean down) {
        if (down) {
            //if (intakeServo.getPosition() < .6 && intakeServo2.getPosition() > .4) {
                intakeServo.setPosition(intakeServo.getPosition() + .1);
                intakeServo2.setPosition(intakeServo2.getPosition() - .1);
           // }
        } else {
          // if (intakeServo.getPosition() > .4 && intakeServo2.getPosition() < .6) {
                intakeServo.setPosition(intakeServo.getPosition() - .1);
                intakeServo2.setPosition((intakeServo2.getPosition() + .1));
           // }
        }
    }

    public void setIntakeSpeed(double power) {
        intakeMotor.setPower(power);
    }

    public void stopAtAngle(double angle) {
        int targetPosition = (int) angle * 4;
        if (Math.abs(intakeMotor.getCurrentPosition()) % 1440 >= targetPosition) {
            setIntakeSpeed(0);
        }
    }
}