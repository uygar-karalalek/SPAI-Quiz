package com.ameti.quiz.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.ameti.quiz.R

class CategoriesFragment : Fragment() {
    data class Category(
        val id: Int,
        val name: String
    )

    private val categories: MutableList<Category> = mutableListOf(
        Category(
            id = 1,
            name = "Film + Serie"
        ),
        Category(
            id = 2,
            name = "Cultura generale"
        ),
        Category(
            id = 3,
            name = "Musica"
        ),
        Category(
            id = 4,
            name = "Programmazione"
        )
    )
    lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val inflate = inflater.inflate(R.layout.fragment_categorie, container, false)
        rootView = inflate.rootView

        val bundle: Bundle = Bundle();
        bundle.putString("categoryId", "1");

        val category1: Button = rootView.findViewById(R.id.category1)
        val category2: Button = rootView.findViewById(R.id.category2)
        val category3: Button = rootView.findViewById(R.id.category3)
        val category4: Button = rootView.findViewById(R.id.category4)
        val categoryButtons: MutableList<Button> = mutableListOf<Button>(category1, category2, category3, category4)
        categoryButtons.forEachIndexed{ index, button ->
            button.text = categories[index].name;
            bundle.putString("categoryId", categories[index].id.toString())
            button.setOnClickListener {
                it.findNavController().navigate(R.id.action_categoriesFragment_to_gameFragment, bundle)
            }
        }
        category1.setOnClickListener {
            it.findNavController().navigate(R.id.action_categoriesFragment_to_gameFragment)
        }

        return inflate
    }

}