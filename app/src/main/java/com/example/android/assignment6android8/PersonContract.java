package com.example.android.assignment6android8;
import android.provider.BaseColumns;

/**
 * Created by THOMAS
 *
 * this is used with the database, kind of like strings.xml but for use with the database
 */
public final class PersonContract {

    public PersonContract() {
    }

    public static abstract class PersonEntry implements BaseColumns {
        public static final String TABLE_NAME = "person";
        public static final String COLUMN_NAME_FIRST = "firstName";
        public static final String COLUMN_NAME_LAST = "lastName";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_EMAIL = "email";
    }
}