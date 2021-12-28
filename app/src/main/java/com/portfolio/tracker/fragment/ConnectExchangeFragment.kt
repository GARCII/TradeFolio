package com.portfolio.tracker.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.portfolio.tracker.R
import com.portfolio.tracker.model.ExchangeType
import com.portfolio.tracker.util.LoadingState
import com.portfolio.tracker.util.TradeFolioSharedPreferencesUtils
import com.portfolio.tracker.util.Utils
import com.portfolio.tracker.viewmodel.ExchangeViewModel
import kotlinx.android.synthetic.main.fragment_connect_exchange.*
import org.knowm.xchange.currency.Currency


class ConnectExchangeFragment : Fragment() {

    private lateinit var exchangeType: ExchangeType
    private lateinit var viewModel: ExchangeViewModel

    companion object {
        const val ARG_EXCHANGE_TYPE = "arg-exchange-type"

        @JvmStatic
        fun newInstance(exchangeType: ExchangeType) =
            ConnectExchangeFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_EXCHANGE_TYPE, exchangeType)
                }
            }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(ARG_EXCHANGE_TYPE, exchangeType)
        super.onSaveInstanceState(outState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_connect_exchange, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            exchangeType = it.getSerializable(ARG_EXCHANGE_TYPE) as ExchangeType
        }
        if (!this::viewModel.isInitialized) {
            viewModel = ViewModelProviders.of(
                this,
                ExchangeViewModel.ExchangeViewModelFactory(exchangeType)
            ).get(ExchangeViewModel::class.java)
        }

        val sharedPreferencesUtils = TradeFolioSharedPreferencesUtils(requireContext())
        exchangeType.getSpecificParamItem()?.let { specific ->
            edit_text_specific_exchange_item.apply {
                visibility = View.VISIBLE
                hint = specific.getHint(requireContext())
            }
        }

        text_view_exchange_name.text = exchangeType.getName(requireContext())
        image_view_exchange_icon.setImageDrawable(exchangeType.getResourceId(requireContext()))

        button_connect.setOnClickListener {
            val apiKey = edit_text_api_key.text.toString()
            val secretKey = edit_text_secret_key.text.toString()
            val specificParam = edit_text_specific_exchange_item.text.toString()

            sharedPreferencesUtils.apply {
                setString(exchangeType.getApiPrefKey(), apiKey)
                setString(exchangeType.getSecretPrefKey(), secretKey)
                exchangeType.getSpecificParamItem()?.let { specific ->
                    setString(specific.getPrefKey(exchangeType), specificParam)
                }
            }

            if (Utils.isConnectedToNetwork(requireContext()) &&
                !sharedPreferencesUtils.getString(exchangeType.getApiPrefKey()).isNullOrBlank() &&
                !sharedPreferencesUtils.getString(exchangeType.getSecretPrefKey()).isNullOrBlank()) {
                viewModel.synchronize(requireContext())
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.tf_error_message),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        button_get_data.setOnClickListener {
            val apiKeyPref = sharedPreferencesUtils.getString(exchangeType.getApiPrefKey())
            val secretKeyPref = sharedPreferencesUtils.getString(exchangeType.getSecretPrefKey())
            exchangeType.getSpecificParamItem()?.let {
                val specificationPref = sharedPreferencesUtils.getString(it.getPrefKey(exchangeType))
                Log.e("TEST", "Specification : $specificationPref")
            }
            Toast.makeText(
                context,
                "ApiKey : $apiKeyPref / SecretKey : $secretKeyPref",
                Toast.LENGTH_SHORT
            ).show()
        }

        viewModel.data.observe(requireActivity(), {
           it.entries.forEach { entry ->
               Log.e("Wallet", "${entry.value.getBalance(Currency.USDT)}")
           }
        })
        viewModel.loadingState.observe(requireActivity(), {
            manageLoading(it)
        })
    }

    /**
     * Manage visibilities of views depending on loading status
     */
    private fun manageLoading(loadingState: LoadingState) {
        when (loadingState.status) {
            LoadingState.Status.LOADING -> progress_circular.visibility = View.VISIBLE
            LoadingState.Status.SUCCESS -> progress_circular.visibility = View.INVISIBLE
            LoadingState.Status.ERROR -> {
                progress_circular.visibility = View.INVISIBLE
                loadingState.msg?.let {
                   Log.e("Wallet", it)
                }
            }
        }
    }
}