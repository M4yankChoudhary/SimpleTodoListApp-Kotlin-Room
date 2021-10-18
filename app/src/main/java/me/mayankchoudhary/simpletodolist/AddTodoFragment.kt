package me.mayankchoudhary.simpletodolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import me.mayankchoudhary.simpletodolist.databinding.FragmentAddTodoBinding
import me.mayankchoudhary.simpletodolist.viewModel.TodoViewModel
import me.mayankchoudhary.simpletodolist.viewModel.TodoViewModelFactory

class AddTodoFragment : Fragment() {

    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
    // to share the ViewModel across fragments.
    private var _binding: FragmentAddTodoBinding? = null
    private val binding
        get() = _binding
    private val viewModel: TodoViewModel by activityViewModels {
        TodoViewModelFactory(
            (activity?.application as TodoApplication).database
                .todoDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddTodoBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            addButton.setOnClickListener{
                if(taskName.text.isNullOrEmpty()) {
                    Toast.makeText(context, "Please enter your task name", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.addNewTodo(
                        taskName.text.toString()
                    )
                    val action = AddTodoFragmentDirections.actionAddTodoFragmentToToodoListFragment()
                    findNavController().navigate(action)
                    Toast.makeText(context, "Todo Added Successfully", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}