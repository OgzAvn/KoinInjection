package com.oguz.coinapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.oguz.coinapp.databinding.FragmentListBinding
import com.oguz.coinapp.model.CryptoModel
import com.oguz.coinapp.viewmodel.CryptoViewModel
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope


class ListFragment : Fragment(),RecyclerViewAdapter.Listener,AndroidScopeComponent {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private var recyclerViewAdapter : RecyclerViewAdapter? = null


    private val viewModel by viewModel<CryptoViewModel>()

    override val scope: Scope by fragmentScope()
    private val hello by inject<String>(qualifier = named("hi"))

    /*
    private val api = get<CrytoAPI>()//TODO: Normal injection

    private val apilazy by inject<CrytoAPI>() //TODO: lazy injection -> Kullanılana kadar initialize etmiyor - Genelde bu kullanılır


     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager

        //viewModel = ViewModelProvider(this)[CryptoViewModel::class.java]
        viewModel.getDataFromAPI()
        //TODO:Veriler gelecek fakat biz observe etmessek kullanıcıya gösteremeyiz.

        observeLiveData()

        println(hello)

    }

    private fun observeLiveData(){

        viewModel.cryptoList.observe(viewLifecycleOwner, Observer {cryptos->

            cryptos?.let {
                binding.recyclerView.visibility = View.VISIBLE
                recyclerViewAdapter = RecyclerViewAdapter(ArrayList(cryptos.data ?: arrayListOf()),this@ListFragment)
                binding.recyclerView.adapter = recyclerViewAdapter
            }
        })

        viewModel.cryptoError.observe(viewLifecycleOwner, Observer { error->

            error?.let {
                if (it.data == true){
                    binding.cryptoErrorText.visibility = View.VISIBLE
                }else{
                    binding.cryptoErrorText.visibility = View.GONE
                }
            }
        })

        viewModel.cryptoLoading.observe(viewLifecycleOwner, Observer { loading->
            loading?.let {
                if (it.data == true) {
                    binding.cryptoProgressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.cryptoErrorText.visibility = View.GONE
                } else {
                    binding.cryptoProgressBar.visibility = View.GONE
                }
            }
        })
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(requireContext(),"Clicked on: ${cryptoModel.currency}", Toast.LENGTH_SHORT).show()
    }

}