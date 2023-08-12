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
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.lastaurus.automatic_congratulations.R
import com.lastaurus.automatic_congratulations.Util.TimePickerDialogCreator
import com.lastaurus.automatic_congratulations.bus.EventHandler
import com.lastaurus.automatic_congratulations.dagger.ComponentManager
import com.lastaurus.automatic_congratulations.data.database.DBManager
import javax.inject.Inject


class CongratulationFragment : Fragment() {

   private var toolbar: Toolbar? = null
   private var contactText: MaterialAutoCompleteTextView? = null
   private var contact: TextInputLayout? = null

   private var contactPhoneText: TextInputEditText? = null
   private var addContactPhone: TextInputLayout? = null

   private var dateText: TextInputEditText? = null
   private var date: TextInputLayout? = null

   private var templateText: TextInputEditText? = null
   private var template: TextInputLayout? = null

   private var timeText: TextInputEditText? = null
   private var time: TextInputLayout? = null

   private var toogle: MaterialButtonToggleGroup? = null
   private var active: MaterialSwitch? = null

   private var viewModel: CongratulationViewModel? = null
   private var saveCongratulation: View? = null

   @RequiresApi(Build.VERSION_CODES.TIRAMISU)
   val PERMISSION_POST_NOTIFICATIONS = Manifest.permission.POST_NOTIFICATIONS


   @Inject
   lateinit var dbManager: DBManager

   @Inject
   lateinit var eventHandler: EventHandler

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?,
   ): View {
      val view: View = inflater.inflate(
         R.layout.fragment_congratulation, container, false
      )

      with(view) {
         toolbar = this.findViewById(R.id.toolbar)

         contactText = this.findViewById(R.id.contactText)
         contact = this.findViewById(R.id.addContact)

         contactPhoneText = this.findViewById(R.id.contactPhoneText)
         addContactPhone = this.findViewById(R.id.addContactPhone)

         dateText = this.findViewById(R.id.dateText)
         date = this.findViewById(R.id.addDate)

         templateText = this.findViewById(R.id.templateText)
         template = this.findViewById(R.id.addTemplate)

         timeText = this.findViewById(R.id.timeText)
         time = this.findViewById(R.id.addTime)

         toogle = this.findViewById(R.id.toggleGroup)
         active = this.findViewById(R.id.active)

         saveCongratulation = this.findViewById(R.id.saveCongratulation)
      }

      toolbar?.let {
         it.setNavigationOnClickListener { requireActivity().onBackPressed() }
      }

      var array: Array<String> = emptyArray()
      viewModel?.getContactList()?.observe(viewLifecycleOwner) { contacts ->
         val items = ArrayList<String>()
         contacts.forEach {
            items.add(it.getName())
         }
         array = items.toArray(arrayOfNulls<String>(0))
      }

      var items1: Array<String> = emptyArray()

      var arrayT: Array<String> = emptyArray()
      viewModel?.getTemplateList()?.observe(viewLifecycleOwner) { templates ->
         val items = ArrayList<String>()
         templates.forEach {
            items.add(it.getTextTemplate())
         }
         arrayT = items.toArray(arrayOfNulls<String>(0))
      }
      template?.setEndIconOnClickListener {
         MaterialAlertDialogBuilder(requireContext())
            .setTitle("Шаблоны")
            .setItems(arrayT) { _, which ->
               templateText?.setText(viewModel?.getTextTemplate(which))
               viewModel?.setTemplate(which)
            }
            .show()
      }

      contact?.setEndIconOnClickListener {
         MaterialAlertDialogBuilder(requireContext())
            .setTitle("Контакты")
            .setItems(array) { _, which ->
               contactText?.setText(viewModel?.getNameContact(which))
               viewModel?.setContact(which)
               viewModel?.getContactPhoneList()
                  ?.let { items1 = it.toArray(arrayOfNulls<String>(0)) }
            }
            .show()
      }
      addContactPhone?.setEndIconOnClickListener {
         MaterialAlertDialogBuilder(requireContext())
            .setTitle("Номера телефонов")
            .setItems(items1) { _, which ->
               contactPhoneText?.setText(viewModel?.getTextPhone(which))
               viewModel?.setPhone(items1[which])
            }
            .show()
      }

      date?.setEndIconOnClickListener {
         TimePickerDialogCreator.DateDialog(requireContext()) {
            viewModel?.setDateTime(it.timeInMillis)
            dateText?.setText(viewModel?.getDate())
            true
         }.create()
      }
      time?.setEndIconOnClickListener {
         TimePickerDialogCreator.TimeDialog(requireContext()) {
            viewModel?.setDateTime(it.timeInMillis)
            timeText?.setText(viewModel?.getTime())
            true
         }.create()
      }
      toogle?.addOnButtonCheckedListener { group, checkedId, isChecked ->
         if (isChecked) {
            when (checkedId) {
               R.id.buttonOneTime -> viewModel?.setPeriodic(false)
               R.id.buttonPeriodic -> viewModel?.setPeriodic(true)
            }
         }
      }
      active?.setOnCheckedChangeListener { buttonView, isChecked ->
         viewModel?.setActive(isChecked)
      }

      val list: MutableList<String> = emptyList<String>().toMutableList()
      var arrayAdapter: ArrayAdapter<String>
      contactText?.addTextChangedListener { textEditable: Editable? ->

         viewModel?.getContactByPeaceName(textEditable.toString())
            ?.observe(viewLifecycleOwner) { contacts ->
               list.clear()
               contacts.forEach { list.add(it.getName()) }
               arrayAdapter =
                  ArrayAdapter<String>(requireContext(), R.layout.dropdown_item, list.toList())
               contactText?.setAdapter(arrayAdapter)
               contactText?.threshold = 2

               contactText?.onItemClickListener =
                  AdapterView.OnItemClickListener { parent, _, position, id ->
                     val selectedItem = parent.getItemAtPosition(position).toString()
                     // Выводим выбранное слово
                     contacts.forEach { con ->
                        if (con.getName() == selectedItem) {
                           viewModel?.setContact(con.getId())
                           viewModel?.getContactPhoneList()
                              ?.let { items1 = it.toArray(arrayOfNulls<String>(0)) }
                        }
                     }
                  }

            }
      }

      viewModel?.apply {
         getActive()?.let { active?.isChecked = it }
         getContactPhoneList()?.let { items1 = it.toArray(arrayOfNulls<String>(0)) }
         contactText?.setText(getNameContact())
         contactPhoneText?.setText(getTextPhone())
         templateText?.setText(getTextTemplate())
         dateText?.setText(getDate())
         timeText?.setText(getTime())
         toogle?.check(
            if (getPeriodic()) {
               R.id.buttonPeriodic
            } else {
               R.id.buttonOneTime
            }
         )
      }
      this.saveCongratulation?.setOnClickListener {
         viewModel?.save()
         createWorkerForNotification()
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
      setHasOptionsMenu(true)
   }

}