package org.deafsapps.android.favquotes.presentationlayer.feature.main.view.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import org.deafsapps.android.favquotes.domainlayer.domain.QuoteBo
import org.deafsapps.android.favquotes.domainlayer.org.deafsapps.android.favquotes.domainlayer.feature.main.MainDomainLayerBridge
import org.deafsapps.android.favquotes.presentationlayer.base.BaseMvvmView
import org.deafsapps.android.favquotes.presentationlayer.base.BaseMvvmViewModel
import org.deafsapps.android.favquotes.presentationlayer.base.ScreenState
import org.deafsapps.android.favquotes.presentationlayer.databinding.ActivityMainBinding
import org.deafsapps.android.favquotes.presentationlayer.di.MainComponent
import org.deafsapps.android.favquotes.presentationlayer.di.MainComponentFactoryProvider
import org.deafsapps.android.favquotes.presentationlayer.domain.FailureVo
import org.deafsapps.android.favquotes.presentationlayer.domain.QuoteVo
import org.deafsapps.android.favquotes.presentationlayer.feature.main.view.adapter.QuoteListAdapter
import org.deafsapps.android.favquotes.presentationlayer.feature.main.view.state.MainState
import org.deafsapps.android.favquotes.presentationlayer.feature.main.viewmodel.MAIN_VIEW_MODEL_TAG
import org.deafsapps.android.favquotes.presentationlayer.feature.main.viewmodel.MainViewModel
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

/**
 *
 */
@ExperimentalCoroutinesApi
class MainActivity :
    AppCompatActivity(),
    BaseMvvmView<BaseMvvmViewModel<MainDomainLayerBridge<QuoteBo>, MainState>, MainDomainLayerBridge<QuoteBo>, MainState, FailureVo> {

    @Inject
    @Named(MAIN_VIEW_MODEL_TAG)
    lateinit var _viewModel: BaseMvvmViewModel<MainDomainLayerBridge<QuoteBo>, MainState>
    override val viewModel: MainViewModel by lazy { _viewModel as MainViewModel }
    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        getMainComponent().inject(this)
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        initModel()
        initView()
        setContentView(viewBinding.root)
    }

    override fun onResume() {
        super.onResume()
        viewModel.onViewResumed()
    }

    override fun processRenderState(renderState: MainState) {
        when (renderState) {
            is MainState.LoadQuoteList -> loadQuoteList(data = renderState.data)
            MainState.NavigateToDetailView -> {
            }
        }
    }

    override fun processRenderError(error: FailureVo) {
        viewBinding.tvNoData.visibility = if (error is FailureVo.NoData) View.VISIBLE else View.GONE
        Timber.e("Error: ${error.msg}")
    }

    override fun initModel() {
        lifecycleScope.launchWhenStarted {
            viewModel.screenState.collect { screenState ->
                when (screenState) {
                    is ScreenState.Idle -> hideLoading()
                    is ScreenState.Loading -> showLoading()
                    is ScreenState.Render<MainState> -> {
                        processRenderState(screenState.renderState)
                        hideLoading()
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.errorState.collect { failure ->
                when (failure) {
                    !is FailureVo.Idle -> {
                        processRenderError(failure)
                        hideLoading()
                    }
                }
            }
        }
    }

    private fun initView() {
        setSupportActionBar(viewBinding.toolbar)
        with(viewBinding.rvItems) {
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            adapter = QuoteListAdapter(itemList = mutableListOf()) { character ->
//                viewModel.onCharacterItemSelected(item = character)
            }
        }
    }

    private fun loadQuoteList(data: List<QuoteVo>) {
        (viewBinding.rvItems.adapter as? QuoteListAdapter)?.updateData(newData = data)
    }

    private fun showLoading() {
        with(viewBinding) {
            pbLoading.visibility = View.VISIBLE
        }
    }

    private fun hideLoading() {
        with(viewBinding) {
            pbLoading.visibility = View.GONE
        }
    }

}

@ExperimentalCoroutinesApi
private fun MainActivity.getMainComponent(): MainComponent =
    (application as MainComponentFactoryProvider).provideMainComponentFactory().create()
