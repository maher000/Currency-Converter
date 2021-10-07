package com.maher.currencyconverter.presentation.compareTwoCurrencies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import com.maher.currencyconverter.R
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.maher.currencyconverter.domain.model.CurrencySymbol
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_compare_currencies.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class CompareCurrenciesFragment : Fragment() {

    private val mViewModel: CompareTwoCurrenciesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compare_currencies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()
    }

    private fun initObservers() {
        lifecycleScope.launchWhenStarted {
            mViewModel.currencyComparison.collect { state ->
                when (state) {
                    is CompareTwoCurrenciesState.Success -> {
                        stopLoading()
                        tvResult.text = state.result
                    }
                    is CompareTwoCurrenciesState.Error -> {
                        stopLoading()
                        tvResult.text = state.error
                    }
                    is CompareTwoCurrenciesState.Loading -> {
                        startLoading()
                    }
                }
            }
        }
    }

    private fun initViews() {
        btnConvert.setOnClickListener {
            mViewModel.compare(
                spFromCurrency.selectedItem.toString(),
                spToCurrency.selectedItem.toString(),
                etAmount.text.toString()
            )
        }
        spFromCurrency.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                CurrencySymbol.values()
            )

        spToCurrency.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            CurrencySymbol.values()
        )
    }

    private fun startLoading() {
        progressBar.isVisible = true
    }

    private fun stopLoading() {
        progressBar.isVisible = false
    }

}