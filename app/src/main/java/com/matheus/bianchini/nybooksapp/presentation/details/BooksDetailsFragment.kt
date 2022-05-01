package com.matheus.bianchini.nybooksapp.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.matheus.bianchini.nybooksapp.databinding.FragmentBooksDetailsBinding

class BooksDetailsFragment : Fragment() {
    private var _binding: FragmentBooksDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: BooksDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBooksDetailsBinding.inflate(layoutInflater, container, false)
        return binding.apply {
            binding.tvTitle.text = args.title
            binding.tvDescription.text = args.description
        }.root
    }
}