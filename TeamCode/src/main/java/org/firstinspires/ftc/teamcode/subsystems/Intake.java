package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class Intake{
    private DcMotor intakeMotor;
    private Servo intakeServo;

    public Intake(DcMotor motor, Servo servo) {
        intakeMotor = motor;
        intakeServo = servo;
    }

    public void setIntakePosition(boolean upDog, boolean downDog) {
        double uncertainServoPosition = 0.0;
        if (upDog) {
            intakeServo.setPosition(intakeServo.getPosition()+.01);
        } else if(downDog){
            intakeServo.setPosition(intakeServo.getPosition()-.01);
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