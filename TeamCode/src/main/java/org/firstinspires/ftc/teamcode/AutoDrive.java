package org.firstinspires.ftc.teamcode;

import java.util.Set;
import com.qualcomm.hardware.bosch.BNO055IMU;

public class AutoDrive {
    private Drive drive;
    public AutoDrive (Drive d){
        drive = d;
    }

    public void DriveStraight(double power, int distance){
        int currentDistance = 0;
        drive.ResetEncoders();
        while(currentDistance < distance){
            drive.SetLeftMotors(power);
            drive.SetRightMotors(power);
            int LeftDistance = drive.GetLeftEncoders();
            int RightDistance = drive.GetRightEncoders();
            currentDistance = Math.abs((LeftDistance+RightDistance)/2);
        }
        drive.SetLeftMotors(0);
        drive.SetRightMotors(0);
    }
    public void Turn(double power, int angle){
        int currentAngle = 0;
        drive.ResetEncoders();
        if (angle > 360) {
            angle %= 360;
        }
        if(0 < angle && angle < 180) {
            while (currentAngle < angle) {
                drive.SetLeftMotors(power);
                drive.SetRightMotors(-power);
                int LeftAngle = drive.GetLeftEncoders();
                int RightAngle = drive.GetRightEncoders();
                currentAngle = Math.abs((LeftAngle+RightAngle)/2);
            }
        }
        if(180 < angle && angle < 360) {
            while (currentAngle < angle) {
                drive.SetLeftMotors(-power);
                drive.SetRightMotors(power);
                int LeftAngle = drive.GetLeftEncoders();
                int RightAngle = drive.GetRightEncoders();
                currentAngle = Math.abs((LeftAngle+RightAngle)/2);
            }
        }
    }
}
