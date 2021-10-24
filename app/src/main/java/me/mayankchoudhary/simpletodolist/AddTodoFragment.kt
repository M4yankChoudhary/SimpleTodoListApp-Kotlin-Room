package me.mayankchoudhary.simpletodolist

import android.app.Dialog
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import me.mayankchoudhary.simpletodolist.databinding.FragmentAddTodoBinding
import me.mayankchoudhary.simpletodolist.viewModel.TodoViewModel
import me.mayankchoudhary.simpletodolist.viewModel.TodoViewModelFactory

class AddTodoFragment : DialogFragment() {

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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

//        AlertDialog.Builder(requireContext())
//            .setMessage("Hello")
//            .setPositiveButton("") { _,_ -> }
//            .create()
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_round_bg);
        // Inflate the layout for this fragment
        _binding = FragmentAddTodoBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    companion object {
        const val TAG = "PurchaseConfirmationDialog"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            addButton.setOnClickListener {
                if (taskName.text.isNullOrEmpty()) {
                    Toast.makeText(context, "Please enter your task name", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    addNewTodo()
//                    val action = AddTodoFragmentDirections.actionAddTodoFragmentToToodoListFragment()
//                    findNavController().navigate(action)
                }
            }
            taskName.setOnKeyListener(object : View.OnKeyListener {
                override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                    if ((event?.action == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)
                    ) {
                        // Perform action on key press
                        addNewTodo()
                        return true;
                    }
                    return false;
                }

            })
        }

    }

    fun addNewTodo() {
        viewModel.addNewTodo(
            binding?.taskName?.text.toString()
        )
        Toast.makeText(context, "Todo Added Successfully", Toast.LENGTH_SHORT).show()
        dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}