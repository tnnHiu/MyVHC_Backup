package com.example.myvhc.models

data class Agency(
    var agencyId: String? = null,
    var agencyName: String? = null,
    var agencyType: String? = null,
    var agencyPhoneNum: String? = null,
    var agencyAddress: String? = null,
    var agencyLatitude: Double? = null,
    var agencyLongitude: Double? = null,
    var agencyWorkTime: String? = null
)