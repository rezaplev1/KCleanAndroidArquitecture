# KCleanAndroidArquitecture
This project tries to show a clean architecture with Kotlin, the scope of this project is to show how to make MVVM project that follows different patterns for separate the software in different layers which not be coupled each to other.

The scope of this project not is set an example of how to do any project, this project is a personal project for I try to sort myself knowledge of the platform and different libraries.

The project is made with different libraries of Android, Square and other third-party libraries.


The requirements are to create a project with pattern MVVM, using Jetpack libraries, Testable without coupled code and no boilerplate code.


In this project, I create an example using the marvel developer API to create an App that shows a list and detail that different comics are available for characters of Marvel.

How to compile the project.

For compile and executing project, open the project in Android Studio and add in file "local.properties" the next keys.

PRIVATE_API_KEY_MARVEL=

PUBLIC_API_KEY_MARVEL=

This keys can get in marvel developer 
https://developer.marvel.com/


### Interesting links of Android architecture.

Jose Alcérreca talking about the transformations Livedata and the problem of event.
https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150

Cristophe Beyls talking about how to reduce Parcelable boilerplate code in Kotlin.
https://medium.com/@BladeCoder/reducing-parcelable-boilerplate-code-using-kotlin-741c3124a49a

Android-CleanArchitecture-Kotlin
https://github.com/android10/Android-CleanArchitecture-Kotlin

Example how to create a pagination network + database
https://github.com/googlesamples/android-architecture-components/blob/master/PagingWithNetworkSample


