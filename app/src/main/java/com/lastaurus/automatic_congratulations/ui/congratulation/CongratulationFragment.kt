package com.lastaurus.automatic_congratulations.ui.congratulation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.lastaurus.automatic_congratulations.R
import com.lastaurus.automatic_congratulations.bus.EventHandler
import com.lastaurus.automatic_congratulations.dagger.ComponentManager
import com.lastaurus.automatic_congratulations.data.DataBaseManager
import com.lastaurus.automatic_congratulations.databinding.FragmentCongratulationBinding
import com.lastaurus.automatic_congratulations.util.TimePickerDialogCreator
import javax.inject.Inject


class CongratulationFragment : Fragment() {

   private var viewModel: CongratulationViewModel? = null

   @RequiresApi(Build.VERSION_CODES.TIRAMISU)
   val PERMISSION_POST_NOTIFICATIONS = Manifest.permission.POST_NOTIFICATIONS

   private var _binding: FragmentCongratulationBinding? = null
   private val binding get() = _binding!!

   @Inject
   lateinit var dbManager: DataBaseManager

   @Inject
   lateinit var eventHandler: EventHandler

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?,
   ): View {
      _binding = FragmentCongratulationBinding.inflate(inflater, container, false)
      val view = binding.root
      binding.apply {
         toolbar.let {
            it.setNavigationOnClickListener { requireActivity().onBackPressed() }
         }
         var array: Array<String> = emptyArray()
         viewModel?.getContactList()?.observe(viewLifecycleOwner) { contacts ->
            val items = ArrayList<String>()
            contacts.forEach {
               items.add(it.name)
            }
            array = items.toArray(arrayOfNulls<String>(0))
         }
         var items1: Array<String> = emptyArray()
         var arrayT: Array<String> = emptyArray()
         viewModel?.getTemplateList()?.observe(viewLifecycleOwner) { templates ->
            val items = ArrayList<String>()
            templates.forEach {
               items.add(it.textTemplate)
            }
            arrayT = items.toArray(arrayOfNulls<String>(0))
         }
         addTemplate.setEndIconOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
               .setTitle("Шаблоны")
               .setItems(arrayT) { _, which ->
                  templateText.setText(viewModel?.getTextTemplate(which))
                  viewModel?.setTemplate(which)
               }
               .show()
         }

         addContact.setEndIconOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
               .setTitle("Контакты")
               .setItems(array) { _, which ->
                  contactText.setText(viewModel?.getNameContact(which))
                  viewModel?.setContact(which)
                  viewModel?.getContactPhoneList()
                     ?.let { items1 = it.toArray(arrayOfNulls<String>(0)) }
               }
               .show()
         }
         addContactPhone.setEndIconOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
               .setTitle("Номера телефонов")
               .setItems(items1) { _, which ->
                  contactPhoneText.setText(viewModel?.getTextPhone(which))
                  viewModel?.setPhone(items1[which])
               }
               .show()
         }

         addDate.setEndIconOnClickListener {
            TimePickerDialogCreator.DateDialog(requireContext()) {
               viewModel?.setDateTime(it.timeInMillis)
               dateText.setText(viewModel?.getDate())
               true
            }.create()
         }
         addTime.setEndIconOnClickListener {
            TimePickerDialogCreator.TimeDialog(requireContext()) {
               viewModel?.setDateTime(it.timeInMillis)
               timeText.setText(viewModel?.getTime())
               true
            }.create()
         }
         toggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
               when (checkedId) {
                  R.id.buttonOneTime -> viewModel?.setPeriodic(false)
                  R.id.buttonPeriodic -> viewModel?.setPeriodic(true)
               }
            }
         }
         active.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel?.setActive(isChecked)
         }

         val list: MutableList<String> = emptyList<String>().toMutableList()
         var arrayAdapter: ArrayAdapter<String>
         contactText.addTextChangedListener { textEditable: Editable? ->

            viewModel?.getContactByPeaceName(textEditable.toString())
               ?.observe(viewLifecycleOwner) { contacts ->
                  list.clear()
                  contacts.forEach { list.add(it.name) }
                  arrayAdapter =
                     ArrayAdapter<String>(requireContext(), R.layout.dropdown_item, list.toList())
                  contactText.setAdapter(arrayAdapter)
                  contactText.threshold = 2

                  contactText.onItemClickListener =
                     AdapterView.OnItemClickListener { parent, _, position, id ->
                        val selectedItem = parent.getItemAtPosition(position).toString()
                        // Выводим выбранное слово
                        contacts.forEach { con ->
                           if (con.name == selectedItem) {
                              viewModel?.setContact(con.id)
                              viewModel?.getContactPhoneList()
                                 ?.let { items1 = it.toArray(arrayOfNulls<String>(0)) }
                           }
                        }
                     }

               }
         }

         viewModel?.apply {
            getActive()?.let { active.isChecked = it }
            getContactPhoneList()?.let { items1 = it.toArray(arrayOfNulls<String>(0)) }
            contactText.setText(getNameContact())
            contactPhoneText.setText(getTextPhone())
            templateText.setText(getTextTemplate())
            dateText.setText(getDate())
            timeText.setText(getTime())
            toggleGroup.check(
               if (getPeriodic()) {
                  R.id.buttonPeriodic
               } else {
                  R.id.buttonOneTime
               }
            )
         }
         this.saveCongratulation.setOnClickListener {
            viewModel?.save()
            createWorkerForNotification()
         }
      }
      return view
   }

   private val requestPermissionLauncher =
      registerForActivityResult(
         ActivityResultContracts.RequestPermission()
      ) { isGranted: Boolean ->
         if (isGranted) createWorkerForNotification()
      }

   private fun createWorkerForNotification() {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
         if (ContextCompat.checkSelfPermission(requireContext(), PERMISSION_POST_NOTIFICATIONS)
            == PackageManager.PERMISSION_GRANTED
         ) {
            createDateForRequest()
         } else {
            requestPermissionLauncher.launch(PERMISSION_POST_NOTIFICATIONS)
         }
      } else {
         createDateForRequest()
      }
   }

   private fun createDateForRequest() {
      viewModel?.apply {
         dbManager.createWorkRequest()
      }
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      ComponentManager.instance.appComponent.inject(this)
      this.viewModel = ViewModelProvider(this)[CongratulationViewModel::class.java]
      viewModel?.init(arguments?.getInt("congratulation_Id", -1))
   }

}