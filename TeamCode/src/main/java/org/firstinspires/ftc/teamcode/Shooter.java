package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Shooter {
    // Make variables
    private DcMotor ShooterMotor;
    private Servo ServoLeft, ServoRight;

    public void Init(HardwareMap HwMap) {
        // Set variables
        ShooterMotor = HwMap.get(DcMotor.class, "Shooter");
        ServoLeft = HwMap.get(Servo.class, "SL");
        ServoRight = HwMap.get(Servo.class, "SR");

        // Set it to break because when testing it wastes time to wait for it to stop
        ShooterMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void SetPower(double Speed) {
        // Set speed
        ShooterMotor.setPower(Speed);
        ServoLeft.setPosition(Speed);
        ServoRight.setPosition(Speed);
    }
}
