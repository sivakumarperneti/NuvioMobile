package com.nuvio.app.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.glassCard(
    shape: Shape = RoundedCornerShape(20.dp),
    backgroundColor: Color = Color(0xFF161618).copy(alpha = 0.65f),
    borderColor: Color = Color.White.copy(alpha = 0.14f),
    borderWidth: Dp = 1.dp,
    elevation: Dp = 0.dp,
): Modifier = this.glassSurface(
    shape = shape,
    backgroundColor = backgroundColor,
    borderColor = borderColor,
    borderWidth = borderWidth,
    hasGradientOverlay = true,
    elevation = elevation,
)

fun Modifier.glassChip(
    shape: Shape = RoundedCornerShape(999.dp),
    isSelected: Boolean = false
): Modifier = this.glassSurface(
    shape = shape,
    backgroundColor = if (isSelected) Color(0xFF3185F5).copy(alpha = NuvioTokens.Glass.defaultAlpha) else Color(0xFF161618).copy(alpha = NuvioTokens.Glass.defaultAlpha),
    borderColor = if (isSelected) Color(0xFF4DE3FF).copy(alpha = 0.6f) else Color.White.copy(alpha = 0.14f),
    borderWidth = 1.dp,
    elevation = if (isSelected) 4.dp else 0.dp
)

@Composable
fun NuvioGlassCard(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.nuvio.shapes.card,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val glassModifier = modifier
        .fillMaxWidth()
        .glassCard(shape = shape)

    if (onClick != null) {
        Surface(
            modifier = glassModifier,
            color = Color.Transparent,
            shape = shape,
            onClick = onClick,
        ) {
            Column(
                modifier = Modifier.padding(MaterialTheme.nuvio.spacing.cardPadding),
                content = content,
            )
        }
    } else {
        Surface(
            modifier = glassModifier,
            color = Color.Transparent,
            shape = shape,
        ) {
            Column(
                modifier = Modifier.padding(MaterialTheme.nuvio.spacing.cardPadding),
                content = content,
            )
        }
    }
}
