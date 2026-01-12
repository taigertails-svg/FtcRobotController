package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shooter {
    private DcMotor ShooterMotor;

    public void Init(HardwareMap hwMap) {
        // Set variable
        ShooterMotor = hwMap.get(DcMotor.class, "Shooter");
    }

    public void SetPower(double Speed) {
        // Set speed
        ShooterMotor.setPower(Speed);
    }
}
