package org.firstinspires.ftc.teamcode.subsystems;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Drive {
    private DcMotor FrontLeftMotor;
    private DcMotor BackLeftMotor;
    private DcMotor FrontRightMotor;
    private DcMotor BackRightMotor;
    public Drive(DcMotor FrontLeftM, DcMotor BackLeftM, DcMotor BackRightM, DcMotor FrontRightM){
        FrontLeftMotor = FrontLeftM;
        BackLeftMotor = BackLeftM;
        FrontRightMotor = FrontRightM;
        BackRightMotor = BackRightM;
    }

    public static void reset(Drive drive) {
        drive.SetLeftMotors(0);
        drive.SetRightMotors(0);
        drive.ResetEncoders();
    }

    public void SetLeftMotors(double power){
        FrontLeftMotor.setPower(power);
        BackLeftMotor.setPower(power);
    }
    public void SetRightMotors(double power){
        FrontRightMotor.setPower(power);
        BackRightMotor.setPower(power);
    }
    public int GetLeftEncoders(){
        int frontVal = FrontLeftMotor.getCurrentPosition();
        int backVal = BackLeftMotor.getCurrentPosition();
        int val = (frontVal+backVal)/2;
        return val;
    }
    public int GetRightEncoders(){
        int frontVal = FrontRightMotor.getCurrentPosition();
        int backVal = BackRightMotor.getCurrentPosition();
        int val = (frontVal+backVal)/2;
        return val;
    }
    public void ResetEncoders(){
        FrontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FrontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}