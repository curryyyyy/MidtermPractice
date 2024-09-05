package com.example.midtermpractice

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.platform.LocalContext
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
            val result = bacStackEntry.arguments?.getString("result") ?: "Null"
            ResultScreen(navController, result = result)
        }

    }
}

@Composable
fun eligibleForm(navController: NavHostController, modifier: Modifier = Modifier){
    var ageInput by remember { mutableStateOf("") }
    var weightInput by remember { mutableStateOf("") }
    var sleepHourInput by remember { mutableStateOf("") }
    var healthCondition by remember { mutableStateOf("") }

    val age = ageInput.toIntOrNull() ?: 0
    val weight = weightInput.toIntOrNull() ?: 0
    val sleepHour = sleepHourInput.toIntOrNull() ?: 0


    val result = eligibility(age, weight, sleepHour, healthCondition)

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

        TextField(
            value = healthCondition,
            onValueChange = {healthCondition = it},
            label = { Text(text = "Health status (Healthy/Sick)")}
        )

        Button(onClick = { navController.navigate("ResultScreen/$result") }) {
            Text(text = "Submit")
        }

        Text(text = age.toString() )
    }


}


@Composable
fun ResultScreen(navController: NavHostController, modifier: Modifier = Modifier, result: String){
    val context = LocalContext.current
    
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        //text(text = if (result) "Eligible" else "Not Eligible")
        Text(text = result)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "For more information please click the link.")

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "https://google.com",
            modifier = Modifier.clickable {
                val intent = Intent (Intent.ACTION_VIEW, Uri.parse("https://google.com"))
                context.startActivity(intent)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("eligibleForm") }) {
            Text(text = "Back")
        }
    }
}



private fun eligibility (age: Int, weight: Int, sleepHour: Int, healthCondition: String): String {

    val eligibleAge: Boolean
    val eligibleWeight: Boolean
    val eligibleSleepHour: Boolean
    val eligibleHealthStatus: Boolean
    //val result = true
    var comment: String

    when(age){
        in 18..60 -> eligibleAge = true
        else -> eligibleAge = false
    }

    if (weight >= 45){
        eligibleWeight = true
    } else {
        eligibleWeight = false
    }

    if (sleepHour > 5){
        eligibleSleepHour = true
    } else {
        eligibleSleepHour = false
    }

    when(healthCondition){
        "Healthy" -> eligibleHealthStatus = true
        else -> eligibleHealthStatus = false
    }

    if (eligibleAge && eligibleWeight && eligibleSleepHour && eligibleHealthStatus) {
        comment = "Eligible"
    } else {
        comment = "Not Eligible"
    }

    return comment
}

//private fun eligibility(age: Int, weight: Int, sleepHour: Int) {
//    val eligibleAge = when (age) {
//        in 18..60 -> true
//        else -> false
//    }
//
//    val eligibleWeight = weight >= 45
//
//    val eligibleSleepHour = sleepHour > 5
//
////    val eligibleHealthStatus = when (healthCondition) {
////        "Healthy" -> true
////        "Sick" -> false
////        else -> false
////    }
//
//    if(eligibleAge && eligibleWeight && eligibleSleepHour){
//        println("Eligible")
//    }else {
//        println("Not Eligible")
//    }
//}




@Preview(showBackground = true)
@Composable
fun Preview() {
    MidtermPracticeTheme {
        BloodDonorEligibilityApp()
    }
}