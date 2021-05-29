package com.example.konamoniruzzaman

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.konamoniruzzaman.multicheck.GenreDataFactory
import com.example.konamoniruzzaman.multicheck.MultiCheckGenreAdapter
import com.example.konamoniruzzaman.view_model.CategoryViewModel


class CategoryActivity : AppCompatActivity() {
    private lateinit var pDialog: SweetAlertDialog
    private var adapter: MultiCheckGenreAdapter? = null
    private lateinit var categoryViewModel: CategoryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = javaClass.simpleName
        val recyclerView = findViewById<View>(R.id.recycler_view) as RecyclerView
        val layoutManager = LinearLayoutManager(this)

        categoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)

        pDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        adapter = MultiCheckGenreAdapter(GenreDataFactory.makeMultiCheckGenres())
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        val clear = findViewById<View>(R.id.clear_button) as Button
        clear.setOnClickListener { adapter!!.clearChoices() }
        val check = findViewById<View>(R.id.check_first_child) as Button
        check.setOnClickListener { adapter!!.checkChild(true, 0, 3) }

        pDialog.show()
        this?.let { it1 -> categoryViewModel.doCategory() };

        observeViewModel()
    }

    private fun observeViewModel() {
        categoryViewModel.response_error.observe(this, androidx.lifecycle.Observer {
            it?.let {
                pDialog.dismiss()
                Toast.makeText(getApplication(), "Found Error "+it, Toast.LENGTH_SHORT).show()
            }
        })
        categoryViewModel.categoryListResponse.observe(
            this,
            androidx.lifecycle.Observer {
                it?.let {
                    Toast.makeText(getApplication(), "Fetch From Database "+it, Toast.LENGTH_SHORT).show()
                    pDialog.dismiss()


                }
            })

    }
}