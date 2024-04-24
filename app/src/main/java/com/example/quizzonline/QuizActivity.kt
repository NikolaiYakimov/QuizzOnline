package com.example.quizzonline

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quizzonline.databinding.ActivityQuizBinding
import com.example.quizzonline.databinding.QuizItemRecyclerRowBinding
import com.example.quizzonline.databinding.ScoreDoalogBinding

class QuizActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        var questionModelList: List<QuestionModel> = listOf()
        var time: String = ""
    }

    lateinit var binding: ActivityQuizBinding
    var currentQuestionIndex = 0;
    var selectedAnswer = ""
    var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            btn0.setOnClickListener(this@QuizActivity)
            btn1.setOnClickListener(this@QuizActivity)
            btn2.setOnClickListener(this@QuizActivity)
            btn3.setOnClickListener(this@QuizActivity)
            btnNext.setOnClickListener(this@QuizActivity)
        }
        loadQuestions()
        startTimer()

    }
    private fun startTimer() {
        val totalTimeInMilliSec = time.toInt() * 60 * 1000L
        object : CountDownTimer(totalTimeInMilliSec, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                val second = millisUntilFinished / 1000
                val minutes = second / 60;
                val remainingSeconds = second % 60
                binding.timerIndicatorTextview.text =
                    String.format("%02d:%02d", minutes, remainingSeconds)
            }

            override fun onFinish() {
                //Finish the quiz
                finishQuiz()
            }

        }.start()

    }
    private fun loadQuestions() {
        selectedAnswer = ""
        if (currentQuestionIndex == questionModelList.size) {
            finishQuiz()
            return
        }
        binding.apply {
            questionIndicatorTextview.text =
                "Question ${currentQuestionIndex + 1}/${questionModelList.size}"
            questionProgressIndicator.progress =
                ((currentQuestionIndex).toFloat() / questionModelList.size.toFloat() * 100).toInt()
            questionTextviewBlock.text = questionModelList[currentQuestionIndex].question
            btn0.text = questionModelList[currentQuestionIndex].options[0]
            btn1.text = questionModelList[currentQuestionIndex].options[1]
            btn2.text = questionModelList[currentQuestionIndex].options[2]
            btn3.text = questionModelList[currentQuestionIndex].options[3]

        }
    }
    override fun onClick(view: View?) {
        binding.apply {
            btn0.setBackgroundColor(getColor(R.color.gray))
            btn1.setBackgroundColor(getColor(R.color.gray))
            btn2.setBackgroundColor(getColor(R.color.gray))
            btn3.setBackgroundColor(getColor(R.color.gray))
        }
        val clickedBtn = view as Button
        if (clickedBtn.id == R.id.btn_next) {
            if (selectedAnswer == questionModelList[currentQuestionIndex].correctOption) {
                score++;
                Log.i("Score of quiz", score.toString())
            }
            currentQuestionIndex++
            loadQuestions()
        } else {
            selectedAnswer = clickedBtn.text.toString()
            clickedBtn.setBackgroundColor(getColor(R.color.orange))
        }
    }
    private fun finishQuiz() {
        val totalQuestion = questionModelList.size
        val percentage = ((score.toFloat() / totalQuestion.toFloat()) * 100).toInt()
        val dialogBinding = ScoreDoalogBinding.inflate(layoutInflater)
        dialogBinding.apply {
            scoreProgressIndicator.progress = percentage
            scoreProgressTextPercentage.text = "$percentage %"
            if (percentage > 60) {
                scoreTitle.text = "Congrats!You have passed"
                scoreTitle.setTextColor(Color.BLUE)
            } else {
                scoreTitle.text = "Ops!You have failed"
                scoreTitle.setTextColor(Color.RED)
            }
            scoreSubtitle.text = "$score out of $totalQuestion are correct"
            finishButton.text = "Finish"
            finishButton.setOnClickListener {
                finish()
            }
        }
        AlertDialog.Builder(this).setView(dialogBinding.root).setCancelable(false).show()
    }
    }

