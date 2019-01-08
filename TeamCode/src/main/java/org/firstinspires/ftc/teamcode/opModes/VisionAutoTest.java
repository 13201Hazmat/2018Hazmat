package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;

import org.firstinspires.ftc.teamcode.auto.commands.DriveCommand;
import org.firstinspires.ftc.teamcode.auto.commands.TurnCommand;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Vision;

@Autonomous(name = "Drive At Wall")
public class VisionAutoTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Vision vision = new Vision();
        vision.init(hardwareMap);

        DcMotor FrontLeftMotor = hardwareMap.dcMotor.get("front_left_motor");
        DcMotor BackLeftMotor = hardwareMap.dcMotor.get("back_left_motor");
        DcMotor FrontRightMotor = hardwareMap.dcMotor.get("front_right_motor");
        DcMotor BackRightMotor = hardwareMap.dcMotor.get("back_right_motor");
        GyroSensor gyro = hardwareMap.gyroSensor.get("gyro");

        Drive drive = new Drive(FrontLeftMotor, BackLeftMotor, BackRightMotor, FrontRightMotor);

        while (opModeIsActive()) {
            vision.scan();

            if (Math.abs(vision.getRobotAngle()) > 2) {
                TurnCommand turn = new TurnCommand(drive, 0.7, vision.getRobotAngle(), gyro);
                turn.runCommand();
            } else if (vision.getRobotX() > 0) {
                DriveCommand driveCommand = new DriveCommand(drive, (int) vision.getRobotX(), 0.5);
            }
        }
    }
}
