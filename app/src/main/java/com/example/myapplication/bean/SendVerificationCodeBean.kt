package com.example.myapplication.bean

data class SendVerificationCodeBean(
    val code: Int,
    val `data`: Boolean,
    val message: String
)