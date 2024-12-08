package com.example.kr.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.kr.R
import com.example.kr.data.Repository
import com.example.kr.model.MediaItem
import com.example.kr.viewmodel.MediaItemViewModel
import com.example.kr.viewmodel.MediaItemViewModelFactory
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class AddItemFragment : Fragment() {

    private lateinit var viewModel: MediaItemViewModel
    private lateinit var editTextName: TextInputEditText
    private lateinit var editTextDescription: TextInputEditText
    private lateinit var editTextGenre: TextInputEditText
    private lateinit var editTextReleaseYear: TextInputEditText
    private lateinit var editTextRating: TextInputEditText
    private lateinit var editTextAuthorOrDirector: TextInputEditText
    private lateinit var editTextImageUrl: TextInputEditText
    private lateinit var buttonSave: MaterialButton

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val factory = MediaItemViewModelFactory(Repository(requireContext()))
        viewModel = ViewModelProvider(this, factory).get(MediaItemViewModel::class.java)

        val rootView = inflater.inflate(R.layout.fragment_add_item, container, false)

        // Знаходимо компоненти
        val textInputLayoutName = rootView.findViewById<TextInputLayout>(R.id.textInputLayoutName)
        val textInputLayoutDescription = rootView.findViewById<TextInputLayout>(R.id.textInputLayoutDescription)
        val textInputLayoutGenre = rootView.findViewById<TextInputLayout>(R.id.textInputLayoutGenre)
        val textInputLayoutReleaseYear = rootView.findViewById<TextInputLayout>(R.id.textInputLayoutReleaseYear)
        val textInputLayoutRating = rootView.findViewById<TextInputLayout>(R.id.textInputLayoutRating)
        val textInputLayoutAuthorOrDirector = rootView.findViewById<TextInputLayout>(R.id.textInputLayoutAuthorOrDirector)
        val textInputLayoutImageUrl = rootView.findViewById<TextInputLayout>(R.id.textInputLayoutImageUrl)

        editTextName = textInputLayoutName.findViewById(R.id.editTextName)
        editTextDescription = textInputLayoutDescription.findViewById(R.id.editTextDescription)
        editTextGenre = textInputLayoutGenre.findViewById(R.id.editTextGenre)
        editTextReleaseYear = textInputLayoutReleaseYear.findViewById(R.id.editTextReleaseYear)
        editTextRating = textInputLayoutRating.findViewById(R.id.editTextRating)
        editTextAuthorOrDirector = textInputLayoutAuthorOrDirector.findViewById(R.id.editTextAuthorOrDirector)
        editTextImageUrl = textInputLayoutImageUrl.findViewById(R.id.editTextImageUrl)
        buttonSave = rootView.findViewById(R.id.buttonSave)


        buttonSave.setOnClickListener {
            val name = editTextName.text.toString().trim()
            val description = editTextDescription.text.toString().trim()
            val genre = editTextGenre.text.toString().trim()
            val releaseYear = editTextReleaseYear.text.toString().toIntOrNull() ?: 0
            val rating = editTextRating.text.toString().toFloatOrNull() ?: 0f
            val authorOrDirector = editTextAuthorOrDirector.text.toString().trim()
            val imageUrl = editTextImageUrl.text.toString().trim()

            if (name.isNotBlank() && description.isNotBlank() && genre.isNotBlank() &&
                releaseYear > 0 && rating > 0f && authorOrDirector.isNotBlank() && imageUrl.isNotBlank()) {
                val item = MediaItem(
                    title = name,
                    description = description,
                    genre = genre,
                    releaseYear = releaseYear,
                    rating = rating,
                    authorOrDirector = authorOrDirector,
                    imageUrl = imageUrl
                )
                viewModel.addMediaItem(item)

                viewModel.fetchMediaItems()
                findNavController().navigateUp()
            } else {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        return rootView
    }
}
