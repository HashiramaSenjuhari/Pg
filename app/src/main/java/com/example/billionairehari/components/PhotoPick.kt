package com.example.billionairehari.components

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.example.billionairehari.components.sheets.BottomModalLayout
import com.example.billionairehari.icons.Photo
import com.example.billionairehari.icons.Photo_camera_Icon
import java.io.ByteArrayOutputStream
import com.example.billionairehari.R
import com.example.billionairehari.layout.component.ROw


fun BitmapToByteArray(bitmap: Bitmap): ByteArray {
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG,90,stream)
    return stream.toByteArray()
}
@Composable
fun PhotoPick(
    onImageChange:(ByteArray) -> Unit,
    onRemoveImage:() -> Unit,
    image: ByteArray?
) {
    val is_open = rememberSaveable { mutableStateOf<Boolean>(false) }
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PreviewImage(
            image = image,
            onRemoveImage = onRemoveImage,
            modifier = Modifier.clickable(
                role = Role.Button,
                onClick = {
                    if(image != null){
                        is_open.value = true
                    }
                }
            )
        )
        ROw(
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            LiveImage(
                onImageChange = {
                    onImageChange(it)
                },
                image = image
            )
            VerticalDivider(modifier = Modifier.height(24.dp))
            Gallery(
                image = image,
                onImageChange = {
                    onImageChange(it)
                }
            )
        }
    }
    if(is_open.value){
        ImagePreview(
            is_open = is_open,
            image = image,
            onRemoveImage = onRemoveImage
        )
    }
}

@Composable
fun ImagePreview(
    is_open: MutableState<Boolean>,
    image: ByteArray? = null,
    onRemoveImage: () -> Unit
){
    Dialog(
        onDismissRequest = {
            is_open.value = false
        },
        properties = DialogProperties(dismissOnBackPress = true)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .background(Color.Black)
        ) {
            ROw(
                modifier = Modifier.fillMaxWidth()
                    .padding(13.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Image Preview",
                    fontSize = 19.sp,
                    color = Color.White.copy(0.6f)
                )
                ROw(
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Icon(
                        Icons.Outlined.Delete,
                        contentDescription = "",
                        tint = Color.Red,
                        modifier = Modifier.clickable(
                            role = Role.Button,
                            onClick = {
                                is_open.value = false
                                onRemoveImage()
                            }
                        )
                    )
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier.clickable{ is_open.value = false}
                    )
                }
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ){
                AsyncImage(
                    model = image,
                    contentDescription = "",
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun LiveImage(
    onImageChange:(ByteArray) -> Unit,
    image: ByteArray?
){
    val capturelauncher = rememberLauncherForActivityResult(
    ActivityResultContracts.TakePicturePreview()
    ) {
        if(it != null){
            val value = BitmapToByteArray(it!!)
            onImageChange(value)
        }
    }
    Card(
        onClick = {
            capturelauncher.launch(null)
        },
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            disabledContainerColor = Color.White
        )
    ) {
        ROw(
            modifier = Modifier
                .padding(vertical = 6.dp, horizontal = 13.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.outline_camera_24),
                contentDescription = "add",
                modifier = Modifier.size(24.dp)
            )
            Text("Camera")
        }
    }
}


@Composable
fun Gallery(onImageChange:(ByteArray) -> Unit,image: ByteArray?){
    val context = LocalContext.current
    val imageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
            uri: Uri? ->
        uri?.let {
            val source = ImageDecoder.createSource(context.contentResolver,uri)
            val bitmap = ImageDecoder.decodeBitmap(source)
            val value = BitmapToByteArray(bitmap)
            onImageChange(value)
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
            great ->
        if(great){
            imageLauncher.launch("image/*")
        }
    }

     Card(
         onClick = {
             permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
         },
         colors = CardDefaults.cardColors(
             containerColor = Color.White,
             disabledContainerColor = Color.White
         )
    ) {
        ROw(
            modifier = Modifier
                .padding(vertical = 6.dp, horizontal = 13.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = Photo,
                contentDescription = "add",
                modifier = Modifier.size(24.dp)
            )
            Text("Gallery")
        }
    }

}

@Composable
fun PreviewImage(
    image: ByteArray? = null,
    onRemoveImage:() -> Unit,
    modifier:Modifier = Modifier
){
    Box {
        if(image !== null){
            IconButton(
                onClick = onRemoveImage,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .size(30.dp)
                    .border(1.dp, color = Color.Black.copy(alpha = 0.1f), shape = CircleShape)
                    .clip(CircleShape)
                    .background(Color.White)
                    .zIndex(20f)
                    .offset(x = 0.dp)
            ) {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = "",
                    tint =  Color.Blue,
                    modifier = Modifier.size(13.dp)
                )
            }
        }
        AsyncImage(
            model = image ?: R.drawable.baseline_person_24,
            contentDescription = null,
            modifier =  Modifier.clip(CircleShape)
                .size(100.dp)
                .border(1.dp,color = Color.Black.copy(alpha = 0.1f), shape = CircleShape)
                .scale(if(image != null) 1f else 3f)
                .then(modifier),
            contentScale = if(image !== null) ContentScale.Crop else ContentScale.None
        )
    }
}
