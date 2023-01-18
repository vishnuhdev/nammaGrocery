
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firstcomposeapp.Components.ButtonWithImage
import com.example.firstcomposeapp.R
import com.example.firstcomposeapp.ui.theme.PrimaryGreen

@Composable
fun LoginScreen() {

    var scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(state = scrollState)
    ) {
        Image(painter = painterResource(id = R.drawable.login_screen),
            contentDescription = null,
            modifier = Modifier
                .width(400.dp)
                .height(320.dp),
//                .padding(horizontal = 0.dp, vertical = 0.dp),
            alignment = Alignment.TopEnd
        )
        TextComponet()
        Spacer(modifier = Modifier
            .height(25.dp)
        )
        ButtonWithImage(title = "Continue with Google", image = R.drawable.google, onClick = {})
        Spacer(modifier = Modifier
            .height(10.dp)
        )
        ButtonWithImage(title = "Continue with Facebook",image = R.drawable.facebook, onClick = {} )
        Spacer(modifier = Modifier.padding(bottom = 25.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextComponet(){
    val textFieldState = rememberSaveable { mutableStateOf("") }

    Column {
        Text(text = "Get your groceries\n" +
                "with nectar",
            fontSize = 26.sp,
            color = Color.Black,
            fontFamily = FontFamily(Font(R.font.font_light)),
            modifier = Modifier
                .padding(start = 15.dp)
        )
        Spacer(modifier = Modifier
            .height(20.dp))

Column(
    modifier = Modifier
        .fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally
){
    TextField(
        value = textFieldState.value,
        onValueChange = {
                text ->
            textFieldState.value = text
        },

        label = {
            Text(text = "Enter Your Mobile Number")
        },
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .padding(start = 10.dp),
        leadingIcon = {
            Row {
                Image(painter = painterResource(id =R.drawable.flag ),
                    contentDescription ="Flag",
                    modifier = Modifier
                        .height(32.dp)
                        .width(26.dp)
                        .padding(start = 5.dp, top = 9.dp)
                )
                Text(
                    text = "(+91)",
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.font_black)),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(start = 5.dp, top = 11.dp, end = 10.dp)
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,
            focusedIndicatorColor = PrimaryGreen,
            focusedLabelColor = PrimaryGreen,
            focusedSupportingTextColor = PrimaryGreen,
            cursorColor= PrimaryGreen
            ),
    )
}
        Spacer(modifier = Modifier
            .height(20.dp))
        Text(
            text = "Or connect with social media",
            fontSize = 14.sp,
            color = Color.Black,
            fontFamily = FontFamily(Font(R.font.font_light)),
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}