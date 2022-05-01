package com.matheus.bianchini.nybooksapp.presentation.books

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.matheus.bianchini.nybooksapp.R
import com.matheus.bianchini.nybooksapp.databinding.BooksFragmentBinding

class BooksFragment : Fragment() {
    private var _binding: BooksFragmentBinding? = null
    private val binding: BooksFragmentBinding get() = _binding!!

    private lateinit var viewModel: BooksViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BooksFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[BooksViewModel::class.java]

        setUpRvBooksBehaviour()
        setUpViewFlipperBehaviour()

        viewModel.getBooks()
    }

    private fun setUpViewFlipperBehaviour() {
        viewModel.viewFlipperLiveData.observe(viewLifecycleOwner) {
            it?.let { viewFlipper ->
                binding.vfMain.displayedChild = viewFlipper.first
                viewFlipper.second?.let { errorMessageId ->
                    binding.tvErrorMessage.text = getString(errorMessageId)
                }
            }
        }
    }

    private fun setUpRvBooksBehaviour() {
        viewModel.booksLiveData.observe(viewLifecycleOwner) {
            it?.let { booksList ->
                with(binding.rvBooks) {
                    layoutManager = LinearLayoutManager(
                        context,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    val dividerItemDecoration = DividerItemDecoration(
                        context,
                        DividerItemDecoration.VERTICAL,
                    )
                    dividerItemDecoration.setDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.dividor
                        )!!
                    )
                    addItemDecoration(dividerItemDecoration)
                    adapter = BooksAdapter(booksList)
                    setHasFixedSize(true)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}