package com.example.billionairehari.components

import android.Manifest
import android.content.Context
import android.graphics.ImageDecoder
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.example.billionairehari.icons.Photo
import com.example.billionairehari.modal.Grid
import kotlinx.coroutines.CoroutineScope
import com.example.billionairehari.layout.component.ROw
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChooseMultiplePhoto(
    onImageChange:(ByteArray) -> Unit,
    images:List<ByteArray>,
    onImageRemove:(Int) -> Unit,
    modifier:Modifier = Modifier,
    contentPadding: PaddingValues,
    onError:() -> Unit,
    size:Dp,
    limit:Int = 4
){
    val context = LocalContext.current
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        ROw(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                ROw(
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Label(
                        name =  "Photos",
                        fontSize = 16.sp
                    )
                    Text(
                        "* max 4 images",
                        fontSize = 12.sp,
                        color = Color.Black.copy(alpha = 0.3f)
                    )
                }
                Text(
                    "Choose the Image of your room \nto display and to be shared",
                    fontSize = 12.sp,
                    color = Color.Black.copy(alpha = 0.3f)
                )
            }
            MultipleImageButton(
                context = context,
                onImageAdd = {
                    onImageChange(UriToByteArray(it, context = context))
                },
                modifier = Modifier.then(modifier),
                contentPadding = contentPadding,
                limit = limit,
                onError = {
                    onError()
                },
                size = images.size
            )
        }
        FlowRow (
        horizontalArrangement = Arrangement.spacedBy(13.dp),
            verticalArrangement = Arrangement.spacedBy(13.dp)
        ) {

            MultipleImagePreview(
                images = images,
                onImageRemove = onImageRemove,
                size = size
            )
        }
    }
}

@Composable
fun MultipleImageButton(
    context:Context,
    size:Int,
    onImageAdd:(Uri) -> Unit,
    onError:() -> Unit,
    limit:Int,
    modifier:Modifier = Modifier,
    icon: ImageVector = Photo,
    contentPadding: PaddingValues= PaddingValues(0.dp)
){
    val getMultipleImages = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) {
            uris ->
            CoroutineScope(Dispatchers.IO).launch {
                val image_uris = uris.take(limit - size)
                image_uris.forEach { uri ->
                    onImageAdd(uri)
                }
            }
        }
    val permission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {
            isGranted ->
        if(isGranted){
            getMultipleImages.launch("image/*")
        }
    }
    Button(
        onClick = { permission.launch(Manifest.permission.READ_MEDIA_IMAGES) },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black.copy(alpha = 0.1f),
            contentColor = Color.Transparent
        ),
        modifier = Modifier.then(modifier),
        contentPadding = contentPadding
    ) {
        ROw {
            Text("Add photots", color = Color.Black)
        }
    }
}

@Composable
fun MultipleImagePreview(size:Dp = 100.dp,images:List<Any>,onImageRemove: (Int) -> Unit){
    Log.d("BillionaireImage",images.size.toString())
    Grid {
        images.let {
            it.forEachIndexed { index, image ->
                ImagePreview(
                    image = image,
                    onImageRemove = {
                        onImageRemove(index)
                    },
                    size = size
                )
            }
        }
    }
}

fun UriToByteArray(uri: Uri,context: Context): ByteArray {
    val contentResolver = context.contentResolver
    val source = ImageDecoder.createSource(contentResolver,uri)
    val bitmapArray = ImageDecoder.decodeBitmap(source)
    val byteArray = BitmapToByteArray(bitmapArray)
    return byteArray
}

@Composable
fun SingleImageSelector(onImageChange:(Uri) -> Unit,modifier:Modifier = Modifier,iconSize:Dp){
    val image_launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        it?.let {
            onImageChange(it)
        }
    }
    val permission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {
        isGranted ->
        if(isGranted){
            image_launcher.launch("image/*")
        }
    }

    IconButton(
        onClick = {
            permission.launch(Manifest.permission.READ_MEDIA_IMAGES)
        },
        modifier = Modifier
            .shadow(elevation = 1.dp, shape = CircleShape, spotColor = Color.Black)
            .then(modifier)
            .background(Color.Black)
    ) {
        Icon(
            Photo,
            tint = Color.White,
            modifier = Modifier.size(iconSize),
            contentDescription = "ImageSelector"
        )
    }}

@Composable
fun ImagePreview(
    image:Any,
    onImageRemove: () -> Unit,
    size: Dp
){
    Box{
        AsyncImage(
            image,
            contentDescription = "billionaire",
            modifier = Modifier.size(size)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )
        IconButton(
            onClick = onImageRemove,
            modifier = Modifier.zIndex(10f).align(Alignment.TopEnd)
                .offset(y = (-10).dp, x = 10.dp)
        ) {
            Icon(
                Icons.Default.Close,
                contentDescription = "",
                modifier = Modifier.clip(CircleShape)
                    .size(size/4)
                    .background(Color.Black)
                    .padding(3.dp),
                tint = Color.White
            )
        }
    }
}