package com.example.midtermpractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.midtermpractice.ui.theme.MidtermPracticeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MidtermPracticeTheme {
                BloodDonorEligibilityApp()
            }
        }
    }
}


@Composable
fun BloodDonorEligibilityApp(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "eligibleForm") {
        composable(route = "eligibleForm") {
            eligibleForm(navController)
        }
        composable(route = "ResultScreen/{result}") {
            bacStackEntry ->
            val result = bacStackEntry.arguments!!.getBoolean("result")
            ResultScreen(navController, result = result)
        }

    }
}

@Composable
fun eligibleForm(navController: NavHostController, modifier: Modifier = Modifier){
    var ageInput by remember { mutableStateOf("") }
    var weightInput by remember { mutableStateOf("") }
    var sleepHourInput by remember { mutableStateOf("") }
    //var healthCondition by remember { mutableStateOf("") }

    val age = ageInput.toInt()
    val weight = weightInput.toInt()
    val sleepHour = sleepHourInput.toInt()

    val result = eligibility(age, weight, sleepHour)

    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Blood Donor Eligibility Form"
        )

        TextField(
            value = ageInput,
            onValueChange = {ageInput = it},
            label = { Text(text = "Age")}
        )

        TextField(
            value = weightInput,
            onValueChange = {weightInput = it},
            label = { Text(text = "Weight (kg)")}
        )

        TextField(
            value = sleepHourInput,
            onValueChange = {sleepHourInput = it},
            label = { Text(text = "Sleep duration (hr)")}
        )

//        TextField(
//            value = healthCondition,
//            onValueChange = {healthCondition = it},
//            label = { Text(text = "Health status (Health/Sick)")}
//        )

        Button(onClick = { navController.navigate("ResultScreen/$result") }) {
            Text(text = "Submit")
        }
    }
}


@Composable
fun ResultScreen(navController: NavHostController, modifier: Modifier = Modifier, result: Boolean){
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = if (result) "Eligible" else "Not Eligible")

        Button(onClick = { navController.navigate("eligibleForm") }) {
            Text(text = "Back")
        }
    }
}



//private fun eligibility (age: Int, weight: Int, sleepHour: Int, healthCondition: String): Boolean {
//
//    val eligibleAge = true
//    val eligibleWeight = true
//    val eligibleSleepHour = true
//    val eligibleHealthStatus = true
//    val result = eligibleAge && eligibleWeight && eligibleSleepHour
//
//    when(age){
//        in 18..60 -> eligibleAge
//        else -> !eligibleAge
//    }
//
//    if (weight >= 45){
//        eligibleWeight
//    } else {
//        !eligibleWeight
//    }
//
//    if (sleepHour > 5){
//        eligibleSleepHour
//    } else {
//        !eligibleSleepHour
//    }
//
////    when(healthCondition){
////        "Healthy" -> eligibleHealthStatus
////        "Sick" -> !eligibleHealthStatus
////    }
//
////    if (eligibleAge == true && eligibleWeight == true && eligibleSleepHour == true && eligibleHealthStatus == true) {
////        result
////    } else {
////        !result
////    }
//
//    return result
//}

private fun eligibility(age: Int, weight: Int, sleepHour: Int): Boolean {
    val eligibleAge = when (age) {
        in 18..60 -> true
        else -> false
    }

    val eligibleWeight = weight >= 45

    val eligibleSleepHour = sleepHour > 5

//    val eligibleHealthStatus = when (healthCondition) {
//        "Healthy" -> true
//        "Sick" -> false
//        else -> false
//    }

    return eligibleAge || eligibleWeight || eligibleSleepHour
}




@Preview(showBackground = true)
@Composable
fun Preview() {
    MidtermPracticeTheme {
        BloodDonorEligibilityApp()
    }
}