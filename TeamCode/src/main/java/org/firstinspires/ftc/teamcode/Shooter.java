package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Shooter {
    private DcMotor ShooterMotor;

    public void Init(HardwareMap hwMap) {
        // Set variable
        ShooterMotor = hwMap.get(DcMotor.class, "FR");
    }

    public void SetPower(double Speed) {
        // Set speed
        ShooterMotor.setPower(Speed);
    }
}
