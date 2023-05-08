    package gst.trainingcourse.fragmentdemo

    import android.icu.lang.UCharacter.GraphemeClusterBreak.V
    import android.os.Bundle
    import android.util.Log
    import androidx.fragment.app.Fragment
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import gst.trainingcourse.fragmentdemo.databinding.ActivityMainBinding
    import gst.trainingcourse.fragmentdemo.databinding.FragmentNameBinding
    import kotlinx.android.synthetic.main.fragment_name.*

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private const val ARG_PARAM1 = "param1"
    private const val ARG_PARAM2 = "param2"

    /**
     * A simple [Fragment] subclass.
     * Use the [NameFragment.newInstance] factory method to
     * create an instance of this fragment.
     */
    class NameFragment : Fragment(R.layout.fragment_name) {
        private val TAG = "FragmentB"
        // TODO: Rename and change types of parameters
        private var param1: String? = null
        private var param2: String? = null

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            arguments?.let {
                param1 = it.getString(ARG_PARAM1)
                param2 = it.getString(ARG_PARAM2)
            }
        }
        private var _binding : FragmentNameBinding? = null
        private val binding get() = _binding!!
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            _binding = FragmentNameBinding.inflate(layoutInflater,container,false)
            val view = binding.root
            return view
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            Log.d(TAG,"onViewCreated: ${parentFragmentManager.backStackEntryCount}")
            if(parentFragmentManager.fragments.isNotEmpty()){
                val fragmentName = java.lang.StringBuilder("")
                for(i in 0 until parentFragmentManager.backStackEntryCount){
                    Log.d(TAG,"onViewCreated: ${parentFragmentManager.getBackStackEntryAt(i)}")
                    fragmentName.append(parentFragmentManager.getBackStackEntryAt(i).name.toString())
                    fragmentName.append(" ")
                }
                binding.tvName.text = fragmentName
            }

        }

        companion object {
            /**
             * Use this factory method to create a new instance of
             * this fragment using the provided parameters.
             *
             * @param param1 Parameter 1.
             * @param param2 Parameter 2.
             * @return A new instance of fragment NameFragment.
             */
            // TODO: Rename and change types and number of parameters
            @JvmStatic
            fun newInstance(param1: String, param2: String) =
                NameFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
        }
    }