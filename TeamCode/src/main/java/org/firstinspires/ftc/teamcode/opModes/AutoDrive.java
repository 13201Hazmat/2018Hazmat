package org.firstinspires.ftc.teamcode.opModes;

import java.util.ArrayList;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;

import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.auto.ICommand;
import org.firstinspires.ftc.teamcode.auto.commands.DriveCommand;
import org.firstinspires.ftc.teamcode.auto.commands.TurnCommand;

@Autonomous
public class AutoDrive extends OpMode {
    private  ArrayList<ICommand> commands;

    @Override
    public void init() {
        telemetry.addData("Status", "Init");
        telemetry.update();
        DcMotor FrontLeftMotor = hardwareMap.dcMotor.get("front_left_motor");
        DcMotor BackLeftMotor = hardwareMap.dcMotor.get("back_left_motor");
        DcMotor FrontRightMotor = hardwareMap.dcMotor.get("front_right_motor");
        DcMotor BackRightMotor = hardwareMap.dcMotor.get("back_right_motor");
        DcMotor intakeMotor = hardwareMap.dcMotor.get("intake_motor");
        BNO055IMU imu = hardwareMap.get(BNO055IMU.class, "imu");
        Drive drive = new Drive(FrontLeftMotor, BackLeftMotor, BackRightMotor, FrontRightMotor);
        commands = new ArrayList<ICommand>();
        commands.add(new DriveCommand(drive, 10, 1));
        //commands.add(new TurnCommand(drive, 1, 90, imu));
    }

    @Override
    public void loop() {
        for (int i = 0; i < commands.size(); i++) {
            while(commands.get(i).runCommand()){
            }
        }
    }
}
