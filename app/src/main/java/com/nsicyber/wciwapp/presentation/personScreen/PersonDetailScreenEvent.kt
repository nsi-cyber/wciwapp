package com.nsicyber.wciwapp.presentation.personScreen


sealed class PersonDetailScreenEvent {
    class LoadPersonDetailScreen(val personId: Int) : PersonDetailScreenEvent()

}