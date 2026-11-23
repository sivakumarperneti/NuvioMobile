package com.nuvio.app.core.ui.glass

import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.matchParentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import com.nuvio.app.core.ui.NuvioTokens
import com.nuvio.app.core.ui.glassSurface
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect

private val GlassSurfaceColor = Color(0xFF11182B)

@Composable
internal fun GlassBarSurface(hazeState: HazeState?, modifier: Modifier = Modifier, glowStrength: Float = 1f) {
    Box(modifier) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && hazeState?.blurEnabled == true && glowStrength > 0f) {
            RefractedGlassBar(hazeState, Modifier.matchParentSize(), glowStrength)
        } else {
            Box(
                Modifier
                    .matchParentSize()
                    .then(if (hazeState != null) Modifier.barBackdrop(hazeState) else Modifier),
            )
        }
        Box(
            Modifier
                .matchParentSize()
                .glassSurface(
                    shape = RoundedCornerShape(20.dp),
                    backgroundColor = GlassSurfaceColor.copy(alpha = NuvioTokens.Glass.defaultAlpha),
                    borderColor = NuvioTokens.Glass.borderColor,
                    borderWidth = NuvioTokens.Glass.borderWidth,
                ),
        )
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
private fun RefractedGlassBar(hazeState: HazeState, modifier: Modifier, glowStrength: Float) {
    val shader = remember { RuntimeShader(GlassBarShader) }
    Box(
        modifier
            .layout { measurable, constraints ->
                val outset = 24.dp.roundToPx()
                val placeable = measurable.measure(constraints.offset(outset * 2, outset * 2))
                layout(placeable.width - outset * 2, placeable.height - outset * 2) {
                    placeable.place(-outset, -outset)
                }
            }
            .graphicsLayer {
                shader.setFloatUniform("resolution", size.width, size.height)
                shader.setFloatUniform("density", density)
                shader.setFloatUniform("outset", 24.dp.roundToPx().toFloat())
                shader.setFloatUniform("glowStrength", glowStrength)
                renderEffect = RenderEffect.createRuntimeShaderEffect(shader, "backdrop").asComposeRenderEffect()
            }
            .barBackdrop(hazeState),
    )
}

private fun Modifier.barBackdrop(hazeState: HazeState): Modifier = hazeEffect(state = hazeState) {
    blurRadius = 24.dp
    backgroundColor = GlassSurfaceColor.copy(alpha = 0.12f)
    tints = listOf(HazeTint(Color.Transparent))
    noiseFactor = 0f
}
