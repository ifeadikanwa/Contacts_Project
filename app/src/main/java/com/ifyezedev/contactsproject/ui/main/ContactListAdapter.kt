package com.ifyezedev.contactsproject.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ifyezedev.contactsproject.Contact
import com.ifyezedev.contactsproject.R

class ContactListAdapter(private val contactItemLayout: Int,
                         private val onItemClicked: (position: Int) -> Unit) : RecyclerView.Adapter<ContactListAdapter.ViewHolder>(){

    private var contactList: List<Contact>? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contactName = holder.contactName
        val contactPhone = holder.contactPhone

        contactList.let {contactsList ->
            contactName.text = contactsList!![position].name
            contactPhone.text = contactsList[position].phone
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(contactItemLayout, parent, false)
        return ViewHolder(view, onItemClicked)
    }

    fun setContactsList(contacts: List<Contact>) {
        contactList = contacts
        notifyDataSetChanged()
    }

    fun getContactAtPosition(position: Int): Contact? {
        return contactList?.get(position)
    }

    override fun getItemCount(): Int {
        return if (contactList == null) 0 else contactList!!.size
    }

    class ViewHolder(itemView: View, private val onItemClicked: (position: Int) -> Unit)
        : RecyclerView.ViewHolder(itemView), View.OnClickListener  {

        init {
            itemView.findViewById<ImageView>(R.id.deleteButton).setOnClickListener(this)
        }

        var contactName: TextView = itemView.findViewById(R.id.nameTextView)
        var contactPhone: TextView = itemView.findViewById(R.id.phoneTextView)

        override fun onClick(v: View?) {
            val position = adapterPosition
            onItemClicked(position)
        }
    }
}
