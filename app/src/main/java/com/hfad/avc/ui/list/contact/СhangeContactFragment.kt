package com.hfad.avc.ui.list.contact

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Data
import com.hfad.avc.R
import com.hfad.avc.dagger.ComponentManager.Companion.instance
import com.hfad.avc.data.model.Contact
import com.hfad.avc.databinding.FragmentContactBinding
import com.hfad.avc.managers.DBManager
import com.hfad.avc.ui.list.PhoneListAdapter
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class СhangeContactFragment : MvpAppCompatFragment(), IСhangeContactViewModel {
   @Inject
   lateinit var dbManager: DBManager

   @InjectPresenter
   lateinit var presenter: СhangeContactPresenter

//   @Inject
//   lateinit var context: Context

   //   private val TAG = "AVc"
   var data: Data? = null

   //   var thisDateCon: Date? = null
//   var coordLayout: CoordinatorLayout? = null
//   var myTime = Calendar.getInstance().time
   private var binding: FragmentContactBinding? = null
//   private val PERMISSION_REQUEST_CODE = 2

   //   @SuppressLint("SimpleDateFormat")
//   var format = SimpleDateFormat()
//   private val id: String? = null
//   private val name: String? = null
//   private val phoneNumber: String? = null
//   private val dateCon: String? = null
//   private val getTextTemplate: String? = null
//   var dateAndTime = Calendar.getInstance()
   private var iconContact: ImageView? = null
   private var mActionBarToolbar: Toolbar? = null
   private var favoriteContact: MenuItem? = null
   private var recyclerView: RecyclerView? = null
   override fun onCreate(savedInstanceState: Bundle?) {
      instance.appComponent.inject(this)
      super.onCreate(savedInstanceState)
   }

   @ProvidePresenter
   fun ProvidePresenterContactPresenter(): СhangeContactPresenter {
      return СhangeContactPresenter(arguments)
   }

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?,
   ): View? {
      binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact, container, false)
      val view = binding?.root
      recyclerView = view?.findViewById(R.id.phonelist)
      iconContact = view?.findViewById(R.id.iconContact)
      mActionBarToolbar = view?.findViewById(R.id.toolbar)
      mActionBarToolbar?.inflateMenu(R.menu.menu_contact)
      mActionBarToolbar?.setNavigationOnClickListener { requireActivity().onBackPressed() }
      favoriteContact = mActionBarToolbar?.menu?.findItem(R.id.favoriteObject)
      iconContact?.let { presenter.setIconContact(it) }
      favoriteContact?.let { it ->
         presenter.setFavoriteContact(it)
         it.setOnMenuItemClickListener {
            presenter.setOnClickFavoriteContact()
            presenter.setFavoriteContact(it)
            presenter.updateContactDB()
            false
         }

      }
      return view
   }

   override fun setData(contact: Contact?) {
      binding?.contactDetail = contact
      contact?.getPhoneList().let {
         recyclerView?.adapter = it?.let { it1 -> PhoneListAdapter(it1) }
      }
      binding?.presenter = presenter
   } //   @Override

   //   public void setWorker(String id, String name, String phoneNumber, String dateCon, String getTextTemplate) {
   //      if (ContextCompat.checkSelfPermission(requireActivity(), PERMISSION_STRING)
   //            == PackageManager.PERMISSION_GRANTED) {
   //         data = new Data.Builder()
   //               .putString("Name", name)
   //               .putString("Phone", phoneNumber)
   //               .putString("TextTemplate", getTextTemplate)
   //               .build();
   //         try {
   //            format.applyPattern("dd.MM.yyyy, HH:mm");
   //            thisDateCon = format.parse(dateCon);
   //         } catch (ParseException e) {
   //            e.printStackTrace();
   //         }
   //         if (thisDateCon != null) {
   //            long seconds = ((thisDateCon.getTime() - myTime.getTime()) / (1000));
   //            if (seconds < 0) {
   //               seconds += 31536000;
   //            }
   //            if (this.binding.worked.isChecked()) {
   //               dbManager.createWorkRequest(data, seconds, id)
   //                     .subscribeOn(Schedulers.io())
   //                     .observeOn(AndroidSchedulers.mainThread())
   //                     .subscribe(
   //                           () -> Log.d(TAG, "Set Worker"),
   //                           throwable -> Log.e(TAG, "Worker error", throwable)
   //                     );
   //               coordLayout = requireActivity().findViewById(R.id.mainFragApp);
   //               Snackbar.make(coordLayout, "Сохранено", BaseTransientBottomBar.LENGTH_LONG).show();
   //            } else {
   //               dbManager.cancelWorkRequest(id)
   //                     .subscribeOn(Schedulers.io())
   //                     .observeOn(AndroidSchedulers.mainThread())
   //                     .subscribe(
   //                           () -> Log.d(TAG, "Set Worker"),
   //                           throwable -> Log.e(TAG, "Worker error", throwable)
   //                     );
   //            }
   //         }
   //      } else {
   //         this.name = name;
   //         this.id = id;
   //         this.phoneNumber = phoneNumber;
   //         this.dateCon = dateCon;
   //         this.getTextTemplate = getTextTemplate;
   //         ActivityCompat.requestPermissions(requireActivity(),
   //               new String[]{Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_CODE);
   //      }
   //   }
   //   @Override
   //   public void setWorker() {
   //      setWorker(this.id, this.name, this.phoneNumber, this.dateCon, this.getTextTemplate);
   //   }
   // отображаем диалоговое окно для выбора даты
   //   @Override
   //   public void setDateNew() {
   //      new DatePickerDialog(getActivity(), d,
   //            dateAndTime.get(java.util.Calendar.YEAR),
   //            dateAndTime.get(java.util.Calendar.MONTH),
   //            dateAndTime.get(java.util.Calendar.DAY_OF_MONTH))
   //            .show();
   //   }
   // отображаем диалоговое окно для выбора времени
   //   @Override
   //   public void setTimeNew() {
   //      new TimePickerDialog(getActivity(), t,
   //            dateAndTime.get(java.util.Calendar.HOUR_OF_DAY),
   //            dateAndTime.get(java.util.Calendar.MINUTE), true)
   //            .show();
   //   }
   //   private void setInitialDateTime() {
   //      DateUtils.formatDateTime(getActivity(),
   //            dateAndTime.getTimeInMillis(),
   //            DateUtils.FORMAT_SHOW_WEEKDAY | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
   //                  | DateUtils.FORMAT_SHOW_TIME
   //      );
   //      this.presenter.setDate(dateAndTime.getTimeInMillis());
   //
   //   }
   // установка обработчика выбора времени
   //   TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
   //      public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
   //         dateAndTime.set(java.util.Calendar.HOUR_OF_DAY, hourOfDay);
   //         dateAndTime.set(java.util.Calendar.MINUTE, minute);
   //         //setInitialDateTime();
   //      }
   //   };
   // установка обработчика выбора даты
   //   DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
   //      public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
   //         dateAndTime.set(java.util.Calendar.YEAR, year);
   //         dateAndTime.set(java.util.Calendar.MONTH, monthOfYear);
   //         dateAndTime.set(java.util.Calendar.DAY_OF_MONTH, dayOfMonth);
   //         setInitialDateTime();
   //      }
   //   };
   companion object {
      const val PERMISSION_STRING = Manifest.permission.SEND_SMS
      fun newInstance(congratulations: Int?): СhangeContactFragment {
         val contactFragment = СhangeContactFragment()
         val args = Bundle()
         args.putInt("congratulations", congratulations!!)
         contactFragment.arguments = args
         return contactFragment
      }
   }
}