package com.matheus.bianchini.nybooksapp.presentation.books

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.matheus.bianchini.nybooksapp.data.model.Book
import com.matheus.bianchini.nybooksapp.databinding.ItemBookBinding

class BooksAdapter(
    private val books: List<Book>
) :
    RecyclerView.Adapter<BooksAdapter.BooksViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BooksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        holder.bindView(books[position])
    }

    override fun getItemCount(): Int = books.count()

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class BooksViewHolder(view: ItemBookBinding) : RecyclerView.ViewHolder(view.root) {
        private val title = view.tvBookName
        private val author = view.tvBookAuthor
        private val btnDetails = view.imDetails

        fun bindView(book: Book) {
            title.text = book.title
            author.text = book.author
            btnDetails.setOnClickListener {
                val action = BooksFragmentDirections.actionBooksFragmentToBooksDetailsFragment(
                    book.title,
                    book.description
                )
                itemView.findNavController()
                    .navigate(action)
            }
        }
    }
}

