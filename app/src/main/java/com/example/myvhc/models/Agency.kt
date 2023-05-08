package com.example.myvhc.models

import android.os.Parcel
import android.os.Parcelable

data class Agency(
    var agencyId: String? = null,
    var agencyName: String? = null,
    var agencyType: String? = null,
    var agencyPhoneNum: String? = null,
    var agencyAddress: String? = null,
    var agencyLatitude: Double? = null,
    var agencyLongitude: Double? = null,
    var agencyWorkTime: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(agencyId)
        parcel.writeString(agencyName)
        parcel.writeString(agencyType)
        parcel.writeString(agencyPhoneNum)
        parcel.writeString(agencyAddress)
        parcel.writeValue(agencyLatitude)
        parcel.writeValue(agencyLongitude)
        parcel.writeString(agencyWorkTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Agency> {
        override fun createFromParcel(parcel: Parcel): Agency {
            return Agency(parcel)
        }

        override fun newArray(size: Int): Array<Agency?> {
            return arrayOfNulls(size)
        }
    }
}