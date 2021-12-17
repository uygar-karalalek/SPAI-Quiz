package com.ameti.quiz.fragment

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import com.ameti.quiz.MainActivity
import com.ameti.quiz.R
import com.ameti.quiz.db.QuizDatabaseManager

class GameFragment : Fragment() {
    var questions = mutableListOf<QuizDatabaseManager.Question>()

    lateinit var dbManager: QuizDatabaseManager
    lateinit var sharedPreferences: SharedPreferences
    lateinit var rootView: View
    lateinit var answer1: Button
    lateinit var answer2: Button
    lateinit var answer3: Button
    lateinit var answer4: Button
    lateinit var answers: MutableList<Button>
    var count = 0
    var score = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbManager = (this.context as MainActivity).dbManager
        sharedPreferences = (this.context as MainActivity).sharedPreferences
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_game, container, false)
        rootView = inflate.rootView

        var user = sharedPreferences.getString("user", null)

        if(arguments != null) {
            if(requireArguments().get("categoryId") != null) {
                val categoryId = requireArguments().get("categoryId").toString()
                questions = dbManager.getQuestions(categoryId)
            }
        }
        answer1 = rootView.findViewById(R.id.risposta1)
        answer2 = rootView.findViewById(R.id.risposta2)
        answer3 = rootView.findViewById(R.id.risposta3)
        answer4 = rootView.findViewById(R.id.risposta4)

        answers = mutableListOf(answer1, answer2, answer3, answer4)
        answers.forEach {
            it.setOnClickListener { buttonClickHandler(it as Button) }
        }

        setData();

        rootView.findViewById<Button>(R.id.avanti).setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        { view: View ->
            count++;
            if(count >= (questions.size)) {
                val bundle = Bundle();
                bundle.putString("puntiFatti", score.toString());
                dbManager.updatePoints(user.toString(), score)
                view.findNavController().navigate(R.id.action_gameFragment_to_gameFinishFragment, bundle)
            }
            else {
                setData();
                initialiseAllButtons()
            }
        }

        return inflate
    }

    private fun setData() {
        rootView.findViewById<TextView>(R.id.id_domanda).text = "Domanda " + (count + 1).toString()
        rootView.findViewById<TextView>(R.id.domanda).text = questions.get(count).value;
        answers.forEachIndexed { id, risposta ->
            risposta.text = questions.get(count).answers[id]
        }
    }

    private fun buttonClickHandler(risposta: Button) {
        val rispostaId = risposta.id;
        disableAllButtons();
        when(rispostaId) {
            R.id.risposta1 -> { checkAnswer(answer1) }
            R.id.risposta2 -> checkAnswer(answer2)
            R.id.risposta3 -> checkAnswer(answer3)
            R.id.risposta4 -> checkAnswer(answer4)
        }
    }

    private fun checkAnswer(risposta: Button) {
        var rightAnswer = questions.get(count).answers.get(questions.get(count).wright - 1);
        if(risposta.text.equals(rightAnswer)) {
            risposta.setBackgroundColor(Color.GREEN)
            score += questions.get(count).win_points
        }
        else {
            risposta.setBackgroundColor(Color.RED)
            val rispostaGiustaButton: Button = answers.filter { it.text == rightAnswer }.get(0)
            rispostaGiustaButton.setBackgroundColor(Color.GREEN)
        }
    }

    private fun disableAllButtons() {
        answers.forEach {
            it.isEnabled = false;
        }
    }

    private fun initialiseAllButtons() {
        answers.forEach {
            it.isEnabled = true;
            it.setBackgroundColor(Color.parseColor("#FF9C27B0"))
        }
    }

}