package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class Main extends OpMode {
    Drive Drive = new Drive();

    double Forward, Strafe, Rotate;

    // Initialize driving
    @Override
    public void init() {
        Drive.Init(hardwareMap);
    }

    // Initialize gamepad controls
    @Override
    public void loop() {
        Forward = gamepad1.left_stick_y;
        Strafe = -gamepad1.left_stick_x;
        Rotate = -gamepad1.right_stick_x;

        Drive.DriveFieldRelative(Forward,Strafe,Rotate);

        if (gamepad1.ps) {
            Drive.ResetIMU();
        }
    }
}
