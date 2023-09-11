package com.lastaurus.automatic_congratulations.ui.contact.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.lastaurus.automatic_congratulations.R
import com.lastaurus.automatic_congratulations.bus.BusEvent
import com.lastaurus.automatic_congratulations.bus.EventHandler
import com.lastaurus.automatic_congratulations.dagger.ComponentManager
import com.lastaurus.automatic_congratulations.databinding.ResultProfileBinding
import com.lastaurus.automatic_congratulations.ui.view.Task
import java.time.LocalDate
import javax.inject.Inject

class FullScreenDialogExample : DialogFragment() {

   private var _binding: ResultProfileBinding? = null
   private val binding get() = _binding!!

   @Inject
   lateinit var eventHandler: EventHandler

   init {
      ComponentManager.instance.appComponent.inject(this)
   }

   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?,
   ): View {
      isCancelable = false
      _binding = ResultProfileBinding.inflate(inflater, container, false)
      val view = binding.root
      binding.apply {
//         iconContact1.setImageResource(R.drawable.no_foto)
         iconContactImg.setImageResource(R.drawable.person)
         val now = LocalDate.now()
         gant.setTasks(
            listOf(
               Task(
                  name = "Task 1",
                  dateStart = now.minusMonths(1),
                  dateEnd = now
               ),
               Task(
                  name = "Task 2 long name",
                  dateStart = now.minusWeeks(2),
                  dateEnd = now.plusWeeks(1)
               ),
               Task(
                  name = "Task 3",
                  dateStart = now.minusMonths(2),
                  dateEnd = now.plusMonths(2)
               ),
               Task(
                  name = "Some Task 4",
                  dateStart = now.plusWeeks(2),
                  dateEnd = now.plusMonths(2).plusWeeks(1)
               ),
               Task(
                  name = "Task 5",
                  dateStart = now.minusMonths(2).minusWeeks(1),
                  dateEnd = now.plusWeeks(1)
               )
            )
         )
         with(toolbarDialog) {
            inflateMenu(R.menu.menu_contact_change)
            setNavigationOnClickListener {
               dismiss()
            }
            menu.apply {
               setOnMenuItemClickListener {
//         eventHandler.postEvent(BusEvent.saveContact(
//            //TODO
//         ))
                  eventHandler.postEvent(BusEvent.Text(binding.newContactName.text.toString()))
                  dismiss()
                  true
               }
            }
         }
      }
      return view
   }

   override fun getTheme(): Int {
      return R.style.AppMainTheme
   }

   override fun onDestroyView() {
      super.onDestroyView()
      _binding = null
   }
}