## Halcyon Mobile Common Android Extensions

This repository is an aggregation of common Android extensions and related lint warnings we are using at Halcyon Mobile.

*Latest version:*![Latest release](https://img.shields.io/github/v/release/halcyonmobile/android-common-extensions)

## Ensure you have the HalcyonMobile GitHub Packages as a repository

```gradle
// top level build.gradle
//..
allprojects {
    repositories {
        // ...
        maven {
            url "https://maven.pkg.github.com/halcyonmobile/android-common-extensions"
            credentials {
                username = project.findProperty("GITHUB_USERNAME") ?: System.getenv("GITHUB_USERNAME")
                password = project.findProperty("GITHUB_TOKEN") ?: System.getenv("GITHUB_TOKEN")
            }
            // https://docs.github.com/en/github/authenticating-to-github/keeping-your-account-and-data-secure/creating-a-personal-access-token
        }
    }
}
// OR
// top level build.gradle.kts
//..
allprojects {
    repositories {
        // ...
        maven {
            url = uri("https://maven.pkg.github.com/halcyonmobile/android-common-extensions")
            credentials {
                username = extra.properties["GITHUB_USERNAME"] as String? ?: System.getenv("GITHUB_USERNAME")
                password = extra.properties["GITHUB_TOKEN"] as String? ?: System.getenv("GITHUB_TOKEN")
            }
            // https://docs.github.com/en/github/authenticating-to-github/keeping-your-account-and-data-secure/creating-a-personal-access-token
        }
    }
}
```

Note: you only need one maven declaration with "halcyonmobile/{specific}", every other package will be accessible.

### I need all of the sub modules, is there a way to include them all?

If you want to include all the sub-modules of this project use
```gradle
implementation "com.halcyonmobile.android.common.extensions:android.all:<latest-version>"
```

### View related extensions

Aggregation of common View extensions we use in our applications.

#### How do I set it up?

```gradle
implementation "com.halcyonmobile.android.common.extensions:view:<latest-version>"
```

#### How do I use it?

For now only contains reliable keyboard showing extension.

```kotlin
View.focusAndShowKeyboard()
```

### Safe Navigation
 
When a user clicks on two or more CTAs, at the same time, that invoke `navigate` the application will crash because the first NavigationEvent 
changes the currentDestination and as a consequence the second NavigationEvent won't be understood by the default NavController. It ultimately 
will result in a crash. Safe navigation guards against this by wrapping the default NavController and checking whether the currentDestinationId 
is valid before calling into NavController's `navigate`, it swallows the subsequent event otherwise.

In addition a lint warning is also included which will suggest to use `findSafeNavController` for navigate calls, hence avoiding the aforementioned crashes.

#### How do I set it up?

```gradle
implementation "com.halcyonmobile.android.common.extensions:safe.navigation:<latest-version>"
```

#### How do I use it?

```kotlin
Fragment.findSafeNavController().navigate(FooDirections.actionFooToBar())  
```

#### Notes
For any other action simply use the default NavController via `Fragment.findNavController()`

## License

    Copyright 2020 HalcyonMobile
       https://www.halcyonmobile.com
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       https://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
