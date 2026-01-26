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

        if (gamepad1.right_bumper) {
            gamepad1.rumble(1);
            ToggleShooterSlowness = !ToggleShooterSlowness;
        }

        Forward = -gamepad1.left_stick_y;
        Strafe = gamepad1.left_stick_x;
        Rotate = gamepad1.right_stick_x;

        Drive.DriveFieldRelative(Strafe, Forward, Rotate);

        Shooter.SetShooterPower(gamepad1.a ? ToggleShooterSlowness ? 0.5 : 1 : gamepad1.b ? -1 : 0);
        Shooter.SetServoPower(ServoSpinDirection > 0 ? 1 : ServoSpinDirection < 0 ? -1 : 0);

        if (gamepad1.ps) {
            Drive.ResetIMU();
        }
    }
}
