package org.firstinspires.ftc.teamcode.auto.commands;

import org.firstinspires.ftc.teamcode.auto.ICommand;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

public class IntakeCommand implements ICommand {
    private Intake intake;
    private double power;
    private boolean position;

    public IntakeCommand(Intake intake, double power, boolean position) {
        this.intake = intake;
        this.power = power;
        this.position = position;
    }

    @Override
    public boolean runCommand() {
        intake.setIntakePosition(position);
        intake.setIntakeSpeed(power);
        return true;
    }
}
