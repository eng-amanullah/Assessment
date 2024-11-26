package com.amanullah.assessment.presentation.customview.imageview

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.SubcomposeAsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.amanullah.assessment.R
import com.amanullah.assessment.presentation.customview.progressbar.CircularProgressBar
import kotlinx.coroutines.Dispatchers
import kotlin.math.max
import kotlin.math.min

@Preview
@Composable
fun ImageView(
    modifier: Modifier = Modifier
        .fillMaxSize(),
    image: String? = null,
    localImage: Painter? = null,
    enableZoom: Boolean = false,
    contentScale: ContentScale = ContentScale.Fit,
    maxZoom: Float = 4f,
    enableOnClick: Boolean = false,
    onClickCallBack: (String) -> Unit = {}
) {
    val context = LocalContext.current

    val displayMetrics = context.resources.displayMetrics
    val screenWidth = with(LocalDensity.current) { displayMetrics.widthPixels.toFloat() }
    val screenHeight = with(LocalDensity.current) { displayMetrics.heightPixels.toFloat() }

    var scale by remember { mutableFloatStateOf(value = 1f) }
    var offsetX by remember { mutableFloatStateOf(value = 0f) }
    var offsetY by remember { mutableFloatStateOf(value = 0f) }

    var imageWidth by remember { mutableFloatStateOf(value = 1f) }
    var imageHeight by remember { mutableFloatStateOf(value = 1f) }

    if (image?.isNotEmpty() == true || localImage != null) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .pointerInput(key1 = Unit) {
                    if (enableZoom) {
                        detectTransformGestures { _, pan, zoom, _ ->
                            scale = max(
                                a = 1f,
                                b = min(a = scale * zoom, maxZoom)
                            ) // Prevent scaling below 1x

                            val maxXOffset = (imageWidth * scale - screenWidth) / 2
                            val maxYOffset = (imageHeight * scale - screenHeight) / 2

                            // Ensure pan only happens when scaled image is larger than the screen
                            offsetX = max(-maxXOffset, min(maxXOffset, offsetX + pan.x))
                            offsetY = max(-maxYOffset, min(maxYOffset, offsetY + pan.y))
                        }
                    }
                }
                .graphicsLayer(
                    scaleX = if (enableZoom) scale else 1f,
                    scaleY = if (enableZoom) scale else 1f,
                    translationX = if (enableZoom) offsetX else 0f,
                    translationY = if (enableZoom) offsetY else 0f
                ),
            contentAlignment = Alignment.Center
        ) {
            when {
                image?.isNotEmpty() == true -> {
                    val listener = object : ImageRequest.Listener {}

                    val imageRequest = ImageRequest.Builder(context)
                        .data(data = image)
                        .listener(listener = listener)
                        .dispatcher(dispatcher = Dispatchers.IO)
                        .memoryCacheKey(key = image)
                        .diskCacheKey(key = image)
                        .error(drawableResId = R.drawable.ic_launcher_background)
                        .fallback(drawableResId = R.drawable.ic_launcher_background)
                        .crossfade(enable = true)
                        .diskCachePolicy(policy = CachePolicy.ENABLED)
                        .memoryCachePolicy(policy = CachePolicy.ENABLED)
                        .build()

                    // Sub Compose AsyncImage from Coil with zoom functionality
                    SubcomposeAsyncImage(
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer {
                                imageWidth = this.size.width
                                imageHeight = this.size.height
                            }
                            .then(
                                if (enableOnClick) Modifier.clickable { onClickCallBack(image) } else Modifier
                            ),
                        model = imageRequest,
                        contentDescription = null,
                        contentScale = contentScale,
                        loading = {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressBar()
                            }
                        }
                    )
                }

                localImage != null -> {
                    Image(
                        painter = localImage,
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxSize()
                            .onGloballyPositioned { layoutCoordinates ->
                                // Capture the size of the image for boundary calculations
                                imageWidth = layoutCoordinates.size.width.toFloat()
                                imageHeight = layoutCoordinates.size.height.toFloat()
                            }
                    )
                }
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Not found")
        }
    }
}