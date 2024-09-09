package com.example.detail.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@Composable
fun TextInstructions(instructions: String) {
    var isExpanded by remember { mutableStateOf(false) }
    var needsToBeExpandable by remember { mutableStateOf(false) }
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    Text(
        "Instructions",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = instructions,
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        maxLines = if (isExpanded) Int.MAX_VALUE else 3,
        style = MaterialTheme.typography.bodyMedium,
        color = Color.Gray,
        onTextLayout = { textLayoutResult ->
            if (!isExpanded && textLayoutResult.hasVisualOverflow) {
                needsToBeExpandable = true
            }
            textLayoutResultState.value = textLayoutResult
        },
    )
    if (needsToBeExpandable) {
        Text(
            text = if (isExpanded) "Read less" else "Read more",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline
            ),
            modifier = Modifier
                .padding(top = 4.dp)
                .clickable {
                    isExpanded = !isExpanded
                }
        )
    }
}