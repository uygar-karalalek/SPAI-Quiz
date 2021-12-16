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
    data class Domanda(
        val domanda: String,
        val risposte: List<String>,
        val rispostaGiusta: String,
        val punti: Int
    )

    private val domande: MutableList<Domanda> = mutableListOf(
        Domanda(domanda = "Quale cantante canta la canzone \"Roar\"?",
                risposte = listOf("Katy Perry", "Miley Cyrus", "Rita Ora", "Adele"),
                rispostaGiusta = "Katy Perry",
                punti = 10

        ),
        Domanda(domanda = "Quale cantante canta la canzone \"Snowman\"?",
            risposte = listOf("Dua Lipa", "Pink", "Sia", "Beyonce"),
            rispostaGiusta = "Sia",
            punti = 20
        )
    )

    lateinit var dbManager: QuizDatabaseManager
    lateinit var sharedPreferences: SharedPreferences
    lateinit var rootView: View
    lateinit var risposta1: Button
    lateinit var risposta2: Button
    lateinit var risposta3: Button
    lateinit var risposta4: Button
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


        risposta1 = rootView.findViewById(R.id.risposta1)
        risposta1.setOnClickListener { controllaRisposta(risposta1) }

        risposta2 = rootView.findViewById(R.id.risposta2)
        risposta2.setOnClickListener { controllaRisposta(risposta2) }

        risposta3 = rootView.findViewById(R.id.risposta3)
        risposta3.setOnClickListener { controllaRisposta(risposta3) }

        risposta4 = rootView.findViewById(R.id.risposta4)

        setData();

        rootView.findViewById<Button>(R.id.avanti).setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        { view: View ->
            count++;
            if(count >= (domande.size)) {
                val bundle: Bundle = Bundle();
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
        rootView.findViewById<TextView>(R.id.domanda).text = domande.get(count).domanda;
        risposta1.text = domande.get(count).risposte[0]
        risposta2.text = domande.get(count).risposte[1]
        risposta3.text = domande.get(count).risposte[2]
        risposta4.text = domande.get(count).risposte[3]
    }

    private fun controllaRisposta(risposta: Button) {
        val rispostaId = risposta.id;
        disableAllButtons();
        when(rispostaId) {
            R.id.risposta1 -> {
                colorBottone(risposta1)
            }
            R.id.risposta2 -> colorBottone(risposta2)
            R.id.risposta3 -> colorBottone(risposta3)
            R.id.risposta4 -> colorBottone(risposta4)
        }
    }

    private fun colorBottone(risposta: Button) {
        if(risposta.text.equals(domande.get(count).rispostaGiusta)) {
            risposta.setBackgroundColor(Color.GREEN)
            score += domande.get(count).punti
        }
        else {
            risposta.setBackgroundColor(Color.RED)
            var risposte: MutableList<Button> = mutableListOf()
            risposte.add(risposta1)
            risposte.add(risposta2)
            risposte.add(risposta3)
            risposte.add(risposta4)
            val rispostaGiustaButton: Button = risposte.filter { it.text == domande.get(count).rispostaGiusta }.get(0)
            rispostaGiustaButton.setBackgroundColor(Color.GREEN)
        }
    }

    private fun disableAllButtons() {
        risposta1.isEnabled = false;
        risposta2.isEnabled = false;
        risposta3.isEnabled = false;
        risposta4.isEnabled = false;
    }

    private fun initialiseAllButtons() {
        var risposte: MutableList<Button> = mutableListOf()
        risposte.add(risposta1)
        risposte.add(risposta2)
        risposte.add(risposta3)
        risposte.add(risposta4)
        risposte.forEach {
            it.isEnabled = true;
            it.setBackgroundColor(Color.parseColor("#FF9C27B0"))
        }
    }

}