package me.mayankchoudhary.simpletodolist

import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import me.mayankchoudhary.simpletodolist.model.Todo

@BindingAdapter("listData")
fun bindRecyclerView(
    recyclerView: RecyclerView,
    data: List<Todo>?
) {
    val adapter = recyclerView.adapter as TodoListAdapter
    adapter.submitList(data)
}