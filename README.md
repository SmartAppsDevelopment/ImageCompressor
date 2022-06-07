# Description
Image Compressor App

# Architecture
<img src="/art/dotainfo_architecture.png" width="75%" height="75%" />



### Compose UI Tests
[UI Tests](/app/src/androidTest/java/com/codingwithmitch/dotainfo/ui)

# build.gradle files
There are 3 types of build.gradle files.
1. android application (app module)
1. android-library-build.gradle
    - Android module that contains ui components.
1. library-build.gradle
    - Pure java/kotlin library.

# API
https://docs.opendota.com/

### Hero Stats (GET)
https://api.opendota.com/api/heroStats

# Known issues
1. [HeroListFilter.kt](ui-heroList/src/main/java/com/codingwithmitch/ui_herolist/components/HeroListFilter.kt)
    - Changing the filter does not rebuild the dialog with the correct size.

# Credits
1. [Hristijan](https://twitter.com/funky_muse)
    - Thanks for the chat and great [sample](https://github.com/FunkyMuse/Aurora).

















