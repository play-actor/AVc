package com.hfad.AVc;

import android.util.Log;

public class Contact {
    private String id;
    private String name = "";
    private String phone = "";
    /**
     Все взаимодействия с контактами
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "id = " + this.id +
                ", Имя = " + this.name +
                ", Телефон = " + this.phone;
    }

    public static class Builder {
        private String id;
        private String name = "";
        private String phone = "";

        public Contact.Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Contact.Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Contact.Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Contact create() {
            Contact contact = new Contact();

            if (this.id != null) {
                contact.setId(this.id);
            }

            if (this.name != null) {
                contact.setName(this.name);
            }

            if (this.phone != null) {
                contact.setPhone(this.phone);
            }
            Log.i("Контакт заполнен", String.valueOf(contact));
            return contact;
        }
    }
}
