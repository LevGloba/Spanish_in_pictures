package com.example.spanish.di

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot


interface TakeDataFromFireStore {
   suspend fun takePictcher(): Task<DocumentSnapshot>
   suspend fun taleMp3(): Task<DocumentSnapshot>
}