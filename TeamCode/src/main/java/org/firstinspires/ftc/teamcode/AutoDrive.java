package org.firstinspires.ftc.teamcode;

import java.util.ArrayList;
import java.util.Set;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Gyroscope;

public class AutoDrive extends LinearOpMode {


    @Override
    public void runOpMode(){

        telemetry.addData("Status","Init");
        telemetry.update();
        DcMotor FrontLeftMotor = hardwareMap.dcMotor.get("front_left_motor");
        DcMotor BackLeftMotor = hardwareMap.dcMotor.get("back_left_motor");
        DcMotor FrontRightMotor = hardwareMap.dcMotor.get("front_right_motor");
        DcMotor BackRightMotor = hardwareMap.dcMotor.get("back_right_motor");
        DcMotor intakeMotor = hardwareMap.dcMotor.get("intake_motor");
        GyroSensor imu = hardwareMap.get(GyroSensor.class, "imu");
        waitForStart();
        while(opModeIsActive()){
            Drive drive = new Drive(FrontLeftMotor, BackLeftMotor, BackRightMotor, FrontRightMotor);
            ArrayList<ICommand> commands = new ArrayList<ICommand>();
            commands.add(new DriveCommand(drive,10,1));
            commands.add(new TurnCommand(drive,1,90,imu));
            for(int x=0;x<commands.size();x++){
                if(commands.get(x) instanceof DriveCommand) {
                    DriveCommand command = (DriveCommand) commands.get(x);
                    while (command.runCommand()) {}
                }else if(commands.get(x) instanceof TurnCommand){
                    TurnCommand command = (TurnCommand) commands.get(x);
                    while (command.runCommand()){}
                }
            }
        }
    }
}