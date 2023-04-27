package com.example.myvhc.models

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Vehicle(
    var vehicleChassisNum: String? = null,
    var vehicleBrand: String? = null,
    var vehicleType: String? = null,
    var vehicleImg: String? = null,
    var vehicleModel: String? = null,
    var vehicleCylinderCap: String? = null,
    var vehiclePrice: String? = null,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(vehicleChassisNum)
        parcel.writeString(vehicleBrand)
        parcel.writeString(vehicleType)
        parcel.writeString(vehicleImg)
        parcel.writeString(vehicleModel)
        parcel.writeString(vehicleCylinderCap)
        parcel.writeString(vehiclePrice)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Vehicle> {
        override fun createFromParcel(parcel: Parcel): Vehicle {
            return Vehicle(parcel)
        }

        override fun newArray(size: Int): Array<Vehicle?> {
            return arrayOfNulls(size)
        }
    }

}
