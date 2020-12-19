package com.yilmazvolkan.languagetandem.data.database

import com.yilmazvolkan.languagetandem.models.TandemData

fun DataEntity.toData() = TandemData(
    this.topic,
    this.firstName,
    this.pictureUrl,
    this.natives,
    this.learns,
    this.referenceCnt
)

fun List<DataEntity>.toDataList() = this.map { it.toData() }

fun TandemData.toDataEntity() = DataEntity(
    topic = this.topic,
    firstName = this.firstName,
    pictureUrl = this.pictureUrl,
    natives = this.natives,
    learns = this.learns,
    referenceCnt = this.referenceCnt
)

fun List<TandemData>.toDataEntityList() = this.map { it.toDataEntity() }
