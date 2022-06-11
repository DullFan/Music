package com.example.wonder.bean

data class SendVerificationCodeBean(
    val code: Int,
    val `data`: Boolean,
    val message: String
)