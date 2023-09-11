package com.lastaurus.automatic_congratulations.data

sealed class TypeCongratulation {
   class Birthday() : TypeCongratulation() {
      fun getId(): Int {
         return 1
      }

      fun getName(): String {
         return "День рождения"
      }
   }

   class Anniversary() : TypeCongratulation() {
      fun getId(): Int {
         return 2
      }

      fun getName(): String {
         return "Годовщина"
      }
   }

   class Other() : TypeCongratulation() {
      fun getId(): Int {
         return 3
      }

      fun getName(): String {
         return "Другое"
      }
   }

   class Special() : TypeCongratulation() {
      var nameCongratulation: String = ""
      fun getId(): Int {
         return 4
      }

      fun setName(name: String) {
         this.nameCongratulation = name
      }

      fun getName(): String {
         return nameCongratulation
      }
   }

   class Default() : TypeCongratulation() {
      fun getId(): Int {
         return 0
      }

      fun getName(): String {
         return "Без названия"
      }
   }
}
