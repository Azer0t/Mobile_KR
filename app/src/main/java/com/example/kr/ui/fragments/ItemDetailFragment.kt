package com.example.kr.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.kr.R
import com.example.kr.data.Repository
import com.example.kr.viewmodel.MediaItemViewModel
import com.example.kr.viewmodel.MediaItemViewModelFactory
import com.google.android.material.button.MaterialButton

class ItemDetailFragment : Fragment() {

    private lateinit var viewModel: MediaItemViewModel
    private lateinit var textName: TextView
    private lateinit var textDescription: TextView
    private lateinit var textGenre: TextView
    private lateinit var textReleaseYear: TextView
    private lateinit var textRating: TextView
    private lateinit var textAuthorOrDirector: TextView
    private lateinit var imageViewItem: ImageView
    private lateinit var buttonDelete: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_item_detail, container, false)

        // Ініціалізація Repository і ViewModel через фабрику
        val repository = Repository(requireContext())
        val factory = MediaItemViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[MediaItemViewModel::class.java]

        textName = rootView.findViewById(R.id.textName)
        textDescription = rootView.findViewById(R.id.textDescription)
        textGenre = rootView.findViewById(R.id.textGenre)
        textReleaseYear = rootView.findViewById(R.id.textReleaseYear)
        textRating = rootView.findViewById(R.id.textRating)
        textAuthorOrDirector = rootView.findViewById(R.id.textAuthorOrDirector)
        imageViewItem = rootView.findViewById(R.id.imageViewItem)
        buttonDelete = rootView.findViewById(R.id.buttonDelete)
        // Отримуємо ID елемента з аргументів
        val itemId = arguments?.getInt("itemId") ?: 0
        viewModel.fetchMediaItems()

        // Спостерігаємо за змінами в списку елементів
        viewModel.mediaItems.observe(viewLifecycleOwner) { items ->
            val selectedItem = items.find { it.id == itemId }
            if (selectedItem != null) {
                textName.text = selectedItem.title
                textDescription.text = selectedItem.description
                textGenre.text = selectedItem.genre
                textReleaseYear.text = selectedItem.releaseYear.toString()
                textRating.text = selectedItem.rating.toString()
                textAuthorOrDirector.text = selectedItem.authorOrDirector
                Glide.with(requireContext())
                    .load(selectedItem.imageUrl)
                    .into(imageViewItem)
            }
        }

        // Обробка кнопки Delete для видалення елемента
        buttonDelete.setOnClickListener {
            val selectedItem = viewModel.mediaItems.value?.find { it.id == itemId }
            selectedItem?.let {
                viewModel.deleteMediaItem(it.id)
                findNavController().navigateUp()  // Повертаємось до попереднього фрагмента
            }
        }

        return rootView
    }
}
