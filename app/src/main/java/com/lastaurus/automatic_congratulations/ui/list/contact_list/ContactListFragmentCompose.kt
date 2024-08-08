package com.lastaurus.automatic_congratulations.ui.list.contact_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lastaurus.automatic_congratulations.bus.EventHandler
import com.lastaurus.automatic_congratulations.dagger.ComponentManager
import com.vomisareg.ui.theme.AppTheme
import javax.inject.Inject

class ContactListFragmentCompose @Inject constructor() : Fragment() {

    private var viewModel: ContactListViewModel? = null
    private var composeView: ComposeView? = null

    @Inject
    lateinit var eventHandler: EventHandler

    init {
        ComponentManager.instance.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).also { composeView = it }

    override fun onDestroyView() {
        super.onDestroyView()
        composeView = null
    }

//    private val requestPermissionLauncher =
//        registerForActivityResult(
//            ActivityResultContracts.RequestPermission()
//        ) { isGranted: Boolean ->
//            if (isGranted) uploadContactList()
//        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.viewModel = ViewModelProvider(this)[ContactListViewModel::class.java]
//        subscribeOnEventBus()
    }

//    @SuppressLint("CheckResult")
//    fun uploadContactList() {
//        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CONTACTS)
//            == PackageManager.PERMISSION_GRANTED
//        ) {
//            viewModel?.firstLoadSystemContactList()
//        } else {
//            requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
//        }
//    }
//
//    private fun subscribeOnEventBus() {
//        eventHandler.subscribeEvent { busEvent ->
//            (busEvent as? BusEvent.Sort)?.apply {
//                if (this.type == TypeObject.CONTACT) {
//                    init(viewModel?.getContactList(this.aZ))
//                }
//            }
//            (busEvent as? BusEvent.SearchByText)?.apply {
//                if (this.type == TypeObject.CONTACT) {
//                    this.text?.apply { init(viewModel?.getContactByPeaceName(this)) }
//                    if (this.text.isNull()) {
//                        init(viewModel?.getContactList())
//                    }
//                }
//            }
//            false
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setContent()
    }

    private fun setContent() {
        composeView?.setContent {

            AppTheme {
                ContactListScrean()
            }
        }
    }

}