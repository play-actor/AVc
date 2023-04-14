package com.lastaurus.automatic_congratulations.dagger

import android.content.ActivityNotFoundException
import androidx.fragment.app.*
import com.github.terrakok.cicerone.*
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen

open class ExtSupportAppNavigator(
   val activity: FragmentActivity,
   val container: Int
) : Navigator {
   private val fragmentManager: FragmentManager = activity.supportFragmentManager
   private val localStackCopy = mutableListOf<String>()
   private val fragmentFactory: FragmentFactory = fragmentManager.fragmentFactory

   override fun applyCommands(commands: Array<out Command>) {

      fragmentManager.executePendingTransactions()

      //copy stack before apply commands
      copyStackToLocal()

      for (command in commands) {
         try {
            applyCommand(command)
         } catch (e: RuntimeException) {
            throw e
         }
      }
   }

   private fun copyStackToLocal() {
      localStackCopy.clear()
      for (i in 0 until fragmentManager.backStackEntryCount) {
         localStackCopy.add(fragmentManager.getBackStackEntryAt(i).name!!)
      }
   }

   /**
    * Perform transition described by the navigation command
    *
    * @param command the navigation command to apply
    */
   private fun applyCommand(command: Command) {
      when (command) {
         is Forward -> forward(command)
         is Replace -> replace(command)
         is BackTo -> backTo(command)
         is Back -> back()
      }
   }

   private fun checkAndStartActivity(screen: ActivityScreen) {
      // Check if we can start activity
      val activityIntent = screen.createIntent(activity)
      try {
         activity.startActivity(activityIntent, screen.startActivityOptions)
      } catch (e: ActivityNotFoundException) {
         throw  e
      }
   }

   private fun forward(command: Forward) {
      when (val screen = command.screen) {
         is ActivityScreen -> {
            checkAndStartActivity(screen)
         }
         is FragmentScreen -> {
            commitNewFragmentScreen(screen, true)
         }
      }
   }

   fun replace(command: Replace) {
      when (val screen = command.screen) {
         is ActivityScreen -> {
            checkAndStartActivity(screen)
            activity.finish()
         }
         is FragmentScreen -> {
            if (localStackCopy.isNotEmpty()) {
               fragmentManager.popBackStack()
               localStackCopy.removeAt(localStackCopy.lastIndex)
               commitNewFragmentScreen(screen, true)
            } else {
               commitNewFragmentScreen(screen, false)
            }
         }
      }
   }

   fun commitNewFragmentScreen(screen: FragmentScreen, addToBackStack: Boolean) {
      val fragment = screen.createFragment(fragmentFactory)
      val transaction = fragmentManager.beginTransaction()
      transaction.setReorderingAllowed(true)
      setupFragmentTransaction(
         screen,
         transaction,
         fragmentManager.findFragmentById(container),
         fragment
      )
      if (screen.clearContainer) {
         transaction.replace(container, fragment, screen.screenKey)
      } else {
         transaction.add(container, fragment, screen.screenKey)
      }
      if (addToBackStack) {
         transaction.addToBackStack(screen.screenKey)
         localStackCopy.add(screen.screenKey)
      }
      transaction.commit()
   }

   /**
    * Override this method to setup fragment transaction [FragmentTransaction].
    * For example: setCustomAnimations(...), addSharedElement(...) or setReorderingAllowed(...)
    *
    * @param fragmentTransaction fragment transaction
    * @param currentFragment     current fragment in container
    *                            (for [Replace] command it will be screen previous in new chain, NOT replaced screen)
    * @param nextFragment        next screen fragment
    */
   @Suppress("UNUSED_PARAMETER")
   private fun setupFragmentTransaction(
      screen: FragmentScreen,
      fragmentTransaction: FragmentTransaction,
      currentFragment: Fragment?,
      nextFragment: Fragment
   ) {
      // Do nothing by default
   }

   fun back() {
      if (localStackCopy.isNotEmpty()) {
         fragmentManager.popBackStack()
         localStackCopy.removeAt(localStackCopy.lastIndex)
      } else {
         activityBack()
      }
   }

   private fun activityBack() {
      activity.finish()
   }

   /**
    * Performs [BackTo] command transition
    */
   private fun backTo(command: BackTo) {
      if (command.screen == null) {
         backToRoot()
      } else {
         val screenKey = command.screen?.screenKey
         val index = localStackCopy.indexOfFirst { it == screenKey }
         if (index != -1) {
            val forRemove = localStackCopy.subList(index, localStackCopy.size)
            fragmentManager.popBackStack(forRemove.first().toString(), 0)
            forRemove.clear()
         } else {
            backToRoot()
         }
      }
   }

   private fun backToRoot() {
      localStackCopy.clear()
      fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
   }
}