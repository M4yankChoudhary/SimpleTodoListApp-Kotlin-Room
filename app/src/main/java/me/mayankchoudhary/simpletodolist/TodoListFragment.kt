package me.mayankchoudhary.simpletodolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import me.mayankchoudhary.simpletodolist.databinding.FragmentToodoListBinding
import me.mayankchoudhary.simpletodolist.model.Todo
import me.mayankchoudhary.simpletodolist.viewModel.TodoViewModel
import me.mayankchoudhary.simpletodolist.viewModel.TodoViewModelFactory

class TodoListFragment : Fragment() {

    private var _binding: FragmentToodoListBinding? = null
    private val binding get() = _binding!!

    var currentTodo: Todo? = null

//    private val recyclerView: RecyclerView? = null

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
        _binding =
            FragmentToodoListBinding.inflate(layoutInflater, container, false)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this
        // Giving the binding access to the TodoViewModel
        binding.viewModel = viewModel
        binding.recylerView.adapter = TodoListAdapter({
            viewModel.deleteTodo(it.id)
        }, { check, id ->
            viewModel.updateTodoItem(check, id)
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        Toast.makeText(context, "Todo deleted successfully", Toast.LENGTH_SHORT).show()
        binding.apply {
//            recylerView?.adapter = adapter
            floatingActionButton.setOnClickListener {
                val action = TodoListFragmentDirections.actionToodoListFragmentToAddTodoFragment()
                findNavController().navigate(action)
            }
//            viewModel.allTodos.observe(viewLifecycleOwner) { todo ->
//                adapter.submitList(todo)
//            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}