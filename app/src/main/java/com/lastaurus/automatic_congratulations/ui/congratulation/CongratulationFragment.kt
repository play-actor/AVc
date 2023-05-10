package com.lastaurus.automatic_congratulations.ui.congratulation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.work.Data
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.lastaurus.automatic_congratulations.R
import com.lastaurus.automatic_congratulations.Util.TimePickerDialogCreator
import com.lastaurus.automatic_congratulations.dagger.ComponentManager
import com.lastaurus.automatic_congratulations.managers.DBManager
import javax.inject.Inject


class CongratulationFragment : Fragment() {

   private var toolbar: Toolbar? = null
   private var contactText: TextInputEditText? = null
   private var contact: TextInputLayout? = null

   private var dateText: TextInputEditText? = null
   private var date: TextInputLayout? = null

   private var templateText: TextInputEditText? = null
   private var template: TextInputLayout? = null

   private var timeText: TextInputEditText? = null
   private var time: TextInputLayout? = null

   private var active: SwitchMaterial? = null

   private var viewModel: CongratulationViewModel? = null
   private var saveCongratulation: View? = null
   val PERMISSION_STRING = Manifest.permission.SEND_SMS


   @Inject
   lateinit var dbManager: DBManager

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?,
   ): View {
      ComponentManager.instance.appComponent.inject(this)
      val view: View = inflater.inflate(
         R.layout.fragment_congratulation, container, false
      )

      with(view) {
         toolbar = this.findViewById(R.id.toolbar)

         contactText = this.findViewById(R.id.contactText)
         contact = this.findViewById(R.id.addContact)

         dateText = this.findViewById(R.id.dateText)
         date = this.findViewById(R.id.addDate)

         templateText = this.findViewById(R.id.templateText)
         template = this.findViewById(R.id.addTemplate)

         timeText = this.findViewById(R.id.timeText)
         time = this.findViewById(R.id.addTime)

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
            }
            .show()
      }

      date?.setEndIconOnClickListener {
         TimePickerDialogCreator.DateDialog(requireContext()) {
            viewModel?.setDate(it.timeInMillis)
            dateText?.setText(viewModel?.getDate())
            true
         }.create()
      }
      time?.setEndIconOnClickListener {
         TimePickerDialogCreator.TimeDialog(requireContext()) {
            viewModel?.setTime(it.timeInMillis)
            timeText?.setText(viewModel?.getTime())
            true
         }.create()
      }
      active?.setOnCheckedChangeListener { buttonView, isChecked ->
         viewModel?.setActive(isChecked)
      }
      viewModel?.getActive()?.let { active?.isChecked = it }
      contactText?.setText(viewModel?.getNameContact())
      templateText?.setText(viewModel?.getTextTemplate())
      dateText?.setText(viewModel?.getDate())
      timeText?.setText(viewModel?.getTime())
      this.saveCongratulation?.setOnClickListener {
         createWorkerForNotification()
         viewModel?.save()
      }
      return view
   }

   fun createWorkerForNotification() {
      if (ContextCompat.checkSelfPermission(requireContext(), PERMISSION_STRING)
         == PackageManager.PERMISSION_GRANTED
      ) {
         val data = Data.Builder()
            .putString("Name", viewModel?.getNameContact())
            .putString("Phone", viewModel?.getPhoneContact())
            .putString("TextTemplate", viewModel?.getTextTemplate())
            .build()
         viewModel?.getContact()?.getId()?.let { dbManager.createWorkRequest(data, it) }
         Log.d("gera", "createWorkerForNotification: ")
      }
   }

//   fun setWorker(name: String?, phoneNumber: String?, dateCon: String?, getTextTemplate: String?) {
//      this.interactorLoad = Applications.INSTANCE.getHelperInteractors().getContactInteractor()
//      if (ContextCompat.checkSelfPermission(requireContext(), PERMISSION_STRING)
//         == PackageManager.PERMISSION_GRANTED
//      ) {
//         val data = Data.Builder()
//            .putString("Name", name)
//            .putString("Phone", phoneNumber)
//            .putString("TextTemplate", getTextTemplate)
//            .build()
//         try {
//            format.applyPattern("dd.MM.yyyy, HH:mm")
//            thisDateCon = format.parse(dateCon)
//         } catch (e: ParseException) {
//            e.printStackTrace()
//         }
//         var seconds: Long = (thisDateCon.getTime() - myTime.getTime()) / 1000
//         if (seconds < 0) {
//            seconds += 31536000
//         }
//         if (this.binding.favorite.isChecked()) {
//            this.interactorLoad.myWork(data, seconds)
//               .subscribeOn(Schedulers.computation())
//               .observeOn(AndroidSchedulers.mainThread())
//               .subscribe(
//                  { Log.i(TAG, "Worker создан") }
//               ) { throwable ->
//                  Log.e(
//                     TAG,
//                     "Worker error",
//                     throwable as Throwable
//                  )
//               }
//            /*                    myWorkRequest = new PeriodicWorkRequest.Builder(SendWorker.class, 31536000, TimeUnit.MINUTES)
//                           .setInputData(data)
//                           .setInitialDelay(10, TimeUnit.SECONDS)
//                           .build();
//               WorkManager.getInstance(Applications.INSTANCE).enqueue(myWorkRequest);*/coordLayout =
//               activity!!.findViewById<CoordinatorLayout>(R.id.mainFragApp)
//            Snackbar.make(coordLayout, "Сохранено", BaseTransientBottomBar.LENGTH_LONG).show()
//         }
//      } else {
//         ActivityCompat.requestPermissions(
//            activity!!,
//            arrayOf<String>(Manifest.permission.SEND_SMS),
//            PERMISSION_REQUEST_CODE
//         )
//      }
//   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      this.viewModel = ViewModelProvider(this)[CongratulationViewModel::class.java]
      viewModel?.init(arguments?.getInt("congratulation_Id", -1))
      setHasOptionsMenu(true)
   }

}