package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class Main extends OpMode {
    // Make variables
    Drive Drive = new Drive();
    Shooter Shooter = new Shooter();

    double Forward, Strafe, Rotate;

    boolean ToggleShooterSlowness = false;
    boolean ToggleDriveSlowness = false;

    // Initialize driving
    @Override
    public void init() {
        Drive.Init(hardwareMap);
        Shooter.Init(hardwareMap);
    }

    // Initialize gamepad controls
    @Override
    public void loop() {
        final double ServoSpinDirection = gamepad1.left_trigger - gamepad1.right_trigger;

        ToggleShooterSlowness = (!gamepad1.right_bumper || !ToggleShooterSlowness) && (gamepad1.left_bumper || ToggleShooterSlowness);
        ToggleDriveSlowness = gamepad1.left_stick_button || gamepad1.right_stick_button;

        telemetry.addData("Drive Slowness", ToggleDriveSlowness);
        telemetry.addData("Shooter Slowness", ToggleShooterSlowness);
        telemetry.addData("Current Max Shooter Power", Shooter.MaxShooterPower);

        final double Aqua = ToggleDriveSlowness ? 0.15 : 1; // Aqua is useful!

        Forward = -gamepad1.left_stick_y * Aqua;
        Strafe = gamepad1.left_stick_x * Aqua;
        Rotate = gamepad1.right_stick_x * Aqua;

        Drive.DriveFieldRelative(Strafe, Forward, Rotate);

        Shooter.SetShooterPower(gamepad1.a ? ToggleShooterSlowness ? 0.5 : 1 : gamepad1.b ? -1 : 0);
        Shooter.SetServoPower(ServoSpinDirection > 0 ? 1 : ServoSpinDirection < 0 ? -1 : 0);
        Shooter.MaxShooterPower = gamepad1.dpadDownWasReleased() ? Shooter.MaxShooterPower - 0.1 : gamepad1.dpadUpWasReleased() ? Shooter.MaxShooterPower + 0.1 : Shooter.MaxShooterPower;

        if (gamepad1.ps) {
            Drive.ResetIMU();
        }
    }
}
