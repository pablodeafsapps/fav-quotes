package org.deafsapps.android.favquotes.presentationlayer.feature.splash.view.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import org.deafsapps.android.favquotes.domainlayer.domain.QuoteBo
import org.deafsapps.android.favquotes.domainlayer.feature.splash.SplashDomainLayerBridge
import org.deafsapps.android.favquotes.presentationlayer.R
import org.deafsapps.android.favquotes.presentationlayer.base.BaseMvvmView
import org.deafsapps.android.favquotes.presentationlayer.base.BaseMvvmViewModel
import org.deafsapps.android.favquotes.presentationlayer.base.ScreenState
import org.deafsapps.android.favquotes.presentationlayer.databinding.ActivitySplashBinding
import org.deafsapps.android.favquotes.presentationlayer.di.SplashComponent
import org.deafsapps.android.favquotes.presentationlayer.di.SplashComponentFactoryProvider
import org.deafsapps.android.favquotes.presentationlayer.domain.FailureVo
import org.deafsapps.android.favquotes.presentationlayer.domain.QuoteVo
import org.deafsapps.android.favquotes.presentationlayer.feature.main.view.ui.MainActivity
import org.deafsapps.android.favquotes.presentationlayer.feature.splash.view.state.SplashState
import org.deafsapps.android.favquotes.presentationlayer.feature.splash.viewmodel.SPLASH_VIEW_MODEL_TAG
import org.deafsapps.android.favquotes.presentationlayer.feature.splash.viewmodel.SplashViewModel
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

/**
 *
 */
@ExperimentalCoroutinesApi
class SplashActivity :
    AppCompatActivity(),
    BaseMvvmView<BaseMvvmViewModel<SplashDomainLayerBridge<QuoteBo>, SplashState>, SplashDomainLayerBridge<QuoteBo>, SplashState, FailureVo> {

    @Inject
    @Named(SPLASH_VIEW_MODEL_TAG)
    lateinit var _viewModel: BaseMvvmViewModel<SplashDomainLayerBridge<QuoteBo>, SplashState>
    override val viewModel: SplashViewModel by lazy { _viewModel as SplashViewModel }
    private lateinit var viewBinding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        getSplashComponent().inject(this)
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySplashBinding.inflate(layoutInflater)
        initModel()
        initView()
        setContentView(viewBinding.root)
    }

    override fun onResume() {
        super.onResume()
        viewModel.onViewResumed()
    }

    override fun initModel() {
        lifecycleScope.launchWhenStarted {
            viewModel.screenState.collect { screenState ->
                when (screenState) {
                    is ScreenState.Idle -> hideLoading()
                    is ScreenState.Loading -> showLoading()
                    is ScreenState.Render<SplashState> -> {
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

    override fun processRenderState(renderState: SplashState) {
        when (renderState) {
            is SplashState.LoadQuote -> loadQuote(data = renderState.quote)
            SplashState.NavigateToMainView -> navigateToMainView()
        }
    }

    override fun processRenderError(error: FailureVo) {
        Timber.e("Error: ${error.msg}")
    }

    private fun initView() {
        viewBinding.tvHint.setOnClickListener {
            viewModel.onHintSelected()
        }
    }

    private fun loadQuote(data: QuoteVo) {
        with(viewBinding) {
            clRoot.visibility = View.VISIBLE
            tvQuote.text = String.format(getString(R.string.text_splash_quote), data.body)
            tvAuthor.text = String.format(getString(R.string.text_splash_author), data.author)
        }
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

    private fun navigateToMainView() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}

@ExperimentalCoroutinesApi
private fun SplashActivity.getSplashComponent(): SplashComponent =
    (application as SplashComponentFactoryProvider).provideSplashComponentFactory().create()
