package com.example.myvhc.models

import android.os.Parcel
import android.os.Parcelable

data class ServiceBookingForm(
    var agencyId: String? = null,
    var customerId: String? = null,
    var vehicleId: String? = null,
    var creationDate: String? = null,
    var serviceDate: String? = null,
    var serviceHours: String? = null,
    var describe: String? = null,
    val status: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
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
        parcel.writeString(agencyId)
        parcel.writeString(customerId)
        parcel.writeString(vehicleId)
        parcel.writeString(creationDate)
        parcel.writeString(serviceDate)
        parcel.writeString(serviceHours)
        parcel.writeString(describe)
        parcel.writeString(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ServiceBookingForm> {
        override fun createFromParcel(parcel: Parcel): ServiceBookingForm {
            return ServiceBookingForm(parcel)
        }

        override fun newArray(size: Int): Array<ServiceBookingForm?> {
            return arrayOfNulls(size)
        }
    }
}


