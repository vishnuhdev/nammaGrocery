package com.example.firstcomposeapp

sealed class Screen (val route: String){
    object LoginWithMobile : Screen(route = "login_with_mobile")
    object LoginWithEmail : Screen(route = "login_with_email")
    object SignUp : Screen(route = "sign_up_screen")
    object OtpScreen : Screen(route = "otp_screen")
    object home : Screen(route = "Home_screen")
}
