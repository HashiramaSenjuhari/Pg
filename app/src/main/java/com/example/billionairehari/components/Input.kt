package com.example.billionairehari.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.billionairehari.icons.TenantIcon
import com.example.billionairehari.layout.component.ROw


enum class InputType {
    STRING,
    NUMBER
}
@Composable
fun Input(
    label:String = "",
    inner_label:String,
    value:String,
    onValueChange:(String) -> Unit,
    leadingIcon:@Composable (() -> Unit)? = null,
    trailingIcon:@Composable (() -> Unit)? = null,
    modifier:Modifier = Modifier,
    onClick:() -> Unit = {},
    keyBoardType: KeyboardType = KeyboardType.Text,
    maxLength:Int = 0,
    tranformation: VisualTransformation? = null,
    type: InputType = InputType.STRING,
    error:String? = null,
    label_font_size: TextUnit = 14.sp,
    readOnly:Boolean = false,
    interactionSource: MutableInteractionSource? = null,
    enabled:Boolean = true
){
    val is_focused = rememberSaveable { mutableStateOf(false) }
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if(label != ""){
            Label(
                name = label,
                is_important = true
            )
        }
        TextField(
            value = value,
            onValueChange = {
                value ->
                val current = when (type){
                    InputType.STRING -> value
                    InputType.NUMBER -> value.filter { it.isDigit() }
                    else -> value
                }
                if (maxLength <= 0){
                    onValueChange(current)
                }else if(maxLength > 0){
                    onValueChange(current.take(maxLength))
                }
            },
            interactionSource = interactionSource,
            placeholder = {
                    Text(
                        inner_label,
                        fontSize = label_font_size,
                        color = Color(0xFF909090),
                    )
            },
            readOnly = readOnly,
            singleLine = true,
            leadingIcon = leadingIcon ?: null,
            trailingIcon = trailingIcon ?: null,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            visualTransformation = if(tranformation != null) {
                tranformation
            }else {
                VisualTransformation.None
            },
            modifier = Modifier.then(modifier)
                .border(1.dp, color = if(is_focused.value) Color.Black.copy(alpha = 0.6f) else Color.Black.copy(alpha = 0.1f), shape = RoundedCornerShape(13.dp)).onFocusChanged{
                        focus_state ->
                    is_focused.value = focus_state.isFocused
                }
                .background(Color.White)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    enabled = true,
                    onClick = onClick,
                    role = Role.Button
                ),
            keyboardOptions = KeyboardOptions(keyboardType = keyBoardType, imeAction = ImeAction.Next),
            enabled = enabled
        )
        if(error != null){
            Text(error, fontSize = 12.sp, color = Color.Red)
        }
    }
}

val PhoneNumberTransformation = VisualTransformation { text ->
    val trimmed = text.text.take(10)
    val b = buildString {
        for(i in trimmed.indices){
            append(trimmed[i])
            if(i == 4){
                append(" ")
            }
        }
    }
    val phoneOffset = object: OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            return if(offset <= 4) offset else offset + 1
        }

        override fun transformedToOriginal(offset: Int): Int {
            return if(offset <= 4) offset else offset - 1
        }
    }
    TransformedText(
        text = AnnotatedString(b),
        offsetMapping = phoneOffset
    )
}

@Composable
fun OutlinedInput(
    label:String,
    value:String,
    onValueChange:(String) -> Unit,
    leadingIcon: ImageVector? = null,
    modifier:Modifier = Modifier,
    onClick:() -> Unit = {},
    keyBoardType: KeyboardType = KeyboardType.Text,
    maxLength:Int = 0,
    tranformation: VisualTransformation? = null,
    type: InputType = InputType.STRING,
    error:String? = null,
    label_font_size: TextUnit = TextUnit.Unspecified,
    readOnly:Boolean = false,
    interactionSource: MutableInteractionSource? = null,
    trailingIcon:(@Composable () -> Unit)? = null,
    leadingIconInput:(@Composable () -> Unit)? = null,
){
    val is_focused = rememberSaveable { mutableStateOf(false) }
    Column {
        ROw(
            horizontalArrangement = Arrangement.spacedBy(13.dp),
        ) {
            if(leadingIcon !== null){
                Icon(leadingIcon, contentDescription = "")
            }
            OutlinedTextField(
                value = value,
                onValueChange = {
                        value ->
                    val current = when (type){
                        InputType.STRING -> value
                        InputType.NUMBER -> value.filter { it.isDigit() }
                        else -> value
                    }
                    if (maxLength <= 0){
                        onValueChange(current)
                    }else if(maxLength > 0){
                        onValueChange(current.take(maxLength))
                    }
                },
                interactionSource = interactionSource,
                trailingIcon = trailingIcon,
                leadingIcon = leadingIconInput,
                label = {
                    Text(
                        label,
                        fontSize = label_font_size,
                        color = Color(0xFF909090)
                    )
                },
                readOnly = readOnly,
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
//                    0xFFB2B0E8
                    unfocusedBorderColor = Color.Black.copy(alpha = 0.1f),
                    focusedBorderColor = Color(0xFFB2B0E8),
                    cursorColor = Color(0xFFB2B0E8)
                ),
                visualTransformation = if(tranformation != null) {
                    tranformation
                }else {
                    VisualTransformation.None
                },
                shape = RoundedCornerShape(13.dp),
                modifier = Modifier.then(modifier)
                    .pointerInput(Unit){
                        awaitPointerEventScope {
                            while(true){
                                val event = awaitPointerEvent()
                                if(event.changes.any { it.pressed }){
                                    onClick()
                                }
                                event.changes.forEach { it.consume() }
                            }
                        }
                    },
                keyboardOptions = KeyboardOptions(keyboardType = keyBoardType, imeAction = ImeAction.Next)
            )
        }
        if(error !== null){
            Box(
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 40.dp, end = 40.dp,top = 6.dp)
            ){
                Text(error.toString(),color = Color.Red, fontSize = 12.sp)
            }
        }
    }
}