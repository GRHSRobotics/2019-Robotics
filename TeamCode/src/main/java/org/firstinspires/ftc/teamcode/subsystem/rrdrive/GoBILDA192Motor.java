package org.firstinspires.ftc.teamcode.subsystem.rrdrive;


import com.qualcomm.robotcore.hardware.configuration.DistributorInfo;
import com.qualcomm.robotcore.hardware.configuration.annotations.DeviceProperties;
import com.qualcomm.robotcore.hardware.configuration.annotations.MotorType;

import org.firstinspires.ftc.robotcore.external.navigation.Rotation;

@MotorType(ticksPerRev=537.6, gearing=19.2, maxRPM=312, orientation= Rotation.CCW)
@DeviceProperties(xmlTag="goBILDA192Motor", name="GoBILDA 5202 series 19.2:1", builtIn = false)
@DistributorInfo(distributor="goBILDA_distributor", model="goBILDA-5202", url="https://www.gobilda.com/5202-series-yellow-jacket-planetary-gear-motors/")
public interface GoBILDA192Motor {
}
