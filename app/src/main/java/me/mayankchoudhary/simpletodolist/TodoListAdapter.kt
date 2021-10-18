package me.mayankchoudhary.simpletodolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.mayankchoudhary.simpletodolist.databinding.SingleListItemBinding
import me.mayankchoudhary.simpletodolist.model.Todo

class TodoListAdapter : ListAdapter<Todo, TodoListAdapter.TodoListViewHolder>(TodoComparator()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TodoListViewHolder {
        val binding = SingleListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class TodoListViewHolder(private val binding: SingleListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo) {
            binding.apply {
                task.text = todo.name
            }
        }
    }


    // comparator

    class TodoComparator : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean =
            oldItem == newItem

    }

}