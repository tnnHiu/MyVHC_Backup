package com.example.myvhc.models

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class CustomerVehicle(
    var customerId: String? = null,
    var vehicleId: String? = null,
    var licensePlate: String? = null,
    var purchaseDate: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(customerId)
        parcel.writeString(vehicleId)
        parcel.writeString(licensePlate)
        parcel.writeString(purchaseDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CustomerVehicle> {
        override fun createFromParcel(parcel: Parcel): CustomerVehicle {
            return CustomerVehicle(parcel)
        }

        override fun newArray(size: Int): Array<CustomerVehicle?> {
            return arrayOfNulls(size)
        }
    }


}
