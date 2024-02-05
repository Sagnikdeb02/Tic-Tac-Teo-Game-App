package com.example.tictactoe.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tictactoe.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


fun checkForWinner(board: List<TicTacToeState>): Pair<Boolean, List<Int>> {
    // Check rows
    for (i in 0 until 3) {
        if (board[i * 3] != TicTacToeState.Empty &&
            board[i * 3] == board[i * 3 + 1] &&
            board[i * 3] == board[i * 3 + 2]
        ) {
            return Pair(true, listOf(i * 3, i * 3 + 1, i * 3 + 2))
        }
    }

    // Check columns
    for (i in 0 until 3) {
        if (board[i] != TicTacToeState.Empty &&
            board[i] == board[i + 3] &&
            board[i] == board[i + 6]
        ) {
            return Pair(true, listOf(i, i + 3, i + 6))
        }
    }

    // Check diagonals
    if (board[0] != TicTacToeState.Empty &&
        board[0] == board[4] &&
        board[0] == board[8]
    ) {
        return Pair(true, listOf(0,4,8))
    }

    if (board[2] != TicTacToeState.Empty &&
        board[2] == board[4] &&
        board[2] == board[6]
    ) {
        return Pair(true, listOf(2,4,6))
    }

    return Pair(false, emptyList())
}

fun getWinningPattern(board: List<TicTacToeState>): List<Int> {
    // Check rows
    for (i in 0 until 3) {
        if (board[i * 3] != TicTacToeState.Empty &&
            board[i * 3] == board[i * 3 + 1] &&
            board[i * 3] == board[i * 3 + 2]
        ) {
            return listOf(i * 3, (i * 3) + 1, (i * 3) + 2)
        }
    }

    // Check columns
    for (i in 0 until 3) {
        if (board[i] != TicTacToeState.Empty &&
            board[i] == board[i + 3] &&
            board[i] == board[i + 6]
        ) {
            return listOf(i, i + 3, i + 6)
        }
    }

    // Check diagonals
    if (board[0] != TicTacToeState.Empty &&
        board[0] == board[4] &&
        board[0] == board[8]
    ) {
        return listOf(0, 4, 8)
    }

    if (board[2] != TicTacToeState.Empty &&
        board[2] == board[4] &&
        board[2] == board[6]
    ) {
        return listOf(2, 4, 6)
    }

    return emptyList()
}

fun hasWinningPattern(board: List<TicTacToeState>): Boolean {
    return getWinningPattern(board).isNotEmpty()}


