package com.amanullah.assessment.presentation.customview.progressbar

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.amanullah.assessment.base.utils.Constants
import com.amanullah.assessment.ui.theme.ColorPrimary
import com.amanullah.assessment.ui.theme.ColorPrimaryWith20Percent

@Preview
@Composable
fun CircularProgressBar(
    modifier: Modifier = Modifier
        .size(size = Constants.PADDING36.dp),
    width: Dp = 3.dp,
    loadingColor: Color = ColorPrimary,
    trackColor: Color = ColorPrimaryWith20Percent
) {
    CircularProgressIndicator(
        modifier = modifier,
        color = loadingColor,
        strokeWidth = width,
        trackColor = trackColor
    )
}