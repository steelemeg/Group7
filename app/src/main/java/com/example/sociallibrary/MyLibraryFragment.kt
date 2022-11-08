package com.example.sociallibrary

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.parse.Parse
import com.parse.ParseObject
import com.parse.ParseException
import com.parse.ParseClassName
import com.parse.ParseQuery
import com.parse.ParseUser


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyLibraryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyLibraryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        // Gets userObjectId from MainActivity into this Fragment
        val userObjectId = requireArguments().getString("userObjectId", "None")
        Log.i("Daniel", "The MyLibrary Fragment has userObjectID " + userObjectId.toString())

        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Change this statement to store the view in a variable instead of a return statement
        val view = inflater.inflate(R.layout.fragment_my_library, container, false)
        val recyclerView = view.findViewById<View>(R.id.rvMyBooks) as RecyclerView
        val searchButton = view.findViewById<Button>(R.id.btnMyLists)
        recyclerView.layoutManager = GridLayoutManager(context, 1)

        // TODO temp for query testing
        val bundle = arguments
        val user = bundle?.getString("userObjectId")
        // Retrieves all book objects
        val getBooks = ParseQuery<ParseObject>("Book")
        // Filters to just books owned by the current user
        getBooks.whereEqualTo("ownerObjectId", user)
        getBooks.findInBackground(
        ) { obj, e ->
            if (e == null) {
                Log.v("book testing", obj.get(0).get("Author").toString())
            } else {
                Log.v("OH NO", "UGH")
            }
        }

        return view
    }

    private fun updateAdapter(recyclerView: RecyclerView, searchParams:String) {
        Log.v("book SEARCH PARAMS", searchParams.toString())
        val bundle = arguments
        val user = bundle?.getString("userObjectId")
        val query = ParseQuery<ParseObject>("Book")
        query.whereEqualTo("ownerObjectId", user.toString())
        Log.v("book query", query.toString())

        }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyLibraryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyLibraryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

