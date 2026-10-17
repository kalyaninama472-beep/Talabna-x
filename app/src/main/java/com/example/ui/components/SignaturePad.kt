package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Draw
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun SignaturePad(
    strokes: List<List<Offset>>,
    onStrokesChange: (List<List<Offset>>) -> Unit,
    modifier: Modifier = Modifier,
    promptLabel: String = "Sign with finger or stylus above",
    clearLabel: String = "Clear"
) {
    var currentStroke by remember { mutableStateOf<List<Offset>>(emptyList()) }

    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF070B12))
                .border(1.5.dp, TalabnaBorder, RoundedCornerShape(16.dp))
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            currentStroke = listOf(offset)
                        },
                        onDrag = { change, _ ->
                            change.consume()
                            currentStroke = currentStroke + change.position
                        },
                        onDragEnd = {
                            if (currentStroke.isNotEmpty()) {
                                onStrokesChange(strokes + listOf(currentStroke))
                                currentStroke = emptyList()
                            }
                        },
                        onDragCancel = {
                            currentStroke = emptyList()
                        }
                    )
                }
                .testTag("signature_canvas")
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                // Draw guidelines / baseline
                val baselineY = size.height * 0.75f
                drawLine(
                    color = Color(0xFF1E293B),
                    start = Offset(24f, baselineY),
                    end = Offset(size.width - 24f, baselineY),
                    strokeWidth = 1.5f
                )

                // Draw existing completed strokes in bright cyber cyan
                strokes.forEach { strokeList ->
                    if (strokeList.size > 1) {
                        val path = Path().apply {
                            moveTo(strokeList.first().x, strokeList.first().y)
                            for (i in 1 until strokeList.size) {
                                lineTo(strokeList[i].x, strokeList[i].y)
                            }
                        }
                        drawPath(
                            path = path,
                            color = TalabnaCyan,
                            style = Stroke(
                                width = 4.dp.toPx(),
                                cap = StrokeCap.Round,
                                join = StrokeJoin.Round
                            )
                        )
                    }
                }

                // Draw current active stroke
                if (currentStroke.size > 1) {
                    val activePath = Path().apply {
                        moveTo(currentStroke.first().x, currentStroke.first().y)
                        for (i in 1 until currentStroke.size) {
                            lineTo(currentStroke[i].x, currentStroke[i].y)
                        }
                    }
                    drawPath(
                        path = activePath,
                        color = TalabnaCyanBright,
                        style = Stroke(
                            width = 4.5.dp.toPx(),
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )
                }
            }

            // Watermark hint if empty
            if (strokes.isEmpty() && currentStroke.isEmpty()) {
                Row(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Draw,
                        contentDescription = null,
                        tint = TalabnaSilverDark.copy(alpha = 0.5f),
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = promptLabel,
                        color = TalabnaSilverDark.copy(alpha = 0.6f),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Clear Button overlay
            if (strokes.isNotEmpty()) {
                OutlinedButton(
                    onClick = {
                        onStrokesChange(emptyList())
                        currentStroke = emptyList()
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = TalabnaSurfaceVariant.copy(alpha = 0.85f),
                        contentColor = TalabnaSilver
                    ),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .testTag("signature_clear_button"),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.DeleteSweep,
                        contentDescription = "Clear",
                        tint = TalabnaRed,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = clearLabel, fontSize = 11.sp, color = TalabnaSilver)
                }
            }
        }
    }
}
