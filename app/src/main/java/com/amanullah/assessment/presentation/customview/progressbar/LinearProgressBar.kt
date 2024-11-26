package com.amanullah.assessment.presentation.customview.progressbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.amanullah.assessment.base.utils.Constants
import com.amanullah.assessment.ui.theme.ColorPrimary

// To use across the application when doing some background works
@Composable
fun LinearProgressBar(state: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = Constants.PADDING8.dp)
    ) {
        when (state) {
            true -> {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = ColorPrimary)
            }

            else -> {
                Spacer(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}