package org.deafsapps.android.favquotes.presentationlayer.feature.detail.view.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import org.deafsapps.android.favquotes.domainlayer.domain.QuoteBo
import org.deafsapps.android.favquotes.domainlayer.org.deafsapps.android.favquotes.domainlayer.feature.detail.DetailDomainLayerBridge
import org.deafsapps.android.favquotes.presentationlayer.base.BaseMvvmView
import org.deafsapps.android.favquotes.presentationlayer.base.BaseMvvmViewModel
import org.deafsapps.android.favquotes.presentationlayer.base.ScreenState
import org.deafsapps.android.favquotes.presentationlayer.databinding.ActivityDetailBinding
import org.deafsapps.android.favquotes.presentationlayer.di.DetailComponent
import org.deafsapps.android.favquotes.presentationlayer.di.DetailComponentFactoryProvider
import org.deafsapps.android.favquotes.presentationlayer.domain.FailureVo
import org.deafsapps.android.favquotes.presentationlayer.domain.QuoteVo
import org.deafsapps.android.favquotes.presentationlayer.feature.detail.view.state.DetailState
import org.deafsapps.android.favquotes.presentationlayer.feature.detail.viewmodel.DETAIL_VIEW_MODEL_TAG
import org.deafsapps.android.favquotes.presentationlayer.feature.detail.viewmodel.DetailViewModel
import org.deafsapps.android.favquotes.presentationlayer.feature.main.view.ui.QUOTE_ITEM_ID
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

private const val NO_TAGS = ""

/**
 *
 */
@ExperimentalCoroutinesApi
class DetailActivity :
    AppCompatActivity(),
    BaseMvvmView<BaseMvvmViewModel<DetailDomainLayerBridge<QuoteBo>, DetailState>, DetailDomainLayerBridge<QuoteBo>, DetailState, FailureVo> {

    @Inject
    @Named(DETAIL_VIEW_MODEL_TAG)
    lateinit var _viewModel: BaseMvvmViewModel<DetailDomainLayerBridge<QuoteBo>, DetailState>
    override val viewModel: DetailViewModel by lazy { _viewModel as DetailViewModel }
    private lateinit var viewBinding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        getDetailComponent().inject(this)
        super.onCreate(savedInstanceState)
        viewBinding = ActivityDetailBinding.inflate(layoutInflater)
        initModel()
        setContentView(viewBinding.root)
    }

    override fun onResume() {
        super.onResume()
        val quoteID = intent.getIntExtra(QUOTE_ITEM_ID, -1)
        viewModel.onViewCreated(data = quoteID)
    }

    override fun processRenderState(renderState: DetailState) {
        when (renderState) {
            is DetailState.LoadQuoteItem -> loadQuoteItem(item = renderState.data)
        }
    }

    override fun processRenderError(error: FailureVo) {
        Timber.e("Error: ${error.msg}")
        viewBinding.tvNoData.apply {
            visibility = View.VISIBLE
            text = error.msg
        }
    }

    override fun initModel() {
        lifecycleScope.launchWhenStarted {
            viewModel.screenState.collect { screenState ->
                when (screenState) {
                    is ScreenState.Idle -> hideLoading()
                    is ScreenState.Loading -> showLoading()
                    is ScreenState.Render<DetailState> -> {
                        hideLoading()
                        processRenderState(screenState.renderState)
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

    private fun loadQuoteItem(item: QuoteVo) {
        with(viewBinding) {
            tvNoData.visibility = View.GONE
            tvAuthor.text = item.author
            tvQuote.text = item.body
            tvFavs.text = item.favoritesCount.toString()
            tvUpvotes.text = item.upvotesCount.toString()
            tvDownvotes.text = item.downvotesCount.toString()
            tvTags.text = item.tags.takeIf { it.isNotEmpty() }?.toString() ?: NO_TAGS
        }
    }

    private fun showLoading() {
        viewBinding.pbLoading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        viewBinding.pbLoading.visibility = View.GONE
    }

}

@ExperimentalCoroutinesApi
private fun DetailActivity.getDetailComponent(): DetailComponent =
    (application as DetailComponentFactoryProvider).provideDetailComponentFactory().create()
