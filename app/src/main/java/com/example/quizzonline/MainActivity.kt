package com.example.quizzonline

import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizzonline.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var quizModelList: MutableList<QuizModel>
    lateinit var adapter: QuizListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
//        enableEdgeToEdge()
        setContentView(binding.root)
        quizModelList= mutableListOf()
        getDataFromFirebase()

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
    }
    private fun setupRecyclerView(){
        adapter= QuizListAdapter(quizModelList)
        binding.recyclerView?.layoutManager =LinearLayoutManager(this)
        binding.recyclerView?.adapter=adapter
    }

    private fun getDataFromFirebase(){
        val listOfQuestionModel= mutableListOf<QuestionModel>()
        listOfQuestionModel.add(QuestionModel("What is android?", mutableListOf("Language","OS","Product","None"),"OS"))
        listOfQuestionModel.add(QuestionModel("Who own android?", mutableListOf("Apple","Google","Samsung","Huawei"),"Google"))
        listOfQuestionModel.add(QuestionModel("Which assistant android use?", mutableListOf("Siri","Cortana","Google Assistant","Alexa"),"Google"))

        quizModelList.add(QuizModel("1","Programming","Basic programming","10",listOfQuestionModel))
        quizModelList.add(QuizModel("2","Computer","Computer questions","20",listOfQuestionModel))
//        quizModelList.add(QuizModel("3","Geography","Boost your knowledge","14"))
//        quizModelList.add(QuizModel("4","History","History about our country","16"))
//        quizModelList.add(QuizModel("5","Car","Car mechanics","25"))
//        quizModelList.add(QuizModel("6","Math","Math algorithms","30"))
        setupRecyclerView()
    }

}