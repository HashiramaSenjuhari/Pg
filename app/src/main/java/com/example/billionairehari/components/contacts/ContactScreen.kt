package com.example.billionairehari.components.contacts

import android.R
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.billionairehari.icons.Phone
import com.example.billionairehari.icons.TenantIcon
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce

data class Contact(
    val name:String,
    val phone:String
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactScreen(navCotroller: NavController,modifier:Modifier = Modifier){
    val context = LocalContext.current
    val tenant_contacts = listOf<Contact>(
        Contact(name = "Billionaireal", phone = "8668072363"),
        Contact(name = "mark",phone = "836297469778"),
        Contact(name = "bill",phone = "43583247989807"),
        Contact(name = "jeff",phone = "378498723987")
    )
    val contacts = listOf<Contact>(
        Contact(name = "Lawyer", phone = "8668072363"),
        Contact(name = "Clean",phone = "836297469778")
    )
    val value= remember { mutableStateOf<String>("") }

    val sorted = (tenant_contacts + contacts).filter { contact ->
            contact.name.contains(value.value, ignoreCase = true)
        }
        .sortedBy {
            it.name
        }.groupBy {
            it.name.first().uppercase()
        }
    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .background(Color.White)
            .then(modifier)
    ) {
        stickyHeader()
        {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .background(Color.White)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    IconButton(
                        onClick = {},
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "arrow_back_icon", tint =  Color.Black)
                    }
                    Text("Contacts" , fontSize = 16.sp,fontWeight = FontWeight.Medium)
                }
                Row(
                    modifier = Modifier.padding(horizontal = 13.dp)
                ) {
                    TextField(
                        enabled = true,
                        value = value.value,
                        onValueChange = {
                            value.value = it
                        },
                        label = null,
                        leadingIcon = {
                            Icon(Icons.Default.Search, contentDescription = "search")
                        },
                        trailingIcon = {
                            if(value.value.length > 0){
                                IconButton(
                                    onClick = {
                                        value.value = ""
                                    }
                                ) {
                                    Icon(Icons.Default.Close, contentDescription = "close")
                                }
                            }
                        },
                        placeholder = {
                            Text("Search Contact", fontSize = 16.sp)
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = Color.Black
                        ),
                        modifier = Modifier.fillMaxWidth().clip(CircleShape).background(Color(0xFFF5F5F5)),
                        textStyle = TextStyle.Default.copy(fontSize = 16.sp)
                    )
                }
            }
        }
        sorted.forEach {
            (inital,contactList) ->
            item {
                Text(inital.toString(),modifier = Modifier.padding(13.dp))
                Column(
                    modifier = Modifier.clip(RoundedCornerShape(24.dp)).background(Color(0xFFF5F5F5))
                ) {
                    contactList.forEach{
                            contact ->
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .padding(13.dp)
                                .clickable(
                                    enabled = true,
                                    onClick = {}
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row (
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(13.dp)
                            ){
                                AsyncImage(
                                    model = "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fi.pinimg.com%2Foriginals%2Fae%2Fec%2Fc2%2Faeecc22a67dac7987a80ac0724658493.jpg&f=1&nofb=1&ipt=88f8b4bf2541fc4a8ad533bfc85e8297b4b61ffe9236fb6a4ce39bdcd8de5613",
                                    contentDescription = "",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.size(40.dp).clip(CircleShape)
                                )
                                Column(
                                    verticalArrangement =  Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(contact.name, fontSize = 14.sp,fontWeight = FontWeight.SemiBold)
                                    Text(contact.phone)
                                }
                            }
                            Row {
                                IconButton(
                                    onClick = {
                                        dial(contact.phone, context = context)
                                    }
                                ) {
                                    Icon(
                                        imageVector = Phone,
                                        contentDescription = "",
                                        tint =  Color(0xFF909090),
                                        modifier = Modifier.size(21.dp)
                                    )
                                }
                            }
                        }
                        HorizontalDivider(color = Color.Black.copy(0.1f))
                    }
                }
            }
        }
    }
}


fun dial(number:String,context: Context) {
    val contact_intent = Intent().apply {
        action = Intent.ACTION_DIAL
        data = Uri.parse("tel:$number")
    }
    context.startActivity(contact_intent)
}

fun whatsapp(number:String,context:Context){
    val whatsapp_intent = Intent().apply {
        action = Intent.ACTION_VIEW
        data = Uri.parse("https://wa.me/$number")
    }
    context.startActivity(whatsapp_intent)
}