@Composable
fun TicTacToeGame(
    navController: NavHostController
) {
    var board by remember { mutableStateOf(List(9) { TicTacToeState.Empty}) }
    var currentPlayer by remember { mutableStateOf(TicTacToeState.CROSS) }
    var isGameOver by remember { mutableStateOf(false) }
    var winner by remember { mutableStateOf<TicTacToeState?>(null) }
    var timer by remember { mutableStateOf(60) }
    var (hasWinner, winningPattern) = checkForWinner(board)
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(colorResource(id = R.color.start_color),
            colorResource(id = R.color.end_color)),
    )
    val turnColor = Brush.horizontalGradient(
        colors = listOf(colorResource(id = R.color.a),
        colorResource(id = R.color.b), colorResource(id = R.color.c))
    )
    var turn = false


    fun resetGame(
        boardSize: Int = 9,
        initialPlayer: TicTacToeState = TicTacToeState.CROSS,
        initialTimerValue: Int = 60
    ) {
        val initialBoard = List(boardSize) { TicTacToeState.Empty }
        board = initialBoard
        currentPlayer = initialPlayer
        isGameOver = false
        winner = null
        timer = initialTimerValue
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradientBrush),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly

    ) {
        Text(
            text = if (isGameOver) {
                if (winner == null) "Game Over! It's a draw."
                else "Game Over! Player ${winner!!.name} wins!"
            } else {
                "Player ${currentPlayer.name}'s turn"
            },
            style = MaterialTheme.typography.h5,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Cursive,
            color = MaterialTheme.colors.primary,
        )
        
        Text(text = "Tic Tac Toe",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Cursive,
            color = Color.White
            )

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            if(!isGameOver){
                if(currentPlayer.name == TicTacToeState.CIRCLE.toString())
                {
                    turn = true
                }
            }
            if(turn) {
                Box(
                    modifier = Modifier
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .clip(RoundedCornerShape(20.dp))
                        .padding(16.dp)
                        .size(100.dp, 40.dp),
                ) {
                    Image(
                        painterResource(id = R.drawable.img),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp),
                        contentScale = ContentScale.Fit,
                    )
                }
                Spacer(modifier = Modifier.padding(horizontal = 40.dp))
                Box(
                    modifier = Modifier
                        .background(brush = turnColor, RoundedCornerShape(8.dp))
                        .clip(RoundedCornerShape(20.dp))
                        .padding(16.dp)
                        .size(100.dp, 40.dp),
                ) {
                    Image(
                        painterResource(id = R.drawable.img3),
                        contentDescription = "",
                        modifier = Modifier.size(200.dp),
                        contentScale = ContentScale.Fit
                    )

                }
            }
            else {
                Box(
                    modifier = Modifier
                        .background(brush = turnColor, RoundedCornerShape(8.dp))
                        .clip(RoundedCornerShape(20.dp))
                        .padding(16.dp)
                        .size(100.dp, 40.dp),
                ) {
                    Image(
                        painterResource(id = R.drawable.img),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp),
                        contentScale = ContentScale.Fit,
                    )
                }
                Spacer(modifier = Modifier.padding(horizontal = 40.dp))
                Box(
                    modifier = Modifier
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .clip(RoundedCornerShape(20.dp))
                        .padding(16.dp)
                        .size(100.dp, 40.dp),
                ) {
                    Image(
                        painterResource(id = R.drawable.img3),
                        contentDescription = "",
                        modifier = Modifier.size(100.dp),
                        contentScale = ContentScale.Fit
                    )

                }
            }
        }
        if (!isGameOver) {
            CountdownTimer(timer) {
                timer = it
                if (it == 0) {
                    isGameOver = true
                }
            }
        }

        TicTacToeBoard(
            board = board,
            onCellClick = { index ->
                if (!isGameOver && board[index] == TicTacToeState.Empty) {
                    board = board.toMutableList().also {
                        it[index] = currentPlayer
                    }
                    if (hasWinningPattern(board)) {
                        winner = currentPlayer
                        isGameOver = true
                        winningPattern = getWinningPattern(board)
                    } else if (board.all { it != TicTacToeState.Empty }) {
                        isGameOver = true
                    } else {
                        currentPlayer = if (currentPlayer == TicTacToeState.CROSS) {
                            TicTacToeState.CIRCLE
                        } else {
                            TicTacToeState.CROSS
                        }
                    }
                }
            },
            winningPattern = winningPattern
        )

        if (hasWinner) {
            winner = currentPlayer
            isGameOver = true
        }

        Button(
            onClick = {
                resetGame()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.d)
            )
        ) {
            Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Restart Game")
        }
    }
}

@Composable
fun CountdownTimer(
    initialTime: Int,
    onTick: (Int) -> Unit
) {
    var time by remember { mutableStateOf(initialTime) }

    DisposableEffect(Unit) {
        val coroutineScope = CoroutineScope(Dispatchers.Default)
        val job = coroutineScope.launch {
            while (time > 0) {
                delay(1000)
                time--
                onTick(time)
            }
        }
        onDispose {
            job.cancel()
        }
    }

    if(time != initialTime){
        time = initialTime
    }

    Text(
        text = "Time Left:\n    $time",
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        fontFamily = FontFamily.Cursive,
        color = if (time <= 10)
            Color.Red
        else
            Color.Black
    )
}

@Preview(showSystemUi = true)
@Composable
fun Show(){
    TicTacToeGame(navController = rememberNavController())
}