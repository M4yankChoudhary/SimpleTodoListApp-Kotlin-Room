package me.mayankchoudhary.simpletodolist

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
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
        setHasOptionsMenu(true)
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = TodoListAdapter({
            viewModel.deleteTodo(it.id)
            Toast.makeText(
                context, "Todo deleted successfully",
                Toast.LENGTH_SHORT
            ).show()
//            binding.searchEditText.setText("")
        }, { check, id ->
            viewModel.updateTodoItem(check, id)
//            binding.searchEditText.setText("")
        })
        binding.apply {
            recylerView.adapter = adapter
            floatingActionButton.setOnClickListener {
                val action = TodoListFragmentDirections.actionToodoListFragmentToAddTodoFragment()
                findNavController().navigate(action)
            }
            viewModel?.allTodos?.observe(viewLifecycleOwner) { todo ->
                progressBar.visibility = View.GONE
                if (todo.isNullOrEmpty()) {
                    emptyImage.visibility = View.VISIBLE
                    emptyText.visibility = View.VISIBLE
                } else {
                    emptyImage.visibility = View.GONE
                    emptyText.visibility = View.GONE
                }
//                searchEditText.addTextChangedListener { text: Editable? ->
//                    val listFromSearch: List<Todo> =
//                        todo.filter { s -> s.name.contains(text.toString(), true) }
//                    adapter.submitList(listFromSearch)
//                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_item, menu)
        val search = menu.findItem(R.id.my_search)
        val searchView = search.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.getSearched(query)
                }
                Log.d("My", "Hello")
                searchView.clearFocus()
                binding.recylerView.scrollToPosition(0)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
        searchView.addOnAttachStateChangeListener(object: View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View?) {

            }

            override fun onViewDetachedFromWindow(v: View?) {
                viewModel.getSearched("")
            }

        })
    }
}