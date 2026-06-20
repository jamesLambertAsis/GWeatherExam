package com.example.gweather.presentation.screens.login

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gweather.R
import com.example.gweather.data.local.entity.UserEntity
import com.example.gweather.domain.model.User
import com.example.gweather.encodeToBase64
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = koinViewModel(),
    onSuccessLogin: () -> Unit
) {

    val state by loginViewModel.state.collectAsStateWithLifecycle()
    val currentState = state
    val context = LocalContext.current
    val password = remember {
        mutableStateOf("")
    }
    val user = remember {
        mutableStateOf("")
    }
    val askGpsEnable = remember {
        mutableStateOf(false)
    }

    val hasPermission = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->

    }

    if (askGpsEnable.value) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Enable Location") },
            text = { Text("Please turn on location services to use this feature.") },
            confirmButton = {
                Button(onClick = {
                    context.startActivity(
                        Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    )
                    askGpsEnable.value = false
                }) {
                    Text("Go to Settings")
                }
            }
        )
    }

    Box(Modifier
        .fillMaxSize()
        .background(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color(0xFF2196F3),
                    Color(0xFF0D47A1),
                    Color(0xFF1F31FF)
                )
            )
        ),
        contentAlignment = Alignment.Center
    ){

        Column(
            Modifier
                .fillMaxWidth(.9f)
                .clip(RoundedCornerShape(5))
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(20.dp))
            Text(
                text = stringResource(R.string.welcome_gweather),
                color = Color.Black
            )
            Spacer(Modifier.height(20.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(.9f),
                value = user.value,
                onValueChange = {
                    user.value = it.take(15)
                },
                singleLine = true,
                placeholder = { Text(text = stringResource(R.string.enter_username)) },
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(R.drawable.ic_account),
                        contentDescription = null,
                        tint = Color.Black,
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    disabledTextColor = Color.Black,
                    disabledBorderColor = Color.Black,
                    focusedPlaceholderColor = Color.Gray,
                    unfocusedPlaceholderColor = Color.Gray
                ),
            )
            Spacer(Modifier.height(20.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(.9f),
                value = password.value,
                onValueChange = {
                    password.value = it.take(15)
                },
                singleLine = true,
                placeholder = { Text(text = stringResource(R.string.enter_password)) },
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(R.drawable.ic_lock),
                        contentDescription = null,
                        tint = Color.Black,
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    disabledTextColor = Color.Black,
                    disabledBorderColor = Color.Black,
                    focusedPlaceholderColor = Color.Gray,
                    unfocusedPlaceholderColor = Color.Gray
                ),
            )
            Spacer(Modifier.height(20.dp))
            Row(
                Modifier.fillMaxWidth(.9f),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier
                        .clickable() {
                            loginViewModel.onEvent(
                                LoginEvent.Register(
                                    User(
                                        userName = user.value,
                                        password = password.value.encodeToBase64()
                                    )
                                )
                            )
                        }
                        .clip(RoundedCornerShape(10))
                ) {
                    Text(
                        text = stringResource(R.string.register),
                        color = Color.Black
                    )
                }
                Spacer(Modifier.width(20.dp))
                Box(
                    Modifier
                        .clickable() {
                            loginViewModel.onEvent(
                                LoginEvent.Login(
                                    User(
                                        userName = user.value,
                                        password = password.value.encodeToBase64()
                                    )
                                )
                            )
                        }
                        .clip(RoundedCornerShape(10))
                        .background(Color(0xFF0D47A1))
                ) {
                    Text(
                        text = stringResource(R.string.login),
                        modifier = Modifier.padding(vertical = 3.dp, horizontal = 10.dp)
                    )
                }
            }
            Spacer(Modifier.height(20.dp))
        }
    }

    when (currentState){
        is LoginScreenState.Failed -> {
            Toast.makeText(context, currentState.msg, Toast.LENGTH_SHORT).show()
            loginViewModel.onEvent(event = LoginEvent.ResetState)
        }
        LoginScreenState.Idle -> Unit
        LoginScreenState.Loading -> {
            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        LoginScreenState.SuccessLogin -> {
            if (isLocationEnabled(context).not()) {
                askGpsEnable.value = true
                return
            }
            if (hasPermission.not()) {
                permissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                return
            }
            onSuccessLogin()
        }
    }
}

private fun isLocationEnabled(context: Context): Boolean {
    val locationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
}