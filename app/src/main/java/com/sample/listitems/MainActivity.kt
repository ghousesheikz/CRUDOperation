package com.sample.listitems

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sample.listitems.model.ItemPojo
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_layout.*

class MainActivity : AppCompatActivity() {
    private lateinit var itemListAdapter: ItemListAdapter
    private lateinit var itemListData: ArrayList<ItemPojo>
    private val READ_REQUEST_CODE = 42
    private var uri: Uri? = null
    private lateinit var dialog: Dialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        itemListData = arrayListOf()
        list_items?.layoutManager = LinearLayoutManager(this)
        list_items?.isNestedScrollingEnabled = false
        itemListAdapter =
            ItemListAdapter(itemListData, this, object : ItemListAdapter.OnItemClickListener {
                override fun onEditClick(response: ItemPojo?, position: Int) {
                    createItemDialog(response, position)
                }

                override fun onDeleteClick(position: Int) {
                    itemListAdapter.apply {
                        MovieListData?.removeAt(position)
                        notifyDataSetChanged()
                    }
                }

            })

        list_items.adapter = itemListAdapter

        fab_additem?.setOnClickListener {
            createItemDialog(null, -1)
        }
    }


    fun createItemDialog(response: ItemPojo?, position: Int) {
        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_layout)
        val window: Window = dialog.getWindow()!!
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)
        dialog.capture_image.setOnClickListener {
            selectImageFromStorage()
        }

        dialog.btn_create.setOnClickListener {
            if (!TextUtils.isEmpty(dialog.edt_label.text.toString()) && !TextUtils.isEmpty(dialog.edt_desc.text.toString())) {
                if (response == null) {
                    itemListAdapter.apply {
                        MovieListData?.add(
                            ItemPojo(
                                dialog.edt_label.text.toString().trim(),
                                dialog.edt_desc.text.toString().trim(),
                                uri
                            )
                        )

                        notifyDataSetChanged()
                    }
                } else {
                    itemListAdapter.apply {

                        MovieListData?.removeAt(position)
                        MovieListData?.add(
                            position, ItemPojo(
                                dialog.edt_label.text.toString().trim(),
                                dialog.edt_desc.text.toString().trim(),
                                uri
                            )
                        )

                        notifyDataSetChanged()
                    }
                }

                dialog.dismiss()
            } else {
                Toast.makeText(this, "Please Enter Label and Description", Toast.LENGTH_LONG).show()
            }
        }

        dialog.btn_cancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


    private fun selectImageFromStorage() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        startActivityForResult(intent, READ_REQUEST_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == READ_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                uri = data.data
                dialog.capture_image.setImageURI(uri)
            }
        }
    }
}