package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.teleOp.TeleArm;

@TeleOp(name = "Arm Test", group = "Teleop")
public class ArmTest extends LinearOpMode {
    public void runOpMode() {
        DcMotor armMotor = hardwareMap.dcMotor.get("arm_motor");
        Arm arm = new Arm(armMotor);
        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("Arm Postition", arm.armTest());
            telemetry.update();
        }
    }
}
