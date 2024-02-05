package com.example.tictactoe.ui.theme

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.example.tictactoe.R

@Composable
fun TicTacToeBoard(
    board: List<TicTacToeState>,
    onCellClick: (Int) -> Unit,
    winningPattern: List<Int> = emptyList()
) {
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            colorResource(id = R.color.start_color),
            colorResource(id = R.color.end_color)
        ),
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .padding(8.dp)
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
    ) {
        for (i in 0 until 3) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                for (j in 0 until 3) {
                    val index = i * 3 + j
                    TicTacToeCell(
                        state = board[index],
                        onClick = { onCellClick(index) },
                        isWinningCell = winningPattern.contains(index),
                    )
                }
            }
        }
        if (winningPattern.isNotEmpty()) {
            DrawWinningLines(winningPattern)
        }
    }
}

@Composable
fun TicTacToeCell(
    state: TicTacToeState,
    onClick: () -> Unit,
    isWinningCell: Boolean,
) {
    val shape = RoundedCornerShape(8.dp)
    val cellModifier = Modifier
        .size(130.dp)
        .then(Modifier.clickable { onClick() })

    Box(
        modifier = cellModifier
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(20.dp),
            )
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White, shape)
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
         when(state) {
            TicTacToeState.CROSS -> {
               Image(painterResource(id = R.drawable.img),
                   contentDescription = null,
                   modifier = Modifier.size(100.dp),
                   contentScale = ContentScale.Fit,
                   colorFilter = ColorFilter.tint(Color.Black)
               )
            }
            TicTacToeState.CIRCLE -> {
               Image(
                   painterResource(id = R.drawable.img3),
                   contentDescription = null,
                   modifier = Modifier.size(100.dp),
                   contentScale = ContentScale.Fit,
                   colorFilter = ColorFilter.tint(Color.Black)
               )
            }
             TicTacToeState.Empty -> {

             }
        }
    }
}

@Composable
fun DrawWinningLines(winningPattern: List<Int>) {
    val cellSize = 130.dp
    val strokeWidth = 5.dp

    if (winningPattern.size == 3) {
        val density = LocalDensity.current
        val cellSizePx = with(density) { cellSize.toPx() }
        val strokeWidthPx = with(density) { strokeWidth.toPx() }

        val startX = winningPattern[0] % 3 * cellSizePx + cellSizePx / 2
        val startY = (winningPattern[0] / 3 * cellSizePx + (cellSizePx / 2)) - (cellSizePx*3)
        val endX = winningPattern[2] % 3 * cellSizePx + cellSizePx / 2
        val endY = (winningPattern[2] / 3 * cellSizePx + cellSizePx / 2) - (cellSizePx * 3)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
                .drawBehind {
                    drawLine(
                        color = Color.Red,
                        strokeWidth = strokeWidthPx,
                        cap = StrokeCap.Round,
                        start = Offset(startX, startY),
                        end = Offset(endX, endY)
                    )
                }
        )
    }
}

@Preview
@Composable
fun TicTacToeBoardPreview() {
    TicTacToeBoard(
        board = List(9) { TicTacToeState.Empty },
        onCellClick = {}
    )
}





