package com.negiji.ttt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener{


    var PLAYER = true
    var TURN_COUNT = 0
    var boardStatus = Array(3){IntArray(3)}

    lateinit var board : Array<Array<Button>>                                                          //array initialization
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        board = arrayOf(                                                                             // Array Declaration
            arrayOf(btn1, btn2, btn3),
            arrayOf(btn4, btn5, btn6),
            arrayOf(btn7, btn8, btn9)
        )

        for(i in board){
            for(a in i){
                a.setOnClickListener(this)
            }
        }

        initializeBoardStatus()

        btn_reset.setOnClickListener {
            TURN_COUNT = 0
            initializeBoardStatus()
            tv1.text = "Player 'X' Turn"
            PLAYER = true
        }
    }

   private fun initializeBoardStatus(){

       for(i in 0..2){
           for(j in 0..2){
               boardStatus[i][j] = -1
           }
       }

       for(i in board){
           for(button in i){
               button.isEnabled = true
               button.text = ""
           }
       }

   }



    override fun onClick(view: View) {

            when(view.id){
                R.id.btn1 -> updateValue(row = 0, col = 0, player=PLAYER)
                R.id.btn2 -> updateValue(row = 0, col = 1, player=PLAYER)
                R.id.btn3 -> updateValue(row = 0, col = 2, player=PLAYER)
                R.id.btn4 -> updateValue(row = 1, col = 0, player=PLAYER)
                R.id.btn5 -> updateValue(row = 1, col = 1, player=PLAYER)
                R.id.btn6 -> updateValue(row = 1, col = 2, player=PLAYER)
                R.id.btn7 -> updateValue(row = 2, col = 0, player=PLAYER)
                R.id.btn8 -> updateValue(row = 2, col = 1, player=PLAYER)
                R.id.btn9 -> updateValue(row = 2, col = 2, player=PLAYER)
            }
        Log.i("try", "Btn Click")
        TURN_COUNT++
        PLAYER=!PLAYER
        if(PLAYER) updateDisplay("Player 'X' Turn")
        else updateDisplay("Player 'O' Turn")

        if(TURN_COUNT==9){
            updateDisplay("!! Game Draw !!")
        }
        checkWinner()

    }

    private fun updateValue(row: Int, col: Int, player: Boolean) {
        val text: String = if(player) "X" else "O"

        board[row][col].apply {
            isEnabled = false
            setText(text)
        }

        boardStatus[row][col]= if(player) 1 else 0
    }


    private fun checkWinner(){
        //Horizontal rows
        for(i in 0..2){

            if(boardStatus[i][0] == boardStatus[i][1] && boardStatus[i][0] == boardStatus[i][2]){
                if(boardStatus[i][0]==1){
                    updateDisplay("WINNER X")
                    break
                }else if(boardStatus[i][0] == 0){
                    updateDisplay("WINNER O")
                    break
                }
            }
        }

        for(i in 0..2){

            if(boardStatus[0][i] == boardStatus[1][i] && boardStatus[0][i] == boardStatus[2][i]){
                if(boardStatus[0][i]==1){
                    updateDisplay("WINNER X")
                    break
                }else if(boardStatus[0][i] == 0){
                    updateDisplay("WINNER O")
                    break
                }
            }
        }

        if (boardStatus[0][0] == boardStatus[1][1] && boardStatus[0][0] == boardStatus[2][2]){
            if(boardStatus[0][0] == 1){
                updateDisplay("WINNER X")
            }
            else if(boardStatus[0][0] == 0){
                updateDisplay("winner O")
            }
        }

        if (boardStatus[0][2] == boardStatus[1][1] && boardStatus[0][2] == boardStatus[2][0]){
            if(boardStatus[0][2] == 1){
                updateDisplay("WINNER X")
            }
            else if(boardStatus[0][2] == 0){
                updateDisplay("winner O")
            }
        }


    }

    private fun updateDisplay(s: String) {
        tv1.text = s
        if(s.contains("WINNER")) disableButton()
    }

    private fun disableButton(){
        for(i in board){
            for(button in i){
                button.isEnabled = false
            }
        }
    }



}