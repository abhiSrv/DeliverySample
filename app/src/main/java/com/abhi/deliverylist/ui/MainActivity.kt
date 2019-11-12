package com.abhi.deliverylist.ui

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhi.deliverylist.BR
import com.abhi.deliverylist.R
import com.abhi.deliverylist.adapters.DeliveryListAdapter
import com.abhi.deliverylist.data.repository.State
import com.abhi.deliverylist.databinding.ActivityMainBinding
import com.abhi.deliverylist.utils.BottomDialogListener
import com.abhi.deliverylist.utils.BottomDialogType
import com.abhi.deliverylist.viewModel.DeliveryViewModel
import kotlinx.android.synthetic.main.activity_main.view.*
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding, DeliveryViewModel>(), BottomDialogListener { //AppCompatActivity() {

    private lateinit var viewModel: DeliveryViewModel
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var adapter: DeliveryListAdapter
    private lateinit var binding: ActivityMainBinding

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getViewModel(): DeliveryViewModel {
        viewModel = ViewModelProviders.of(this, factory)
            .get(DeliveryViewModel::class.java)
        return viewModel
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewDataBinding()
        init()
        setUpObserver()
    }

    override fun setUpObserver() {

        viewModel.itemList.observe(this, Observer {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
            if (it != null && it.size > 0 && it[0] != null) {
                viewModel.item.set(it[0])
                viewModel.noData.set(false)
            }else{
                viewModel.noData.set(true)
            }
            if (binding.swipeRefresh.isRefreshing) {
                binding.swipeRefresh.isRefreshing = false
            }
        })

        viewModel.boundaryCallback.state.observe(this, Observer {
            when (it) {
                State.ERROR, State.NETWORK_ERROR -> {
                    viewModel.isLoading.set(it == State.ERROR)
                    adapter.setLoading(false, adapter.itemCount != 0)
                    showErrorDialog(
                        if (it == State.NETWORK_ERROR) {
                            R.string.network_error
                        } else R.string.list_error_message
                    )
                    if (binding.swipeRefresh.isRefreshing) {
                        binding.swipeRefresh.isRefreshing = false
                    }


                    //  adapter.notifyItemChanged(adapter.itemCount - 1)
                }
                State.DONE->{
                    if (binding.swipeRefresh.isRefreshing) {
                        binding.swipeRefresh.isRefreshing = false
                    }
                    adapter.setLoading(false, false)
                    showSuccessDialog(R.string.all_data_fetched)
                }
                State.PAGE_LOADING -> {
                    adapter.setLoading(loading = true, loadMore = false)

                    //  adapter.notifyItemChanged(adapter.itemCount - 1)
                }
                State.LOADED -> {
                    adapter.setLoading(loading = false, loadMore = false)
                    viewModel.isLoading.set(it == State.LOADING)
                   // showSuccessDialog(R.string.all_data_fetched)
                }
                else -> {
                    adapter.setLoading(loading = false, loadMore = false)
                    viewModel.isLoading.set(it == State.LOADING)
                }
            }
        })



      /*  viewModel.boundaryCallback.state.observe(this, Observer {
            when (it) {
                State.ERROR, State.NETWORK_ERROR -> {
                    viewModel.isLoading.set(it == State.ERROR)

                    showErrorDialog(
                        if (it == State.NETWORK_ERROR) {
                            R.string.network_error
                        } else R.string.list_error_message
                    )
                    if (binding.swipeRefresh.isRefreshing) {
                        binding.swipeRefresh.isRefreshing = false
                    }
                    //  adapter.notifyItemChanged(adapter.itemCount - 1)
                }
                State.PAGE_LOADING -> {
                    //  adapter.notifyItemChanged(adapter.itemCount - 1)
                }
                State.LOADED -> {
                    //viewModel.isLoading.set(it == State.LOADING)
                   // showSuccessDialog(R.string.all_data_fetched)
                    adapter.notifyItemChanged(adapter.itemCount)
                }
                State.DONE->{
                    if (binding.swipeRefresh.isRefreshing) {
                        binding.swipeRefresh.isRefreshing = false
                    }
                    showSuccessDialog(R.string.all_data_fetched)
                    }
                else -> {
                    viewModel.isLoading.set(it == State.LOADING)
                }
            }
        })*/

    }

    override fun showSuccessDialog(message: Int) {
      //  Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        openDialog(
            BottomDialogType.ALL_DATA_FETCHED,
            getString(message), this
        )
    }

    override fun showErrorDialog(message: Int) {
       // Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

        openDialog(
            BottomDialogType.ERROR_IN_FETCHING,
            getString(message), this
        )
    }

    private fun init() {
        adapter = DeliveryListAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
       /* binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL
            )
        )*/
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.onRefresh()
        }

        binding.swipeRefresh.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )
    }

    override fun onDialogClickListener(retry: Boolean) {
        if (retry) {
            viewModel.retry()
        }
    }

}
