package com.example.spanish.domain

import android.util.Log
import com.example.spanish.di.SingltonObject
import com.example.spanish.di.TakeDataFromFireStore
import com.example.spanish.di.TakeRulse
import com.example.spanish.di.model.Requests
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Source
import javax.inject.Inject

class TakeDataFromFireStoreImpl @Inject constructor(private val takeRulse: TakeRulse):
    TakeDataFromFireStore {

    override suspend fun takePictcher(): Task<DocumentSnapshot> {
        val a = takeRulse.getRulse()
        val tabl = a.tabl
        val doc = a.doc
        return SingltonObject.db.collection(
            tabl
        ).document(
            doc
        ).get(Source.SERVER).addOnSuccessListener {
            Log.e(null, "take = ${it.data}")
          }
    }

    override suspend fun taleMp3(): Task<DocumentSnapshot> {
        val a = Requests.TESTMP3
        val tabl = a.tabl
        val doc = a.doc
        return SingltonObject.db.collection(
            tabl
        ).document(
            doc
        ).get(Source.SERVER).addOnSuccessListener {
            Log.e(null, "take = ${it.data}")
        }
    }

}