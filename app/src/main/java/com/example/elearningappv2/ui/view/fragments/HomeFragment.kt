package com.example.elearningappv2.ui.view.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.elearningappv2.R
import com.example.elearningappv2.databinding.FragmentHomeBinding
import com.example.elearningappv2.domain.model.Course
import com.example.elearningappv2.ui.view.CourseListVideoActivity
import com.example.elearningappv2.ui.view.DetailActivity
import com.example.elearningappv2.ui.view.adapter.RecyclerAdapter
import com.example.elearningappv2.ui.viewmodel.CourseViewModel
import com.example.elearningappv2.ui.viewmodel.CreateAccountViewModel
import com.example.elearningappv2.ui.viewmodel.IntroductionViewModel
import com.example.elearningappv2.utilities.Helpers
import com.facebook.shimmer.ShimmerFrameLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var contexto: Context
    private val introductionViewModel: IntroductionViewModel by viewModels()
    private val courseViewModel: CourseViewModel by viewModels()
    private var listCourses: List<Course> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            contexto = context
        } catch (e: ClassCastException) {
            throw ClassCastException("$context debe implementar ParametrosListener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        courseViewModel.onCreateCourse()
        courseViewModel.courseModel.observe(this, Observer
        {
            if (it != null) {
                initUI(view, it)
            }
            else
            {
                Toast.makeText(
                    contexto,
                    "Algo falló en la API.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
        initObservers(contexto)
    }

    private fun initUI(view: View, response: List<Course>){
        if(Helpers.isInternetAvailable(contexto)){
            //Obtenemos la lista de cursos de la Api
            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main) {
                    binding.shimmerViewContainer.stopShimmer()
                }
                listCourses = response //Helpers.convertListDataClass(contexto, response)

                CoroutineScope(Dispatchers.Main).launch {
                    //val courseFragment = this
                    var coursesFiltered: List<Course>

                    binding.recycler.apply {
                        layoutManager = LinearLayoutManager(view.context)
                        adapter = RecyclerAdapter(listCourses, { course -> onItemSelected(course) }) //courseFragment
                    }

                    binding.buttonAll.setOnClickListener{
                        binding.recycler.adapter = RecyclerAdapter(listCourses, { course -> onItemSelected(course) }) //this
                    }

                    binding.buttonDesign.setOnClickListener{
                        coursesFiltered = listCourses.filter { c -> c.category.contains("Diseño") }
                        binding.recycler.adapter = RecyclerAdapter(coursesFiltered, { course -> onItemSelected(course) }) //this
                    }

                    binding.buttonProgrammation.setOnClickListener {
                        coursesFiltered = listCourses.filter { c -> c.category.contains("Programación") }
                        binding.recycler.adapter = RecyclerAdapter(coursesFiltered, { course -> onItemSelected(course) }) //this
                    }

                    binding.buttonWeb.setOnClickListener {
                        coursesFiltered = listCourses.filter { c -> c.category.contains("Desarrollo Web") }
                        binding.recycler.adapter = RecyclerAdapter(coursesFiltered, { course -> onItemSelected(course) }) //this
                    }
                    binding.shimmerViewContainer.visibility = View.GONE

                }
            }
        }
        else
        {
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(
                    contexto,
                    "No hay conexión a internet.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun initObservers(context: Context) {
        introductionViewModel.navigateToDetailCourse.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                //Log.d("observer", it.toString())
                goToDetail(context, it)
            }

        })
    }

    private fun goToDetail(context: Context, course: Course) {
        //Log.d("goToDetail", course.toString())
        startActivity(DetailActivity.create(context, course, true))
    }

    private fun onItemSelected(course: Course){
//        Log.d("onItemSelected", course.toString())
//        initObservers(contexto)
        introductionViewModel.onDetailCourseSelected(course)
    }

}