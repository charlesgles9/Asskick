package com.example.asskick

import android.app.Activity
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.example.asskick.ui.theme.AssKickTheme
import com.example.asskick.ui.theme.Typography

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AssKickTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                   UI(this)
                }
            }
        }
    }
}

@Composable
fun UI(context:Activity){
    Scaffold (topBar = {

        TopAppBar(title = { Text(text = "Ass Kick")})

    }){

        purchaseUI(context)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun purchaseUI(context: Activity){

    val state= rememberScrollState()
    BoxWithConstraints(modifier= Modifier
        .fillMaxSize()
        .verticalScroll(state, enabled = true)) {
        val constraints = ConstraintSet {
            val title = createRefFor("title")
            val icon = createRefFor("icon")
            val payBill = createRefFor("payBill")
            val accountName = createRefFor("account")
            val accountBalance = createRefFor("accountBalance")
            val amount = createRefFor("amount")
            val kick = createRefFor("kick")
            val message = createRefFor("message")
            val messageAction = createRefFor("messageAction")
            constrain(message) {
                bottom.linkTo(messageAction.top)
                centerHorizontallyTo(parent)
            }
            constrain(kick) {
                centerHorizontallyTo(parent)
            }

            constrain(messageAction) {
                centerHorizontallyTo(message)
                bottom.linkTo(kick.top)
            }
            constrain(accountBalance) {
                bottom.linkTo(message.top)
            }
            constrain(amount) {
                bottom.linkTo(message.top)
                start.linkTo(accountBalance.end)
            }
            constrain(accountName) {
                bottom.linkTo(accountBalance.top)
            }
            constrain(payBill) {
                bottom.linkTo(accountName.top)
            }

            constrain(title) {
                bottom.linkTo(payBill.top)
                centerHorizontallyTo(parent)
            }
            constrain(icon) {
                bottom.linkTo(title.top)
                centerHorizontallyTo(parent)
            }


        }
        ConstraintLayout(
            constraints, modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {

            val mpesaMessge="PKX76UZO Confimed. Ksh40.00 sent to KENA RAILWAYS for account" +
                    " MWIKI on 03/9/2022 at 08:57 PM New M-PESA balance is Ksh 360.69 Transaction cost, Ksh0.00 Amount you can transact within the day" +
                    "is 298,920.00. Separate personal and business funds through Pochi la briashara on *334#."
            var msg by remember { mutableStateOf(" ") }
            var accountBalance by remember { mutableStateOf("400") }
            var amount by remember { mutableStateOf("40.00") }
            var accountName by remember { mutableStateOf("Mwiki") }
            var payBill by remember { mutableStateOf("KENYA RAILWAYS") }
            var visible by remember { mutableStateOf(false) }
            Button(
                onClick = {
                    visible = !visible
                    msg=mpesaMessge
                    },
                modifier = Modifier
                    .layoutId("kick")
                    .fillMaxWidth(0.9f)
            ) {
                Text(text = "kick")

            }

            OutlinedTextField(value = accountBalance,
                onValueChange = { text -> accountBalance = text },
                modifier = Modifier
                    .layoutId("accountBalance")
                    .padding(top = 2.dp, bottom = 30.dp, start = 5.dp, end = 5.dp)
                    .fillMaxWidth(0.4f),
                label = { Text(text = "Balance KSH") })

            OutlinedTextField(value = amount,
                onValueChange = { text -> amount = text },
                modifier = Modifier
                    .layoutId("amount")
                    .padding(top = 5.dp, bottom = 30.dp, start = 5.dp, end = 5.dp)
                    .fillMaxWidth(0.5f),
                label = { Text(text = "Amount kSH") })

            OutlinedTextField(value = accountName,
                onValueChange = { text -> accountName = text },
                modifier = Modifier
                    .layoutId("account")
                    .padding(2.dp)
                    .fillMaxWidth(),
                label = { Text(text = "Account") })

            OutlinedTextField(value = payBill,
                onValueChange = { text -> payBill = text },
                modifier = Modifier
                    .layoutId("payBill")
                    .padding(2.dp)
                    .fillMaxWidth(),
                label = { Text(text = "PayBill") })

            Text(
                text = "Ass Kick", modifier = Modifier.layoutId("title"),
                color = com.example.asskick.ui.theme.Teal200,
                style = Typography.h4
            )

            Image(
                painter = painterResource(R.mipmap.ic_launcher),
                contentDescription = "Icon", modifier = Modifier
                    .layoutId("icon")
                    .size(70.dp)
            )

            OutlinedTextField(value = msg, enabled = visible,
                onValueChange = { text -> msg = text },
                modifier = Modifier
                    .layoutId("message")
                    .padding(10.dp)
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            context.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                        } else {
                            context.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
                            context.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
                        }

                    }
                    .alpha(if (visible) 1f else 0f),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Default),
                label = { Text(text = "message") })
            Row(
                modifier = Modifier
                    .layoutId("messageAction")
                    .padding(bottom = 15.dp)
                    .alpha(if (visible) 1f else 0f)
            ) {
                Text(
                    text = "Copy",
                    modifier = Modifier
                        .padding(top = 10.dp, end = 10.dp)
                        .clickable {

                            visible = false
                            msg=" "
                        }
                        .alpha(if (visible) 1f else 0f),
                    color = com.example.asskick.ui.theme.Teal200)
                Text(
                    text = "Cancel",
                    modifier = Modifier
                        .padding(top = 10.dp, start = 10.dp)
                        .clickable {
                            visible = false
                            msg=" "
                        }
                        .alpha(if (visible) 1f else 0f),
                    color = com.example.asskick.ui.theme.Red)
            }

        }

    }
}
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AssKickTheme {
        Greeting("Android")
    }
}