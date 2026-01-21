// https://github.com/FireFlies-Robotics/IntoTheDeep-FtcRobotController/blob/master/TeamCode/src/main/java/org/firstinspires/ftc/teamcode/Wheels.java
// <3

package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Drive {
    private DcMotor FrontLeft, FrontRight, BackLeft, BackRight;
    private IMU Imu; // Gyros used to get the robots rotation
    private final Constants Constants = new Constants();

    public void Init(HardwareMap HwMap) {
        // Retrieve the IMU from the hardware map
        Imu = HwMap.get(IMU.class, Constants.IMU_NAME);

        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));

        Imu.initialize(parameters);

        // Getting the wheel motors and setting them up

        FrontLeft = HwMap.get(DcMotor.class, Constants.FRONT_LEFT_WHEEL_NAME);
        FrontRight = HwMap.get(DcMotor.class, Constants.FRONT_RIGHT_WHEEL_NAME);
        BackLeft = HwMap.get(DcMotor.class, Constants.BACK_LEFT_WHEEL_NAME);
        BackRight = HwMap.get(DcMotor.class, Constants.BACK_RIGHT_WHEEL_NAME);

        FrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        BackLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        FrontLeft.setZeroPowerBehavior(Constants.WHEEL_ZERO_POWER_BEHAVIOUR);
        FrontRight.setZeroPowerBehavior(Constants.WHEEL_ZERO_POWER_BEHAVIOUR);
        BackLeft.setZeroPowerBehavior(Constants.WHEEL_ZERO_POWER_BEHAVIOUR);
        BackRight.setZeroPowerBehavior(Constants.WHEEL_ZERO_POWER_BEHAVIOUR);

        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        FrontLeft.setMode(Constants.WHEEL_RUN_MODE);
        FrontRight.setMode(Constants.WHEEL_RUN_MODE);
        BackLeft.setMode(Constants.WHEEL_RUN_MODE);
        BackRight.setMode(Constants.WHEEL_RUN_MODE);
    }

    public void DriveFieldRelative(double x, double y, double Rotation) {
        double MaxSpeed = Constants.MAX_DRIVE_SPEED;
        double Yaw = Imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS); // Get the Yaw angle of the robot

        // Rotate the movement direction counter to the robot's rotation
        double RotX = x * Math.cos(-Yaw) - y * Math.sin(-Yaw);
        double RotY = x * Math.sin(-Yaw) + y * Math.cos(-Yaw);

        RotX = RotX * 1.1;  // Counteract imperfect strafing

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]

        double Denominator = Math.max(Math.abs(RotY) + Math.abs(RotX) + Math.abs(Rotation), 1);
        double FrontLeftPower = (RotY + RotX + Rotation) / Denominator;
        double BackLeftPower = (RotY - RotX + Rotation) / Denominator;
        double FrontRightPower = (RotY - RotX - Rotation) / Denominator;
        double BackRightPower = (RotY + RotX - Rotation) / Denominator;

        // Move motors.

        FrontLeft.setPower(FrontLeftPower * MaxSpeed);
        BackLeft.setPower(BackLeftPower * MaxSpeed);
        FrontRight.setPower(FrontRightPower * MaxSpeed);
        BackRight.setPower(BackRightPower * MaxSpeed);
    }

    public void ResetIMU() {
        Imu.resetYaw();
    }
}