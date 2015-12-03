package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.ftcrobotcontroller.opmodes.Teleop5459;
import com.qualcomm.robotcore.hardware.DcMotorController;
//import com.qualcomm.robotcore.hardware.GyroSensor;

import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.content.Context;

public class GyroAuto extends AutonomousBase5459 {

	public GyroAuto () {}

	GyroSensor gyro;
	float gyro_rotations;

	/*
	List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
	// ^^ to figure out: is it gyro or uncalibrated gyro?
	// if uncalibrated, you'll need to PID.

	private SensorManager mSensorManager;
	private Sensor gyro;
	private static final float NS2S = 1.0f / 1000000000.0f;
	private final float[] deltaRotationVector = new float[4]();
	private float timestamp;
	*/

	start();

	@Override
	public void init() {

		drive_left_front = hardwareMap.dcMotor.get(ml1);
        drive_left_front.setDirection(DcMotor.Direction.REVERSE);
        drive_left_back = hardwareMap.dcMotor.get(ml2);
        drive_left_back.setDirection(DcMotor.Direction.REVERSE);

        drive_right_front = hardwareMap.dcMotor.get(mr1);
        drive_right_back = hardwareMap.dcMotor.get(mr2);

        gyro = hardwareMap.gyroSensor.get("Gyro");
        gyro_rotations = 0;

        /*
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gyro = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
		*/
	}
/*
	public void onSensorChanged(SensorEvent event) {
		if (timestamp != 0) {
			final float dT = (event.timestamp - timestamp) * NS2S;
			float axisX = event.values[0]; //
			float axisY = event.values[1]; // the three rates of rotation
			float axisZ = event.values[2]; //

			float omegaMagnitude = sqrt(axisX*axisX + axisY*axisY + axisZ*axisZ);

			// what is this part doing?
			if (omegaMagnitude > EPSILON) {
				axisX /= omegaMagnitude;
				axisY /= omegaMagnitude;
				axisZ /= omegaMagnitude;
			}

			float thetaOverTwo = omegaMagnitude * dT / 2.0f; // integration
			float sinThetaOverTwo = sin(thetaOverTwo);
			float cosThetaOverTwo = cos(thetaOverTwo);
			deltaRotationVector[0] = sinThetaOverTwo * axisX;
			deltaRotationVector[1] = sinThetaOverTwo * axisY; // what are these three?
			deltaRotationVector[2] = sinThetaOverTwo * axisZ;
			deltaRotationVector[3] = cosThetaOverTwo;
		}
		timestamp = event.timestamp;
		float[] deltaRotationMatrix = new float[9];
		SensorManager.getRotationMatrixFromVector(deltaRotationMatrix, deltaRotationVector);

		//rotationCurrent = rotationCurrent * deltaRotationMatrix;
	}
*/
	@Override
	public void loop() {
		gyro_rotations = gyro.getRotation();

	}
	
}