package com.yilmazvolkan.languagetandem.data.database

import com.yilmazvolkan.languagetandem.models.TandemData

fun TandemDataEntity.toData() = TandemData(
    this.topic,
    this.firstName,
    this.pictureUrl,
    this.natives,
    this.learns,
    this.referenceCnt
)

fun List<TandemDataEntity>.toDataList() = this.map { it.toData() }

fun TandemData.toDataEntity() = TandemDataEntity(
    topic = this.topic,
    firstName = this.firstName,
    pictureUrl = this.pictureUrl,
    natives = this.natives,
    learns = this.learns,
    referenceCnt = this.referenceCnt
)

fun List<TandemData>.toDataEntityList() = this.map { it.toDataEntity() }
