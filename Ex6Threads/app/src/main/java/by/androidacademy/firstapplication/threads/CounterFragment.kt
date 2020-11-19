package by.androidacademy.firstapplication.threads

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.androidacademy.firstapplication.R
import kotlinx.android.synthetic.main.fragment_threads.btnCancel
import kotlinx.android.synthetic.main.fragment_threads.btnCreate
import kotlinx.android.synthetic.main.fragment_threads.btnStart
import kotlinx.android.synthetic.main.fragment_threads.tvValue

private const val FRAGMENT_TYPE = "fragment_type"

class CounterFragment : Fragment(R.layout.fragment_threads) {

    companion object {
        fun newInstance(fragmentTitle: String): CounterFragment {
            val fragment = CounterFragment()

            val bundle = Bundle(1).apply {
                putString(FRAGMENT_TYPE, fragmentTitle)
            }
            fragment.arguments = bundle

            return fragment
        }
    }

    private var listener: TaskEventContract.Operationable? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (activity != null && activity is TaskEventContract.Operationable) {
            listener = activity as TaskEventContract.Operationable
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnCreate.setOnClickListener { listener?.createTask() }
        btnStart.setOnClickListener { listener?.startTask() }
        btnCancel.setOnClickListener { listener?.cancelTask() }

        //UNPACK OUR DATA FROM OUR BUNDLE
        val fragmentText = this.arguments?.getString(FRAGMENT_TYPE)
        tvValue.text = fragmentText
    }

    override fun onDetach() {
        super.onDetach()

        listener = null
    }

    fun updateFragmentText(text: String) {
        tvValue.text = text
    }
}
