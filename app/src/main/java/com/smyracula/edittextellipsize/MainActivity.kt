package com.smyracula.edittextellipsize

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smyracula.edittextellipsize.ui.theme.EdittextellipsizeTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EdittextellipsizeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier.padding(16.dp),
    viewModel: MainViewModel
) {

    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }
    var placeHolder = "Buraya text gelecek"
    Column(modifier = modifier) {

        if (isFocused || (!isFocused && viewModel.text.value.isEmpty())) {
            BasicTextField(
                value = viewModel.text.value,
                maxLines = 1,
                onValueChange = { newText ->
                    viewModel.onTextChanged(newText)
                },
                textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
                modifier = Modifier
                    .border(1.dp, Color.Gray)
                    .padding(8.dp)
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    },
                decorationBox = @Composable { innerTextField ->
                    TextFieldDefaults.TextFieldDecorationBox(
                        value = viewModel.text.value,
                        visualTransformation = VisualTransformation.None,
                        innerTextField = innerTextField,
                        placeholder = placeHolder.let {
                            {
                                Text(
                                    modifier = Modifier.semantics {
                                        contentDescription = placeHolder.lowercase()
                                    },
                                    text = placeHolder
                                )
                            }
                        },
                        singleLine = true,
                        enabled = true,
                        interactionSource = MutableInteractionSource(),
                        contentPadding = PaddingValues(
                            start = 12.dp,
                            top = 22.dp,
                            end = 12.dp
                        )
                    )
                }

            )
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
        } else {
            Text(
                text = viewModel.text.value,
                style = TextStyle(fontSize = 18.sp, color = Color.Black),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .border(1.dp, Color.Gray)
                    .padding(8.dp)
                    .fillMaxWidth()
                    .clickable {
                        isFocused = true

                    }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        BasicTextField(
            value = viewModel.text2.value,
            onValueChange = { viewModel.onTextChanged2(it) },
            textStyle = TextStyle(fontSize = 18.sp, color = Color.Black),
            maxLines = 1,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .border(1.dp, Color.Gray)
        )
        Text(
            text = "You typed: ${viewModel.text.value}",
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}
