package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp(name="ChassisIntakeTest", group="Teleop")
public class ChassisIntakeTest extends OpMode{
    private Drive drive;
    private TeleDrive teleDrive;
    private Intake intake;
    private DcMotor intakeR;
    private DcMotor intakeL;

    public void init() {
        DcMotor fl = hardwareMap.dcMotor.get("fl");
        DcMotor bl = hardwareMap.dcMotor.get("fl");
        DcMotor br = hardwareMap.dcMotor.get("fr");
        DcMotor fr = hardwareMap.dcMotor.get("fr");

        intakeR = hardwareMap.dcMotor.get("intakeR");
        intakeL = hardwareMap.dcMotor.get("intakeL");

        drive = new Drive(fl,bl,br,fr);
        teleDrive = new TeleDrive(drive, gamepad1);
        intake = new Intake(drive, gamepad1, intakeR, intakeL);
    }
    public void loop() {
        teleDrive.Update();
        intake.Update();
    }
}
