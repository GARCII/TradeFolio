package com.portfolio.tracker.model

data class AssetResultResponse(
    val code: Int,
    val mgs: String,
    val snapshotVos: List<SnapshotVos>
)

data class SnapshotVos(
    //AccountType enum
    val type: String,
    val data: SnapshotData
)

data class SnapshotData(
    val balances: List<Balance>
)

data class Balance(
    val asset: String,
    val free: String,
    val locked: String
)

