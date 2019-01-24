package org.firstinspires.ftc.teamcode.opModes;

import java.util.ArrayList;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.auto.commands.ClimbCommand;
import org.firstinspires.ftc.teamcode.subsystems.Climb;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.auto.ICommand;
import org.firstinspires.ftc.teamcode.auto.commands.DriveCommand;
import org.firstinspires.ftc.teamcode.auto.commands.TurnCommand;

@Autonomous
public class AutoPath1 extends OpMode {
    private ArrayList<ICommand> commands;
    private int currentIndex;
    private Drive drive;
    private Climb climber;
    private boolean climbed;
    private ClimbCommand climbingCommand;
    BNO055IMU imu;

    @Override
    public void init() {

        DcMotor FrontLeftMotor = hardwareMap.dcMotor.get("front_left_motor");
        DcMotor BackLeftMotor = hardwareMap.dcMotor.get("back_left_motor");
        DcMotor FrontRightMotor = hardwareMap.dcMotor.get("front_right_motor");
        DcMotor BackRightMotor = hardwareMap.dcMotor.get("back_right_motor");
        DcMotor intakeMotor = hardwareMap.dcMotor.get("intake_motor");
        DcMotor climberMotor = hardwareMap.dcMotor.get("lift_motor");
        TouchSensor sensor = hardwareMap.touchSensor.get("touch_sensor");

        //
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        //
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        drive = new Drive(FrontLeftMotor, BackLeftMotor, BackRightMotor, FrontRightMotor);
        Drive.reset(drive);
        climber = new Climb(climberMotor);

        commands = new ArrayList<ICommand>();
        commands.add(new ClimbCommand(climber, true));
        commands.add(new DriveCommand(drive, 100, 1));
        commands.add(new TurnCommand(drive, 1, -1, imu));
        commands.add(new DriveCommand(drive, 100, -1));

        climbingCommand = new ClimbCommand(climber, false);
        currentIndex = 0;
        climbed = false;
        telemetry.addData("Status", "Init");
        telemetry.update();
    }

    @Override
    public void loop() {
        telemetry.addData("Left Encoders: ", drive.GetLeftEncoders());
        telemetry.addData("Right Encoders: ", drive.GetRightEncoders());
        telemetry.addData("Angle: ", imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES));
        telemetry.update();
        if (currentIndex > 0 && !climbed) {
            climbed = !climbingCommand.runCommand();
        }
        if (currentIndex < commands.size()&& gamepad1.a) {
            if (commands.get(currentIndex).runCommand() && gamepad1.b) {
                currentIndex++;
                Drive.reset(drive);

            }
        }
    }
}
