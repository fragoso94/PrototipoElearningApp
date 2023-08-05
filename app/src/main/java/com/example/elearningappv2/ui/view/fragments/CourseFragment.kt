package com.example.elearningappv2.ui.view.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.elearningappv2.R
import com.example.elearningappv2.databinding.FragmentCourseBinding
import com.example.elearningappv2.databinding.FragmentHomeBinding
import com.example.elearningappv2.domain.model.Course
import com.example.elearningappv2.ui.view.DetailActivity
import com.example.elearningappv2.ui.view.adapter.RecyclerAdapter
import com.example.elearningappv2.ui.viewmodel.CourseViewModel
import com.example.elearningappv2.ui.viewmodel.IntroductionViewModel
import com.example.elearningappv2.utilities.Helpers
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class CourseFragment : Fragment() {

    private lateinit var binding: FragmentCourseBinding
    private lateinit var contexto: Context
    private val introductionViewModel: IntroductionViewModel by viewModels()
    private val courseViewModel: CourseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course, container, false)
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
        binding = FragmentCourseBinding.bind(view)

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
            var listCourses: List<Course> = listOf()
            courseViewModel.getListShoppingCourse()
            courseViewModel.courseShoppingModel.observe(this, Observer{
                listCourses = response.filter { item ->
                    it.contains(item.id)
                }
                binding.recycler.apply {
                    layoutManager = LinearLayoutManager(view.context)
                    adapter = RecyclerAdapter(listCourses, { course -> onItemSelected(course) }) //courseFragment
                }
            })

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
        startActivity(DetailActivity.create(context, course, false))
    }

    private fun onItemSelected(course: Course){
        introductionViewModel.onDetailCourseSelected(course)
    }

}