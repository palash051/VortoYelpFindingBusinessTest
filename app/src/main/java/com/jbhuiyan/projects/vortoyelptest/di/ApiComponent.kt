package com.jbhuiyan.projects.vortoyelptest.di

import com.jbhuiyan.projects.vortoyelptest.businesslogic.YelpBusinessService
import com.jbhuiyan.projects.vortoyelptest.viewmodel.YelpBusinessViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: YelpBusinessService)
    fun inject(yelpBusinessViewModel:YelpBusinessViewModel)
}