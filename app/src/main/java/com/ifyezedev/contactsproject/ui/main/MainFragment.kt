package com.ifyezedev.contactsproject.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ifyezedev.contactsproject.Contact
import com.ifyezedev.contactsproject.R
import com.ifyezedev.contactsproject.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var adapter: ContactListAdapter? = null

    val viewModel: MainViewModel by viewModels()

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listenerSetup()
        observerSetup()
        recyclerviewSetup()
    }

    private fun recyclerviewSetup() {
        adapter = ContactListAdapter(R.layout.list_item){position -> onListItemClick(position)}
        binding.contactsRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.contactsRecyclerview.adapter = adapter
    }

    private fun onListItemClick(position: Int) {
        val contact = adapter?.getContactAtPosition(position)
        if (contact != null) {
            viewModel.deleteContact(contact.id)
        }
    }

    private fun observerSetup() {
        viewModel.getAllContacts()?.observe(this, Observer { contacts ->
            contacts?.let {
                adapter?.setContactsList(it)
            }
        })

        viewModel.getSearchResults().observe(this, Observer { contacts ->
            if (contacts.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "There are no contacts that match your search", Toast.LENGTH_SHORT).show()
            }
            else {
                adapter?.setContactsList(contacts)
            }
        })

        viewModel.getAllContactsASCResult().observe(this, Observer { contacts ->
                adapter?.setContactsList(contacts)
        })

        viewModel.getAllContactsDESCResult().observe(this, Observer { contacts ->
                adapter?.setContactsList(contacts)
        })

    }

    private fun listenerSetup() {
        binding.addButton.setOnClickListener{addContact()}
        binding.findButton.setOnClickListener{findContact()}
        binding.ascButton.setOnClickListener{arrangeContactsInASC()}
        binding.descButton.setOnClickListener{arrangeContactsInDESC()}
    }

    private fun arrangeContactsInDESC() {
        viewModel.getAllContactsDESC()
    }

    private fun arrangeContactsInASC() {
        viewModel.getAllContactsASC()
    }

    private fun findContact() {
        val searchCriteria = binding.nameEditText.text.toString()

        if (searchCriteria.isBlank()) {
            Toast.makeText(requireContext(), "You must enter a search criteria in the name field", Toast.LENGTH_SHORT).show()
        }
        else {
            viewModel.findContact(searchCriteria)
            clearFields()
        }
    }

    private fun addContact() {
        val name = binding.nameEditText.text.toString()
        val phone = binding.phoneEditText.text.toString()

        if(name.isBlank() || phone.isBlank()) {
            Toast.makeText(requireContext(), "You must enter a name and a phone number", Toast.LENGTH_SHORT).show()
        }
        else {
            viewModel.insertContact(Contact(name, phone))
            clearFields()
        }
    }

    private fun clearFields() {
        binding.nameEditText.editableText.clear()
        binding.phoneEditText.editableText.clear()
    }

}